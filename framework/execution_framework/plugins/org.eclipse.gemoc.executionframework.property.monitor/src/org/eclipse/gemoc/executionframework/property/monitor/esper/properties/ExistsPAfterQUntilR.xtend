package org.eclipse.gemoc.executionframework.property.monitor.esper.properties

import java.util.List
import java.util.Map
import org.eclipse.gemoc.executionframework.property.model.property.AfterUntil
import org.eclipse.gemoc.executionframework.property.model.property.BoundType
import org.eclipse.gemoc.executionframework.property.model.property.Existence
import org.eclipse.gemoc.executionframework.property.monitor.esper.TruthValue
import org.eclipse.viatra.query.patternlanguage.emf.specification.SpecificationBuilder
import org.eclipse.viatra.query.runtime.api.IQuerySpecification

class ExistsPAfterQUntilR extends AbstractExistenceProperty {
	
	val IQuerySpecification<?> p
	val IQuerySpecification<?> q
	val IQuerySpecification<?> r
	
	new(SpecificationBuilder builder, String name, Existence exists, AfterUntil afterUntil) {
		super(builder, name, exists)
		p = builder.getOrCreateSpecification(exists.pattern)
		q = builder.getOrCreateSpecification(afterUntil.lowerBoundPattern)
		r = builder.getOrCreateSpecification(afterUntil.upperBoundPattern)
		queries.put(p.fullyQualifiedName, p)
		queries.put(q.fullyQualifiedName, q)
		queries.put(r.fullyQualifiedName, r)
	}
	
	override protected String getStatementString() {
		val pFqn = p.fqn
		val qFqn = q.fqn
		val rFqn = r.fqn
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
						R as R.«rFqn»? is not null,
						EoE as EoE.executionAboutToStop? is not null
				)
			'''
		return result
	}
	
	private def String rec(int i) {
		'''«IF i == 0»
			nP*? (P«IF exists.boundType == BoundType.LOWER_BOUND»1«ENDIF» | R | EoE)
			«ELSEIF i == 1»
			nP*? (P1«IF exists.boundType != BoundType.LOWER_BOUND» «rec(i - 1)»«ENDIF» | R | EoE)
			«ELSE»
			nP*? (P«IF exists.boundType == BoundType.UPPER_BOUND»1«ENDIF» «rec(i - 1)» | R | EoE)
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
		val lQ = events.get("Q")
		val reachedQ = !(lQ === null || lQ.empty)
		if (reachedQ) {
			val lP = events.get("P")
			val reachedP = !(lP === null || lP.empty)
			val execEnd = events.get("EoE")
			val reachedEoE = !(execEnd === null || execEnd.empty)
			if (reachedP) {
				if (reachedEoE) {
					return TruthValue.SATISFIED
				} else {
					return TruthValue.UNKNOWN
				}
			} else {
				if (exists.boundType == BoundType.UPPER_BOUND) {
					val lR = events.get("R")
					val reachedR = !(lR === null || lR.empty)
					if (reachedEoE || reachedR) {
						return TruthValue.SATISFIED // For upper bound, satisfied if no P1 found at all
					} else {
						return TruthValue.VIOLATED // But violated if a P (and not R or EoE) caused the final match
					}
				} else {
					return TruthValue.VIOLATED // Otherwise, violated
				}
			}
		} else {
			return TruthValue.SATISFIED // EoE
		}
	}
}