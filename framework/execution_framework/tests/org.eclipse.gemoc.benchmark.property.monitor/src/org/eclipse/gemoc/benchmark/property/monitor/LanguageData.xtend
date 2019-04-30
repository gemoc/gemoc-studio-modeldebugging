package org.eclipse.gemoc.benchmark.property.monitor

import org.eclipse.gemoc.benchmark.languages.K3Language

class LanguageData {

	// Constants
	public static val String modelFolderName = "models"
	public static val String outputFolderName = "output"
	public static val int NBMEASURES = 1
	public static val String projectName = "benchmark-project"

	static val fsm = new K3Language(
		"fsm",
		"public static void org.eclipse.gemoc.example.k3fsm.k3dsa.FSMAspect.main(org.eclipse.gemoc.example.k3fsm.FSM)",
		"org.eclipse.gemoc.example.k3fsm.k3dsa.FSMAspect.initializeModel",
		#{"TwoStatesFsm.k3fsm"},
		modelFolderName)

	// Input data for all tests
	public static val tracingCases = #[
//		"Clone",
		"None"
//		"Generic",
//		"Generated"
	]
	
	public static val languages = #{fsm}
	
}
