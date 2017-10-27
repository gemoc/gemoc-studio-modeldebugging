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

import java.util.ArrayList
import java.util.HashMap
import java.util.HashSet
import java.util.Map
import java.util.Set
import opsemanticsview.EventEmitter
import opsemanticsview.EventHandler
import opsemanticsview.OperationalSemanticsView
import org.eclipse.core.resources.IFolder
import org.eclipse.emf.common.util.URI
import org.eclipse.emf.ecore.EClass
import org.eclipse.emf.ecore.EClassifier
import org.eclipse.emf.ecore.EOperation
import org.eclipse.emf.ecore.EPackage
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl
import org.eclipse.emf.mwe2.language.scoping.QualifiedNameProvider
import org.eclipse.jdt.core.IJavaProject
import org.eclipse.jdt.core.IMethod
import org.eclipse.jdt.core.JavaCore
import org.eclipse.pde.internal.core.project.PDEProject
import org.eclipse.xtext.naming.IQualifiedNameProvider

class EventInterpreterGenerator {
	
	private val IQualifiedNameProvider fqnProvider = new QualifiedNameProvider
	
	private val EclipseProjectHelper projectHelper = new EclipseProjectHelper
	
	private val EcoreExtensions ecoreExt = new EcoreExtensions

	private Map<EClass, EventHandler> inputEventToHandler = new HashMap

	private Map<EClass, EventEmitter> outputEventToEmitter = new HashMap

	private Map<EOperation, EOperation> eventMethodToCondition = new HashMap

	private IJavaProject targetProject

	private String behavioralAPIClassName

	private EPackage ePackage

	private String eventPackageString

	private String languagePackageString
	
	private Set<EClassifier> eventParameterTypes = new HashSet
	
	private Set<String> otherTypes = new HashSet
	
	private Set<String> elementReferences = new HashSet
	
	private val String dslName
	
	private val OperationalSemanticsView operationalSemanticsView
	
	private val String basePluginName
	
	private val String pluginName
	
	private val String eventPluginName
	
	private val String eventEcoreUri
	
	private val Map<EOperation, IMethod> operationToMethod
	
	new(OperationalSemanticsView operationalSemanticsView, Map<EOperation, IMethod> operationToMethod, String basePluginName) {
		this.operationalSemanticsView = operationalSemanticsView
		this.basePluginName = basePluginName
		this.operationToMethod = operationToMethod
		eventPluginName = basePluginName + ".event"
		pluginName = basePluginName + ".eventinterpreter"
		dslName = operationalSemanticsView.executionMetamodel.name
		behavioralAPIClassName = '''«dslName.toFirstUpper»BehavioralAPI'''
		eventEcoreUri = '''platform:/resource/«eventPluginName»/model/«dslName»Event.ecore'''
	}
	
	public def void generateEventInterpreter() {
		targetProject = JavaCore.create(projectHelper.createEventInterpreterProject(basePluginName))

		processLanguage
		
		val pluginXml = PDEProject::getPluginXml(targetProject.project)
		val changer = new PluginXmlChanger
		changer.load(pluginXml.location.toString)
		
		val extensionPoint = changer.addExtension("org.eclipse.gemoc.executionframework.event.api")
		val element = changer.addChild(extensionPoint, "org.eclipse.gemoc.event.api")
		
		changer.addAttribute(element, "class", pluginName + "." + behavioralAPIClassName)
		
		changer.save(2)
	}
	
	private def void processLanguage() {
		val resSet = new ResourceSetImpl
		val res = resSet.getResource(URI.createURI(eventEcoreUri), true)
		ePackage = res.contents.head as EPackage
		operationalSemanticsView.rules
				.forEach[r|
					if (r instanceof EventHandler) {
						r.processEventHandler
					}
					if (r instanceof EventEmitter) {
						r.processEventEmitter
					}
				]
		if (!inputEventToHandler.empty || !outputEventToEmitter.empty) {
			val qNameSegments = new ArrayList(fqnProvider.getFullyQualifiedName(inputEventToHandler.keySet.filterNull.head).segments)
			qNameSegments.remove(qNameSegments.size - 1)
			eventPackageString = eventPluginName + "." + qNameSegments.join(".")
			languagePackageString = basePluginName
			val sourceFolder = targetProject.allPackageFragmentRoots.findFirst [p|
				if (p.resource instanceof IFolder) {
					val folder = p.resource as IFolder
					val path = folder.fullPath.toString
					return path == ("/" + pluginName + "/src")
				}
				return false
			]
			
			sourceFolder.createPackageFragment(pluginName, true, null)
				.createCompilationUnit(behavioralAPIClassName + ".java", generateCode, true, null)
		}
	}
	
	private def void processEventHandler(EventHandler handler) {
		val eventName = '''«handler.containingClass.name.toFirstUpper»«handler.operation.name.toFirstUpper»Event'''
		val eventClass = ecoreExt.findClassifier(ePackage, eventName)
		if (eventClass !== null) {
			if (handler.condition !== null) {
				eventMethodToCondition.put(handler.operation, handler.condition)
			}
			inputEventToHandler.put(eventClass as EClass, handler)
		}
	}
	
	private def void processEventEmitter(EventEmitter emitter) {
		val eventName = '''«emitter.containingClass.name.toFirstUpper»«emitter.operation.name.toFirstUpper»Event'''
		val eventClass = ecoreExt.findClassifier(ePackage, eventName)
		if (eventClass !== null) {
			outputEventToEmitter.put(eventClass as EClass, emitter)
		}
	}

	private def String generateCode() {
		val body = generateBody
		'''
			package «pluginName»;
			
			«generateImports»
			
			public class «behavioralAPIClassName» implements IBehavioralAPI {
			
				«body»
			}
		'''
	}

	private def String generateImports() {
		'''
			import java.util.HashSet;
			import java.util.List;
			import java.util.Set;
			
			import org.eclipse.emf.ecore.EClass;
			import org.eclipse.emf.ecore.EObject;
			import org.eclipse.emf.ecore.EOperation;
			import org.eclipse.emf.ecore.EPackage;
			import org.eclipse.gemoc.executionframework.event.manager.IBehavioralAPI;
			import org.eclipse.gemoc.executionframework.event.model.event.Event;
			«IF !outputEventToEmitter.empty»
			import org.eclipse.gemoc.trace.commons.model.trace.Step;
			import org.eclipse.gemoc.xdsmlframework.api.core.IExecutionEngine;
			«ENDIF»
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
			import «languagePackageString».«operationalSemanticsView.executionMetamodel.name».«operationalSemanticsView.executionMetamodel.name.toFirstUpper»Package;
			«FOR otherType : otherTypes»
			import «otherType»;
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
			
			«generateGetOutputEvent»
			«generateEventEmitters»
			
			«generateCanHandleMethod»
		'''
	}
	
	private def generateCanHandleMethod() {
		'''
			@Override
			public boolean canHandle(EPackage pkg) {
				return pkg == «operationalSemanticsView.executionMetamodel.name.toFirstUpper»Package.eINSTANCE;
			}
			
		'''
	}

	private def String generateCanSendEventMethod() {
		'''
			@Override
			public boolean canSendEvent(Event _event) {
				«FOR entry : inputEventToHandler.entrySet SEPARATOR " else"»
				«val eventClass = entry.key»
				«val eventHandler = entry.value»
				«val eventClassName = eventClass.name»
				if (_event instanceof «eventClassName») {
					«val eventCondition = eventHandler.condition»
					«IF eventCondition === null»
					return true;
					«ELSE»
					return canSend«eventClassName»((«eventClassName») _event);
					«ENDIF»
				}
				«ENDFOR»
				return false;
			}
		'''
	}

	private def String generateEventConditions() {
		'''
			«FOR entry : inputEventToHandler.entrySet»
				«val eventClass = entry.key»
				«val eventCondition = entry.value.condition»
				«IF eventCondition !== null»
					
					«generateEventCondition(eventClass as EClass, entry.value)»
				«ENDIF»
			«ENDFOR»
		'''
	}

	// FIXME wrong eventConditionClass
	private def String generateEventCondition(EClass eventClass, EventHandler handler) {
		val method = operationToMethod.get(handler.condition)
		val eventClassName = eventClass.name
		val eventConditionName = handler.condition.name
		val eventConditionClass = method.declaringTypeName
		val eventParametersDeclaration = eventClass.eventHandlerParametersDeclaration
		val eventParameters = eventClass.eventHandlerParameters
		'''
			private boolean canSend«eventClassName»(«eventClassName» _event) {
				«eventParametersDeclaration»
				return «eventConditionClass».«eventConditionName»(«eventParameters»);
			}
		'''
	}

	private def String generateDispatch() {
		'''
			@Override
			public void dispatchEvent(Event _event) {
				«FOR eventHandler : inputEventToHandler.entrySet SEPARATOR " else"»
					«val eventClassName = eventHandler.key.name»
					if (_event instanceof «eventClassName») {
						handle«eventClassName»((«eventClassName») _event);
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

	private def String generateEventHandler(EClass eventClass, EventHandler eventHandler) {
		val method = operationToMethod.get(eventHandler.operation)
		val eventClassName = eventClass.name
		val eventHandlerOperation = eventHandler.operation
		val eventHandlerName = eventHandlerOperation.name
		val eventHandlingClass = method.declaringTypeName
		val eventParametersDeclaration = eventClass.eventHandlerParametersDeclaration
		val eventParameters = eventClass.eventHandlerParameters
		val eventCondition = eventHandler.condition
		'''
			private void handle«eventClassName»(«eventClassName» _event) {
				«eventParametersDeclaration»
				«IF eventCondition !== null»
				if («eventHandlingClass».«eventCondition.name»(«eventParameters»)) {
					«eventHandlingClass».«eventHandlerName»(«eventParameters»);
				}
				«ELSE»
				«eventHandlingClass».«eventHandlerName»(«eventParameters»);
				«ENDIF»
			}
		'''
	}
	
	private def generateGetOutputEvent() {
		'''
			@Override
			public Event getOutputEvent(EOperation operation, EObject caller, List<Object> parameters) {
				«FOR entry : outputEventToEmitter.entrySet SEPARATOR " else"»
					«val eventClassName = entry.key.name»
					«val eventEmitter = entry.value»
					«val op = eventEmitter.operation»
					if (operation.getName().equals("«op.name»") && caller instanceof «eventEmitter.containingClass.name») {
«««						return get«eventClassName»(«generateEventEmitterParameters(eventEmitter)»);
					}
				«ENDFOR»
				return null;
			}
		'''
	}
	
	private def generateEventEmitterParameters(EventEmitter e) {
		val caller = '''(«e.containingClass.name») caller, '''
		val parameters = e.operation.EParameters.map[EType.instanceClass.simpleName]
		'''«caller»«FOR i : 0..(parameters.size - 1) SEPARATOR ", "»(«parameters.get(i)») parameters.get(«i»)«ENDFOR»'''
	}
	
	private def generateEventEmitters() {
		'''
			«FOR entry : outputEventToEmitter.entrySet»
				«val eventClass = entry.key»
				«val eventEmitter = entry.value»
				
				«generateEventEmitter(eventClass as EClass, eventEmitter)»
			«ENDFOR»
		'''
		
	}
	
	private def generateEventEmitter(EClass eventClass, EventEmitter emitter) {
		'''
			private Event get«eventClass.name»(«generateEventEmitterParametersDeclaration(emitter)») {
				final «eventClass.name» _event = «ePackage.name.toFirstUpper»Factory.eINSTANCE.create«eventClass.name»();
				«FOR i : 0..eventClass.EAllStructuralFeatures.size() - 1»
					«val f = eventClass.EAllStructuralFeatures.get(i)»
					«val name = f.name»
					«IF i == 0»
					_event.set«name.toFirstUpper»(caller);
					«ELSE»
					_event.set«name.toFirstUpper»(«name»);
					«ENDIF»
				«ENDFOR»
				return _event;
			}
		'''
	}
	
	private def generateEventEmitterParametersDeclaration(EventEmitter e) {
		val caller = '''«e.containingClass.name» caller, '''
		val parameterTypes = e.operation.EParameters.map[EType.instanceClass.simpleName]
		val parameterNames = e.operation.EParameters.map[name]
		'''«caller»«FOR i : 0..(parameterTypes.size - 1) SEPARATOR ", "»«parameterTypes.get(i)» «parameterNames.get(i)»«ENDFOR»'''
	}
	
	private def String getDeclaringTypeName(IMethod method) {
		otherTypes.add(method.declaringType.fullyQualifiedName)
		return method.declaringType.elementName
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
			final «parameterType» «name» = _event.get«name.toFirstUpper»();
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
