package org.eclipse.gemoc.executionframework.property.monitor.esper;

import java.util.Arrays;
import java.util.Set;

public class ComplexPatternMatchEvent {

	private PatternMatchEvent[] matchEvents;
	private final PatternMatchEvent[] sampleArray = new PatternMatchEvent[0];
	
	public ComplexPatternMatchEvent(Set<PatternMatchEvent> matchEvents) {
		this.matchEvents = matchEvents.toArray(sampleArray);
	}
	
	public PatternMatchEvent[] getMatchEvents() {
		return matchEvents;
	}
	
	@Override
	public String toString() {
		return Arrays.stream(matchEvents).map(e -> e.getPatternName()).reduce((s1, s2) -> s1 + " " + s2).orElse("");
	}
}
