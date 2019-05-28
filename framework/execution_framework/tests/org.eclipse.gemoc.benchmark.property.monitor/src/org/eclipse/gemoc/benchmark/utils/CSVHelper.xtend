package org.eclipse.gemoc.benchmark.utils

import java.util.List
import java.util.ArrayList

class CSVHelper {

	public List<Long> totalExecutionTimes = new ArrayList
	public List<Long> initializationTimes = new ArrayList
	public Long memoryFootprint = 0L
	
	def exportExecutionTimes() {
		totalExecutionTimes.map[t|t/1000000.0].join("\n").replace(".", ",")
	}
	
	def exportInitializationTimes() {
		initializationTimes.map[t|t/1000000.0].join("\n").replace(".", ",")
	}
	
	def exportMemoryFootprint() {
		memoryFootprint
	}
}
