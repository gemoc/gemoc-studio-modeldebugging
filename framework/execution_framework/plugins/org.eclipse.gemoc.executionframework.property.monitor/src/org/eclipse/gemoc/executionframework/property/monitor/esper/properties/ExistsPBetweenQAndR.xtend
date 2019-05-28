package org.eclipse.gemoc.executionframework.property.monitor.esper.properties

import java.util.List
import java.util.Map
import org.eclipse.gemoc.executionframework.property.model.property.Between
import org.eclipse.gemoc.executionframework.property.model.property.BoundType
import org.eclipse.gemoc.executionframework.property.model.property.Existence
import org.eclipse.gemoc.executionframework.property.monitor.esper.TruthValue
import org.eclipse.viatra.query.patternlanguage.emf.specification.SpecificationBuilder
import org.eclipse.viatra.query.runtime.api.IQuerySpecification

class ExistsPBetweenQAndR extends AbstractExistenceProperty {
	
	val IQuerySpecification<?> p
	val IQuerySpecification<?> q
	val IQuerySpecification<?> r
	
	new(SpecificationBuilder builder, String name, Existence exists, Between between) {
		super(builder, name, exists)
		p = builder.getOrCreateSpecification(exists.pattern)
		q = builder.getOrCreateSpecification(between.lowerBoundPattern)
		r = builder.getOrCreateSpecification(between.upperBoundPattern)
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
					measures P1 as P, P2 as PExcess, Q as Q, ExecEnd as EoE
					«pattern»
					define
						P as P.«pFqn»? is not null,
						P1 as P1.«pFqn»? is not null,
						P2 as P2.«pFqn»? is not null,
						nP as nP.«pFqn»? is null,
						Q as Q.«qFqn»? is not null,
						R as R.«rFqn»? is not null,
						ExecEnd as ExecEnd.executionAboutToStop? is not null
				)
			'''
		return result
	}
	
	private def String rec(int i) {
		'''
			«IF i == 0»
			nP*? (P«IF exists.boundType == BoundType.LOWER_BOUND»1«ELSEIF exists.boundType == BoundType.UPPER_BOUND»2«ENDIF» A?? (R | ExecEnd) | (R | ExecEnd))
			«ELSEIF i == 1»
			nP*? (P1«IF exists.boundType != BoundType.LOWER_BOUND» «rec(i - 1)»«ENDIF» | (R | ExecEnd))
			«ELSE»
			nP*? (P«IF exists.boundType == BoundType.UPPER_BOUND»1«ENDIF» «rec(i - 1)» | (R | ExecEnd))
			«ENDIF»
		'''
	}
	
	private def getPattern() {
		val pattern =
			'''
				pattern (Q «rec(exists.n)» | ExecEnd)
			'''
		println(pattern)
		return pattern
	}
	
	override protected getStatus(Map<String, List<Map<?, ?>>> events) {
		val lQ = events.get("Q")
		val reachedQ = !(lQ === null || lQ.empty)
		if (reachedQ) {
			val lR = events.get("R")
			val reachedR = !(lR === null || lR.empty)
			if (reachedR) {
				val lP = events.get("P")
				val reachedP = !(lP === null || lP.empty)
				if (exists.boundType == BoundType.UPPER_BOUND) {
					if (reachedP) {
						return TruthValue.UNKNOWN // Property not violated yet
					} else {
						val lPExcess = events.get("PExcess")
						val reachedPExcess = !(lPExcess === null || lPExcess.empty)
						if (reachedPExcess) {
							return TruthValue.VIOLATED
						} else {
							return TruthValue.UNKNOWN
						}
					}
				} else {
					if (reachedP) {
						return TruthValue.UNKNOWN
					} else {
						return TruthValue.VIOLATED
					}
				}
			} else {
				return TruthValue.SATISFIED
			}
		} else {
			return TruthValue.SATISFIED // EoE
		}
	}
}