package org.eclipse.gemoc.executionframework.event.manager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.concurrent.LinkedTransferQueue;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.gemoc.executionframework.event.model.event.Event;
import org.eclipse.gemoc.trace.commons.model.trace.MSE;
import org.eclipse.gemoc.trace.commons.model.trace.MSEOccurrence;
import org.eclipse.gemoc.trace.commons.model.trace.Step;
import org.eclipse.gemoc.xdsmlframework.api.core.EngineStatus.RunStatus;
import org.eclipse.gemoc.xdsmlframework.api.core.IExecutionEngine;

public class EventManager implements IEventManager {

	private final LinkedTransferQueue<Event> inputEventQueue = new LinkedTransferQueue<>();

	private boolean canManageEvents = true;

	private boolean waitForEvents = false;

	private IBehavioralAPI api;

	private IExecutionEngine engine;

	public EventManager(EPackage languageRootPackage) {
		IConfigurationElement[] confElts = Platform.getExtensionRegistry()
				.getConfigurationElementsFor("org.eclipse.gemoc.executionframework.event.api");
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
	public void engineInitialized(IExecutionEngine executionEngine) {
		engine = executionEngine;
	}

	@Override
	public void queueEvent(Event input) {
		inputEventQueue.add(input);
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
				Event event = null;
				if (waitForEvents) {
					try {
						engine.setEngineStatus(RunStatus.WaitingForEvent);
						event = inputEventQueue.take();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					engine.setEngineStatus(RunStatus.Running);
					waitForEvents = false;
				} else {
					event = inputEventQueue.poll();
				}
				while (event != null) {
					final boolean interruptible = api.isInterruptible(event.eClass());
					if (!interruptible) {
						canManageEvents = false;
						api.dispatchEvent(event);
						canManageEvents = true;
					} else {
						api.dispatchEvent(event);
					}
					event = inputEventQueue.poll();
				}
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
			listeners.forEach(l -> l.eventReceived(event));
		}
	}

	@Override
	public Set<EClass> getEventClasses() {
		return api == null ? Collections.emptySet() : api.getEventClasses();
	}

	@Override
	public Set<EClass> getStartEventClasses() {
		return api == null ? Collections.emptySet() : api.getStartEventClasses();
	}

	@Override
	public List<Event> getInputEventQueue() {
		return new ArrayList<>(inputEventQueue);
	}
}
