package org.eclipse.gemoc.benchmark.languages

import java.util.Set

abstract class BenchmarkLanguage {

	val String name
	
	val Set<String> models
	
	val String folderName
	

	new(String name, Set<String> models, String folderName) {
		this.name = name
		this.models = models
		this.folderName = folderName
	}

	def getModels() {
		return models
	}
	
	def String getName() {
		return name
	}

	def String getFolderName() {
		return folderName
	}

}
