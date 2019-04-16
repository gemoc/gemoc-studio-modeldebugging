package org.eclipse.gemoc.executionframework.property.monitor.esper;

import org.eclipse.viatra.query.runtime.api.IPatternMatch;

public class PatternMatchEvent {

	private IPatternMatch match;

	private String patternName;
	
	private boolean appearance;

	public PatternMatchEvent(IPatternMatch patternMatch, String patternName, boolean appearance) {
		this.patternName = patternName;
		this.match = patternMatch;
		this.appearance = appearance;
	}

	@Override
	public String toString() {
		return "match for " + patternName;
	}

	public IPatternMatch getMatch() {
		return match;
	}

	public String getPatternName() {
		return patternName;
	}
	
	public boolean getAppearance() {
		return appearance;
	}
}
