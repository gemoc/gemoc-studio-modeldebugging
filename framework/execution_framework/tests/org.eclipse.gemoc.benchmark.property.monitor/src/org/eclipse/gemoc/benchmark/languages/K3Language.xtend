package org.eclipse.gemoc.benchmark.languages

class K3Language extends BenchmarkLanguage {

	val String entryPoint
	
	val String initializationMethod

	new(String name, String entryPoint, String initializationMethod, String folderName) {
		super(name, folderName)
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
