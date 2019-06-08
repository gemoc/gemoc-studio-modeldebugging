package org.eclipse.gemoc.benchmark.utils

class BenchmarkAmplifier {
	
	def static void main(String[] args) {
//		println(amplifyModel(100, 1000))
//		println
		println(amplifyScopeQueries(50))
//		println
//		println(amplifyPatternQueries(50))
	}
	
	def static String amplifyModel(int n, int t) {
		'''
		activity benchmark {
			// constants
			int inc = 1,
			int maxLoop = «t-1»,
			int mod = 10,
			
			// variables
			int counterVar = 0,
			«IF n > 1»
			«FOR i : 0..n-1»
			int alwaysVar_«i» = 0,
			int existsVar_«i» = 0,
			int absenceVar_«i» = 0,
			int precedenceVar1_«i» = 0,
			int precedenceVar2_«i» = 0,
			int precedenceVar3_«i» = -«t - 9»,
			int precedenceVar4_«i» = -«t - 7»,
			int responseVar1_«i» = 0,
			int responseVar2_«i» = 0,
			int afterVar_«i» = -«t / 2»,
			int beforeVar_«i» = -«t - 5»,
			int betweenVar1_«i» = 0,
			int betweenVar2_«i» = 0,
			int afterUntilVar1_«i» = 0,
			int afterUntilVar2_«i» = 0,
			«ENDFOR»
			bool doneVar = false,
			bool notDoneVar = true
			
			nodes {
				initial initialNode out(edge01),
				merge mergeNode in (edge01, edge13) out (edge02),
				action counterAction comp {
					counterVar = counterVar + inc,
					doneVar = counterVar >= maxLoop,
					notDoneVar = !doneVar
				} in (edge02) out (edge03),
				action alwaysAction comp {
					«FOR i : 0..n-1 SEPARATOR ","»
					alwaysVar_«i» = counterVar % mod
					«ENDFOR»
				} in (edge03) out (edge04),
				action existsAction comp {
					«FOR i : 0..n-1 SEPARATOR ","»
					existsVar_«i» = counterVar % mod
					«ENDFOR»
				} in (edge04) out (edge05),
				action absenceAction comp {
					«FOR i : 0..n-1 SEPARATOR ","»
					absenceVar_«i» = counterVar % mod
					«ENDFOR»
				} in (edge05) out (edge06),
				action precedenceAction comp {
					«FOR i : 0..n-1 SEPARATOR ","»
					precedenceVar1_«i» = counterVar % mod,
					precedenceVar2_«i» = counterVar % mod,
					precedenceVar3_«i» = precedenceVar3_«i» + inc,
					precedenceVar4_«i» = precedenceVar4_«i» + inc
					«ENDFOR»
				} in (edge06) out (edge07),
				action responseAction comp {
					«FOR i : 0..n-1 SEPARATOR ","»
					responseVar1_«i» = counterVar % mod,
					responseVar2_«i» = counterVar % mod
					«ENDFOR»
				} in (edge07) out (edge08),
				action afterAction comp {
					«FOR i : 0..n-1 SEPARATOR ","»
					afterVar_«i» = afterVar_«i» + inc
					«ENDFOR»
				} in (edge08) out (edge09),
				action beforeAction comp {
					«FOR i : 0..n-1 SEPARATOR ","»
					beforeVar_«i» = beforeVar_«i» + inc
					«ENDFOR»
				} in (edge09) out (edge10),
				action betweenAction comp {
					«FOR i : 0..n-1 SEPARATOR ","»
					betweenVar1_«i» = counterVar % mod,
					betweenVar2_«i» = counterVar % mod
					«ENDFOR»
				} in (edge10) out (edge11),
				action afterUntilAction comp {
					«FOR i : 0..n-1 SEPARATOR ","»
					afterUntilVar1_«i» = counterVar % mod,
					afterUntilVar2_«i» = counterVar % mod
					«ENDFOR»
				} in (edge11) out (edge12),
				decision decisionNode in(edge12) out(edge13, edge14),
				final finalNode in(edge14)
			}
			«ELSE»
			int alwaysVar = 0,
			int existsVar = 0,
			int absenceVar = 0,
			int precedenceVar1 = 0,
			int precedenceVar2 = 0,
			int precedenceVar3 = 0,
			int precedenceVar4 = 0,
			int responseVar1 = 0,
			int responseVar2 = 0,
			int afterVar = -50,
			int beforeVar = -95,
			int betweenVar1 = 0,
			int betweenVar2 = 0,
			int afterUntilVar1 = 0,
			int afterUntilVar2 = 0,
			bool doneVar = false,
			bool notDoneVar = true
			
			nodes {
				initial initialNode out(edge01),
				merge mergeNode in (edge01, edge13) out (edge02),
				action counterAction comp {
					counterVar = counterVar + inc,
					doneVar = counterVar >= maxLoop,
					notDoneVar = !doneVar
				} in (edge02) out (edge03),
				action alwaysAction comp {
					alwaysVar = counterVar % mod
				} in (edge03) out (edge04),
				action existsAction comp {
					existsVar = counterVar % mod
				} in (edge04) out (edge05),
				action absenceAction comp {
					absenceVar = counterVar % mod
				} in (edge05) out (edge06),
				action precedenceAction comp {
					precedenceVar1 = counterVar % mod,
					precedenceVar2 = counterVar % mod,
					precedenceVar3 = counterVar % mod,
					precedenceVar4 = counterVar % mod
				} in (edge06) out (edge07),
				action responseAction comp {
					responseVar1 = counterVar % mod,
					responseVar2 = counterVar % mod
				} in (edge07) out (edge08),
				action afterAction comp {
					afterVar = afterVar + inc
				} in (edge08) out (edge09),
				action beforeAction comp {
					beforeVar = beforeVar + inc
				} in (edge09) out (edge10),
				action betweenAction comp {
					betweenVar1 = counterVar % mod,
					betweenVar2 = counterVar % mod
				} in (edge10) out (edge11),
				action afterUntilAction comp {
					afterUntilVar1 = counterVar % mod,
					afterUntilVar2 = counterVar % mod
				} in (edge11) out (edge12),
				decision decisionNode in(edge12) out(edge13, edge14),
				final finalNode in(edge14)
			}
			«ENDIF»
			
			edges {
				flow edge01 from initialNode to mergeNode,
				flow edge02 from mergeNode to counterAction,
				flow edge03 from counterAction to alwaysAction,
				flow edge04 from alwaysAction to existsAction,
				flow edge05 from existsAction to absenceAction,
				flow edge06 from absenceAction to precedenceAction,
				flow edge07 from precedenceAction to responseAction,
				flow edge08 from responseAction to afterAction,
				flow edge09 from afterAction to beforeAction,
				flow edge10 from beforeAction to betweenAction,
				flow edge11 from betweenAction to afterUntilAction,
				flow edge12 from afterUntilAction to decisionNode,
				flow edge13 from decisionNode to mergeNode [notDoneVar],
				flow edge14 from decisionNode to finalNode [doneVar]
			}
		}
		'''
	}
	
	def static String amplifyScopeQueries(int n) {
		'''
		package ad.queries«IF n > 1».x«n»«ENDIF»
		
		import "http://org.gemoc.activitydiagram.sequential.xactivitydiagram/activitydiagram/" as ad
		«IF n > 1»import java ^java.lang.String«ENDIF»
		
		«IF n > 1»
		pattern beforeScope() {
			«FOR i : 0..n-1»
			find beforeScopeAux(_, _, "beforeVar_«i»");
			«ENDFOR»
		}
		
		pattern afterScope() {
			«FOR i : 0..n-1»
			find afterScopeAux(_, _, "afterVar_«i»");
			«ENDFOR»
		}
		
		pattern between1Scope() {
			«FOR i : 0..n-1»
			find between1ScopeAux(_, _, "betweenVar1_«i»");
			«ENDFOR»
		}
		
		pattern between2Scope() {
			«FOR i : 0..n-1»
			find between2ScopeAux(_, _, "betweenVar2_«i»");
			«ENDFOR»
		}
		
		pattern afterUntil1Scope() {
			«FOR i : 0..n-1»
			find afterUntil1ScopeAux(_, _, "afterUntilVar1_«i»");
			«ENDFOR»
		}
		
		pattern afterUntil2Scope() {
			«FOR i : 0..n-1»
			find afterUntil2ScopeAux(_, _, "afterUntilVar2_«i»");
			«ENDFOR»
		}
		
		pattern beforeScopeAux(alwaysVar: IntegerVariable, varValue : IntegerValue, varName : java String) {
			IntegerVariable.name(alwaysVar, varName);
			IntegerVariable.currentValue(alwaysVar, varValue);
			IntegerValue.value(varValue, 0);
		}
		
		pattern afterScopeAux(alwaysVar: IntegerVariable, varValue : IntegerValue, varName : java String) {
			IntegerVariable.name(alwaysVar, varName);
			IntegerVariable.currentValue(alwaysVar, varValue);
			IntegerValue.value(varValue, 0);
		}
		
		pattern between1ScopeAux(alwaysVar: IntegerVariable, varValue : IntegerValue, varName : java String) {
			IntegerVariable.name(alwaysVar, varName);
			IntegerVariable.currentValue(alwaysVar, varValue);
			IntegerValue.value(varValue, 0);
		}
		
		pattern between2ScopeAux(alwaysVar: IntegerVariable, varValue : IntegerValue, varName : java String) {
			IntegerVariable.name(alwaysVar, varName);
			IntegerVariable.currentValue(alwaysVar, varValue);
			IntegerValue.value(varValue, 5);
		}
		
		pattern afterUntil1ScopeAux(alwaysVar: IntegerVariable, varValue : IntegerValue, varName : java String) {
			IntegerVariable.name(alwaysVar, varName);
			IntegerVariable.currentValue(alwaysVar, varValue);
			IntegerValue.value(varValue, 0);
		}
		
		pattern afterUntil2ScopeAux(alwaysVar: IntegerVariable, varValue : IntegerValue, varName : java String) {
			IntegerVariable.name(alwaysVar, varName);
			IntegerVariable.currentValue(alwaysVar, varValue);
			IntegerValue.value(varValue, 5);
		}
		«ELSE»
		pattern beforeScope(alwaysVar: IntegerVariable, varValue : IntegerValue) {
			IntegerVariable.name(alwaysVar, "beforeVar_0");
			IntegerVariable.currentValue(alwaysVar, varValue);
			IntegerValue.value(varValue, 0);
		}
		
		pattern afterScope(alwaysVar: IntegerVariable, varValue : IntegerValue) {
			IntegerVariable.name(alwaysVar, "afterVar_0");
			IntegerVariable.currentValue(alwaysVar, varValue);
			IntegerValue.value(varValue, 0);
		}
		
		pattern between1Scope(alwaysVar: IntegerVariable, varValue : IntegerValue) {
			IntegerVariable.name(alwaysVar, "betweenVar1_0");
			IntegerVariable.currentValue(alwaysVar, varValue);
			IntegerValue.value(varValue, 0);
		}
		
		pattern between2Scope(alwaysVar: IntegerVariable, varValue : IntegerValue) {
			IntegerVariable.name(alwaysVar, "betweenVar2_0");
			IntegerVariable.currentValue(alwaysVar, varValue);
			IntegerValue.value(varValue, 5);
		}
		
		pattern afterUntil1Scope(alwaysVar: IntegerVariable, varValue : IntegerValue) {
			IntegerVariable.name(alwaysVar, "afterUntilVar1_0");
			IntegerVariable.currentValue(alwaysVar, varValue);
			IntegerValue.value(varValue, 0);
		}
		
		pattern afterUntil2Scope(alwaysVar: IntegerVariable, varValue : IntegerValue) {
			IntegerVariable.name(alwaysVar, "afterUntilVar2_0");
			IntegerVariable.currentValue(alwaysVar, varValue);
			IntegerValue.value(varValue, 5);
		}
		«ENDIF»
		'''
	}
	
	def static String amplifyPatternQueries(int n) {
		'''
		package ad.queries«IF n > 1».x«n»«ENDIF»
		
		import "http://org.gemoc.activitydiagram.sequential.xactivitydiagram/activitydiagram/" as ad
		«IF n > 1»import java ^java.lang.String«ENDIF»
		
		«IF n > 1»
		pattern alwaysPattern() {
			«FOR i : 0..n-1»
			find alwaysPatternAux(_, _, "alwaysVar_«i»");
			«ENDFOR»
		}
		
		pattern existsPattern() {
			«FOR i : 0..n-1»
			find existsPatternAux(_, _, "existsVar_«i»");
			«ENDFOR»
		}
		
		pattern neverPattern() {
			«FOR i : 0..n-1»
			find neverPatternAux(_, _, "neverVar_«i»");
			«ENDFOR»
		}
		
		pattern precedence1Pattern() {
			«FOR i : 0..n-1»
			find precedence1PatternAux(_, _, "precedenceVar1_«i»");
			«ENDFOR»
		}
		
		pattern precedence2Pattern() {
			«FOR i : 0..n-1»
			find precedence2PatternAux(_, _, "precedenceVar2_«i»");
			«ENDFOR»
		}
		
		pattern response1Pattern() {
			«FOR i : 0..n-1»
			find response1PatternAux(_, _, "responseVar1_«i»");
			«ENDFOR»
		}
		
		pattern response2Pattern() {
			«FOR i : 0..n-1»
			find response2PatternAux(_, _, "responseVar2_«i»");
			«ENDFOR»
		}
		
		pattern precedence3Pattern() {
			«FOR i : 0..n-1»
			find precedence3PatternAux(_, _, "precedenceVar3_«i»");
			«ENDFOR»
		}
		
		pattern precedence4Pattern() {
			«FOR i : 0..n-1»
			find precedence4PatternAux(_, _, "precedenceVar4_«i»");
			«ENDFOR»
		}
		
		pattern alwaysPatternAux(alwaysVar: IntegerVariable, varValue : IntegerValue, varName : java String) {
			IntegerVariable.name(alwaysVar, varName);
			IntegerVariable.currentValue(alwaysVar, varValue);
			neg IntegerValue.value(varValue, 10);
		}
		
		pattern existsPatternAux(alwaysVar: IntegerVariable, varValue : IntegerValue, varName : java String) {
			IntegerVariable.name(alwaysVar, varName);
			IntegerVariable.currentValue(alwaysVar, varValue);
			IntegerValue.value(varValue, 3);
		}
		
		pattern neverPatternAux(alwaysVar: IntegerVariable, varValue : IntegerValue, varName : java String) {
			IntegerVariable.name(alwaysVar, varName);
			IntegerVariable.currentValue(alwaysVar, varValue);
			neg IntegerValue.value(varValue, 10);
		}
		
		pattern precedence1PatternAux(alwaysVar: IntegerVariable, varValue : IntegerValue, varName : java String) {
			IntegerVariable.name(alwaysVar, varName);
			IntegerVariable.currentValue(alwaysVar, varValue);
			IntegerValue.value(varValue, 1);
		}
		
		pattern precedence2PatternAux(alwaysVar: IntegerVariable, varValue : IntegerValue, varName : java String) {
			IntegerVariable.name(alwaysVar, varName);
			IntegerVariable.currentValue(alwaysVar, varValue);
			IntegerValue.value(varValue, 4);
		}
		
		pattern response1PatternAux(alwaysVar: IntegerVariable, varValue : IntegerValue, varName : java String) {
			IntegerVariable.name(alwaysVar, varName);
			IntegerVariable.currentValue(alwaysVar, varValue);
			IntegerValue.value(varValue, 1);
		}
		
		pattern response2PatternAux(alwaysVar: IntegerVariable, varValue : IntegerValue, varName : java String) {
			IntegerVariable.name(alwaysVar, varName);
			IntegerVariable.currentValue(alwaysVar, varValue);
			IntegerValue.value(varValue, 4);
		}
		
		pattern precedence3PatternAux(alwaysVar: IntegerVariable, varValue : IntegerValue, varName : java String) {
			IntegerVariable.name(alwaysVar, varName);
			IntegerVariable.currentValue(alwaysVar, varValue);
			IntegerValue.value(varValue, 0);
		}
		
		pattern precedence4PatternAux(alwaysVar: IntegerVariable, varValue : IntegerValue, varName : java String) {
			IntegerVariable.name(alwaysVar, varName);
			IntegerVariable.currentValue(alwaysVar, varValue);
			IntegerValue.value(varValue, 0);
		}
		«ELSE»
		pattern alwaysPattern(alwaysVar: IntegerVariable, varValue : IntegerValue) {
			IntegerVariable.name(alwaysVar, "alwaysVar_0");
			IntegerVariable.currentValue(alwaysVar, varValue);
			neg IntegerValue.value(varValue, 10);
		}
		
		pattern existsPattern(alwaysVar: IntegerVariable, varValue : IntegerValue) {
			IntegerVariable.name(alwaysVar, "existsVar_0");
			IntegerVariable.currentValue(alwaysVar, varValue);
			IntegerValue.value(varValue, 3);
		}
		
		pattern neverPattern(alwaysVar: IntegerVariable, varValue : IntegerValue) {
			IntegerVariable.name(alwaysVar, "neverVar_0");
			IntegerVariable.currentValue(alwaysVar, varValue);
			neg IntegerValue.value(varValue, 10);
		}
		
		pattern precedence1Pattern(alwaysVar: IntegerVariable, varValue : IntegerValue) {
			IntegerVariable.name(alwaysVar, "precedenceVar1_0");
			IntegerVariable.currentValue(alwaysVar, varValue);
			IntegerValue.value(varValue, 1);
		}
		
		pattern precedence2Pattern(alwaysVar: IntegerVariable, varValue : IntegerValue) {
			IntegerVariable.name(alwaysVar, "precedenceVar2_0");
			IntegerVariable.currentValue(alwaysVar, varValue);
			IntegerValue.value(varValue, 4);
		}
		
		pattern response1Pattern(alwaysVar: IntegerVariable, varValue : IntegerValue) {
			IntegerVariable.name(alwaysVar, "responseVar1_0");
			IntegerVariable.currentValue(alwaysVar, varValue);
			IntegerValue.value(varValue, 1);
		}
		
		pattern response2Pattern(alwaysVar: IntegerVariable, varValue : IntegerValue) {
			IntegerVariable.name(alwaysVar, "responseVar2_0");
			IntegerVariable.currentValue(alwaysVar, varValue);
			IntegerValue.value(varValue, 4);
		}
		
		pattern precedence3Pattern(alwaysVar: IntegerVariable, varValue : IntegerValue) {
			IntegerVariable.name(alwaysVar, "precedenceVar3_0");
			IntegerVariable.currentValue(alwaysVar, varValue);
			IntegerValue.value(varValue, 0);
		}
		
		pattern precedence4Pattern(alwaysVar: IntegerVariable, varValue : IntegerValue) {
			IntegerVariable.name(alwaysVar, "precedenceVar4_0");
			IntegerVariable.currentValue(alwaysVar, varValue);
			IntegerValue.value(varValue, 0);
		}
		«ENDIF»
		'''
	}
	
}