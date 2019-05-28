package org.eclipse.gemoc.benchmark.cases

import java.util.Set
import org.eclipse.gemoc.executionframework.engine.core.AbstractExecutionEngine
import org.eclipse.gemoc.xdsmlframework.api.core.IExecutionContext
import org.eclipse.gemoc.xdsmlframework.api.core.IRunConfiguration
import org.eclipse.gemoc.xdsmlframework.api.engine_addon.IEngineAddon
import org.eclipse.gemoc.benchmark.utils.AbstractEngineProvider
import org.eclipse.gemoc.benchmark.utils.AbstractRunConfigurationProvider

abstract class BenchmarkingCase<E extends AbstractExecutionEngine<C, R>, C extends IExecutionContext<R, ?, ?>, R extends IRunConfiguration> {
	
	protected val Set<IEngineAddon> addonsToLoad
	
	protected def AbstractEngineProvider<E, C, R> getEngineHelper()
	
	protected def AbstractRunConfigurationProvider<R> getRunConfigurationHelper()
	
	new(Set<IEngineAddon> addonsToLoad) {
		this.addonsToLoad = addonsToLoad
	}
	
	def long initialize() {
		engineHelper.removeStoppedEngines
		val t = System.nanoTime
		engineHelper.prepareEngine(runConfigurationHelper.launchConfiguration, addonsToLoad)
		System.nanoTime - t
	}
	
	def execute() {
		engineHelper.execute
	}

}
