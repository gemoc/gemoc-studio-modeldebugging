package org.eclipse.gemoc.benchmark.cases

import java.util.Set
import org.eclipse.gemoc.benchmark.utils.AbstractEngineHelper
import org.eclipse.gemoc.benchmark.utils.AbstractRunConfigurationHelper
import org.eclipse.gemoc.executionframework.engine.core.AbstractExecutionEngine
import org.eclipse.gemoc.xdsmlframework.api.core.IExecutionContext
import org.eclipse.gemoc.xdsmlframework.api.core.IRunConfiguration
import org.eclipse.gemoc.xdsmlframework.api.engine_addon.IEngineAddon

abstract class BenchmarkingCase<E extends AbstractExecutionEngine<C, R>, C extends IExecutionContext<R, ?, ?>, R extends IRunConfiguration> {

	val AbstractEngineHelper<E, C, R> engineHelper = getEngineHelper
	
	val AbstractRunConfigurationHelper<R> runConfigurationHelper = getRunConfigurationHelper
	
	protected val Set<IEngineAddon> addonsToLoad
	
	protected def AbstractEngineHelper<E, C, R> getEngineHelper()
	
	protected def AbstractRunConfigurationHelper<R> getRunConfigurationHelper()
	
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
