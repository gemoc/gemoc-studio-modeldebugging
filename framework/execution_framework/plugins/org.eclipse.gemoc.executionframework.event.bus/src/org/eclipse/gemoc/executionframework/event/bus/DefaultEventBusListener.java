package org.eclipse.gemoc.executionframework.event.bus;

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.gemoc.executionframework.event.manager.EventManager;
import org.eclipse.gemoc.executionframework.event.manager.IEventManager;
import org.eclipse.gemoc.executionframework.event.model.event.EventOccurrence;
import org.eclipse.gemoc.xdsmlframework.api.core.IExecutionEngine;

/**
 * Instances of this class connect an execution engine to the event bus
 * through its event manager. The translateEvent method must be implemented,
 * translating events on the bus into events sent to the engine.
 * 
 * @author dorian
 *
 */
public class DefaultEventBusListener implements IEventBusListener {

	private final Resource resource;
	private final IEventManager eventManager;
	private final IEventTranslator eventTranslator;

	public DefaultEventBusListener(IExecutionEngine engine, IEventTranslator eventTranslator) {
		this.eventTranslator = eventTranslator;
		resource = engine.getExecutionContext().getResourceModel();
		eventManager = engine.getAddon(EventManager.class);
	}

	@Override
	public void eventReceived(EventOccurrence event) {
		eventTranslator.translateEvent(event, resource).ifPresent(evt -> eventManager.queueEvent(evt));
	}
}
