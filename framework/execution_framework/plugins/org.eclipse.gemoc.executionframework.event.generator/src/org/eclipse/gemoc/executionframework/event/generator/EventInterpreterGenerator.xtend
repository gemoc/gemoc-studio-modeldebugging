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
import java.util.List
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
import org.eclipse.jdt.core.JavaCore
import org.eclipse.pde.internal.core.project.PDEProject
import org.eclipse.xtext.naming.IQualifiedNameProvider

class EventInterpreterGenerator {
	
	private val IQualifiedNameProvider fqnProvider = new QualifiedNameProvider
	
	private val EclipseProjectHelper projectHelper = new EclipseProjectHelper
	
	private val EcoreExtensions ecoreExt = new EcoreExtensions

	private Map<EClass, EOperation> inputEventToHandler = new HashMap

	private Map<EClass, EOperation> outputEventToEmitter = new HashMap

	private Map<EOperation, EOperation> eventMethodToCondition

	private IJavaProject targetProject

	private String projectName

	private String behavioralAPIClassName

	private String packageName

	private EPackage ePackage

	private String eventPackageString

	private String languagePackageString
	
	private List<EOperation> initializeMethods
	
	private Set<EClassifier> eventParameterTypes
	
	private Set<String> elementReferences
	
	private val String dslName
	
	private val OperationalSemanticsView operationalSemanticsView
	
	private val String pluginName
	
	private val String eventEcoreUri
	
	new(OperationalSemanticsView operationalSemanticsView, String pluginName) {
		this.operationalSemanticsView = operationalSemanticsView
		this.pluginName = pluginName
		dslName = operationalSemanticsView.executionMetamodel.name
		eventEcoreUri = '''platform:/resource/«this.pluginName».event/model/«dslName»Event.ecore'''
	}
	
	public def void generateEventInterpreter() {
		// clean before start
		targetProject = JavaCore.create(projectHelper.createEventInterpreterProject(pluginName))
		eventMethodToCondition = newHashMap
		inputEventToHandler = newHashMap
		outputEventToEmitter = newHashMap
		initializeMethods = newArrayList
		eventParameterTypes = newHashSet
		elementReferences = newHashSet
		projectName = '''«pluginName».eventinterpreter'''
		behavioralAPIClassName = '''«dslName»BehavioralAPI'''
		packageName = '''«pluginName».eventinterpreter'''

		processLanguage
		
		val pluginXml = PDEProject::getPluginXml(targetProject.project)
		val changer = new PluginXmlChanger
		changer.load(pluginXml.location.toString)
		
		val extensionPoint = changer.addExtension("org.gemoc.gemoc_language_workbench.engine_addon")
		val element = changer.addChild(extensionPoint, "Addon")
		
		changer.addAttribute(element, "Class", packageName + "." + behavioralAPIClassName)
		changer.addAttribute(element, "Default", "false")
		changer.addAttribute(element, "id", packageName)
		changer.addAttribute(element, "Default", "false")
		changer.addAttribute(element, "Name", dslName + " Event Manager")
		changer.addAttribute(element, "ShortDescription", "Handles events for the " + dslName + " language")
		changer.addAttribute(element, "AddonGroupId", "Sequential.AddonGroup")
		
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
			eventPackageString = pluginName + ".event." + qNameSegments.join(".")
			languagePackageString = pluginName
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
	
	private def void processEventHandler(EventHandler handler) {
		val containingEClass = handler.containingClass
		val op = handler.operation
		val condition = handler.condition
		
		if (condition != null) {
			eventMethodToCondition.put(op, condition)
		}
		
		val eventName = '''«containingEClass.name.toFirstUpper»«op.name.toFirstUpper»Event'''
		val eventClass = ecoreExt.findClassifier(ePackage, eventName)
		if (eventClass != null) {
			inputEventToHandler.put(eventClass as EClass, op)
		}
	}
	
	private def void processEventEmitter(EventEmitter emitter) {
		val containingEClass = emitter.containingClass
		val op = emitter.operation
		val eventName = '''«containingEClass.name.toFirstUpper»«op.name.toFirstUpper»Event'''
		val eventClass = ecoreExt.findClassifier(ePackage, eventName)
		if (eventClass != null) {
			outputEventToEmitter.put(eventClass as EClass, op)
		}
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

	private def String generateEventHandler(EClass eventClass, EOperation eventHandler) {
		val eventClassName = eventClass.name
		val eventHandlerName = eventHandler.name
		val eventHandlingClass = eventHandler.EContainingClass.name
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

	private def String generateEventCondition(EClass eventClass, EOperation eventCondition) {
		val eventClassName = eventClass.name
		val eventHandlerName = eventCondition.name
		val eventHandlingClass = eventCondition.EContainingClass.name
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
