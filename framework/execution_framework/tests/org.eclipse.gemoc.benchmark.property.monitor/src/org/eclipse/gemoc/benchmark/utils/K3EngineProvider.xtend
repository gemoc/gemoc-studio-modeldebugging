package org.eclipse.gemoc.benchmark.utils

import org.eclipse.gemoc.execution.sequential.javaengine.K3RunConfiguration
import org.eclipse.gemoc.execution.sequential.javaengine.PlainK3ExecutionEngine
import org.eclipse.gemoc.execution.sequential.javaengine.SequentialModelExecutionContext
import org.eclipse.gemoc.xdsmlframework.api.core.ExecutionMode

class K3EngineProvider extends AbstractEngineProvider<PlainK3ExecutionEngine, SequentialModelExecutionContext<K3RunConfiguration>, K3RunConfiguration> {

	override protected getExecutionContext(K3RunConfiguration runConfiguration,
		ExecutionMode executionMode) {
		return new SequentialModelExecutionContext<K3RunConfiguration>(runConfiguration, executionMode);
	}

	override protected getExecutionEngine() {
		return new PlainK3ExecutionEngine
	}

}
