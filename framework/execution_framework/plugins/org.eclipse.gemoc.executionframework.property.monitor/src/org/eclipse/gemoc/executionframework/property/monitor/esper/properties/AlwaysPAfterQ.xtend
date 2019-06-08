package org.eclipse.gemoc.executionframework.property.monitor.esper.properties

import java.util.List
import java.util.Map
import org.eclipse.gemoc.executionframework.property.model.property.After
import org.eclipse.gemoc.executionframework.property.model.property.Universality
import org.eclipse.gemoc.executionframework.property.monitor.esper.TruthValue
import org.eclipse.viatra.query.patternlanguage.emf.specification.SpecificationBuilder
import org.eclipse.viatra.query.runtime.api.IQuerySpecification

class AlwaysPAfterQ extends AbstractTemporalProperty {
	
	val IQuerySpecification<?> p
	val IQuerySpecification<?> q
	
	new(SpecificationBuilder builder, String name, Universality prop, After after) {
		super(builder, name)
		p = builder.getOrCreateSpecification(prop.pattern)
		q = builder.getOrCreateSpecification(after.lowerBoundPattern)
		queries.put(p.fullyQualifiedName, p)
		queries.put(q.fullyQualifiedName, q)
	}
	
	override protected String getStatementString() {
		val pFqn = p.fqn
		val qFqn = q.fqn
		val result =
			'''
				select * from «name»
				match_recognize (
					measures nP as nP
					pattern (EoE | (Q P* (nP | EoE)))
					define
						P as P.«pFqn»? is not null,
						nP as nP.«pFqn»? is null,
						Q as Q.«qFqn»? is not null,
						EoE as EoE.executionAboutToStop? is not null
				)
			'''
		return result
	}
	
	override protected getStatus(Map<String, List<Map<?, ?>>> events) {
		val lnP = events.get("nP")
		return if (lnP === null || lnP.empty) TruthValue.SATISFIED else TruthValue.VIOLATED
	}
}