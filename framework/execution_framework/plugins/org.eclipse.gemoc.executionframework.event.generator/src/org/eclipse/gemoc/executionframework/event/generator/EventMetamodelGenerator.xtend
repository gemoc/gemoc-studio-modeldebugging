package org.eclipse.gemoc.executionframework.event.generator

import java.io.IOException
import java.util.HashMap
import java.util.HashSet
import java.util.List
import java.util.Map
import java.util.Set
import opsemanticsview.EventEmitter
import opsemanticsview.EventHandler
import opsemanticsview.OperationalSemanticsView
import opsemanticsview.Rule
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
import org.eclipse.emf.ecore.EDataType
import org.eclipse.emf.ecore.EPackage
import org.eclipse.emf.ecore.EParameter
import org.eclipse.emf.ecore.EcoreFactory
import org.eclipse.emf.ecore.EcorePackage
import org.eclipse.emf.ecore.plugin.EcorePlugin
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.emf.ecore.resource.ResourceSet
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl
import org.eclipse.gemoc.xdsmlframework.behavioralinterface.behavioralInterface.BehavioralInterfacePackage

class EventMetamodelGenerator {
	
	private val EclipseProjectHelper projectHelper = new EclipseProjectHelper
	
	private var Set<EPackage> basePkgs = new HashSet
	
	private val EPackage rootPackage

	private var EClass dslSpecificEventClass
	
	private var EPackage eventPkg
	
	private var Set<GenPackage> refGenPackages
	
	private var String ecoreURI
	
	private var String genmodelURI
	
	private val Set<GenModel> fixedGenModels = new HashSet
	
	private var Map<EClassifier, EClass> rtToSuperEvent = new HashMap
	
	private val OperationalSemanticsView operationalSemanticsView
	
	private val String dslName
	
	private val String pluginName
	
	private val String eventEcoreUri
	
	private val String eventGenmodelUri
	
	private val String eventGenerationPath
	
	new(String languageName, OperationalSemanticsView operationalSemanticsView, String basePluginName) {
		this.operationalSemanticsView = operationalSemanticsView
		pluginName = basePluginName + ".event"
		rootPackage = operationalSemanticsView.executionMetamodel
		basePkgs += rootPackage.eAllContents.filter(EPackage).toSet
		basePkgs += rootPackage
		ecoreURI = '''platform:/resource«rootPackage.eResource.URI.toPlatformString(true)»'''
		genmodelURI = '''platform:/resource«rootPackage.eResource.URI.trimFileExtension.appendFileExtension("genmodel").toPlatformString(true)»'''
		dslName = if (languageName.contains(".")) languageName.substring(languageName.lastIndexOf(".") + 1) else languageName
		eventGenerationPath = '''../«pluginName»/src-gen/'''
		eventEcoreUri = '''platform:/resource/«pluginName»/model/«dslName»Event.ecore'''
		eventGenmodelUri = '''platform:/resource/«pluginName»/model/«dslName»Event.genmodel'''
	}
	
	def void generateBehavioralInterface() {
		Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put("xmi", new XMIResourceFactoryImpl());
		projectHelper.createEMFEventProject(pluginName)
		createEcore
		createGenmodelAndGenerateCode
	}
	
	private def void createEcore() {
		eventPkg = EcoreFactory.eINSTANCE.createEPackage => [
			name = (dslName + "event").toLowerCase
			nsPrefix = name
			nsURI = rootPackage.nsURI+"event/"
		]
		
		dslSpecificEventClass = EcoreFactory.eINSTANCE.createEClass => [c|
			c.name = dslName.toFirstUpper + "DSLEvent"
			c.abstract = true
			c.ESuperTypes += BehavioralInterfacePackage.Literals.EVENT
		]
		
		eventPkg.EClassifiers += dslSpecificEventClass
		
		operationalSemanticsView.rules
				.forEach[r|
//					if (r instanceof EventHandler || r instanceof EventEmitter) {
						r.generateEvent
//					}
				]
		
		val resSet = new ResourceSetImpl
		val usedGenpkgRes = resSet.getResource(URI::createURI(genmodelURI), true)
		refGenPackages = (usedGenpkgRes.contents.head as GenModel).genPackages.toSet
		
		val res = resSet.createResource(URI.createPlatformResourceURI(pluginName+"/model/"+dslName+"Event.ecore", true))
		res.contents += eventPkg
		res.save(null)
	}
	
	private def void createGenmodelAndGenerateCode() {
		val resSet = new ResourceSetImpl
		val pkgRes = resSet.getResource(URI::createURI(eventEcoreUri), true)
		val pkgs = pkgRes.contents.map[it as EPackage]
		val fileExtension = refGenPackages.findFirst[!fileExtension.nullOrEmpty]?.fileExtension

		val genmodel = GenModelFactory.eINSTANCE.createGenModel => [
			complianceLevel = GenJDKLevel.JDK70_LITERAL
			modelDirectory = eventGenerationPath
				.replaceFirst("platform:/resource", "").replaceFirst("..", "")
			foreignModel += ecoreURI
			modelName = dslName + "event"
			modelPluginID = pluginName
			initialize(pkgs)
			genPackages.forEach[gp|
				gp.basePackage = pluginName
				if (!fileExtension.nullOrEmpty) {
					gp.fileExtensions = fileExtension + "e"
				} else {
					gp.fileExtensions = dslName.toLowerCase.toString + "e"
				}
			]
		]
		
		val res = resSet.createResource(URI::createURI(eventGenmodelUri))
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
	
	def void generateCode(GenModel genModel) {
		genModel.reconcile
		genModel.canGenerate = true
		genModel.validateModel = true

		val generator = GenModelUtil::createGenerator(genModel)
		generator.generate(
			genModel,
			GenBaseGeneratorAdapter::MODEL_PROJECT_TYPE,
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
	
	private val Map<String, EDataType> typeNameToType =
		#{
			"java.lang.Boolean" -> EcorePackage.Literals.EBOOLEAN,
			"java.lang.Float" -> EcorePackage.Literals.EFLOAT,
			"java.lang.Integer" -> EcorePackage.Literals.EINT,
			"java.lang.String" -> EcorePackage.Literals.ESTRING,
			"bool" -> EcorePackage.Literals.EBOOLEAN,
			"float" -> EcorePackage.Literals.EFLOAT,
			"int" -> EcorePackage.Literals.EINT
		}
	
	def private void generateEvent(Rule handler) {
		val containingEClass = handler.containingClass
		val op = handler.operation
		val eventName = containingEClass.name.toFirstUpper + op.name.toFirstUpper + "Event"
		val List<EParameter> params = op.EParameters
		eventPkg.EClassifiers += EcoreFactory.eINSTANCE.createEClass => [c|
			c.name = eventName
			
			val callerTypeName = containingEClass.name
			val callerClassifier = basePkgs.findFirst[getEClassifier(callerTypeName) !== null]?.getEClassifier(callerTypeName)
			if (callerClassifier !== null) {
				c.ESuperTypes += callerClassifier.superEventClass
			}
			
			params.forEach[opParam|
				val parameterInstanceTypeName = opParam.EType.instanceClassName
				val parameterDataType = typeNameToType.get(parameterInstanceTypeName)
				if (parameterDataType !== null) {
					c.EStructuralFeatures += EcoreFactory.eINSTANCE.createEAttribute => [
						name = opParam.name.toFirstLower
						lowerBound = opParam.lowerBound
						upperBound = opParam.upperBound
						EType = parameterDataType
					]
				} else {
					val parameterTypeName = opParam.EType.name
					val parameterClassifier = basePkgs.findFirst[getEClassifier(parameterTypeName) !== null]?.getEClassifier(parameterTypeName)
					if (parameterClassifier !== null) {
						c.EStructuralFeatures += EcoreFactory.eINSTANCE.createEReference => [
							EType = parameterClassifier
							lowerBound = opParam.lowerBound
							upperBound = opParam.upperBound
							containment = false
							name = opParam.name.toFirstLower
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
					EClassifier = dslSpecificEventClass
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
			genModel.usedGenPackages.removeAll(genModel.usedGenPackages.immutableCopy.filter[p|p.genModel === null])
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
			if (ePackage !== null) { // happens for activities
				var URI missingGenModelURI = genModelLocationMapEnvironment.get(ePackage.getNsURI())
				if (missingGenModelURI === null) {
					missingGenModelURI = genModelLocationMapTargetEnvironment.get(ePackage.getNsURI())
				}
				if (missingGenModelURI === null) {
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
