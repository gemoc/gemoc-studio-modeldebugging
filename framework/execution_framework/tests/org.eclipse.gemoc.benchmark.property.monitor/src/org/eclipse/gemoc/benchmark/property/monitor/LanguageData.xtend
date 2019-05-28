package org.eclipse.gemoc.benchmark.property.monitor

import org.eclipse.gemoc.benchmark.languages.K3Language

class LanguageData {

	// Constants
	public static val String modelFolderName = "models"
	public static val String propertyFolderName = "properties"
	public static val String outputFolderName = "output"
	public static val int NBMEASURES = 1
	public static val String projectName = "benchmark-project"

	public static val fsm = new K3Language(
		"org.eclipse.gemoc.example.k3fsm.K3fsm",
		"public static void org.eclipse.gemoc.example.k3fsm.k3dsa.FSMAspect.main(org.eclipse.gemoc.example.k3fsm.FSM)",
		"org.eclipse.gemoc.example.k3fsm.k3dsa.FSMAspect.initializeModel",
		modelFolderName)
	
	public static val languages = #{
		fsm.name -> fsm
	}
}
