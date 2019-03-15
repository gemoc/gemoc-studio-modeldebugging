package org.eclipse.gemoc.executionframework.event.manager;

import java.util.Map;
import java.util.function.Consumer;

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.gemoc.executionframework.event.model.event.EventFactory;
import org.eclipse.gemoc.executionframework.event.model.event.EventOccurrence;
import org.eclipse.gemoc.executionframework.event.model.event.EventOccurrenceArgument;
import org.eclipse.gemoc.executionframework.event.model.event.EventOccurrenceType;
import org.eclipse.gemoc.xdsmlframework.behavioralinterface.behavioralInterface.BehavioralInterface;
import org.eclipse.gemoc.xdsmlframework.behavioralinterface.behavioralInterface.Event;

public abstract class SubtypingRuleSubscriber {

	private BehavioralInterface subtype;

	private BehavioralInterface supertype;

	private Consumer<EventOccurrence> eventOccurrenceConsumer;

	protected Resource executedResource;

	abstract public String getStatement();

	protected final void consumeEventOccurrence(EventOccurrence eventOccurrence) {
		eventOccurrenceConsumer.accept(eventOccurrence);
	}

	protected final EventOccurrence createAcceptedEventOccurrence(String eventName, Map<String, Object> arguments) {
		return createEventOccurrence(eventName, arguments, false);
	}

	protected final EventOccurrence createExposedEventOccurrence(String eventName, Map<String, Object> arguments) {
		return createEventOccurrence(eventName, arguments, true);
	}
	
	private EventOccurrence createEventOccurrence(String eventName, Map<String, Object> arguments, boolean exposed) {
		final Event event = (exposed ? supertype : subtype).getEvents().stream()
				.filter(e -> e.getName().equals(eventName)).findFirst().orElse(null);
		if (event != null) {
			final EventOccurrence eventOccurrence = EventFactory.eINSTANCE.createEventOccurrence();
			eventOccurrence.setEvent(event);
			eventOccurrence.setType(exposed ? EventOccurrenceType.EXPOSED : EventOccurrenceType.ACCEPTED);
			event.getParams().stream().forEach(p -> arguments.entrySet().stream()
					.filter(a -> p.getName().equals(a.getKey())).findFirst().ifPresent(a -> {
						final EventOccurrenceArgument arg = EventFactory.eINSTANCE.createEventOccurrenceArgument();
						arg.setValue(EventManagerUtils.convertObjectToValue(a.getValue()));
						arg.setParameter(p);
						eventOccurrence.getArgs().add(arg);
					}));
			return eventOccurrence;
		}
		return null;
	}

	public void configure(BehavioralInterface supertype, BehavioralInterface subtype,
			Consumer<EventOccurrence> eventOccurrenceConsumer, Resource executedResource) {
		this.supertype = supertype;
		this.subtype = subtype;
		this.eventOccurrenceConsumer = eventOccurrenceConsumer;
		this.executedResource = executedResource;
	}
}
