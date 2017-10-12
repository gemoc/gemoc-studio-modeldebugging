package org.eclipse.gemoc.executionframework.event.generator

import fr.inria.diverse.k3.al.annotationprocessor.Aspect
import fr.inria.diverse.k3.al.annotationprocessor.Step
import java.io.IOException
import java.lang.reflect.Method
import java.net.URL
import java.net.URLClassLoader
import java.util.ArrayList
import java.util.HashMap
import java.util.HashSet
import java.util.List
import java.util.Map
import java.util.Set
import org.eclipse.core.resources.IProject
import org.eclipse.core.runtime.Path
import org.eclipse.emf.codegen.ecore.genmodel.GenJDKLevel
import org.eclipse.emf.codegen.ecore.genmodel.GenModel
import org.eclipse.emf.codegen.ecore.genmodel.GenModelFactory
import org.eclipse.emf.codegen.ecore.genmodel.GenPackage
import org.eclipse.emf.codegen.ecore.genmodel.generator.GenBaseGeneratorAdapter
import org.eclipse.emf.codegen.ecore.genmodel.util.GenModelUtil
import org.eclipse.emf.common.util.Diagnostic
import org.eclipse.emf.common.util.Monitor
import org.eclipse.emf.common.util.URI
import org.eclipse.emf.common.util.UniqueEList
import org.eclipse.emf.ecore.EClass
import org.eclipse.emf.ecore.EClassifier
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EOperation
import org.eclipse.emf.ecore.EPackage
import org.eclipse.emf.ecore.EcoreFactory
import org.eclipse.emf.ecore.EcorePackage
import org.eclipse.emf.ecore.plugin.EcorePlugin
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.emf.ecore.resource.ResourceSet
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl
import org.eclipse.gemoc.dsl.Dsl
import org.eclipse.gemoc.dsl.SimpleValue
import org.eclipse.gemoc.executionframework.event.model.event.EventPackage
import org.eclipse.jdt.core.IJavaProject
import org.eclipse.jdt.core.JavaCore
import org.eclipse.jdt.launching.JavaRuntime

class EventMetamodelGenerator {
	
	private val EclipseProjectHelper projectHelper = new EclipseProjectHelper
	
	private var Set<EPackage> basePkgs
	
	private var EPackage rootPackage

	private var EClass eventSpecificClass
	
	private var EPackage eventPkg
	
	private var Set<GenPackage> refGenPackages
	
	private var String ecoreURI
	
	private var String genmodelURI
	
	private var Resource abstractSyntax
	
	private Set<GenModel> fixedGenModels = new HashSet
	
	private var Map<EClassifier, EClass> rtToSuperEvent
	
	private var IJavaProject javaProject
	
	private var ClassLoader loader
	
	private def String getEventGenerationPath(Dsl dsl) {
		return '''../«dsl.name».event/src/'''
	}
	
	private def String getEventEcoreUri(Dsl dsl) {
		return '''platform:/resource/«dsl.name».event/model/«dsl.displayName.value»Event.ecore'''
	}

	private def String getEventGenmodelUri(Dsl dsl) {
		return '''platform:/resource/«dsl.name».event/model/«dsl.displayName.value»Event.genmodel'''
	}
	
	private def EPackage getRootPackage(EPackage pkg) {
		var EObject result = pkg
		while (result.eContainer instanceof EPackage) {
			result = result.eContainer
		}
		return result as EPackage
	}
	
	def void generateBehavioralInterface(Dsl dsl, IProject project) {
		javaProject = JavaCore.create(project)
		loader = createClassLoader
		Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put("xmi", new XMIResourceFactoryImpl());
		val resourceSet = new ResourceSetImpl
		ecoreURI = (dsl.abstractSyntax.values.findFirst[it instanceof SimpleValue && name == "ecore"] as SimpleValue).values.head
		genmodelURI = (dsl.abstractSyntax.values.findFirst[it instanceof SimpleValue && name == "genmodel"] as SimpleValue).values.head
		abstractSyntax = resourceSet.getResource(URI.createURI(ecoreURI, true), true)
		basePkgs = abstractSyntax.allContents.filter(EPackage).toSet
		rootPackage = basePkgs.head.rootPackage;
		rtToSuperEvent = new HashMap
		projectHelper.createEMFEventProject(dsl)
		dsl.createEcore
		dsl.createGenmodelAndGenerateCode
	}
	
	private def void createEcore(Dsl dsl) {
		val allEOperations = new HashSet
		allEOperations += abstractSyntax.allContents.filter(EOperation).toSet
		
		eventPkg = EcoreFactory.eINSTANCE.createEPackage => [
			name = (dsl.displayName.value + "event").toLowerCase
			nsPrefix = name
			nsURI = rootPackage.nsURI+"event/"
		]
		
		eventSpecificClass = EcoreFactory.eINSTANCE.createEClass => [c|
			c.name = dsl.displayName.value + "DSLEvent"
			c.abstract = true
			c.ESuperTypes += EventPackage.Literals.EVENT
		]
		
		eventPkg.EClassifiers += eventSpecificClass
		
		val classNames = dsl.semantic.values
				.filter[it instanceof SimpleValue && name == "k3"]
				.map[(it as SimpleValue).values]
				.flatten.toSet
		classNames.map[fqn|
			loader.loadClass(fqn)
		].filter[isAspect].forEach[a|
			a.methods.filter[isStep].forEach[op|
				if (allEOperations.exists[it.name == op.name && it.EContainingClass.name == a.aspectedClass.simpleName]) {
					if (op.eventHandler) {
						val eventName = a.aspectedClass.simpleName.toFirstUpper + op.name.toFirstUpper + "Event"
						op.generateInputEvent(eventName)
					} else if (op.eventEmitter) {
						val eventName = a.aspectedClass.simpleName.toFirstUpper + op.name.toFirstUpper + "Event"
						op.generateOutputEvent(eventName)
					}
				}
			]
		]
		
		val resSet = new ResourceSetImpl
		val usedGenpkgRes = resSet.getResource(URI::createURI(genmodelURI), true)
		refGenPackages = (usedGenpkgRes.contents.head as GenModel).genPackages.toSet
		
		val res = resSet.createResource(URI.createPlatformResourceURI(dsl.name+".event/model/"+dsl.displayName.value+"Event.ecore", true))
		res.contents += eventPkg
		res.save(null)
	}
	
	private def void createGenmodelAndGenerateCode(Dsl dsl) {
		val resSet = new ResourceSetImpl
		val pkgRes = resSet.getResource(URI::createURI(dsl.eventEcoreUri), true)
		val pkgs = pkgRes.contents.map[it as EPackage]
		val fileExtension = refGenPackages.findFirst[!fileExtension.nullOrEmpty]?.fileExtension

		val genmodel = GenModelFactory.eINSTANCE.createGenModel => [
			complianceLevel = GenJDKLevel.JDK70_LITERAL
			modelDirectory = dsl.eventGenerationPath
				.replaceFirst("platform:/resource", "").replaceFirst("..", "")
			foreignModel += ecoreURI
			modelName = dsl.displayName.value + "event"
			modelPluginID = dsl.name + ".event"
			initialize(pkgs)
			genPackages.forEach[gp|
				gp.basePackage = dsl.name + ".event"
				if (!fileExtension.nullOrEmpty) {
					gp.fileExtensions = fileExtension + "e"
				} else {
					gp.fileExtensions = dsl.displayName.value.toLowerCase.toString + "e"
				}
			]
		]
		
		val res = resSet.createResource(URI::createURI(dsl.eventGenmodelUri))
		res.contents += genmodel
		
		fixedGenModels.clear
		genmodel.fixUsedGenPackages(resSet)
		
		try {
			res.save(null)
		} catch (IOException e) {
//			log.error("Error while serializing new genmodel", e)
		}
		
		pkgRes.unload
		pkgRes.load(null)
		
		genmodel.generateCode
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
	
	def void generateCode(GenModel genModel) {
		genModel.reconcile
		genModel.canGenerate = true
		genModel.validateModel = true

		val generator = GenModelUtil::createGenerator(genModel)
		generator.generate(
			genModel,
			GenBaseGeneratorAdapter::MODEL_PROJECT_TYPE,
			// NullMonitor
			new Monitor() {
				override beginTask(String name, int totalWork) {}
				override clearBlocked() {}
				override done() {}
				override internalWorked(double work) {}
				override isCanceled() { return false }
				override setBlocked(Diagnostic reason) {}
				override setCanceled(boolean value) {}
				override setTaskName(String name) {}
				override subTask(String name) {}
				override worked(int work) {}
			}
		)
	}
	
	private def boolean isAspect(Class<?> cls) {
		return cls.declaredAnnotations.exists[it instanceof Aspect]
	}
	
	private def boolean isStep(Method m) {
		return m.declaredAnnotations.exists[it instanceof Step]
	}
	
	private def boolean isEventHandler(Method m) {
		val annotation = m.declaredAnnotations.findFirst[it instanceof Step]
		if (annotation != null) {
			return (annotation as Step).eventHandler
		}
		return false
	}
	
	private def boolean isEventEmitter(Method m) {
		val annotation = m.declaredAnnotations.findFirst[it instanceof Step]
		if (annotation != null) {
			return (annotation as Step).eventEmitter
		}
		return false
	}

	private def Class<?> aspectedClass(Class<?> aspect) {
		val annotation = aspect.declaredAnnotations.findFirst[it instanceof Aspect]
		if (annotation != null) {
			return (annotation as Aspect).className
		}
		return null
	}
	
	def private void generateInputEvent(Method op, String eventName) {
		val params = op.parameters
		eventPkg.EClassifiers += EcoreFactory.eINSTANCE.createEClass => [c|
			c.name = eventName
			
			params.head => [opParam|
				val parameterTypeName = opParam.type.simpleName
				val parameterClassifier = basePkgs.findFirst[getEClassifier(parameterTypeName) != null]?.getEClassifier(parameterTypeName)
				if (parameterClassifier != null) {
					c.ESuperTypes += parameterClassifier.superEventClass
				}
			]
			
			params.tail.forEach[opParam|
				val parameterTypeName = opParam.type.simpleName
				if (parameterTypeName == "String" || parameterTypeName == "Integer" || parameterTypeName == "Boolean") {
					c.EStructuralFeatures += EcoreFactory.eINSTANCE.createEAttribute => [
						name = opParam.name.toFirstLower
						lowerBound = 0
						upperBound = 1
						EType = switch (parameterTypeName) {
							case "String": EcorePackage.Literals.ESTRING
							case "Integer": EcorePackage.Literals.EINT
							case "Boolean": EcorePackage.Literals.EBOOLEAN
						}
					]
				} else {
					val parameterClassifier = basePkgs.findFirst[getEClassifier(parameterTypeName) != null]?.getEClassifier(parameterTypeName)
					if (parameterClassifier != null) {
						c.EStructuralFeatures += EcoreFactory.eINSTANCE.createEReference => [
							EType = parameterClassifier
							upperBound = 1
							lowerBound = 1
							containment = false
							name = opParam.name.toFirstLower
						]
					}
				}
			]
		]
	}
	
	def private void generateOutputEvent(Method op, String eventName) {
		val params = op.parameters
		eventPkg.EClassifiers += EcoreFactory.eINSTANCE.createEClass => [c|
			c.name = eventName
			// Creating the generic super type of the property and binding it
			c.ESuperTypes += eventSpecificClass
			
			params.head => [opParam|
				val parameterTypeName = opParam.type.simpleName
				val parameterClassifier = basePkgs.findFirst[getEClassifier(parameterTypeName) != null]?.getEClassifier(parameterTypeName)
				if (parameterClassifier != null) {
					c.EStructuralFeatures += EcoreFactory.eINSTANCE.createEReference => [
						name = "source"
						upperBound = 1
						lowerBound = 1
						EType = parameterClassifier
						containment = false
					]
				}
			]
			
			params.tail.forEach[opParam|
				val parameterTypeName = opParam.type.simpleName
				if (parameterTypeName == "String" || parameterTypeName == "Integer" || parameterTypeName == "Boolean") {
					c.EStructuralFeatures += EcoreFactory.eINSTANCE.createEAttribute => [
						name = opParam.name.toFirstLower
						lowerBound = 0
						upperBound = 1
						EType = switch (parameterTypeName) {
							case "String": EcorePackage.Literals.ESTRING
							case "Integer": EcorePackage.Literals.EINT
							case "Boolean": EcorePackage.Literals.EBOOLEAN
						}
					]
				} else {
					val parameterClassifier = basePkgs.findFirst[getEClassifier(parameterTypeName) != null]?.getEClassifier(parameterTypeName)
					if (parameterClassifier != null) {
						c.EStructuralFeatures += EcoreFactory.eINSTANCE.createEReference => [
							name = opParam.name.toFirstLower
							upperBound = 1
							lowerBound = 1
							EType = parameterClassifier
							containment = false
						]
					}
				}
			]
		]
	}
	
	def private EClass getSuperEventClass(EClassifier c) {
		rtToSuperEvent.computeIfAbsent(c, [cls|
			val superEvent = EcoreFactory.eINSTANCE.createEClass => [
				name = cls.name + "Event"
				abstract = true
				EGenericSuperTypes += EcoreFactory.eINSTANCE.createEGenericType => [
					EClassifier = eventSpecificClass
				]
				EStructuralFeatures += EcoreFactory.eINSTANCE.createEReference => [
					name = cls.name.toFirstLower
					upperBound = 1
					lowerBound = 1
					EType = cls
					containment = false
				]
			]
			eventPkg.EClassifiers += superEvent
			return superEvent
		])
	}
	

	/**
	 * Tries to fix the "usedGenPackages" collection of a genmodel (and recursively of all genmodels it references)
	 * 1) remove all usedGenPackages that have a null genModel (for a mysterious reason...)
	 * 2) use the magical method 'computeMissingGenPackages' to find missing packages, and add them to usedGenPackages
	 * 3) as a bonus, store all referenced gen packages in 'referencedGenPackages' for later use
	 */
	private def void fixUsedGenPackages(GenModel genModel, ResourceSet resourceSet) {
		if (!fixedGenModels.contains(genModel)) {
			fixedGenModels.add(genModel)
			genModel.usedGenPackages.removeAll(genModel.usedGenPackages.immutableCopy.filter[p|p.genModel == null])
			val List<GenPackage> missingGenPackages = genModel.computeMissingGenPackages(resourceSet)
			for (genPackage : missingGenPackages) {
				genPackage.genModel.fixUsedGenPackages(resourceSet)
			}
			genModel.usedGenPackages += missingGenPackages
		}
	}
	
	private def List<GenPackage> computeMissingGenPackages(GenModel genModel, ResourceSet resourceSet) {
		val List<GenPackage> missingGenPackages = new UniqueEList<GenPackage>()
		val Map<String, URI> genModelLocationMapTargetEnvironment = EcorePlugin.getEPackageNsURIToGenModelLocationMap(true)
		val Map<String, URI> genModelLocationMapEnvironment = EcorePlugin.getEPackageNsURIToGenModelLocationMap(false)
		for (EPackage ePackage : genModel.getMissingPackages()) {
			if (ePackage != null) { // happens for activities
				var URI missingGenModelURI = genModelLocationMapEnvironment.get(ePackage.getNsURI())
				if (missingGenModelURI == null) {
					missingGenModelURI = genModelLocationMapTargetEnvironment.get(ePackage.getNsURI())
				}
				if (missingGenModelURI == null) {
					throw new RuntimeException(
						"Unable to load generator model of required package \'" + ePackage.getNsURI() + "\'.")
				}
				var Resource missingGenModelResource = null
				try {
					missingGenModelResource = resourceSet.getResource(missingGenModelURI, true)
				} catch (RuntimeException e) {
					throw new RuntimeException(
						"Unable to load generator model of required package \'" + ePackage.getNsURI() + "\'.")
				}
				val GenModel missingGenModel = missingGenModelResource.getContents().get(0) as GenModel
				missingGenModel.reconcile
				missingGenPackages.addAll(missingGenModel.getGenPackages())
			}
		}
		return missingGenPackages;
	}
}
