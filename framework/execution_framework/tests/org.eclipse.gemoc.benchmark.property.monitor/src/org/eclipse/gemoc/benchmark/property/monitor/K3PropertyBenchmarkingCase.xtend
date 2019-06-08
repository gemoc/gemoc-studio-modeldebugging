package org.eclipse.gemoc.benchmark.property.monitor

import java.util.Set
import org.eclipse.emf.common.util.URI
import org.eclipse.gemoc.benchmark.cases.K3BenchmarkingCase
import org.eclipse.gemoc.benchmark.languages.K3Language
import org.eclipse.gemoc.executionframework.property.model.property.TemporalProperty
import org.eclipse.gemoc.executionframework.property.monitor.manager.PropertyManager

class K3PropertyBenchmarkingCase extends K3BenchmarkingCase {
	
	new(URI entryPointElementUri, String initializationArguments, K3Language language, Set<TemporalProperty> properties) {
		super(entryPointElementUri, initializationArguments, language, #{}, #{new PropertyManager()})
		if (properties !== null) {
			val propertyManager = addonsToLoad.filter(PropertyManager).head
			properties.filterNull.forEach[p|propertyManager?.addProperty(p)]
		}
	}
}
