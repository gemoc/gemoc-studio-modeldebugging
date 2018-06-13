package org.eclipse.gemoc.executionframework.event.generator.ui.commands

import org.eclipse.core.commands.ExecutionEvent
import org.eclipse.core.commands.ExecutionException
import org.eclipse.core.resources.IFile
import org.eclipse.core.resources.IProject
import org.eclipse.core.runtime.IProgressMonitor
import org.eclipse.core.runtime.Status
import org.eclipse.core.runtime.jobs.Job
import org.eclipse.gemoc.executionframework.event.generator.BehavioralInterfaceGeneratorIntegration
import org.eclipse.gemoc.xdsmlframework.ide.ui.commands.AbstractDslSelectHandler

class GenerateBehavioralInterfaceHandler extends AbstractDslSelectHandler {
	val static String pluginId = "org.eclipse.gemoc.executionframework.event.generator.ui"

	override executeForSelectedLanguage(ExecutionEvent event, IProject updatedGemocLanguageProject,
		String language) throws ExecutionException {
		val IFile dslFile = getDslFileFromSelection(event);
		val baseProjectName = dslFile.project.name

//		// If the base project name doesn't end with the language name, we suggest it		
//		val basePluginName = if (baseProjectName.endsWith(language.toLowerCase)) {
//				baseProjectName
//			} else {
//				baseProjectName + "." + language.toLowerCase
//			}

		val Job j = new Job("Generating behavioral interface for " + dslFile.toString) {
			override protected run(IProgressMonitor monitor) {
				try {

					BehavioralInterfaceGeneratorIntegration.generateAddon(dslFile, language, baseProjectName, true,
						monitor)

				} catch (Exception e) {
					return new Status(Status.ERROR, pluginId,
						"An error occured while generating the behavioral interface. Please expand below for the complete error stack trace.",
						e)
					}
					return new Status(Status.OK, pluginId, "Behavioral interface generated.")
				}
			}
			// And we start the job
			j.schedule

			return null;
		}

		override getSelectionMessage() {
			"This is the selection message"
		}

	}
	