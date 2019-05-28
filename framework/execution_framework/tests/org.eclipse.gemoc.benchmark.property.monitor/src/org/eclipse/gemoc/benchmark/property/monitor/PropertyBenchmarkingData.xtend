package org.eclipse.gemoc.benchmark.property.monitor

import java.util.List
import java.util.Map
import org.eclipse.gemoc.benchmark.languages.BenchmarkLanguage

import static org.eclipse.gemoc.benchmark.property.monitor.LanguageData.*

class PropertyBenchmarkingData {
	
	static def getPropertyBenchmarkingCases() {
		cases.entrySet.map[caseEntry|
			val language = caseEntry.key
			caseEntry.value.entrySet.map[modelEntry|
				val model = modelEntry.key
				modelEntry.value.entrySet.map[argEntry|
					val arg = argEntry.key
					argEntry.value.map[property|
						val Object[] result = #[language.class.simpleName, language, model, arg, property]
						return result
					]
				].flatten
			].flatten
		].flatten.toList
	}
	
	static def String multiply(String baseString, int times) {
		return '''«FOR int i : 1..times»«baseString»«ENDFOR»'''
	}
	
	static val String[] inputs = #[
		"abababababab",
		"abababababab".multiply(10),
		"abababababab".multiply(100),
		"abababababab".multiply(1000)
	]
	
	static val Map<BenchmarkLanguage, Map<String, Map<String, List<String>>>> cases = #{
		fsm -> #{
			"TwoStatesUpcast.k3fsm" -> #{
//				inputs.get(0) -> #[
//					"none",
//					"exists_p_after_q.property",
//					"exists_p_between_q_and_r_RIGHT.property"
//				],
//				inputs.get(1) -> #[
//					"none",
//					"exists_p_after_q.property",
//					"exists_p_between_q_and_r_RIGHT.property"
//				],
//				inputs.get(2) -> #[
//					"none",
//					"exists_p_after_q.property",
//					"exists_p_between_q_and_r_RIGHT.property"
//				],
				inputs.get(3) -> #[
//					"none",
//					"exists_p_after_q.property",
					"exists_p_between_q_and_r_RIGHT.property"
				]
			}
		}
	}
}
