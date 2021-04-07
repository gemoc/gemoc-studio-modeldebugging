package org.eclipse.gemoc.executionframework.event.manager;

import java.util.Map;
import java.util.function.Consumer;

import org.eclipse.gemoc.executionframework.behavioralinterface.behavioralInterface.BehavioralInterface;
import org.eclipse.gemoc.executionframework.behavioralinterface.behavioralInterface.Event;
import org.eclipse.gemoc.executionframework.event.model.event.EventFactory;
import org.eclipse.gemoc.executionframework.event.model.event.EventOccurrence;
import org.eclipse.gemoc.executionframework.event.model.event.EventOccurrenceArgument;
import org.eclipse.gemoc.executionframework.event.model.event.EventOccurrenceType;
import org.eclipse.gemoc.executionframework.value.model.value.Value;

public abstract class ImplementationRuleSubscriber {

	private BehavioralInterface behavioralInterface;
	
	private Consumer<ICallRequest> callRequestConsumer;

	private Consumer<EventOccurrence> eventOccurrenceConsumer;
	
	private Map<String, Event> behavioralUnitToEvent;

	abstract public String getStatement();

	protected final void consumeCallRequest(ICallRequest callRequest) {
		callRequestConsumer.accept(callRequest);
	}
	
	protected final void consumeEventOccurrence(EventOccurrence eventOccurrence) {
		eventOccurrenceConsumer.accept(eventOccurrence);
	}
	
	protected final EventOccurrence createEventOccurrence(String eventName, Map<String, Object> arguments) {
		final Event event = behavioralUnitToEvent.computeIfAbsent(eventName, k -> behavioralInterface.getEvents()
				.stream().filter(e -> e.getName().equals(k)).findFirst().orElse(null));
		if (event != null) {
			final EventOccurrence eventOccurrence = EventFactory.eINSTANCE.createEventOccurrence();
			eventOccurrence.setEvent(event);
			eventOccurrence.setType(EventOccurrenceType.EXPOSED);
			event.getParams().stream().forEach(p -> arguments.entrySet().stream()
					.filter(a -> p.getName().equals(a.getKey())).findFirst().ifPresent(a -> {
						final EventOccurrenceArgument arg = EventFactory.eINSTANCE.createEventOccurrenceArgument();
						arg.setValue((Value) EventManagerUtils.convertObjectToValue(a.getValue()));
						arg.setParameter(p);
						eventOccurrence.getArgs().add(arg);
					}));
			return eventOccurrence;
		}
		return null;
	}
	
	public void configure(BehavioralInterface behavioralInterface,
			Consumer<ICallRequest> callRequestConsumer,
			Consumer<EventOccurrence> eventOccurrenceConsumer,
			Map<String, Event> behavioralUnitToEvent) {
		this.behavioralInterface = behavioralInterface;
		this.callRequestConsumer = callRequestConsumer;
		this.eventOccurrenceConsumer = eventOccurrenceConsumer;
		this.behavioralUnitToEvent = behavioralUnitToEvent;
	}
}
