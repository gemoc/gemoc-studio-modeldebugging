package org.eclipse.gemoc.executionframework.event.manager;

import java.util.List;
import java.util.Set;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.gemoc.executionframework.event.model.event.EventOccurrence;
import org.eclipse.gemoc.xdsmlframework.api.engine_addon.IEngineAddon;

public interface IEventManager extends IEngineAddon {

	void queueEvent(EventOccurrence event);

	void processEvents();

	void waitForEvents();

	void addListener(IEventManagerListener listener);

	void removeListener(IEventManagerListener listener);

	boolean canSendEvent(EventOccurrence event);

	Set<EClass> getEventClasses();

	List<EventOccurrence> getInputEventQueue();
}
