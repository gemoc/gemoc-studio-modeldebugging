package org.eclipse.gemoc.benchmark.languages

abstract class BenchmarkLanguage {

	val String name
	
	val String folderName

	new(String name, String folderName) {
		this.name = name
		this.folderName = folderName
	}
	
	def String getName() {
		return name
	}

	def String getFolderName() {
		return folderName
	}

}
