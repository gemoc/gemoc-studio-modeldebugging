package org.eclipse.gemoc.executionframework.event.bus;

import org.eclipse.gemoc.executionframework.event.model.event.Event;

public interface IEventBus {

	void emitEvent(Object emitter, Event event);

	void addListener(IEventBusListener listener);

	void removeListener(IEventBusListener listener);
}
