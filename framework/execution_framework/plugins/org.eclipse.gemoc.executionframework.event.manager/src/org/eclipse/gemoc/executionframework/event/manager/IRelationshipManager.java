package org.eclipse.gemoc.executionframework.event.manager;

import java.util.Set;

import org.eclipse.gemoc.executionframework.behavioralinterface.behavioralInterface.Event;
import org.eclipse.gemoc.executionframework.event.model.event.EventOccurrence;

public interface IRelationshipManager {

	void registerImplementationRelationship(IImplementationRelationship implementationRelationship);
	
	void unregisterImplementationRelationship(IImplementationRelationship implementationRelationship);

	void registerSubtypingRelationship(ISubtypingRelationship subtypingRelationship);
	
	void unregisterSubtypingRelationship(ISubtypingRelationship subtypingRelationship);
	
	void notifyEventOccurrence(EventOccurrence eventOccurrence);
	
	void notifyCallRequest(ICallRequest callRequest);
	
	void notifyCall(BehavioralUnitNotification callNotification);

	Set<Event> getEvents();
}
