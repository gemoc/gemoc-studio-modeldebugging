/*******************************************************************************
 * Copyright (c) 2017 Inria and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Inria - initial API and implementation
 *******************************************************************************/
package org.eclipse.gemoc.executionframework.event.generator

import java.io.ByteArrayInputStream
import java.io.Closeable
import java.io.IOException
import java.io.InputStream
import org.apache.log4j.Logger
import org.eclipse.core.resources.IContainer
import org.eclipse.core.resources.IFile
import org.eclipse.core.resources.IProject
import org.eclipse.core.resources.ResourcesPlugin
import org.eclipse.core.runtime.CoreException
import org.eclipse.core.runtime.IPath
import org.eclipse.core.runtime.IProgressMonitor
import org.eclipse.core.runtime.NullProgressMonitor
import org.eclipse.core.runtime.Path
import org.eclipse.core.runtime.SubProgressMonitor
import org.eclipse.jdt.core.JavaCore
import org.eclipse.pde.internal.core.natures.PDE

/**
 * A collection of utilities around Eclipse's APIs to manage the creation,
 * maintenance, etc. of the Eclipse projects/MANIFEST/plugin.xml generated
 * by Melange.
 */
class EclipseProjectHelper
{
	private static final Logger log = Logger.getLogger(EclipseProjectHelper)

	private def void closeQuietly(Closeable c) {
		if (c === null)
			return;

		try {
			c.close
		} catch (IOException e) {
			log.error("Couldn't close resource", e)
		}
	}
	
	def IProject createEMFEventProject(String dslName) {
		try {
			// FIXME: Everything's hardcoded...
			val project = createEclipseProject(
				dslName + ".event",
				#[JavaCore::NATURE_ID, PDE::PLUGIN_NATURE],
				#[JavaCore::BUILDER_ID,	PDE::MANIFEST_BUILDER_ID, PDE::SCHEMA_BUILDER_ID],
				#["src"],
				#[],
				#[],
				#[],
				#[],
				new NullProgressMonitor
			)

			val modelFolder = project.getFolder("model")
			modelFolder.create(false, true, null)

			log.debug('''Event EMF project «project» created.''')

			return project
		} catch (Exception e) {
			log.error("Unexpected exception while creating new event project", e)
		}

		return null
	}
	
	def IProject createEventInterpreterProject(String dslName) {
		try {
			val projectName = dslName + ".eventinterpreter"
			val project = createEclipseProject(
				projectName,
				#[JavaCore::NATURE_ID, PDE::PLUGIN_NATURE],
				#[JavaCore::BUILDER_ID,	PDE::MANIFEST_BUILDER_ID, PDE::SCHEMA_BUILDER_ID],
				#["src"],
				#[],
				#["org.eclipse.gemoc.executionframework.event.interpreter",
					"org.eclipse.gemoc.trace.commons.model",
					"org.eclipse.gemoc.xdsmlframework.api",
					dslName + ".event"],
				#[projectName],
				#[],
				new NullProgressMonitor
			)

			log.debug('''Event manager EMF project «project» created.''')

			return project
		} catch (Exception e) {
			log.error("Unexpected exception while creating new reactive interface project", e)
		}

		return null
	}

	/**
	 * Wololo, wololo wololo.
	 */
	def private IProject createEclipseProject(
		String name,
		Iterable<String> natures,
		Iterable<String> builders,
		Iterable<String> srcFolders,
		Iterable<IProject> referencedProjects,
		Iterable<String> requiredBundles,
		Iterable<String> exportedPackages,
		Iterable<String> extensions,
		IProgressMonitor monitor
	) {
		try {
			monitor.beginTask("", 10)
			monitor.subTask("Creating project " + name)

			val workspace = ResourcesPlugin.workspace
			val project = workspace.root.getProject(name)

			var IPath previousProjectLocation = null
			if (project.exists){
				previousProjectLocation = project.location
				project.delete(true, true, new SubProgressMonitor(monitor, 1))
			}

			val javaProject = JavaCore::create(project)
			val description = workspace.newProjectDescription(name)

			description.location = previousProjectLocation
			project.create(description, new SubProgressMonitor(monitor, 1))

			val classpathEntries = newArrayList

			if (!referencedProjects.empty) {
				description.referencedProjects = referencedProjects
				classpathEntries += referencedProjects.map[
					JavaCore::newProjectEntry(fullPath)
				]
			}

			description.natureIds = natures
			description.buildSpec = builders.map[buildName |
				description.newCommand => [builderName = buildName]
			]

			project.open(new SubProgressMonitor(monitor, 1))
			project.setDescription(description, new SubProgressMonitor(monitor, 1))

			srcFolders.forEach[src |
				val container = project.getFolder(src)

				try {
					if (!container.exists)
						container.create(false, true, new SubProgressMonitor(monitor, 1))

					classpathEntries.add(0, JavaCore::newSourceEntry(container.fullPath))
				} catch (CoreException e) {
					log.error("Couldn't update project classpath", e)
				}
			]
		
			classpathEntries += JavaCore::newContainerEntry(new Path("org.eclipse.jdt.launching.JRE_CONTAINER"))
			classpathEntries += JavaCore::newContainerEntry(new Path("org.eclipse.pde.core.requiredPlugins"))

			val binFolder = project.getFolder("bin")
			binFolder.create(false, true, new SubProgressMonitor(monitor, 1))
			javaProject.setRawClasspath(classpathEntries, new SubProgressMonitor(monitor, 1))
			javaProject.setOutputLocation(binFolder.fullPath, new SubProgressMonitor(monitor, 1))

			createManifest(name, requiredBundles, exportedPackages, monitor, project)
			createPluginXml(project, extensions, monitor)
			createBuildProperties(project, srcFolders, monitor)

			return project
		} catch (Exception e) {
			log.error("Unexpected exception while generating new project", e)
		}

		return null
	}

	def private void createManifest(
		String name,
		Iterable<String> requiredBundles,
		Iterable<String> exportedPackages,
		IProgressMonitor monitor,
		IProject project
	) throws CoreException {
		val content = '''
			Manifest-Version: 1.0
			Bundle-ManifestVersion: 2
			Bundle-Name: «name»
			Bundle-SymbolicName: «name»;singleton:=true
			Bundle-Version: 0.1.0
			«IF !requiredBundles.empty»
			Require-Bundle: «FOR b : requiredBundles SEPARATOR ",\n  "»«b»«ENDFOR»
			«ENDIF»
			«IF !exportedPackages.empty»
			Export-Package: «FOR p : exportedPackages SEPARATOR ",\n  "»«p»«ENDFOR»
			«ENDIF»
			Bundle-RequiredExecutionEnvironment: JavaSE-1.8

		'''

		val metaInf = project.getFolder("META-INF")
		metaInf.create(false, true, new SubProgressMonitor(monitor, 1))
		createFile("MANIFEST.MF", metaInf, content, monitor)
	}

	def private void createPluginXml(
		IProject project,
		Iterable<String> extensions,
		IProgressMonitor monitor
	) {
		val content = '''
			<?xml version="1.0" encoding="UTF-8"?>
			<?eclipse version="3.0"?>

			<plugin>

			«FOR e : extensions»«e»
			«ENDFOR»

			</plugin>
		'''
		createFile("plugin.xml", project, content, monitor)
	}

	def private void createBuildProperties(
		IProject project,
		Iterable<String> srcFolders,
		IProgressMonitor monitor
	) {
		val content = '''
			source.. = «FOR f : srcFolders SEPARATOR ",\\n  "»«f»«ENDFOR»
			bin.includes = META-INF/,\
			  .
		'''

		createFile("build.properties", project, content, monitor)
	}

	def private IFile createFile(
		String name,
		IContainer container,
		String content,
		IProgressMonitor monitor
	) {
		val f = container.getFile(new Path(name))
		var InputStream stream = null

		try {
			stream = new ByteArrayInputStream(content.getBytes(f.getCharset))
			if (f.exists)
				f.setContents(stream, true, true, monitor)
			else
				f.create(stream, true, monitor)
		} catch (Exception e) {
			log.error("Error while creating new IFile", e)
		} finally {
			stream.closeQuietly
		}

		monitor.worked(1)
		return f
	}
}
