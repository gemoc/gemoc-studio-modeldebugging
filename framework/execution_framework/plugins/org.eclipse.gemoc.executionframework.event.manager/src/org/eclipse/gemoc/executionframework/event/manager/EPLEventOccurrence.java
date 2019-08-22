package org.eclipse.gemoc.executionframework.event.manager;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.gemoc.executionframework.event.model.event.EventOccurrence;
import org.eclipse.gemoc.executionframework.event.model.event.EventOccurrenceType;
import org.eclipse.gemoc.executionframework.behavioralinterface.behavioralInterface.Event;

public class EPLEventOccurrence {

	private Event event;

	private EventOccurrenceType type;

	private Map<String, Object> args = new HashMap<>();

	public EPLEventOccurrence(EventOccurrence eventOccurrence) {
		event = eventOccurrence.getEvent();
		type = eventOccurrence.getType();
		eventOccurrence.getArgs().forEach(
				a -> args.put(a.getParameter().getName(), EventManagerUtils.convertValueToObject(a.getValue())));
	}
	
	public Event getEvent() {
		return event;
	}
	
	public EventOccurrenceType getType() {
		return type;
	}
	
	public Map<String, Object> getArgs() {
		return args;
	}
}
