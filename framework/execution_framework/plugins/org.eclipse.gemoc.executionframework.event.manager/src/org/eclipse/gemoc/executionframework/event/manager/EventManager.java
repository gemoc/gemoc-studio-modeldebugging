package org.eclipse.gemoc.executionframework.event.manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EParameter;
import org.eclipse.gemoc.executionframework.event.model.event.Event;
import org.eclipse.gemoc.trace.commons.model.trace.MSE;
import org.eclipse.gemoc.trace.commons.model.trace.MSEOccurrence;
import org.eclipse.gemoc.trace.commons.model.trace.Step;
import org.eclipse.gemoc.xdsmlframework.api.core.IExecutionEngine;

public class EventManager implements IEventManager {

	private final Queue<Event> inputEventQueue = new ConcurrentLinkedQueue<>();

	private final Queue<Event> outputEventQueue = new ConcurrentLinkedQueue<>();

	private boolean canManageEvents = true;

	private boolean waitForEvents = false;

	private Thread t = null;

	private IBehavioralAPI api;

	public EventManager(EPackage languageRootPackage) {
		IConfigurationElement[] confElts = Platform.getExtensionRegistry().getConfigurationElementsFor("org.eclipse.gemoc.executionframework.event.api");
		for (IConfigurationElement confElt : confElts) {
			try {
				final IBehavioralAPI candidate = (IBehavioralAPI) confElt.createExecutableExtension("class");
				if (candidate.canHandle(languageRootPackage)) {
					api = candidate;
					break;
				}
				break;
			} catch (CoreException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void queueEvent(Event input) {
		inputEventQueue.add((Event) input);
		if (t != null) {
			synchronized (t) {
				t.notify();
			}
			t = null;
		}
	}

	@Override
	public boolean canSendEvent(Event event) {
		return api == null ? false : api.canSendEvent(event);
	}

	private List<IEventManagerListener> listeners = new ArrayList<>();

	@Override
	public void addListener(IEventManagerListener listener) {
		listeners.add(listener);
	}

	@Override
	public void removeListener(IEventManagerListener listener) {
		listeners.remove(listener);
	}

	@Override
	public void processEvents() {
		if (api != null) {
			if (canManageEvents) {
				canManageEvents = false;
				if (waitForEvents && inputEventQueue.isEmpty()) {
					t = Thread.currentThread();
					synchronized (t) {
						try {
							t.wait();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
					waitForEvents = false;
				}
				Event event = inputEventQueue.poll();
				while (event != null) {
					api.dispatchEvent(event);
					event = inputEventQueue.poll();
				}
				canManageEvents = true;
			}
		}
	}

	@Override
	public void waitForEvents() {
		waitForEvents = true;
	}
	
	@Override
	public void stepExecuted(IExecutionEngine engine, Step<?> stepExecuted) {
		final MSEOccurrence mseOcc = stepExecuted.getMseoccurrence();
		final MSE mse = mseOcc.getMse();
		final Event event = api.getOutputEvent(mse.getAction(), mse.getCaller(), mseOcc.getParameters());
		if (event != null) {
			/* 
			 * TODO choose between one output event queue per listener or
			 * notifying each listener, letting them store events (or not)
			 * 
			 * outputEventQueue.add(event);
			 * listeners.forEach(l -> l.eventReceived(event));
			 */
		}
	}
}
