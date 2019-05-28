package org.eclipse.gemoc.executionframework.property.monitor.esper;

import org.eclipse.viatra.query.runtime.api.IPatternMatch;

public class PatternMatchEvent {

	private IPatternMatch match;

	private String patternName;
	
	private int status;

	public PatternMatchEvent(IPatternMatch patternMatch, String patternName, int status) {
		this.patternName = patternName;
		this.match = patternMatch;
		this.status = status;
	}

	@Override
	public String toString() {
		return "match " + status + " for " + patternName;
	}

	public IPatternMatch getMatch() {
		return match;
	}

	public String getPatternName() {
		return patternName;
	}
	
	public int getStatus() {
		return status;
	}
}
