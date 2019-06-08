package org.eclipse.gemoc.executionframework.property.monitor.esper.properties

import java.util.List
import java.util.Map
import org.eclipse.gemoc.executionframework.property.model.property.Between
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
					measures P as P, Q as Q, EoE as EoE
					«pattern»
					define
						P as P.«pFqn»? is not null,
						nPR as nPR.«pFqn»? is null and nPR.«rFqn»? is null,
						Q as Q.«qFqn»? is not null,
						R as R.«rFqn»? is not null,
						EoE as EoE.executionAboutToStop? is not null
				)
			'''
		return result
	}
	
	private def getPattern() {
		val pattern =
			'''
				pattern (EoE | Q nPR* (P nPR*)* (R | EoE))
			'''
		return pattern
	}
	
	override protected getStatus(Map<String, List<Map<?, ?>>> events) {
		val lQ = events.get("Q")
		val reachedQ = !(lQ === null || lQ.empty)
		if (reachedQ) {
			val lEoE = events.get("EoE")
			val reachedEoE = !(lEoE === null || lEoE.empty)
			if (reachedEoE) {
				return TruthValue.SATISFIED
			} else {
				val lP = events.get("P")
				val nP = if (lP === null) 0 else lP.size
				return switch (exists.boundType) {
					case EXACT: {
						if (nP == exists.n) TruthValue.UNKNOWN else TruthValue.VIOLATED
					}
					case LOWER_BOUND: {
						if (nP >= exists.n) TruthValue.UNKNOWN else TruthValue.VIOLATED
					}
					case UPPER_BOUND: {
						if (nP <= exists.n) TruthValue.UNKNOWN else TruthValue.VIOLATED
					}
				}
			}
		} else {
			return TruthValue.SATISFIED
		}
	}
}