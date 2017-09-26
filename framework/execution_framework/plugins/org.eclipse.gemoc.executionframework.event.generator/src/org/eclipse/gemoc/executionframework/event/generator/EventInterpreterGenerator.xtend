/*******************************************************************************
 * Copyright (c) 2017 Inria and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Inria - initial API and implementation
 *******************************************************************************/
package org.eclipse.gemoc.executionframework.event.generator

import fr.inria.diverse.k3.al.annotationprocessor.Aspect
import fr.inria.diverse.k3.al.annotationprocessor.Step
import java.lang.reflect.Method
import java.net.URL
import java.net.URLClassLoader
import java.util.ArrayList
import java.util.HashMap
import java.util.List
import java.util.Map
import java.util.Set
import org.eclipse.core.resources.IFolder
import org.eclipse.core.resources.IProject
import org.eclipse.core.runtime.Path
import org.eclipse.emf.common.util.URI
import org.eclipse.emf.ecore.EClass
import org.eclipse.emf.ecore.EClassifier
import org.eclipse.emf.ecore.EPackage
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl
import org.eclipse.gemoc.dsl.Dsl
import org.eclipse.gemoc.dsl.SimpleValue
import org.eclipse.jdt.core.IJavaProject
import org.eclipse.jdt.core.JavaCore
import org.eclipse.jdt.launching.JavaRuntime
import org.eclipse.pde.internal.core.project.PDEProject
import org.eclipse.xtext.naming.IQualifiedNameProvider
import org.eclipse.emf.mwe2.language.scoping.QualifiedNameProvider

class EventInterpreterGenerator {
	
	private val IQualifiedNameProvider fqnProvider = new QualifiedNameProvider
	
	private val EclipseProjectHelper projectHelper = new EclipseProjectHelper
	
	private val EcoreExtensions ecoreExt = new EcoreExtensions

	private Map<EClass, Method> inputEventToHandler = new HashMap

	private Map<EClass, Method> outputEventToEmitter = new HashMap

	private Map<Method, Method> eventMethodToCondition

	private IJavaProject targetProject

	private String projectName

	private String behavioralAPIClassName

	private String packageName

	private EPackage ePackage

	private String eventPackageString

	private String languagePackageString
	
	private List<Method> initializeMethods
	
	private Set<EClassifier> eventParameterTypes
	
	private Set<String> elementReferences
	
	private var IJavaProject javaProject
	
	private var ClassLoader loader
	
	private var String ecoreURI
	
	private def String getEventEcoreUri(Dsl dsl) {
		return '''platform:/resource/«dsl.name».event/model/«dsl.displayName.value»Event.ecore'''
	}

	public def void generateEventInterpreter(Dsl dsl, IProject project) {
		
		javaProject = JavaCore.create(project)
		loader = createClassLoader
		ecoreURI = (dsl.abstractSyntax.values.findFirst[it instanceof SimpleValue && name == "ecore"] as SimpleValue).values.head
		
		// clean before start
		targetProject = JavaCore.create(projectHelper.createEventInterpreterProject(dsl))
		eventMethodToCondition = newHashMap
		inputEventToHandler = newHashMap
		outputEventToEmitter = newHashMap
		initializeMethods = newArrayList
		eventParameterTypes = newHashSet
		elementReferences = newHashSet
		projectName = dsl.name + ".eventinterpreter"
		behavioralAPIClassName = '''«dsl.displayName.value»BehavioralAPI'''
		packageName = dsl.name + ".eventinterpreter"

		dsl.processLanguage
		
		val pluginXml = PDEProject::getPluginXml(targetProject.project)
		val changer = new PluginXmlChanger
		changer.load(pluginXml.location.toString)
		
		val extensionPoint = changer.addExtension("org.gemoc.gemoc_language_workbench.engine_addon")
		val element = changer.addChild(extensionPoint, "Addon")
		
		changer.addAttribute(element, "Class", packageName + "." + behavioralAPIClassName)
		changer.addAttribute(element, "Default", "false")
		changer.addAttribute(element, "id", packageName)
		changer.addAttribute(element, "Default", "false")
		changer.addAttribute(element, "Name", dsl.displayName.value + " Event Manager")
		changer.addAttribute(element, "ShortDescription", "Handles events for the " + dsl.displayName.value + " language")
		changer.addAttribute(element, "AddonGroupId", "Sequential.AddonGroup")
		
		changer.save(2)
	}
	
	private def void processLanguage(Dsl dsl) {
		val resSet = new ResourceSetImpl
		val res = resSet.getResource(URI.createURI(dsl.eventEcoreUri), true)
		ePackage = res.contents.head as EPackage
		val classNames = dsl.semantic.values
				.filter[it instanceof SimpleValue && name == "k3"]
				.map[(it as SimpleValue).values]
				.flatten.toSet
		classNames.map[fqn|
			loader.loadClass(fqn)
		].filter[isAspect].forEach[a|
			a.processAspect
		]
		if (!inputEventToHandler.empty || !outputEventToEmitter.empty) {
			val qNameSegments = new ArrayList(fqnProvider.getFullyQualifiedName(inputEventToHandler.keySet.filterNull.head).segments)
			qNameSegments.remove(qNameSegments.size - 1)
			eventPackageString = dsl.name + ".event." + qNameSegments.join(".")
			languagePackageString = dsl.name
			val sourceFolder = targetProject.allPackageFragmentRoots.findFirst [p|
				if (p.resource instanceof IFolder) {
					val folder = p.resource as IFolder
					val path = folder.fullPath.toString
					return path == ("/" + projectName + "/src")
				}
				return false
			]
			
			sourceFolder.createPackageFragment(packageName, true, null)
				.createCompilationUnit(behavioralAPIClassName + ".java", generateCode, true, null)
		}
	}

	private def boolean isAspect(Class<?> cls) {
		return cls.declaredAnnotations.exists[it instanceof Aspect]
	}

	private def boolean isEventHandler(Method m) {
		val stepAnnotation = m.getAnnotation(Step)
		if (stepAnnotation != null) {
			return stepAnnotation.eventHandler
		}
		return false
	}

	private def boolean isEventEmitter(Method m) {
		val stepAnnotation = m.getAnnotation(Step)
		if (stepAnnotation != null) {
			return stepAnnotation.eventEmitter
		}
		return false
	}
	
	private def String getConditionName(Method m) {
		val stepAnnotation = m.getAnnotation(Step)
		if (stepAnnotation != null) {
			return stepAnnotation.precondition
		}
		return ""
	}

	private def void processAspect(Class<?> aspect) {
		val aspectEventHandlers = aspect.methods.filter[eventHandler]
		
		aspectEventHandlers.forEach[e|
			val conditionName = e.conditionName
			if (conditionName != "") {
				val conditionMethods = aspect.methods.filter[m|
					m.name == conditionName
				].toList
				if (conditionMethods.size == 1) {
					eventMethodToCondition.put(e, conditionMethods.get(0))
				}
			}
		]
		
		aspectEventHandlers.forEach [ m |
			val eventName = '''«aspect.aspectedClass.simpleName.toFirstUpper»«m.name.toFirstUpper»Event'''
			val eventClass = ecoreExt.findClassifier(ePackage, eventName)
			if (eventClass != null) {
				inputEventToHandler.put(eventClass as EClass, m)
			}
		]
		
		val aspectEventEmitters = aspect.methods.filter[eventEmitter]
		
		aspectEventEmitters.forEach [m|
			val eventName = '''«aspect.aspectedClass.simpleName.toFirstUpper»«m.name.toFirstUpper»Event'''
			val eventClass = ecoreExt.findClassifier(ePackage, eventName)
			if (eventClass != null) {
				outputEventToEmitter.put(eventClass as EClass, m)
			}
		]
	}
	
	private def Class<?> aspectedClass(Class<?> aspect) {
		val annotation = aspect.declaredAnnotations.findFirst[it instanceof Aspect]
		if (annotation != null) {
			return (annotation as Aspect).className
		}
		return null
	}

	private def ClassLoader createClassLoader() {
		val classPathEntries = JavaRuntime.computeDefaultRuntimeClassPath(javaProject)
		val urlList = new ArrayList<URL>()
		for (var i = 0; i < classPathEntries.length; i++) {
			val entry = classPathEntries.get(i)
			val path = new Path(entry)
			val url = path.toFile().toURI().toURL()
			urlList.add(url)
		}
		val parentClassLoader = Thread.currentThread().getContextClassLoader()
		return new URLClassLoader(urlList, parentClassLoader)
	}

	private def String generateCode() {
		val body = generateBody
		
		'''
			package «packageName»;
			
			«generateImports»
			
			public class «behavioralAPIClassName» implements IBehavioralAPI {
			
				«body»
			}
		'''
	}

	private def String generateImports() {
		'''
			import java.util.HashSet;
			import java.util.Set;
			
			import org.eclipse.emf.ecore.EClass;
			import org.eclipse.gemoc.executionframework.event.interpreter.IBehavioralAPI;
			import org.eclipse.gemoc.executionframework.event.interpreter.EventInstance;
			«IF !outputEventToEmitter.empty»
			import org.eclipse.gemoc.trace.commons.model.trace.Step;
			import org.eclipse.gemoc.xdsmlframework.api.core.IExecutionEngine;
			«ENDIF»
			«FOR handler : inputEventToHandler.values.filterNull.map[declaringClass].toSet»
			import «handler.name»;
			«ENDFOR»
			«FOR elementReference : elementReferences»
			import «languagePackageString».«elementReference»;
			«ENDFOR»
			import «eventPackageString».«ePackage.name.toFirstUpper»Package;
			«IF !outputEventToEmitter.empty»
			import «eventPackageString».«ePackage.name.toFirstUpper»Factory;
			«ENDIF»
			«FOR event : inputEventToHandler.keySet.filterNull»
			import «eventPackageString».«event.name»;
			«ENDFOR»
			«FOR event : outputEventToEmitter.keySet.filterNull»
			import «eventPackageString».«event.name»;
			«ENDFOR»
			«FOR parameterType : eventParameterTypes»
			import «languagePackageString».«fqnProvider.getFullyQualifiedName(parameterType)»;
			«ENDFOR»
		'''
	}

	private def String generateBody() {
		'''
			«generateCanSendEventMethod»
			
			«generateEventClassesGetter»
			
			«generateDispatch»
			«generateEventHandlers»
			«generateEventConditions»
			
		'''
	}

	private def String generateCanSendEventMethod() {
		'''
			@Override
			public boolean canSendEvent(EventInstance event) {
				«FOR entry : inputEventToHandler.entrySet SEPARATOR " else"»
				«val eventClass = entry.key»
				«val eventHandler = entry.value»
				«val eventClassName = eventClass.name»
				if (event.getOriginalEvent() instanceof «eventClassName») {
					«val eventCondition = eventMethodToCondition.get(eventHandler)»
					«IF eventCondition == null»
					return true;
					«ELSE»
					return canSend«eventClassName»(event);
					«ENDIF»
				}
				«ENDFOR»
				return false;
			}
		'''
	}

	private def String generateDispatch() {
		'''
			@Override
			public void dispatchEvent(EventInstance event) {
				«FOR eventHandler : inputEventToHandler.entrySet SEPARATOR " else"»
					«val eventClassName = eventHandler.key.name»
					if (event.getOriginalEvent() instanceof «eventClassName») {
						handle«eventClassName»(event);
					}
				«ENDFOR»
			}
		'''
	}

	private def String generateEventHandlers() {
		'''
			«FOR entry : inputEventToHandler.entrySet»
				«val eventClass = entry.key»
				«val eventHandler = entry.value»
				
				«generateEventHandler(eventClass as EClass, eventHandler)»
			«ENDFOR»
		'''
	}

	private def String generateEventHandler(EClass eventClass, Method eventHandler) {
		val eventClassName = eventClass.name
		val eventHandlerName = eventHandler.name
		val eventHandlingClass = eventHandler.declaringClass.simpleName
		val eventParametersDeclaration = eventClass.eventHandlerParametersDeclaration
		val eventParameters = eventClass.eventHandlerParameters
		val eventCondition = eventMethodToCondition.get(eventHandler)
		return '''
			private void handle«eventClassName»(EventInstance _event) {
				«eventParametersDeclaration»
				«IF eventCondition != null»
				if («eventHandlingClass».«eventCondition.name»(«eventParameters»)) {
					«eventHandlingClass».«eventHandlerName»(«eventParameters»);
				}
				«ELSE»
				«eventHandlingClass».«eventHandlerName»(«eventParameters»);
				«ENDIF»
			}
		'''
	}

	private def String generateEventConditions() {
		'''
			«FOR entry : inputEventToHandler.entrySet»
				«val eventClass = entry.key»
				«val eventCondition = eventMethodToCondition.get(entry.value)»
				«IF eventCondition != null»
					
					«generateEventCondition(eventClass as EClass, eventCondition)»
				«ENDIF»
			«ENDFOR»
		'''
	}

	private def String generateEventCondition(EClass eventClass, Method eventCondition) {
		val eventClassName = eventClass.name
		val eventHandlerName = eventCondition.name
		val eventHandlingClass = eventCondition.declaringClass.simpleName
		val eventParametersDeclaration = eventClass.eventHandlerParametersDeclaration
		val eventParameters = eventClass.eventHandlerParameters
		return '''
			private boolean canSend«eventClassName»(EventInstance _event) {
				«eventParametersDeclaration»
				return «eventHandlingClass».«eventHandlerName»(«eventParameters»);
			}
		'''
	}

	private def EClassifier addType(EClassifier type) {
		eventParameterTypes.add(type)
		return type
	}
	
	private def String getEventHandlerParametersDeclaration(EClass eventClass) {
		'''
			«IF !eventClass.EAllStructuralFeatures.empty»
			«FOR i : 0..(eventClass.EAllStructuralFeatures.size - 1)»
			«val f = eventClass.EAllStructuralFeatures.get(i)»
			«val name = f.name»
			«val parameterType =
				if (f.EType instanceof EClass) {
					addType(f.EType).name
				} else {
					f.EType.instanceClass.simpleName
				}»
			final «parameterType» «name» = («parameterType») _event.getParameters().get(_event.getOriginalEvent().eClass().getEAllStructuralFeatures().get(«i»));
			«ENDFOR»
			«ENDIF»
		'''
	}

	private def String getEventHandlerParameters(EClass eventClass) {
		val parameters = new ArrayList
		eventClass.EAllStructuralFeatures.forEach[f|
			parameters += '''«f.name»'''
		]
		parameters.join(", ")
	}

	private def String generateEventClassesGetter() {
		'''
			@Override
			public Set<EClass> getEventClasses() {
				final Set<EClass> eventClasses = new HashSet<>();
				«FOR entry : inputEventToHandler.entrySet»
					«val eventClass = entry.key as EClass»
					eventClasses.add(«ePackage.name.toFirstUpper»Package.eINSTANCE.get«eventClass.name»());
				«ENDFOR»
				return eventClasses;
			}
		'''
	}
}
