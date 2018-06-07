package org.eclipse.gemoc.executionframework.event.bus;

import org.eclipse.gemoc.executionframework.event.model.event.EventOccurrence;

public interface IEventBus {

	void emitEvent(Object emitter, EventOccurrence event);

	void addListener(IEventBusListener listener);

	void removeListener(IEventBusListener listener);
}
