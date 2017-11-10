package org.eclipse.gemoc.executionframework.event.manager;

import java.util.List;
import java.util.Set;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.gemoc.executionframework.event.model.event.Event;

public interface IBehavioralAPI {

	void dispatchEvent(Event event);
	
	boolean canSendEvent(Event event);
	
	Set<EClass> getEventClasses();
	
	Set<EClass> getStartEventClasses();
	
	Event getOutputEvent(EOperation operation, EObject caller, List<Object> parameters);
	
	boolean canHandle(EPackage pkg);
	
	boolean isInterruptible(EClass event);
}
