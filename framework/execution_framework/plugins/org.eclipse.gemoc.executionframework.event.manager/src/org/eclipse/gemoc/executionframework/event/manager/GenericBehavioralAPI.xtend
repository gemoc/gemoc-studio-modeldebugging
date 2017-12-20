package org.eclipse.gemoc.executionframework.event.manager

import java.lang.reflect.Method
import java.util.List
import java.util.Map
import opsemanticsview.OperationalSemanticsView
import org.eclipse.core.runtime.IConfigurationElement
import org.eclipse.core.runtime.Platform
import org.eclipse.emf.common.util.URI
import org.eclipse.emf.ecore.EClass
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EOperation
import org.eclipse.emf.ecore.EPackage
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl
import org.eclipse.gemoc.dsl.Dsl
import org.eclipse.gemoc.executionframework.event.model.event.Event
import org.osgi.framework.Bundle
import opsemanticsview.EventHandler

class GenericBehavioralAPI implements IBehavioralAPI {
	
	private Dsl dsl = null;
	private Bundle bundle = null;
//	private val OperationalSemanticsView opsemview
	private val Map<EClass, List<EOperation>> typeToOperation = newHashMap
	private val Map<EOperation, Method> operationToMethod = newHashMap
//	private val List<EPackage> packages
	
	new(String languageName) {
		val languages = Platform.getExtensionRegistry().getConfigurationElementsFor("org.eclipse.gemoc.gemoc_language_workbench.sequential.xdsml");
		for (IConfigurationElement lang : languages) {
			val xdsmlPath = lang.getAttribute("xdsmlFilePath");
			val xdsmlName = lang.getAttribute("name");
			if (xdsmlName.equals(languageName) && xdsmlPath.endsWith(".dsl")) {
				val res = (new ResourceSetImpl()).getResource(URI.createURI(xdsmlPath), true);
				dsl = res.getContents().get(0) as Dsl;
				bundle = Platform.getBundle(lang.getContributor().getName());
			}
		}
		if (dsl !== null) {
//			packages = dsl.findPackages.immutableCopy
//			opsemview = dsl.findOperationalSemanticsView
//			val behavioralInterface = dsl.entries.findFirst[name == "behavioralinterface" && it instanceof CompositeValue]
//			if (behavioralInterface !== null) {
//				val typeToHandlingMethods = (behavioralInterface as CompositeValue).values
//				typeToHandlingMethods.forEach[v|
//					val p = packages.findFirst[p|p.getEClassifier(v.name) !== null]
//					val c = p.getEClassifier(v.name) as EClass
//					val operations = if (typeToOperation.get(c) === null) {val l = newArrayList typeToOperation.put(c, l) l} else typeToOperation.get(c)
//					operations += opsemview.rules.filter(EventHandler).filter[containingClass == c].map[operation]
//					operations.forEach[o|
//						val opToMethod = (v as CompositeValue).values.findFirst[name == o.name]
//						if (opToMethod !== null) {
//							val mFqn = (opToMethod as SimpleValue).values.head
//							val lastDot = mFqn.lastIndexOf(".")
//							val m = bundle.loadClass(mFqn.substring(0, lastDot)).methods.findFirst[name == mFqn.substring(lastDot + 1)]
//							if (m !== null) {
//								operationToMethod.put(o, m)
//							}
//						}
//					]
//				]
//			}
		} else {
//			opsemview = null
//			packages = emptyList
		}
	}
	
	private def OperationalSemanticsView findOperationalSemanticsView(Dsl dsl) {
		if (dsl === null) {
			return null
		}
		val resSet = new ResourceSetImpl()
		Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put("xmi", new XMIResourceFactoryImpl())
//		val value = dsl.values.findFirst[name == "opsemview" && it instanceof SimpleValue]
//		if (value !== null) {
//			val uri = URI.createURI((value as SimpleValue).values.head.replace("platform:/resource", "platform:/plugin"), true)
//			return resSet.getResource(uri, true).contents.head as OperationalSemanticsView
//		} else {
			return null;
//		}
	}
	
//	private def List<EPackage> findPackages(Dsl dsl) {
//		val resSet = new ResourceSetImpl()
//		Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put("xmi", new XMIResourceFactoryImpl())
//		return dsl.abstractSyntax.values.map[(it as SimpleValue).values].flatten
//				.map[s|URI.createURI(s.replace("platform:/resource", "platform:/plugin"), true)]
//				.map[uri|(resSet.getResource(uri, true).contents.head as EPackage).nsURI]
//				.map[uri|Platform.extensionRegistry.getConfigurationElementsFor("org.eclipse.emf.ecore.generated_package")
//							.filter[getAttribute("uri") == uri]
//							.map[Platform.getBundle(it.contributor.name).loadClass(it.getAttribute("class")).fields.findFirst[name == "eINSTANCE"].get(null) as EPackage]
//				].flatten.toList
//	}
	
	override canHandle(EPackage pkg) {
		throw new UnsupportedOperationException("TODO: auto-generated method stub")
	}
	
	override canSendEvent(Event event) {
		throw new UnsupportedOperationException("TODO: auto-generated method stub")
	}
	
	override dispatchEvent(Event event) {
		throw new UnsupportedOperationException("TODO: auto-generated method stub")
	}
	
	override getEventClasses() {
		throw new UnsupportedOperationException("TODO: auto-generated method stub")
	}
	
	override getOutputEvent(EOperation operation, EObject caller, List<Object> parameters) {
		throw new UnsupportedOperationException("TODO: auto-generated method stub")
	}
	
	override getStartEventClasses() {
		throw new UnsupportedOperationException("TODO: auto-generated method stub")
	}
	
	override isInterruptible(EClass event) {
		throw new UnsupportedOperationException("TODO: auto-generated method stub")
	}
	
}