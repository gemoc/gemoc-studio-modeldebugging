package org.eclipse.gemoc.benchmark.utils

import java.io.InputStream
import java.net.URL
import java.util.Collection
import java.util.HashSet
import java.util.List
import java.util.Map
import java.util.Set
import org.eclipse.core.resources.IFile
import org.eclipse.core.resources.ResourcesPlugin
import org.eclipse.core.runtime.FileLocator
import org.eclipse.core.runtime.IProgressMonitor
import org.eclipse.core.runtime.Path
import org.eclipse.core.runtime.Platform
import org.eclipse.core.runtime.Status
import org.eclipse.core.runtime.jobs.Job
import org.eclipse.e4.core.internal.contexts.EclipseContext
import org.eclipse.emf.ecore.resource.ResourceSet
import org.eclipse.gemoc.executionframework.engine.Activator
import org.eclipse.gemoc.xdsmlframework.api.core.EngineStatus
import org.eclipse.ui.PlatformUI
import org.eclipse.xtext.util.SimpleAttributeResolver
import org.eclipse.xtext.util.SimpleCache
import org.osgi.framework.Bundle
import org.eclipse.e4.core.contexts.IEclipseContext

class Util {
	static def Long mean(Collection<Long> values) {
		val sum = values.reduce[p1, p2|p1 + p2]
		return sum / values.size
	}

	static def Long median(List<Long> values) {
		val sorted = values.sort
		val int middle = sorted.size / 2;
		if (sorted.size % 2 == 1) {
			return sorted.get(middle);
		} else {
			return (sorted.get(middle - 1) + sorted.get(middle)) / 2;
		}
	}

	static def String format(int value, int amountNumbers) {
		String.format("%0" + amountNumbers + "d", value);
	}

	static def void cleanup(String semanticsPlugin) {
		gemocCleanUp()
		k3CleanUp(semanticsPlugin)
		xtextCleanUp()
		eclipseCleanUp()
	}

	static Set<Map<?, ?>> k3Maps = new HashSet

	static def void gemocCleanUp() {
		val stoppedEnginesEntries = Activator.getDefault().gemocRunningEngineRegistry.getRunningEngines().entrySet().
			filter[it.value.getRunningStatus == EngineStatus.RunStatus.Stopped]
		val resourceSets = new HashSet<ResourceSet>
		for (stopped : stoppedEnginesEntries) {
			val resourceSet = stopped.getValue().executionContext.resourceModel.resourceSet
			resourceSets.add(resourceSet)
			for (resource : resourceSet.resources) {
				resource.eAdapters.clear()
				resource.unload()
			}
			stopped.getValue().dispose();
			Activator.getDefault().gemocRunningEngineRegistry.unregisterEngine(stopped.getKey());
		}
		for (resourceSet : resourceSets) {
			resourceSet.resources.clear
		}
	}

	static def void k3CleanUp(String semanticsPlugin) {

		// find all k3 maps
		if (k3Maps.empty) {
			val bundle = Platform::getBundle(semanticsPlugin)
			val bundleIterator = bundle.findEntries("/", "*Context.class", true)
			while (bundleIterator.hasMoreElements) {
				val URL bundleElement = bundleIterator.nextElement
				val className = bundleElement.file.replaceFirst("/bin/", "").replaceFirst(".class", "").
					replaceAll("/", ".")
				val Class<?> c = bundle.loadClass(className)
				if (c.fields.exists[it.name == "INSTANCE"]) {
					val instanceField = c.getField("INSTANCE")
					val instance = instanceField.get(c)
					val mapGetter = instance.class.methods.findFirst[it.name == "getMap"]
					val map = mapGetter.invoke(instance) as Map<?, ?>
					k3Maps.add(map)
				}
			}
		}

		// clear all maps
		for (m : k3Maps) {
			m.clear
		}
	}

	static def void xtextCleanUp() {
		val valueCacheField = SimpleAttributeResolver.getDeclaredField("valueCache")
		val attributeCacheField = SimpleAttributeResolver.getDeclaredField("attributeCache")
		valueCacheField.accessible = true
		attributeCacheField.accessible = true
		val valueCache = valueCacheField.get(SimpleAttributeResolver::NAME_RESOLVER) as SimpleCache<?, ?>
		val attributeCache = attributeCacheField.get(SimpleAttributeResolver::NAME_RESOLVER) as SimpleCache<?, ?>
		valueCache.clear
		attributeCache.clear
	}

	static def void eclipseCleanUp() {
		val workbench = PlatformUI::workbench
		val context = workbench.getService(IEclipseContext) as EclipseContext
		context.cleanup
	}

	static def cleanWorkspace() {
		val job = new Job("cleanup workspace") {
			override protected run(IProgressMonitor m) {
				for (p : ResourcesPlugin.getWorkspace().getRoot().projects) {
					println("cleanup " + p)
					p.delete(true, m)
				}
				return Status.OK_STATUS
			}
		}
		job.schedule
		EclipseTestUtil::waitForJobs
	}

	static def InputStream openFileFromPlugin(String pluginName, String pathInPlugin) {
		val Bundle bundle = Platform::getBundle(pluginName);
		val path = new Path(pathInPlugin)
		val InputStream stream = FileLocator::openStream(bundle, path, false);
		return stream
	}

	static def copyFileFromPlugin(String pluginName, String pathInPlugin, IFile targetFile, IProgressMonitor m) {
		val stream = openFileFromPlugin(pluginName, pathInPlugin)
		targetFile.create(stream, true, m);
		stream.close
	}

}
