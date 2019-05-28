package org.eclipse.gemoc.executionframework.property.monitor.esper.properties

import org.eclipse.gemoc.executionframework.property.model.property.Existence
import org.eclipse.gemoc.executionframework.property.monitor.esper.AbstractTemporalProperty
import org.eclipse.viatra.query.patternlanguage.emf.specification.SpecificationBuilder

abstract class AbstractExistenceProperty extends AbstractTemporalProperty {
	
	protected val Existence exists
	
	new(SpecificationBuilder builder, String name, Existence exists) {
		super(builder, name)
		this.exists = exists
	}
	
	protected def getRange() {
		switch (exists.boundType) {
			case EXACT: {
				'''{«exists.n»}'''
			}
			case LOWER_BOUND: {
				'''{«exists.n», }'''
			}
			case UPPER_BOUND: {
				'''{ , «exists.n»}'''
			}
		}
	}
	
	protected def getComplementaryRange() {
		switch (exists.boundType) {
			case EXACT: {
				'''{ , «exists.n - 1»}'''
			}
			case LOWER_BOUND: {
				'''{ , «exists.n - 1»}'''
			}
			case UPPER_BOUND: {
				'''{«exists.n + 1», }'''
			}
			default: {
				""
			}
		}
	}
}