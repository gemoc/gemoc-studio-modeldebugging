package org.eclipse.gemoc.executionframework.event.manager;

import java.util.Set;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.gemoc.executionframework.event.model.event.Event;
import org.eclipse.gemoc.xdsmlframework.api.engine_addon.IEngineAddon;

public interface IBehavioralAPI extends IEngineAddon {

	void dispatchEvent(Event event);
	
	boolean canSendEvent(Event event);
	
	Set<EClass> getEventClasses();
}
