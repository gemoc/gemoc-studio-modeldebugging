package org.eclipse.gemoc.executionframework.event.manager;

import java.lang.invoke.CallSite;
import java.lang.invoke.LambdaConversionException;
import java.lang.invoke.LambdaMetafactory;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.LinkedTransferQueue;
import java.util.stream.Collectors;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.gemoc.dsl.Dsl;
import org.eclipse.gemoc.executionframework.event.model.event.EventFactory;
import org.eclipse.gemoc.executionframework.event.model.event.EventOccurrence;
import org.eclipse.gemoc.executionframework.event.model.event.EventPackage;
import org.eclipse.gemoc.trace.commons.model.trace.MSE;
import org.eclipse.gemoc.trace.commons.model.trace.MSEOccurrence;
import org.eclipse.gemoc.trace.commons.model.trace.Step;
import org.eclipse.gemoc.xdsmlframework.api.core.EngineStatus.RunStatus;
import org.eclipse.gemoc.xdsmlframework.api.core.IExecutionEngine;
import org.eclipse.gemoc.xdsmlframework.behavioralinterface.behavioralInterface.BehavioralInterface;
import org.eclipse.gemoc.xdsmlframework.behavioralinterface.behavioralInterface.Event;
import org.eclipse.gemoc.xdsmlframework.behavioralinterface.behavioralInterface.InputEvent;
import org.eclipse.gemoc.xdsmlframework.behavioralinterface.behavioralInterface.OutputEvent;
import org.osgi.framework.Bundle;

public class GenericEventManager implements IEventManager {

	private final LinkedTransferQueue<EventOccurrence> inputEventOccurrenceQueue = new LinkedTransferQueue<>();

	private boolean canManageEvents = true;

	private boolean waitForEvents = false;

	private IExecutionEngine<?> engine;

	private BehavioralInterface behavioralInterface;

	private Map<Event, MethodHandle> eventToMethod = new HashMap<>();

	private Map<Event, MethodHandle> eventToPrecondition = new HashMap<>();

	public GenericEventManager(String languageName) {
		loadLanguage(languageName);
	}

	@Override
	public void engineInitialized(IExecutionEngine<?> executionEngine) {
		engine = executionEngine;
	}

	@Override
	public void queueEvent(EventOccurrence input) {
		inputEventOccurrenceQueue.add(input);
	}

	@Override
	public boolean canSendEvent(EventOccurrence eventOccurrence) {
		final Event event = eventOccurrence.getEvent();
		final MethodHandle precondition = eventToPrecondition.get(event);
		if (precondition != null && !((Boolean) performCall(precondition))) {
			return false;
		} else {
			return true;
		}
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
		if (behavioralInterface != null) {
			if (canManageEvents) {
				EventOccurrence eventOccurrence = null;
				if (waitForEvents) {
					try {
						engine.setEngineStatus(RunStatus.WaitingForEvent);
						eventOccurrence = inputEventOccurrenceQueue.take();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					engine.setEngineStatus(RunStatus.Running);
					waitForEvents = false;
				} else {
					eventOccurrence = inputEventOccurrenceQueue.poll();
				}
				while (eventOccurrence != null) {
					final boolean interruptible = false;// TODO event.getEvent().interruptible;
					if (!interruptible) {
						canManageEvents = false;
						dispatchEvent(eventOccurrence);
						canManageEvents = true;
					} else {
						dispatchEvent(eventOccurrence);
					}
					eventOccurrence = inputEventOccurrenceQueue.poll();
				}
			}
		}
	}

	@Override
	public void waitForEvents() {
		waitForEvents = true;
	}

	@Override
	public void stepExecuted(IExecutionEngine<?> engine, Step<?> stepExecuted) {
		final MSEOccurrence mseOcc = stepExecuted.getMseoccurrence();
		final MSE mse = mseOcc.getMse();
		behavioralInterface.getEvents().stream()
				.filter(e -> e instanceof OutputEvent)
				.filter(e -> e.getRule().substring(e.getRule().lastIndexOf(".")).equals(mse.getAction().getName()))
				.findFirst().map(e -> {
					final EventOccurrence occ = EventFactory.eINSTANCE.createEventOccurrence();
					occ.setEvent(e);
					return occ;
				})
				.ifPresent(occ -> listeners.forEach(l -> l.eventReceived(occ)));
	}

	@Override
	public Set<Event> getEvents() {
		return behavioralInterface == null ? Collections.emptySet()
				: new HashSet<>(behavioralInterface.getEvents());
	}

	@Override
	public List<EventOccurrence> getInputEventQueue() {
		return new ArrayList<>(inputEventOccurrenceQueue);
	}

	private void dispatchEvent(EventOccurrence eventOccurrence) {
		if (eventOccurrence.eClass().equals(EventPackage.Literals.STOP_EVENT_OCCURRENCE)) {
			
		} else {
			final Event event = eventOccurrence.getEvent();
			final MethodHandle rule = eventToMethod.get(event);
			if (canSendEvent(eventOccurrence)) {
				performCall(rule);
			}
		}
	}
	
	private Object performCall(MethodHandle toCall, Object... parameters) {
		Object result = null;
		try {
			final CallSite preconditionCallSite = LambdaMetafactory.metafactory(lookup, "applyRule", toCall.type(), toCall.type(), toCall, toCall.type());
			result = preconditionCallSite.getTarget().invokeExact();
		} catch (LambdaConversionException e) {
			e.printStackTrace();
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return result;
	}

	private void loadLanguage(String languageName) {
		final ResourceSet resSet = new ResourceSetImpl();
		IConfigurationElement language = Arrays
				.asList(Platform.getExtensionRegistry()
						.getConfigurationElementsFor("org.eclipse.gemoc.gemoc_language_workbench.sequential.xdsml"))
				.stream().filter(l -> l.getAttribute("xdsmlFilePath").endsWith(".dsl")
						&& l.getAttribute("name").equals(languageName))
				.findFirst().orElse(null);

		if (language != null) {
			final Resource res = resSet.getResource(URI.createURI(language.getAttribute("xdsmlFilePath")), true);
			final Dsl dsl = (Dsl) res.getContents().get(0);
			final Bundle bundle = Platform.getBundle(language.getContributor().getName());

			if (dsl != null) {
				final List<EPackage> packages = dsl.getEntries().stream().filter(e -> e.getKey().equals("ecore"))
						.findFirst()
						.map(as -> Arrays.asList(as.getValue().split(", ")).stream()
								.map(s -> URI.createURI(s.replace("platform:/resource", "platform:/plugin"), true))
								.map(uri -> ((EPackage) resSet.getResource(uri, true).getContents().get(0)).getNsURI()))
						.map(uris -> uris.map(uri -> Arrays
								.asList(Platform.getExtensionRegistry()
										.getConfigurationElementsFor("org.eclipse.emf.ecore.generated_package"))
								.stream().filter(c -> c.getAttribute("uri").equals(uri)).map(c -> loadPackage(c))
								.filter(p -> p != null).findFirst().orElse(null)))
						.map(s -> s.collect(Collectors.toList())).orElse(Collections.emptyList());

				final List<Class<?>> classes = dsl.getEntries().stream().filter(e -> e.getKey().equals("k3"))
						.findFirst()
						.map(os -> Arrays.asList(os.getValue().split(",")).stream().map(cn -> loadClass(cn, bundle))
								.filter(c -> c != null).collect(Collectors.toList()))
						.orElse(Collections.emptyList()).stream().map(c -> (Class<?>) c).collect(Collectors.toList());

				final BehavioralInterface behavioralInterface = dsl.getEntries().stream()
						.filter(e -> e.getKey().equals("behavioral-interface")).findFirst()
						.map(bi -> URI.createURI(bi.getValue().replace("platform:/resource", "platform:/plugin"), true))
						.map(uri -> (BehavioralInterface) (resSet.getResource(uri, true).getContents().get(0)))
						.orElse(null);

				validateLanguage(behavioralInterface, classes, packages);
			}
		}
	}

	private EPackage loadPackage(IConfigurationElement configurationElement) {
		EPackage result = null;
		try {
			List<Field> fields = Arrays.asList(Platform.getBundle(configurationElement.getContributor().getName())
					.loadClass(configurationElement.getAttribute("class")).getFields());
			result = fields.stream().filter(f -> f.getName().equals("eINSTANCE")).findFirst().map(f -> {
				try {
					return (EPackage) f.get(null);
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
				return null;
			}).orElse(null);
		} catch (SecurityException e1) {
			e1.printStackTrace();
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}
		return result;
	}

	private Class<?> loadClass(String className, Bundle bundle) {
		Class<?> result = null;
		try {
			result = bundle.loadClass(className.replaceAll("\\s", "").trim());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return result;
	}

	private boolean validateLanguage(BehavioralInterface bi, List<Class<?>> os, List<EPackage> as) {
		final Class<?>[] emptyClassArray = new Class<?>[0];
		final boolean diagnostic = bi.getEvents().stream().allMatch(evt -> {
			final Class<?>[] parameters = evt.getParams().stream().map(p -> {
				Class<?> result = null;
				try {
					result = Class.forName(p.getType());
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
				return result;
			}).collect(Collectors.toList()).toArray(emptyClassArray);
			final String rule = evt.getRule();
			final MethodHandle ruleHandle = findMethod(rule, parameters, os);
			if (ruleHandle == null) {
				return false;
			} else {
				eventToMethod.put(evt, ruleHandle);
			}
			if (evt instanceof InputEvent) {
				// Try to load precondition method if a precondition is specified
				final String precondition = ((InputEvent) evt).getPrecondition();
				if (precondition != null && !precondition.isEmpty()) {
					final MethodHandle preconditionHandle = findMethod(precondition, parameters, os);
					if (preconditionHandle == null) {
						return false;
					} else {
						eventToPrecondition.put(evt, ruleHandle);
					}
				}
			}
			return true;
		});
		if (!diagnostic) {
			eventToMethod.clear();
			eventToPrecondition.clear();
		} else {
			behavioralInterface = bi;
		}
		return diagnostic;
	}

	private final MethodHandles.Lookup lookup = MethodHandles.lookup();

	private MethodHandle findMethod(String methodFqn, Class<?>[] parameters, List<Class<?>> os) {
		final int lastDot = methodFqn.lastIndexOf(".");
		final String classFqn = methodFqn.substring(0, lastDot);
		final String methodName = methodFqn.substring(lastDot);
		return os.stream().filter(c -> c.getName().equals(classFqn)).findFirst().map(c -> {
			MethodHandle result = null;
			try {
				Method m = c.getDeclaredMethod(methodName, parameters);
				result = lookup.unreflect(m);
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			} catch (SecurityException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
			return result;
		}).orElse(null);
	}
}
