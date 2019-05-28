package org.eclipse.gemoc.benchmark.cases

import java.util.Set
import org.eclipse.emf.common.util.URI
import org.eclipse.gemoc.benchmark.languages.K3Language
import org.eclipse.gemoc.benchmark.utils.K3EngineProvider
import org.eclipse.gemoc.benchmark.utils.K3RunConfigurationProvider
import org.eclipse.gemoc.execution.sequential.javaengine.K3RunConfiguration
import org.eclipse.gemoc.execution.sequential.javaengine.PlainK3ExecutionEngine
import org.eclipse.gemoc.execution.sequential.javaengine.SequentialModelExecutionContext
import org.eclipse.gemoc.xdsmlframework.api.engine_addon.IEngineAddon

class K3BenchmarkingCase extends BenchmarkingCase<PlainK3ExecutionEngine, SequentialModelExecutionContext<K3RunConfiguration>, K3RunConfiguration> {
	
	val URI entryPointElementUri
	val String initializationArguments
	val K3Language language
	val Set<String> addonIds
	var K3EngineProvider engineProvider
	var K3RunConfigurationProvider runConfigurationProvider
	
	new(URI entryPointElementUri, String initializationArguments, K3Language language, Set<String> addonIds, Set<IEngineAddon> addonsToLoad) {
		super(addonsToLoad)
		this.entryPointElementUri = entryPointElementUri
		this.initializationArguments = initializationArguments
		this.language = language
		this.addonIds = addonIds
	}
	
	new(URI entryPointElementUri, String initializationArguments, K3Language language, Set<String> addonIds) {
		super(emptySet)
		this.initializationArguments = initializationArguments
		this.entryPointElementUri = entryPointElementUri
		this.language = language
		this.addonIds = addonIds
	}
	
	override protected getEngineHelper() {
		if (engineProvider === null) {
			engineProvider = new K3EngineProvider
		}
		return engineProvider
	}
	
	override protected getRunConfigurationHelper() {
		if (runConfigurationProvider === null) {
			runConfigurationProvider = new K3RunConfigurationProvider(entryPointElementUri, initializationArguments, language, addonIds)
		}
		return runConfigurationProvider
	}
}
