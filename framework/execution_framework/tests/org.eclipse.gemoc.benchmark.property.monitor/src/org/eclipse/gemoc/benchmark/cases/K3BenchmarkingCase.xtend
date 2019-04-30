package org.eclipse.gemoc.benchmark.cases

import java.util.Set
import org.eclipse.emf.common.util.URI
import org.eclipse.gemoc.benchmark.languages.K3Language
import org.eclipse.gemoc.benchmark.utils.K3EngineHelper
import org.eclipse.gemoc.benchmark.utils.K3RunConfigurationHelper
import org.eclipse.gemoc.execution.sequential.javaengine.K3RunConfiguration
import org.eclipse.gemoc.execution.sequential.javaengine.PlainK3ExecutionEngine
import org.eclipse.gemoc.execution.sequential.javaengine.SequentialModelExecutionContext
import org.eclipse.gemoc.xdsmlframework.api.engine_addon.IEngineAddon

class K3BenchmarkingCase extends BenchmarkingCase<PlainK3ExecutionEngine, SequentialModelExecutionContext<K3RunConfiguration>, K3RunConfiguration> {
	
	val URI entryPointElementUri
	val K3Language language
	val Set<String> addonIds
	
	new(URI entryPointElementUri, K3Language language, Set<String> addonIds, Set<IEngineAddon> addonsToLoad) {
		super(addonsToLoad)
		this.entryPointElementUri = entryPointElementUri
		this.language = language
		this.addonIds = addonIds
	}
	
	new(URI entryPointElementUri, K3Language language, Set<String> addonIds) {
		super(emptySet)
		this.entryPointElementUri = entryPointElementUri
		this.language = language
		this.addonIds = addonIds
	}
	
	override protected getEngineHelper() {
		return new K3EngineHelper
	}
	
	override protected getRunConfigurationHelper() {
		return new K3RunConfigurationHelper(entryPointElementUri, language, addonIds)
	}

}
