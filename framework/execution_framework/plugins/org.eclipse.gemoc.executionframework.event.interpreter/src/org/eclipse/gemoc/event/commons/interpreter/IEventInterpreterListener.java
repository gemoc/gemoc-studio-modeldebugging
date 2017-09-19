package org.eclipse.gemoc.event.commons.interpreter;

import org.eclipse.gemoc.executionframework.event.model.scenario.Event;

public interface IEventInterpreterListener {
	
	public void eventReceived(Event event);
}
