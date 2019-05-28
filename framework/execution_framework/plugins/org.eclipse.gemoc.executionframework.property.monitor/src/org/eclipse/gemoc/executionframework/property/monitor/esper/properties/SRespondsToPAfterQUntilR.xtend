package org.eclipse.gemoc.executionframework.property.monitor.esper.properties

import java.util.List
import java.util.Map
import org.eclipse.gemoc.executionframework.property.model.property.AfterUntil
import org.eclipse.gemoc.executionframework.property.model.property.Response
import org.eclipse.gemoc.executionframework.property.monitor.esper.TruthValue
import org.eclipse.viatra.query.patternlanguage.emf.specification.SpecificationBuilder
import org.eclipse.viatra.query.runtime.api.IQuerySpecification

class SRespondsToPAfterQUntilR extends AbstractTemporalProperty {
	
	val IQuerySpecification<?> p
	val IQuerySpecification<?> q
	val IQuerySpecification<?> r
	val IQuerySpecification<?> s
	
	new(SpecificationBuilder builder, String name, Response response, AfterUntil afterUntil) {
		super(builder, name)
		p = builder.getOrCreateSpecification(response.pattern)
		q = builder.getOrCreateSpecification(afterUntil.lowerBoundPattern)
		r = builder.getOrCreateSpecification(afterUntil.upperBoundPattern)
		s = builder.getOrCreateSpecification(response.otherPattern)
		queries.put(p.fullyQualifiedName, p)
		queries.put(q.fullyQualifiedName, q)
		queries.put(r.fullyQualifiedName, r)
		queries.put(s.fullyQualifiedName, s)
	}
	
	override protected String getStatementString() {
		val pFqn = p.fqn
		val qFqn = q.fqn
		val rFqn = r.fqn
		val sFqn = s.fqn
		val result =
			'''
				select * from «name»
				match_recognize (
					measures P as P, Q as Q, R as R, S as S
					pattern (EoE | Q nPR* (P nRS* S nPR*)* (P nRS*)? (R | EoE))
					define
						P as P.«pFqn»? is not null,
						Q as Q.«qFqn»? is not null,
						R as R.«rFqn»? is not null,
						S as S.«sFqn»? is not null,
						nPR as nPR.«pFqn»? is null and nPR.«rFqn»? is null,
						nRS as nRS.«rFqn»? is null and nRS.«sFqn»? is null,
						EoE as EoE.executionAboutToStop? is not null
				)
			'''
		return result
	}
	
	override protected getStatus(Map<String, List<Map<?, ?>>> events) {
		val lP = events.get("P")
		val nP = if (lP === null) 0 else lP.size
		val lQ = events.get("Q")
		val reachedQ = !(lQ === null || lQ.empty)
		val lR = events.get("R")
		val reachedR = !(lR === null || lR.empty)
		val lS = events.get("S")
		val nS = if (lS === null) 0 else lS.size
		if (reachedQ) {
			if (nP == nS) {
				if (reachedR) {
					return TruthValue.UNKNOWN
				} else {
					return TruthValue.SATISFIED
				}
			} else {
				return TruthValue.VIOLATED
			}
		} else {
			return TruthValue.SATISFIED
		}
	}
}