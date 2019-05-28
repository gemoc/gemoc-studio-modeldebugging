package org.eclipse.gemoc.benchmark.utils

import java.util.Set
import org.eclipse.debug.core.DebugPlugin
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy
import org.eclipse.emf.common.util.URI
import org.eclipse.gemoc.benchmark.languages.BenchmarkLanguage
import org.eclipse.gemoc.dsl.debug.ide.launch.AbstractDSLLaunchConfigurationDelegate
import org.eclipse.gemoc.xdsmlframework.api.core.IRunConfiguration
import org.eclipse.gemoc.xdsmlframework.api.extensions.engine_addon.EngineAddonSpecificationExtensionPoint

abstract class AbstractRunConfigurationProvider<R extends IRunConfiguration> {
	
	protected val ILaunchConfigurationWorkingCopy launchConfiguration

	new(String launchConfigurationType, URI model, BenchmarkLanguage language, Set<String> addonsToLoad) {
		val launchType = DebugPlugin.getDefault.launchManager.getLaunchConfigurationType(launchConfigurationType)
		launchConfiguration = launchType.newInstance(null, language.name)
		launchConfiguration.setAttribute(IRunConfiguration.LAUNCH_SELECTED_LANGUAGE, language.name)
		launchConfiguration.setAttribute(AbstractDSLLaunchConfigurationDelegate.RESOURCE_URI, model.toPlatformString(true))
		EngineAddonSpecificationExtensionPoint.specifications.filter[addonsToLoad.contains(it.id)].forEach[launchConfiguration.setAttribute(it.id, true)]
	}
	
	def R getLaunchConfiguration()
}
