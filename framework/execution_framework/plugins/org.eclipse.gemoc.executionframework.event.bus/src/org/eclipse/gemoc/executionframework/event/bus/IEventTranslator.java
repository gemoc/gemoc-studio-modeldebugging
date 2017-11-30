package org.eclipse.gemoc.executionframework.event.bus;

import java.util.Optional;

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.gemoc.executionframework.event.model.event.Event;

public interface IEventTranslator<T extends Event> {

	Optional<T> translateEvent(Event event, Resource targetModel);
}
