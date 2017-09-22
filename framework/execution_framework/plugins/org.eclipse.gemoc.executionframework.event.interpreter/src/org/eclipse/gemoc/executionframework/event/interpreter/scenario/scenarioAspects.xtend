package org.eclipse.gemoc.executionframework.event.interpreter.scenario

import fr.inria.diverse.k3.al.annotationprocessor.Aspect
import fr.inria.diverse.k3.al.annotationprocessor.OverrideAspectMethod
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.gemoc.executionframework.event.interpreter.property.StatePropertyAspect
import org.eclipse.gemoc.executionframework.event.model.scenario.ElementProvider
import org.eclipse.gemoc.executionframework.event.model.scenario.ElementQuery
import org.eclipse.gemoc.executionframework.event.model.scenario.ElementReference

@Aspect(className=ElementProvider)
class ElementProviderAspect {
	public def EObject resolve(Resource r) {
		return null
	}
}

@Aspect(className=ElementReference)
class ElementReferenceAspect extends ElementProviderAspect {
	@OverrideAspectMethod
	public def EObject resolve(Resource r) {
		return _self.element as EObject
	}
}

@Aspect(className=ElementQuery)
class ElementQueryAspect extends ElementProviderAspect {
	@OverrideAspectMethod
	public def EObject resolve(Resource r) {
		val queriedType = _self.eClass.EGenericSuperTypes.head.ETypeArguments.head.EClassifier
		return r.allContents.filter[o|(o.eClass == queriedType || o.eClass.EAllSuperTypes.contains(queriedType))
			&& StatePropertyAspect.evaluate(_self.query, o)
		].head
	}
}
