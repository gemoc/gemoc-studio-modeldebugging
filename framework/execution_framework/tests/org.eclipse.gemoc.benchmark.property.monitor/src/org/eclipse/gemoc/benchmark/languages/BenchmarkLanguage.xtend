package org.eclipse.gemoc.benchmark.languages

abstract class BenchmarkLanguage {

	val String name
	
	new(String name) {
		this.name = name
	}
	
	def String getName() {
		return name
	}

}
