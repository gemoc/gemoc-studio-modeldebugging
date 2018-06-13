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
import java.util.List
import java.util.Set
import org.eclipse.emf.codegen.ecore.genmodel.GenModel
import org.eclipse.emf.codegen.ecore.genmodel.GenPackage
import org.eclipse.emf.codegen.ecore.genmodel.generator.GenBaseGeneratorAdapter
import org.eclipse.emf.codegen.ecore.genmodel.util.GenModelUtil
import org.eclipse.emf.codegen.util.CodeGenUtil
import org.eclipse.emf.common.util.Diagnostic
import org.eclipse.emf.common.util.Monitor
import org.eclipse.emf.ecore.EAttribute
import org.eclipse.emf.ecore.EClass
import org.eclipse.emf.ecore.EClassifier
import org.eclipse.emf.ecore.EDataType
import org.eclipse.emf.ecore.EEnum
import org.eclipse.emf.ecore.EModelElement
import org.eclipse.emf.ecore.ENamedElement
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EOperation
import org.eclipse.emf.ecore.EPackage
import org.eclipse.emf.ecore.EReference
import org.eclipse.emf.ecore.EStructuralFeature
import org.eclipse.emf.ecore.EcoreFactory
import org.eclipse.emf.ecore.EcorePackage
import org.eclipse.emf.ecore.util.EcoreUtil

class EcoreExtensions {
	def boolean isContainedBy(EObject children, EObject parent) {
		var iter = children.eContainer

		while (iter !== null) {
			if (iter == parent)
				return true
			iter = iter.eContainer
		}

		return false
	}

	def boolean emfEquals(EObject o1, EObject o2) {
		return EcoreUtil.equals(o1, o2)
	}

	def String formatFeatureID(EClass cls, EStructuralFeature f) {
		return '''«cls.name.camelToUnderscores»__«f.name.camelToUnderscores»'''
	}

	def String camelToUnderscores(String s) {
		return CodeGenUtil.format(s, "_", "", true, true).toUpperCase
	}

	def Iterable<EClass> sortByClassInheritance(Iterable<EClass> classes) {
		return classes.sortWith[clsA, clsB |
			if (clsA.EAllSuperTypes.contains(clsB))
				return -1
			else if (clsB.EAllSuperTypes.contains(clsA))
				return 1
			else
				return 0
		]
	}

	def Iterable<EOperation> sortByOverridingPriority(Iterable<EOperation> ops) {
		return ops.sortWith[opA, opB |
			val retA = opA.EType
			val retB = opB.EType

			if (retA instanceof EClass) {
				if (!(retB instanceof EClass))
					return 1
				else if (retA.EAllSuperTypes.contains(retB))
					return -1
			} else {
				if (retB instanceof EClass)
					return -1
				else return 1
			}

			return 0
		]
	}

	/**
	 * Returns the top-{@link EPackage} containing {@code pkg}.
	 */
	def EPackage getRootPackage(EPackage pkg) {
		var tmp = pkg

		while (tmp.ESuperPackage !== null)
			tmp = tmp.ESuperPackage

		return tmp
	}

	def boolean isEcore(EModelElement m) {
		return
			m !== null
			&& m.isContainedBy(EcorePackage::eINSTANCE)
	}
	
	/**
	 * Search a subPackage in @{link root} named {@link fqn}.
	 * Return null if not found.
	 * 
	 * @param fqn In the form of 'subpackage(.subpackage)*'
	 */
	def EPackage findSubPackage(EPackage root, String fqn){
		
		if(root === null) return null
		
		val splitName = fqn.split("\\.")
		
		if(splitName.length == 1){
			return root.ESubpackages.findFirst[name == fqn]
		}
		else{
			val subRoot = root.ESubpackages.findFirst[name == splitName.get(0)]
			val subFqn = fqn.substring(splitName.get(0).length+1)
			return findSubPackage(subRoot, subFqn)
		}
	}
	
	/** 
	 * Search in {@link pkg} for {@link qualifiedClsName}.
	 * Return null if not found.
	 * 
	 * @param clsName In the form of 'pkg(.subpackage)*.simpleName',
	 * or just 'simpleName'
	 */
	def EClass getClass(EPackage pkg, String clsName){
		val indexOf = clsName.indexOf(".")
		if(indexOf == -1){
			//Simple name
			return pkg.EClassifiers.filter(EClass).findFirst[name == clsName]
		}
		else{
			val withoutRoot = clsName.substring(indexOf+1)
			val lastDot = withoutRoot.lastIndexOf(".")
			
			if(lastDot == -1){
				return pkg.EClassifiers.filter(EClass).findFirst[name == withoutRoot]	
			}
			else{
				val subpack = withoutRoot.substring(0, lastDot)
				val simpleName = withoutRoot.substring(lastDot + 1)
				val ePack = pkg.findSubPackage(subpack)
				if(ePack === null){
					return null
				}
				else{
					return ePack.EClassifiers.filter(EClass).findFirst[name == simpleName]
				}
			}
		}
	}
	
	def EClass findClass(Set<EPackage> pkgs, String clsName) {
		return pkgs.map[findClass(it,clsName)].filterNull.head
	}

	/**
	 * Search in {@link pkg} for an EClass named {@link clsName}.
	 * The search is also performed in subpackages if {@link clsName}
	 * is a qualified named.
	 *
	 * Return null if not found
	 * 
	 * @see findQualifiedClass(EPackage rootPkg, String qualifiedClsName)
	 */
	def EClass findClass(EPackage pkg, String clsName) {
		
		if(clsName == null)
			return null
		
		if(clsName.contains(".")){
			return findQualifiedClass(pkg,clsName)
		}
		
		return pkg.allClasses.findFirst[name == clsName]
	}

	/**
	 * Search in {@link rootPkg} for an EClass named {@link qualifiedClsName}.
	 * The prefix packages are ignored. We consider only the part of {@link qualifiedClsName}
	 * starting with rootPkg.getName()
	 */
	def EClass findQualifiedClass(EPackage rootPkg, String qualifiedClsName) {
		
		if(qualifiedClsName == null || !qualifiedClsName.contains("."))
			return null
		
		val rootName = rootPkg.name
		if(qualifiedClsName.startsWith(rootName+"."))
			return rootPkg.allClasses.findFirst[getUniqueId == qualifiedClsName]
		
		return rootPkg.allClasses.findFirst[qualifiedClsName.endsWith(getUniqueId)]
	}

	def EClassifier findClassifier(Set<EPackage> pkgs, String clsName) {
		return pkgs.map[findClassifier(it,clsName)].filterNull.head
	}
	
	/**
	 * Search in {@link pkg} for an EClassifier named {@link clsName}.
	 * The search is also performed in subpackages if {@link clsName}
	 * is a qualified named.
	 *
	 * Return null if not found
	 * 
	 * @see findQualifiedClass(EPackage rootPkg, String qualifiedClsName)
	 */
	def EClassifier findClassifier(EPackage pkg, String clsName) {
		
		if(clsName.contains(".")){
			return findQualifiedClassifier(pkg,clsName)
		}
		
		return pkg.EClassifiers.findFirst[name == clsName]
	}
	
	/**
	 * Similar to findQualifiedClass(EPackage rootPkg, String qualifiedClsName)
	 * but for Classifiers
	 */
	def EClassifier findQualifiedClassifier(EPackage rootPkg, String qualifiedClsName) {
		
		if(qualifiedClsName == null || !qualifiedClsName.contains("."))
			return null
		
		val rootName = rootPkg.name
		if(qualifiedClsName.startsWith(rootName+"."))
			return rootPkg.allClassifiers.findFirst[getUniqueId == qualifiedClsName]
		
		return rootPkg.allClassifiers.findFirst[qualifiedClsName.endsWith(getUniqueId)]
	}

	def List<EClassifier> getAllClassifiers(List<EPackage> pkgs) {
		return pkgs.map[EClassifiers].flatten.toList
	}

	def dispatch String getUniqueId(Void it) {
		return '''null'''
	}

	def dispatch String getUniqueId(EPackage it) {
		return '''«IF ESuperPackage !== null»«ESuperPackage.uniqueId».«ENDIF»«name»'''
	}

	def dispatch String getUniqueId(EClassifier it) {
		return '''«EPackage.uniqueId».«name»'''
	}

	def dispatch String getUniqueId(EOperation it) {
		return '''«EContainingClass.uniqueId».«name»'''
	}

	def dispatch String getUniqueId(EStructuralFeature it) {
		return '''«EContainingClass.uniqueId».«name»'''
	}

	def boolean isInstantiable(EClass cls) {
		return !cls.^abstract && !cls.^interface && cls.abstractable
	}

	def boolean isAbstractable(EClass cls) {
		return cls.name != "EObject" && cls.instanceClass === null && cls.instanceTypeName === null
	}

	def boolean isAspectSpecific(EModelElement e) {
		return e.EAnnotations.exists[source == "aspect"]
	}

	def void addAspectAnnotation(EModelElement e) {
		e.EAnnotations += EcoreFactory.eINSTANCE.createEAnnotation => [
			source = "aspect"
		]
	}
	
	def void addContainmentAnnotation(EModelElement e) {
		e.EAnnotations += EcoreFactory.eINSTANCE.createEAnnotation => [
			source = "containment"
		]
	}
	
	def void addOppositeAnnotation(EModelElement e,String ref) {
		e.EAnnotations += EcoreFactory.eINSTANCE.createEAnnotation => [
			source = "opposite"
			details.put("value",ref)
		]
	}
	
	def boolean hasContainmentAnnotation(EModelElement e) {
		return e.EAnnotations.exists[source == "containment"]
	}

	def boolean hasSuppressedVisibility(ENamedElement f) {
		return f.getGenmodelAnnotationValue("suppressedVisibility") == "true"
	}

	def boolean needsUnsetterInterface(EStructuralFeature f) {
		return
			   f.unsettable
			&& f.getGenmodelAnnotationValue("suppressedUnsetVisibility") != "true"
	}

	def boolean needsUnsetterImplementation(EStructuralFeature f) {
		return f.unsettable
	}

	def boolean needsUnsetterCheckerInterface(EStructuralFeature f) {
		return
			   f.unsettable
			&& f.getGenmodelAnnotationValue("suppressedIsSetVisibility") != "true"
	}

	def boolean needsUnsetterCheckerImplementation(EStructuralFeature f) {
		return f.unsettable
	}

	def String getGenmodelAnnotationValue(EModelElement e, String key) {
		return
			e.getEAnnotation("http://www.eclipse.org/emf/2002/GenModel")
			?.details?.findFirst[d | d.key == key]
			?.value ?: ""
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

	/**
	 * Look for {@link fqn} in {@link aspPkg}.
	 * Otherwise create a new class in {@link aspPkg} (end eventually the sub-package hierarchy).
	 */
	def EClass getOrCreateClass(EPackage aspPkg, String fqn) {
		var find = aspPkg.getClass(fqn) 

		if (find !== null) {
			return find
		} else {
			val segments = fqn.split("\\.")
			if(segments.length == 1){
				val newCls = EcoreFactory.eINSTANCE.createEClass => [cls |
					cls.name = fqn
				]
				newCls.addAspectAnnotation
				aspPkg.EClassifiers += newCls
				return newCls
			}
			else{
				if(segments.get(0) != aspPkg.name){
					//fqn is not in aspPkg's fragment so we put it inside the root package 
					val newCls = EcoreFactory.eINSTANCE.createEClass => [cls |
						cls.name = segments.last
					]
					newCls.addAspectAnnotation
					aspPkg.EClassifiers += newCls
					return newCls
				}
				else{
					var last = aspPkg
					for(var int i = 1; i < segments.size-1; i++){
						val finalLast = last
						val segment = segments.get(i)
						val newPack = EcoreFactory.eINSTANCE.createEPackage => [p |
								p.name = segment
								p.nsPrefix = finalLast.nsPrefix+"."+segment
								p.nsURI = finalLast.nsURI+"."+segment
							]
						last.ESubpackages += newPack
						last = newPack
					}
					val newCls = EcoreFactory.eINSTANCE.createEClass => [cls |
						cls.name = segments.last
					]
					newCls.addAspectAnnotation
					last.EClassifiers += newCls
					return newCls
				}
			}
		}
	}

	def EClassifier getOrCreateDataType(EPackage pkg, String name, String instanceTypeName) {
		val primitiveTypes = #[
			"java.lang.Boolean", "java.lang.Byte",
			"java.lang.Double",  "java.lang.Float",
			"java.lang.Integer", "java.lang.Long",
			"java.lang.Short"
		]

		val ecoreDtName = "E" + name.toFirstUpper + if (primitiveTypes.contains(instanceTypeName)) "Object" else ""
		val find = pkg.EClassifiers.filter(EDataType).findFirst[it.name == name && it.instanceTypeName == instanceTypeName]
		val findDt = EcorePackage.eINSTANCE.findClassifier(ecoreDtName)

		if (find !== null) {
			return find
		} else if (name != "Object" && findDt !== null) {
			return findDt
		} else {
			val newDt = EcoreFactory.eINSTANCE.createEDataType => [dt |
				dt.name = name
				dt.instanceTypeName = instanceTypeName
				dt.addAspectAnnotation
			]

			pkg.EClassifiers += newDt

			return newDt
		}
	}

	def EClassifier getOrCreateEnum(EPackage pkg, String name, Iterable<String> literals) {
		val find = pkg.EClassifiers.filter(EEnum).findFirst[it.name == name]

		if (find !== null) {
			return find
		} else {
			val newE = EcoreFactory.eINSTANCE.createEEnum => [e |
				e.name = name
				e.ELiterals += literals.map[litValue | EcoreFactory.eINSTANCE.createEEnumLiteral => [lit |
					lit.name = litValue
				]]
				e.addAspectAnnotation
			]

			pkg.EClassifiers += newE

			return newE
		}
	}

	def EPackage copy(EPackage pkg) {
		return pkg.copy(pkg.name, pkg.name, pkg.nsURI)
	}

	def EPackage copy(EPackage pkg, String name) {
		return pkg.copy(name, name.toLowerCase, '''http://«name.toLowerCase»/''')
	}

	def EPackage copy(EPackage pkg, String pkgName, String prefix, String uri) {
		val newPkg = EcoreFactory.eINSTANCE.createEPackage => [
			name = pkgName.toLowerCase
			nsPrefix = prefix
			nsURI = uri
		]

		newPkg.EClassifiers.addAll(EcoreUtil.copyAll(pkg.EClassifiers))

		return newPkg
	}

	def List<EPackage> getReferencedPkgs(EPackage pkg) {
		val ret = new ArrayList<EPackage>
		getReferencedPkgsRec(pkg, ret)
		return ret
	}

	def void getReferencedPkgsRec(EPackage pkg, List<EPackage> ret) {
		EcoreUtil.ExternalCrossReferencer.find(pkg).filter[o, s | o instanceof EClass].forEach[cls, s|
			var container = cls

			while (container !== null && !(container instanceof EPackage))
				container = container.eContainer

			val referenced = container as EPackage

			if (referenced !== null && !ret.exists[nsURI == referenced.nsURI] && !referenced.isEcore) {
				ret += referenced
				referenced.getReferencedPkgsRec(ret)
			}
		]
	}
	
	def List<EClass> getAllClasses(EPackage pkg) {
		val ret = newArrayList
		
		ret.addAll(pkg.EClassifiers.filter(EClass))
		ret.addAll(pkg.allSubPkgs.map[EClassifiers].flatten.filter(EClass))
		
		return ret
	}
	
	def List<EClassifier> getAllClassifiers(EPackage pkg) {
		val ret = newArrayList
		
		ret.addAll(pkg.EClassifiers)
		ret.addAll(pkg.allSubPkgs.map[EClassifiers].flatten)
		
		return ret
	}

	def List<EPackage> getAllSubPkgs(EPackage pkg) {
		val ret = newArrayList
		getAllSubPkgsRec(pkg, ret)
		return ret
	}

	def void getAllSubPkgsRec(EPackage pkg, List<EPackage> ret) {
		pkg.ESubpackages.forEach[p |
			getAllSubPkgsRec(p, ret)
			ret.add(p)
		]
	}

	def List<GenPackage> getAllGenPkgs(GenModel gm) {
		val ret = newArrayList
		getAllGenPkgsRec(gm, ret)
		return ret
	}

	def void getAllGenPkgsRec(GenModel gm, List<GenPackage> ret) {
		gm.genPackages.filter[gp | !ret.exists[getEcorePackage.nsURI == gp.getEcorePackage.nsURI]].forEach[gp |
			ret.add(gp)
			getAllGenPkgsRec(gp, ret)
		]
		gm.usedGenPackages.filter[gp | gp !== null && gp.getEcorePackage !== null && !ret.exists[getEcorePackage.nsURI == gp.getEcorePackage.nsURI]].forEach[gp |
			ret.add(gp)
			getAllGenPkgsRec(gp.genModel, ret)
			getAllGenPkgsRec(gp, ret)
		]
	}

	def void getAllGenPkgsRec(GenPackage gp, List<GenPackage> ret) {
		gp.subGenPackages.filter[gpp | !ret.exists[getEcorePackage.nsURI == gpp.getEcorePackage.nsURI]].forEach[gpp |
			ret.add(gpp)
			getAllGenPkgsRec(gpp, ret)
		]
	}

	def boolean needsSetterInterface(EStructuralFeature attr) {
		// TODO: Checks against EMF generation
		return attr.changeable && !attr.many
	}

	def boolean needsSetterImplementation(EStructuralFeature attr) {
		// TODO: Checks against EMF generation
		return attr.changeable && !attr.many
	}

	def boolean isEMFMapDetails(EReference ref) {
		return ref?.name == "details" && ref.EReferenceType?.name == "EStringToStringMapEntry"
	}

	def boolean isEcore(EPackage pkg) {
		return pkg.nsURI == "http://www.eclipse.org/emf/2002/Ecore"
	}

	def boolean isUml(EPackage pkg) {
		return pkg.name == "uml"
	}

	def void replaceLocalEObjectReferencesToEcoreEObjectReferences(EPackage pkg) {
		val eObject = pkg.findClass("EObject")

		if (eObject !== null) {
			EcoreUtil.UsageCrossReferencer::find(eObject, pkg).forEach[setting |
				val ref = setting.EObject
				if (ref instanceof EReference) {
					val replacement = EcoreUtil::copy(ref) => [
						EType = EcorePackage.Literals.EOBJECT
					]
					EcoreUtil::replace(ref, replacement)
				}
			]
			EcoreUtil::delete(eObject)
		}
	}

	/**
	 * Replaces a datatype with a new EClass and update the pointing references
	 */
	def void replaceDataTypeWithEClass(Set<EPackage> pkgs, EDataType dt) {
		val dtName = dt.uniqueId
		val find = pkgs.findClass(dtName)

		val replacement =
			if (find !== null)
				find
			else{
				val newCls =EcoreFactory.eINSTANCE.createEClass => [
					name = dt.name
				]
				if(dt.isAspectSpecific)
					newCls.addAspectAnnotation
				newCls
			}
		EcoreUtil.UsageCrossReferencer::find(dt, pkgs).forEach[setting |
			val attr = setting.EObject
			if (attr instanceof EAttribute) {
				val featureReplacement = EcoreFactory.eINSTANCE.createEReference => [ref |
					ref.name = attr.name
					ref.lowerBound = attr.lowerBound
					ref.upperBound = attr.upperBound
					ref.EType = replacement
					ref.unique = attr.unique
				]
				if(attr.isAspectSpecific)
					featureReplacement.addAspectAnnotation
				if(attr.hasContainmentAnnotation)
					featureReplacement.containment = true
				EcoreUtil::replace(attr, featureReplacement)
			}
		]

		if (find !== null)
			EcoreUtil::delete(dt)
		else
			EcoreUtil::replace(dt, replacement)
	}
	
	/**
	 * Initializes the NsURI of {@link syntax} and all its sub packages.
	 * The URI of the {@link language} is used as the base for theses NsURI
	 */
	def void initializeNsUriWith(EPackage syntax, String nsUriBase){
		val base = 
			if(nsUriBase.endsWith("/")) nsUriBase
			else nsUriBase + "/"
		syntax.nsURI = 
			base
			+ syntax.uniqueId.replace('.','/')
			+ "/"
		syntax.allSubPkgs.forEach[pkg|
			val suffix = pkg.uniqueId.replace('.','/')
			pkg.nsURI = base + suffix + "/"	
		]
	}
}
