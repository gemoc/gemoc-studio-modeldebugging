package org.eclipse.gemoc.benchmark.utils

import java.util.Set
import org.eclipse.emf.common.util.URI
import org.eclipse.gemoc.benchmark.languages.K3Language
import org.eclipse.gemoc.execution.sequential.javaengine.K3RunConfiguration

class K3RunConfigurationProvider extends AbstractRunConfigurationProvider<K3RunConfiguration> {
	
	new(URI entryPointElement, String initializationArguments, K3Language language, Set<String> addonIds) {
		super("org.eclipse.gemoc.execution.sequential.javaengine.ui.launcher", entryPointElement.trimFragment, language, addonIds)
		launchConfiguration.setAttribute(K3RunConfiguration.LAUNCH_METHOD_ENTRY_POINT, language.entryPoint)
		launchConfiguration.setAttribute(K3RunConfiguration.LAUNCH_INITIALIZATION_METHOD, language.initializationMethod)
		launchConfiguration.setAttribute(K3RunConfiguration.LAUNCH_INITIALIZATION_ARGUMENTS, initializationArguments)
		launchConfiguration.setAttribute(K3RunConfiguration.LAUNCH_MODEL_ENTRY_POINT, "/")
	}
	
	override getLaunchConfiguration() {
		return new K3RunConfiguration(launchConfiguration)
	}
}