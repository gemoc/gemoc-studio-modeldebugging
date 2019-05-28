package org.eclipse.gemoc.benchmark.utils

import java.util.Map.Entry
import java.util.Set
import org.eclipse.core.runtime.CoreException
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.emf.transaction.util.TransactionUtil
import org.eclipse.gemoc.executionframework.engine.Activator
import org.eclipse.gemoc.executionframework.engine.commons.EngineContextException
import org.eclipse.gemoc.executionframework.engine.core.AbstractExecutionEngine
import org.eclipse.gemoc.xdsmlframework.api.core.ExecutionMode
import org.eclipse.gemoc.xdsmlframework.api.core.IExecutionContext
import org.eclipse.gemoc.xdsmlframework.api.core.IExecutionEngine
import org.eclipse.gemoc.xdsmlframework.api.core.IRunConfiguration
import org.eclipse.gemoc.xdsmlframework.api.engine_addon.IEngineAddon
import org.eclipse.jdt.core.IType
import org.eclipse.jdt.core.search.SearchMatch
import org.eclipse.jdt.core.search.SearchRequestor

abstract class AbstractEngineProvider<E extends AbstractExecutionEngine<C, R>, C extends IExecutionContext<R, ?, ?>, R extends IRunConfiguration> {

	E executionEngine;

	C executionContext;

	static class DefaultSearchRequestor extends SearchRequestor {

		public IType _binaryType

		override acceptSearchMatch(SearchMatch match) throws CoreException {
			_binaryType = match.element as IType
			System.out.println(match.element)
		}
	}

	protected def C getExecutionContext(R runConfiguration, ExecutionMode executionMode)

	protected def E getExecutionEngine()

	def prepareEngine(R runConf, Set<IEngineAddon> addonsToLoad) throws CoreException, EngineContextException {
		val ExecutionMode executionMode = ExecutionMode.Run
		executionContext = getExecutionContext(runConf, executionMode)
		executionContext.initializeResourceModel
		addonsToLoad.forEach[executionContext.executionPlatform.addEngineAddon(it)]
		executionEngine = getExecutionEngine
		executionEngine.initialize(executionContext)
	}

	def execute() {
		executionEngine.start;
		executionEngine.joinThread;
	}

	def Resource getModel() {
		return executionEngine.executionContext.resourceModel
	}

	def removeStoppedEngines() {
		for (Entry<String, IExecutionEngine<?>> engineEntry : Activator.getDefault.gemocRunningEngineRegistry.
			runningEngines.entrySet) {
			switch (engineEntry.value.runningStatus) {
				case Stopped: {
					Activator.getDefault.gemocRunningEngineRegistry.unregisterEngine(engineEntry.key)
				}
				default: {
				}
			}
		}
	}

	def clearCommandStackAndAdapters() {
		TransactionUtil.getEditingDomain(executionContext.resourceModel).commandStack.flush
		executionContext.resourceModel.eAdapters.clear
		executionContext.resourceModel.unload
	}

}
