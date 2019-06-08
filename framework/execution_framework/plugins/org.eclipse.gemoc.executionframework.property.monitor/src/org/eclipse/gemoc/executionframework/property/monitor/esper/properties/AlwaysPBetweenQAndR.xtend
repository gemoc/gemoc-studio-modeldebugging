package org.eclipse.gemoc.executionframework.property.monitor.esper.properties

import java.util.List
import java.util.Map
import org.eclipse.gemoc.executionframework.property.model.property.Between
import org.eclipse.gemoc.executionframework.property.model.property.Universality
import org.eclipse.gemoc.executionframework.property.monitor.esper.TruthValue
import org.eclipse.viatra.query.patternlanguage.emf.specification.SpecificationBuilder
import org.eclipse.viatra.query.runtime.api.IQuerySpecification

class AlwaysPBetweenQAndR extends AbstractTemporalProperty {
	
	val IQuerySpecification<?> p
	val IQuerySpecification<?> q
	val IQuerySpecification<?> r
	
	new(SpecificationBuilder builder, String name, Universality always, Between between) {
		super(builder, name)
		p = builder.getOrCreateSpecification(always.pattern)
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
					measures nP as nP, EoE as EoE
					pattern (EoE | (Q P*? nP*? P*? (EoE | R)))
					define
						P as P.«pFqn»? is not null,
						nP as nP.«pFqn»? is null,
						Q as Q.«qFqn»? is not null,
						R as R.«rFqn»? is not null,
						EoE as EoE.executionAboutToStop? is not null
				)
			'''
		return result
	}
	
	override protected getStatus(Map<String, List<Map<?, ?>>> events) {
		val execEnd = events.get("EoE")
		val lnP = events.get("nP")
		if ((execEnd === null || execEnd.empty)) {
			return if (lnP === null || lnP.empty) TruthValue.UNKNOWN else TruthValue.VIOLATED
		} else {
			return TruthValue.SATISFIED
		}
	}
	
}