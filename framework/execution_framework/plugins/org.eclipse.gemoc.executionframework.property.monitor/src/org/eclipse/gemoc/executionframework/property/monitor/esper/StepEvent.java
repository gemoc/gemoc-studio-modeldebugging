package org.eclipse.gemoc.executionframework.property.monitor.esper;

public class StepEvent {

	private final String name;
	
	private final boolean start;
	
	public StepEvent(String name, boolean start) {
		this.name = name;
		this.start = start;
	}
	
	public String getName() {
		return name;
	}
	
	public boolean getStart() {
		return start;
	}
}
