/*******************************************************************************
 * Copyright (c) 2016, 2017 Inria and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Inria - initial API and implementation
 *******************************************************************************/
package org.eclipse.gemoc.executionframework.event.generator

import java.io.IOException
import java.util.Map
import opsemanticsview.OperationalSemanticsView
import org.eclipse.core.resources.IFile
import org.eclipse.core.resources.IProject
import org.eclipse.core.runtime.CoreException
import org.eclipse.core.runtime.IProgressMonitor
import org.eclipse.core.runtime.Platform
import org.eclipse.core.runtime.Status
import org.eclipse.emf.common.util.URI
import org.eclipse.emf.ecore.EOperation
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl
import org.eclipse.gemoc.dsl.Dsl
import org.eclipse.gemoc.opsemanticsview.gen.OperationalSemanticsViewGenerator
import org.eclipse.jdt.core.IMethod

/**
 * Plenty of ways to call the generator in an eclipse context.
 * Relies on a OperationalSemanticsViewGenerator found using an extension point.
 */
class BehavioralInterfaceGeneratorIntegration {

	static def void generateAddon(IFile dslFile, String selectedLanguage, String basePluginName, boolean replace,
		IProgressMonitor monitor) {

		// Loading
		val selection = loadDsl(dslFile)

		// We find all extension points
		val configNew = Platform.getExtensionRegistry().getConfigurationElementsFor(
			"org.eclipse.gemoc.opsemanticsview.gen");

		// Using them, we instantiate objects and look for one that can work with the current selected language 
		val OperationalSemanticsViewGenerator validViewGenerator = configNew.map [ e |
			e.createExecutableExtension("class")
		].filter(OperationalSemanticsViewGenerator).findFirst [ conf |
			conf.canHandle(selection, dslFile.project)
		]

		// If we find one, we generate
		if (validViewGenerator !== null) {
			val OperationalSemanticsView mmextension = validViewGenerator.generate(selection, dslFile.project)
			generateAddon(dslFile.project, selectedLanguage, basePluginName, replace, monitor, mmextension,
				validViewGenerator.operationToMethod)
		} // Otherwise, we error
		else {
			throw new CoreException(new Status(
				Status.ERROR,
				"org.eclipse.gemoc.executionframework.event.generator",
				"Impossible to create a trace addon: couldn't find an opsemanticsview generator that can manage the chosen language."
			));
		}
	}

	/**
	 * Central operation of the class, that calls business operations
	 */
	static def void generateAddon(IProject project, String selectedLanguage, String basePluginName, boolean replace,
		IProgressMonitor monitor, OperationalSemanticsView executionExtension,
		Map<EOperation, IMethod> operationToMethod) throws CoreException {
		try {
			val BehavioralInterfaceGenerator behavioralInterfaceGenerator = new BehavioralInterfaceGenerator(project,
				selectedLanguage, executionExtension, basePluginName)
			val ImplementationRelationshipGenerator implementationRelationshipGenerator = new ImplementationRelationshipGenerator(
				project, selectedLanguage, executionExtension, basePluginName)
			behavioralInterfaceGenerator.generateBehavioralInterface
			implementationRelationshipGenerator.generateImplementationRelationship

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	protected static def Dsl loadDsl(IFile dslFile) {
		val Resource res = (new ResourceSetImpl()).getResource(URI.createURI(dslFile.getFullPath().toOSString()), true);
		val Dsl dsl = res.getContents().get(0) as Dsl;
		return dsl
	}

}
