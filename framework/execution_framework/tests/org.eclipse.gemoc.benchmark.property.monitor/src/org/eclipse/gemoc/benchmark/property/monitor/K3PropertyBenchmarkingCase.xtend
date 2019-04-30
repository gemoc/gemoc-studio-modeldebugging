package org.eclipse.gemoc.benchmark.property.monitor

import java.util.Set
import org.eclipse.emf.common.util.URI
import org.eclipse.gemoc.benchmark.cases.K3BenchmarkingCase
import org.eclipse.gemoc.benchmark.languages.K3Language
import org.eclipse.gemoc.executionframework.property.model.property.EPLProperty
import org.eclipse.gemoc.executionframework.property.monitor.manager.PropertyManager

class K3PropertyBenchmarkingCase extends K3BenchmarkingCase {
	
	val Set<EPLProperty> properties
	
	new(URI entryPointElementUri, K3Language language, Set<EPLProperty> properties) {
		super(entryPointElementUri, language, #{}, #{new PropertyManager()})
		this.properties = properties
	}
	
	override initialize() {
		val t = super.initialize()
		val t1 = System.nanoTime
		addonsToLoad.filter(PropertyManager).forEach[m|properties.forEach[p|m.addProperty(p)]]
		t + System.nanoTime - t1
	}
	
}
