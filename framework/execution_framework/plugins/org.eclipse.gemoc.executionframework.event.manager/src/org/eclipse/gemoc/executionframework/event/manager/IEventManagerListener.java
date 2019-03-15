package org.eclipse.gemoc.executionframework.event.manager;

import java.util.Set;

import org.eclipse.gemoc.executionframework.event.model.event.EventOccurrence;
import org.eclipse.gemoc.xdsmlframework.behavioralinterface.behavioralInterface.BehavioralInterface;

public interface IEventManagerListener {
	
	void eventReceived(EventOccurrence event);
	
	Set<BehavioralInterface> getBehavioralInterfaces();
}
