package org.eclipse.gemoc.executionframework.property.monitor.esper.properties

import java.util.List
import java.util.Map
import org.eclipse.gemoc.executionframework.property.model.property.Universality
import org.eclipse.gemoc.executionframework.property.monitor.esper.TruthValue
import org.eclipse.viatra.query.patternlanguage.emf.specification.SpecificationBuilder
import org.eclipse.viatra.query.runtime.api.IQuerySpecification

class AlwaysPGlobally extends AbstractTemporalProperty {
	
	val IQuerySpecification<?> p
	
	new(SpecificationBuilder builder, String name, Universality always) {
		super(builder, name)
		p = builder.getOrCreateSpecification(always.pattern)
		queries.put(p.fullyQualifiedName, p)
	}
	
	override protected String getStatementString() {
		val pFqn = p.fqn
		val result =
			'''
				select * from «name»
				match_recognize (
					measures nP as nP, EoE as EoE
					pattern (P* (EoE | nP))
					define
						P as P.«pFqn»? is not null,
						nP as nP.«pFqn»? is null,
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