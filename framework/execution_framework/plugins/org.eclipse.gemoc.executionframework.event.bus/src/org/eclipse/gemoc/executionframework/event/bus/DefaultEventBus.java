package org.eclipse.gemoc.executionframework.event.bus;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.gemoc.executionframework.event.model.event.Event;

public class DefaultEventBus implements IEventBus {

	private final Set<IEventBusListener> listeners = new HashSet<>();
	
	@Override
	public void emitEvent(Object emitter, Event event) {
		listeners.stream().filter(l -> l != emitter).forEach(l -> l.eventReceived(event));
	}

	@Override
	public void addListener(IEventBusListener listener) {
		listeners.add(listener);
	}

	@Override
	public void removeListener(IEventBusListener listener) {
		listeners.remove(listener);
	}

}
