package org.eclipse.gemoc.executionframework.property.monitor.esper.properties

import java.util.List
import java.util.Map
import org.eclipse.gemoc.executionframework.property.model.property.After
import org.eclipse.gemoc.executionframework.property.model.property.Response
import org.eclipse.gemoc.executionframework.property.monitor.esper.TruthValue
import org.eclipse.viatra.query.patternlanguage.emf.specification.SpecificationBuilder
import org.eclipse.viatra.query.runtime.api.IQuerySpecification

class SRespondsToPAfterQ extends AbstractTemporalProperty {
	
	val IQuerySpecification<?> p
	val IQuerySpecification<?> s
	val IQuerySpecification<?> q
	
	new(SpecificationBuilder builder, String name, Response response, After after) {
		super(builder, name)
		p = builder.getOrCreateSpecification(response.pattern)
		s = builder.getOrCreateSpecification(response.otherPattern)
		q = builder.getOrCreateSpecification(after.lowerBoundPattern)
		queries.put(p.fullyQualifiedName, p)
		queries.put(q.fullyQualifiedName, q)
		queries.put(s.fullyQualifiedName, s)
	}
	
	override protected String getStatementString() {
		val pFqn = p.fqn
		val sFqn = s.fqn
		val qFqn = q.fqn
		val result =
			'''
				select * from «name»
				match_recognize (
					measures P as P, Q as Q, S as S
					pattern (EoE | Q nP* (P nS* S nP*)* (P nS*)? EoE)
					define
						P as P.«pFqn»? is not null,
						S as S.«sFqn»? is not null,
						Q as Q.«qFqn»? is not null,
						nP as nP.«pFqn»? is null,
						nS as nS.«sFqn»? is null,
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
		val lS = events.get("S")
		val nS = if (lS === null) 0 else lS.size
		if (reachedQ) {
			if (nP == nS) {
				return TruthValue.SATISFIED
			} else {
				return TruthValue.VIOLATED
			}
		} else {
			return TruthValue.SATISFIED
		}
	}
}