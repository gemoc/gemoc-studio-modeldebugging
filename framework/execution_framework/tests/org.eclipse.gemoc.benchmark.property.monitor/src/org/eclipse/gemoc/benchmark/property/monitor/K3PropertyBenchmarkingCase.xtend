package org.eclipse.gemoc.benchmark.property.monitor

import java.util.Set
import org.eclipse.emf.common.util.URI
import org.eclipse.gemoc.benchmark.cases.K3BenchmarkingCase
import org.eclipse.gemoc.benchmark.languages.K3Language
import org.eclipse.gemoc.executionframework.property.model.property.EPLProperty
import org.eclipse.gemoc.executionframework.property.monitor.manager.PropertyManager

class K3PropertyBenchmarkingCase extends K3BenchmarkingCase {
	
	val Set<EPLProperty> properties
	
	new(URI entryPointElementUri, String initializationArguments, K3Language language, Set<EPLProperty> properties) {
		super(entryPointElementUri, initializationArguments, language, #{}, #{new PropertyManager()})
		this.properties = properties
	}
	
	override initialize() {
		val t = super.initialize()
		val t1 = System.nanoTime
		if (properties !== null) {
			val propertyManager = addonsToLoad.filter(PropertyManager).head
			properties.forEach[p|propertyManager?.addProperty(p)]
		}
		t + System.nanoTime - t1
	}
	
}
