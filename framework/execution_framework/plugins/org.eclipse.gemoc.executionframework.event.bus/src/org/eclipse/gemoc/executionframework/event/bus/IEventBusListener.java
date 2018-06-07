package org.eclipse.gemoc.executionframework.event.bus;

import org.eclipse.gemoc.executionframework.event.model.event.EventOccurrence;

public interface IEventBusListener {

	void eventReceived(EventOccurrence event);
}
