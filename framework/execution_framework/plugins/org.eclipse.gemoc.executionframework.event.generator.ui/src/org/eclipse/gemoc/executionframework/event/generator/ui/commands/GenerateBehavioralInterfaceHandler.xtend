package org.eclipse.gemoc.executionframework.event.generator.ui.commands

import org.eclipse.core.commands.ExecutionEvent
import org.eclipse.core.commands.ExecutionException
import org.eclipse.core.resources.IProject
import org.eclipse.emf.common.util.URI
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl
import org.eclipse.gemoc.dsl.Dsl
import org.eclipse.gemoc.executionframework.event.generator.EventMetamodelGenerator
import org.eclipse.gemoc.executionframework.event.generator.EventInterpreterGenerator
import org.eclipse.gemoc.xdsmlframework.ide.ui.commands.AbstractDslSelectHandler

class GenerateBehavioralInterfaceHandler extends AbstractDslSelectHandler {
	
	private val EventMetamodelGenerator eventMetamodelGenerator = new EventMetamodelGenerator
	
	private val EventInterpreterGenerator eventInterpreterGenerator = new EventInterpreterGenerator
	
	override executeForSelectedLanguage(ExecutionEvent event, IProject updatedGemocLanguageProject, String language) throws ExecutionException {
		val dslFile = getDslFileFromSelection(event);
		val res = (new ResourceSetImpl).getResource(URI.createURI(dslFile.getFullPath().toOSString()), true)
		val dsl = res.getContents().get(0) as Dsl
		eventMetamodelGenerator.generateBehavioralInterface(dsl, updatedGemocLanguageProject)
		eventInterpreterGenerator.generateEventInterpreter(dsl, updatedGemocLanguageProject)
		return null
	}
	
	override getSelectionMessage() {
		"This is the selection message"
	}
	
}