package org.eclipse.gemoc.executionframework.event.bus;

import java.util.Optional;

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.gemoc.executionframework.event.model.event.EventOccurrence;

public interface IEventTranslator {

	Optional<EventOccurrence> translateEvent(EventOccurrence event, Resource targetModel);
}
