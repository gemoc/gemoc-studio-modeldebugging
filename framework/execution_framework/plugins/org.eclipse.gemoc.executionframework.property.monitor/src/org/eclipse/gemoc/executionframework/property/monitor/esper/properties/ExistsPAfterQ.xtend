package org.eclipse.gemoc.executionframework.property.monitor.esper.properties

import java.util.List
import java.util.Map
import org.eclipse.gemoc.executionframework.property.model.property.After
import org.eclipse.gemoc.executionframework.property.model.property.BoundType
import org.eclipse.gemoc.executionframework.property.model.property.Existence
import org.eclipse.gemoc.executionframework.property.monitor.esper.TruthValue
import org.eclipse.viatra.query.patternlanguage.emf.specification.SpecificationBuilder
import org.eclipse.viatra.query.runtime.api.IQuerySpecification

class ExistsPAfterQ extends AbstractExistenceProperty {
	
	val IQuerySpecification<?> p
	val IQuerySpecification<?> q
	
	new(SpecificationBuilder builder, String name, Existence exists, After after) {
		super(builder, name, exists)
		p = builder.getOrCreateSpecification(exists.pattern)
		q = builder.getOrCreateSpecification(after.lowerBoundPattern)
		queries.put(this.p.fullyQualifiedName, this.p)
		queries.put(this.q.fullyQualifiedName, this.q)
	}
	
	override protected String getStatementString() {
		val pFqn = p.fqn
		val qFqn = q.fqn
		val result =
			'''
				select * from «name»
				match_recognize (
					measures P1 as P, Q as Q, EoE as EoE
					«pattern»
					define
						P as P.«pFqn»? is not null,
						P1 as P1.«pFqn»? is not null,
						nP as nP.«pFqn»? is null,
						Q as Q.«qFqn»? is not null,
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
				pattern (Q «rec(exists.n)» | EoE)
			'''
		return pattern
	}
	
	override protected getStatus(Map<String, List<Map<?, ?>>> events) {
		val lP = events.get("P")
		val lQ = events.get("Q")
		val reachedP = !(lP === null || lP.empty)
		val reachedQ = !(lQ === null || lQ.empty)
		if (!reachedQ) {
			return TruthValue.SATISFIED
		} else if (exists.boundType == BoundType.LOWER_BOUND) {
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