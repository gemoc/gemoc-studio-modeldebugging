package org.eclipse.gemoc.executionframework.event.manager;

import java.util.Set;

import org.eclipse.gemoc.executionframework.behavioralinterface.behavioralInterface.BehavioralInterface;
import org.eclipse.gemoc.executionframework.event.model.event.EventOccurrence;

public interface IEventManagerListener {
	
	void eventReceived(EventOccurrence event);
	
	Set<BehavioralInterface> getBehavioralInterfaces();
}
