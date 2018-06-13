package org.eclipse.gemoc.executionframework.event.manager;

import org.eclipse.gemoc.executionframework.event.model.event.EventOccurrence;

public interface IEventManagerListener {
	
	public void eventReceived(EventOccurrence event);
}
