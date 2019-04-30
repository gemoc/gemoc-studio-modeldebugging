package org.eclipse.gemoc.benchmark.languages

import java.util.Set

class K3Language extends BenchmarkLanguage {

	val String entryPoint
	
	val String initializationMethod

	new(String name, String entryPoint, String initializationMethod, Set<String> models, String folderName) {
		super(name, models, folderName)
		this.entryPoint = entryPoint
		this.initializationMethod = initializationMethod
	}

	def String getEntryPoint() {
		return entryPoint
	}

	def String getInitializationMethod() {
		return initializationMethod
	}

}
