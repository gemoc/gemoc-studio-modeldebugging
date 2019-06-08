package org.eclipse.gemoc.executionframework.property.monitor.esper.properties

import org.eclipse.gemoc.executionframework.property.model.property.Existence
import org.eclipse.viatra.query.patternlanguage.emf.specification.SpecificationBuilder
import org.eclipse.viatra.query.runtime.api.IQuerySpecification
import java.util.Map
import java.util.List
import org.eclipse.gemoc.executionframework.property.model.property.BoundType
import org.eclipse.gemoc.executionframework.property.monitor.esper.TruthValue

class ExistsPGlobally extends AbstractExistenceProperty {
	
	val IQuerySpecification<?> p
	
	new(SpecificationBuilder builder, String name, Existence exists) {
		super(builder, name, exists)
		p = builder.getOrCreateSpecification(exists.pattern)
		queries.put(p.fullyQualifiedName, p)
	}
	
	override protected String getStatementString() {
		val pFqn = p.fqn
		val result =
			'''
				select * from «name»
				match_recognize (
					measures P1 as P, EoE as EoE
					«pattern»
					define
						P as P.«pFqn»? is not null,
						P1 as P1.«pFqn»? is not null,
						nP as nP.«pFqn»? is null,
						EoE as EoE.executionAboutToStop? is not null
				)
			'''
		return result
	}
	
	private def String rec(int i) {
		'''«IF i == 0»
			nP*? (P«IF exists.boundType == BoundType.LOWER_BOUND»1«ENDIF» | EoE)
			«ELSEIF i == 1»
			nP*? (P1«IF exists.boundType != BoundType.LOWER_BOUND» «rec(i - 1)»«ENDIF» | EoE)
			«ELSE»
			nP*? (P«IF exists.boundType == BoundType.UPPER_BOUND»1«ENDIF» «rec(i - 1)» | EoE)
			«ENDIF»'''
	}
	
	private def getPattern() {
		val pattern =
			'''
				pattern («rec(exists.n)»)
			'''
		return pattern
	}
	
	override protected getStatus(Map<String, List<Map<?, ?>>> events) {
		val lP = events.get("P")
		val reachedP = !(lP === null || lP.empty)
		if (exists.boundType == BoundType.LOWER_BOUND) {
			return if (reachedP) TruthValue.SATISFIED else TruthValue.VIOLATED
		} else {
			val execEnd = events.get("EoE")
			val reachedEoE = !(execEnd === null || execEnd.empty)
			if (exists.boundType == BoundType.UPPER_BOUND) {
				return if (reachedEoE) TruthValue.SATISFIED else TruthValue.VIOLATED
			} else {
				return if (reachedP && reachedEoE) TruthValue.SATISFIED else TruthValue.VIOLATED
			}
		}
	}
}