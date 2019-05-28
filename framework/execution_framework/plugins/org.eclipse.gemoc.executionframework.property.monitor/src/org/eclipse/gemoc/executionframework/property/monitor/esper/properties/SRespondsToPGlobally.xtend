package org.eclipse.gemoc.executionframework.property.monitor.esper.properties

import java.util.List
import java.util.Map
import org.eclipse.gemoc.executionframework.property.model.property.Response
import org.eclipse.gemoc.executionframework.property.monitor.esper.TruthValue
import org.eclipse.viatra.query.patternlanguage.emf.specification.SpecificationBuilder
import org.eclipse.viatra.query.runtime.api.IQuerySpecification

class SRespondsToPGlobally extends AbstractTemporalProperty {
	
	val IQuerySpecification<?> p
	val IQuerySpecification<?> s
	
	new(SpecificationBuilder builder, String name, Response response) {
		super(builder, name)
		p = builder.getOrCreateSpecification(response.pattern)
		s = builder.getOrCreateSpecification(response.otherPattern)
		queries.put(p.fullyQualifiedName, p)
		queries.put(s.fullyQualifiedName, s)
	}
	
	override protected String getStatementString() {
		val pFqn = p.fqn
		val sFqn = s.fqn
		val result =
			'''
				select * from «name»
				match_recognize (
					measures P as P, S as S
					pattern (EoE | P nS*? (EoE | S))
					define
						P as P.«pFqn»? is not null,
						S as S.«sFqn»? is not null,
						nS as nS.«sFqn»? is null,
						EoE as EoE.executionAboutToStop? is not null
				)
			'''
		return result
	}
	
	override protected getStatus(Map<String, List<Map<?, ?>>> events) {
		val lP = events.get("P")
		val reachedP = !(lP === null || lP.empty)
		val lS = events.get("S")
		val reachedS = !(lS === null || lS.empty)
		if (reachedP) {
			if (reachedS) {
				return TruthValue.UNKNOWN
			} else {
				return TruthValue.VIOLATED
			}
		} else {
			if (reachedS) {
				return TruthValue.VIOLATED
			} else {
				return TruthValue.SATISFIED
			}
		}
	}
}