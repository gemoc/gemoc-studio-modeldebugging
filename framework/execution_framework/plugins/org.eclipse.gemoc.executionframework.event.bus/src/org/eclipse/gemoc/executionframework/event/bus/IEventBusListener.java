package org.eclipse.gemoc.executionframework.event.bus;

import org.eclipse.gemoc.executionframework.event.model.event.Event;

public interface IEventBusListener {

	void eventReceived(Event event);
}
