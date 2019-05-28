package org.eclipse.gemoc.executionframework.property.monitor.esper.properties

import java.util.List
import java.util.Map
import org.eclipse.gemoc.executionframework.property.model.property.Precedence
import org.eclipse.gemoc.executionframework.property.monitor.esper.AbstractTemporalProperty
import org.eclipse.gemoc.executionframework.property.monitor.esper.TruthValue
import org.eclipse.viatra.query.patternlanguage.emf.specification.SpecificationBuilder
import org.eclipse.viatra.query.runtime.api.IQuerySpecification

class SPrecedesPGlobally extends AbstractTemporalProperty {
	
	val IQuerySpecification<?> p
	val IQuerySpecification<?> s
	
	new(SpecificationBuilder builder, String name, Precedence precedence) {
		super(builder, name)
		p = builder.getOrCreateSpecification(precedence.pattern)
		s = builder.getOrCreateSpecification(precedence.otherPattern)
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
					measures P as P
					pattern (EoE | S | P)
					define
						P as P.«pFqn»? is not null,
						S as S.«sFqn»? is not null,
						EoE as EoE.executionAboutToStop? is not null
				)
			'''
		return result
	}
	
	override protected getStatus(Map<String, List<Map<?, ?>>> events) {
		val lP = events.get("P")
		val reachedP = !(lP === null || lP.empty)
		if (reachedP) {
			return TruthValue.VIOLATED
		} else {
			return TruthValue.SATISFIED
		}
	}
}