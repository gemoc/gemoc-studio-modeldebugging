package org.eclipse.gemoc.executionframework.event.manager;

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
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EParameter;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.gemoc.executionframework.value.model.value.ManyReferenceValue;
import org.eclipse.gemoc.executionframework.value.model.value.SingleObjectValue;
import org.eclipse.gemoc.executionframework.value.model.value.SingleReferenceValue;
import org.eclipse.gemoc.executionframework.value.model.value.Value;
import org.eclipse.gemoc.dsl.Dsl;
import org.eclipse.gemoc.executionframework.event.model.event.EventOccurrence;
import org.eclipse.gemoc.executionframework.event.model.event.EventOccurrenceType;
import org.eclipse.gemoc.executionframework.event.model.event.StopEventOccurrence;
import org.eclipse.gemoc.trace.commons.model.trace.MSE;
import org.eclipse.gemoc.trace.commons.model.trace.MSEOccurrence;
import org.eclipse.gemoc.trace.commons.model.trace.Step;
import org.eclipse.gemoc.xdsmlframework.api.core.EngineStatus.RunStatus;
import org.eclipse.gemoc.xdsmlframework.api.core.IExecutionEngine;
import org.eclipse.gemoc.executionframework.behavioralinterface.behavioralInterface.BehavioralInterface;
import org.eclipse.gemoc.executionframework.behavioralinterface.behavioralInterface.Event;
import org.osgi.framework.Bundle;

public class GenericEventManager implements IEventManager {

	private final LinkedTransferQueue<ICallRequest> callRequestQueue = new LinkedTransferQueue<>();

	private boolean canManageEvents = true;

	private boolean waitForCallRequests = false;

	private IExecutionEngine<?> engine;

	private final RelationshipManager relationshipManager;

	public GenericEventManager(String languageName, Resource executedResource,
			List<IImplementationRelationship> implementationRelationships,
			List<ISubtypingRelationship> subtypingRelationships) {
		loadLanguage(languageName);
		relationshipManager = new RelationshipManager(this);
		implementationRelationships.forEach(r -> relationshipManager.registerImplementationRelationship(r));
		subtypingRelationships.forEach(r -> relationshipManager.registerSubtypingRelationship(r));
	}

	public RelationshipManager getRelationshipManager() {
		return relationshipManager;
	}

	@Override
	public void engineInitialized(IExecutionEngine<?> executionEngine) {
		engine = executionEngine;
		relationshipManager.setExecutedResource(engine.getExecutionContext().getResourceModel());
	}

	@Override
	public void processEventOccurrence(EventOccurrence eventOccurrence) {
		if (eventOccurrence instanceof StopEventOccurrence) {
			processCallRequest(new StopRequest());
		} else {
			convertEventToExecutedResource(eventOccurrence, engine.getExecutionContext().getResourceModel());
			relationshipManager.notifyEventOccurrence(eventOccurrence);
		}
	}

	private Map<BehavioralInterface, List<IEventManagerListener>> listeners = new HashMap<>();

	@Override
	public void addListener(IEventManagerListener listener) {
		listener.getBehavioralInterfaces().forEach(bi -> {
			final List<IEventManagerListener> interfaceListeners = listeners.computeIfAbsent(bi,
					itf -> new ArrayList<>());
			interfaceListeners.add(listener);
		});
	}

	@Override
	public void removeListener(IEventManagerListener listener) {
		listener.getBehavioralInterfaces().forEach(bi -> {
			final List<IEventManagerListener> interfaceListeners = listeners.get(bi);
			if (interfaceListeners != null) {
				interfaceListeners.add(listener);
			}
		});
	}

	@Override
	public void processCallRequests() {
		if (canManageEvents) {
			ICallRequest callRequest = null;
			if (waitForCallRequests) {
				try {
					engine.setEngineStatus(RunStatus.WaitingForEvent);
					callRequest = callRequestQueue.take();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				engine.setEngineStatus(RunStatus.Running);
				waitForCallRequests = false;
			} else {
				callRequest = callRequestQueue.poll();
			}
			while (callRequest != null) {
				final boolean runToCompletion = callRequest.isRunToCompletion();
				if (runToCompletion) {
					canManageEvents = false;
					dispatchEvent(callRequest);
					canManageEvents = true;
				} else {
					dispatchEvent(callRequest);
				}
				callRequest = callRequestQueue.poll();
			}
		}
	}

	@Override
	public void waitForCallRequests() {
		waitForCallRequests = true;
	}

	@Override
	public void aboutToExecuteStep(IExecutionEngine<?> engine, Step<?> stepToExecute) {
		final MSEOccurrence mseOccurrence = stepToExecute.getMseoccurrence();
		final String behavioralUnit = mseOccurrence.getMse().getAction().getEContainingClass().getInstanceClassName()
				+ "." + mseOccurrence.getMse().getAction().getName();
		final Map<String, Object> argsMap = getArguments(mseOccurrence);
		final CallNotification callNotification = new CallNotification(behavioralUnit, argsMap);
		relationshipManager.notifyCall(callNotification);
		processCallRequests();
	}

	@Override
	public void stepExecuted(IExecutionEngine<?> engine, Step<?> stepExecuted) {
		final MSEOccurrence mseOccurrence = stepExecuted.getMseoccurrence();
		final String behavioralUnit = mseOccurrence.getMse().getAction().getEContainingClass().getInstanceClassName()
				+ "." + mseOccurrence.getMse().getAction().getName();
		final Map<String, Object> argsMap = getArguments(mseOccurrence);
		final ReturnNotification returnNotification = new ReturnNotification(behavioralUnit, argsMap,
				mseOccurrence.getResult());
		relationshipManager.notifyCall(returnNotification);
		processCallRequests();
	}

	private Map<String, Object> getArguments(MSEOccurrence mseOccurrence) {
		final Map<String, Object> argsMap = new HashMap<>();
		final MSE mse = mseOccurrence.getMse();
		argsMap.put("_self", mse.getCaller());
		final List<EParameter> parameters = mse.getAction().getEParameters();
		final List<Object> arguments = mseOccurrence.getParameters();
		for (int i = 0; i < parameters.size(); i++) {
			final String key = parameters.get(i).getName();
			final Object value = arguments.get(i);
			argsMap.put(key, value);
		}
		return argsMap;
	}

	private final Set<BehavioralInterface> allBehavioralInterfaces = new HashSet<>();

	@Override
	public Set<BehavioralInterface> getBehavioralInterfaces() {
		if (allBehavioralInterfaces.isEmpty()) {
			relationshipManager.getEvents()
					.forEach(e -> allBehavioralInterfaces.add((BehavioralInterface) e.eContainer()));
		}
		return allBehavioralInterfaces;
	}

	@Override
	public Set<Event> getEvents() {
		return relationshipManager.getEvents();
	}

	private void dispatchEvent(ICallRequest callRequest) {
		if (callRequest instanceof StopRequest) {
			engine.stop();
		} else if (callRequest instanceof CompositeCallRequest) {
			((CompositeCallRequest) callRequest).getCallRequests().forEach(cr -> dispatchEvent(cr));
		} else if (callRequest instanceof SimpleCallRequest) {
			final SimpleCallRequest simpleCallRequest = (SimpleCallRequest) callRequest;
			final Method rule = findMethod(simpleCallRequest);
			performCall(rule, simpleCallRequest.getArguments());
		}
	}

	private Object performCall(Method toCall, List<Object> args) {
		Object result = null;
		try {
			result = toCall.invoke(null, args.toArray());
		} catch (Throwable e1) {
			e1.printStackTrace();
		}
		return result;
	}

	private final List<EPackage> packages = new ArrayList<>();

	private final List<Class<?>> operationalSemantics = new ArrayList<>();

	private void loadLanguage(String languageName) {
		final ResourceSet resSet = new ResourceSetImpl();
		IConfigurationElement language = Arrays
				.asList(Platform.getExtensionRegistry()
						.getConfigurationElementsFor("org.eclipse.gemoc.gemoc_language_workbench.xdsml"))
				.stream().filter(l -> l.getAttribute("xdsmlFilePath").endsWith(".dsl")
						&& l.getAttribute("name").equals(languageName))
				.findFirst().orElse(null);
		
		if (language != null) {
			String xdsmlFilePath = language.getAttribute("xdsmlFilePath");
			if (!xdsmlFilePath.startsWith("platform:/plugin")) {
				xdsmlFilePath = "platform:/plugin" + xdsmlFilePath;
			}
			final Resource res = resSet.getResource(URI.createURI(xdsmlFilePath), true);
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

				this.packages.addAll(packages);
				this.operationalSemantics.addAll(classes);
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

	private Method findMethodInClass(Class<?> clazz, String name, Class<?>[] parameters) {
		return Arrays.stream(clazz.getDeclaredMethods()).map(m -> {
			Method result = null;
			if (m.getName().equals(name) && m.getParameterCount() == parameters.length) {
				final Class<?>[] t = m.getParameterTypes();
				result = m;
				for (int i = 0; i < t.length; i++) {
					Class<?> type = t[i];
					if (!type.isAssignableFrom(parameters[i])) {
						result = null;
						break;
					}
				}
			}
			return result;
		}).filter(m -> m != null).findFirst().orElse(null);
	}

	private Method findMethod(SimpleCallRequest callRequest) {
		final Class<?>[] parameters = (Class[]) callRequest.getArguments().stream().map(o -> o.getClass())
				.collect(Collectors.toList()).toArray(new Class[0]);
		final String methodName = callRequest.getBehavioralUnit();
		final Method method = operationalSemantics.stream().filter(c -> methodName.startsWith(c.getName()))
				.map(c -> findMethodInClass(c, methodName.substring(c.getName().length() + 1), parameters))
				.filter(m -> m != null).findFirst().orElse(null);
		return method;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void convertReferences(EObject object, Resource executedResource, String executedResourceURI) {
		final List<EReference> references = object.eClass().getEAllReferences();
		references.forEach(r -> {
			if (r.isMany()) {
				final Map<EObject, EObject> toChange = new HashMap<>();
				((List) object.eGet(r)).stream().forEach(o -> {
					final EObject refered = (EObject) o;
					if (refered != null) {
						EcoreUtil.resolveAll(refered);
						final Resource referedResource = refered.eResource();
						if (referedResource != null) {
							final String referedResourceURI = referedResource.getURI().toString();
							if (referedResourceURI.equals(executedResourceURI) && referedResource != executedResource) {
								final String uriFragment = referedResource.getURIFragment(refered);
								final EObject effectiveRefered = executedResource.getEObject(uriFragment);
								toChange.put(refered, effectiveRefered);
							}
						}
					}
				});
				toChange.forEach((o, n) -> {
					final List l = (List) object.eGet(r);
					l.add(l.indexOf(o), n);
					l.remove(o);
				});
			} else {
				final EObject refered = (EObject) object.eGet(r);
				if (refered != null) {
					EcoreUtil.resolveAll(refered);
					final Resource referedResource = refered.eResource();
					if (referedResource != null) {
						final String referedResourceURI = referedResource.getURI().toString();
						if (referedResourceURI.equals(executedResourceURI) && referedResource != executedResource) {
							final String uriFragment = referedResource.getURIFragment(refered);
							final EObject effectiveRefered = executedResource.getEObject(uriFragment);
							object.eSet(r, effectiveRefered);
						}
					}
				}
			}
		});
	}

	private void convertReferencesToExecutedResource(EObject root, Resource executedResource,
			String executedResourceURI) {
		convertReferences(root, executedResource, executedResourceURI);
		root.eAllContents().forEachRemaining(c -> {
			convertReferences(c, executedResource, executedResourceURI);
		});
	}

	private void convertEventToExecutedResource(EventOccurrence eventOccurrence, Resource executedResource) {
		final String executedResourceURI = executedResource.getURI().toString();
		EcoreUtil.resolveAll(eventOccurrence);
		eventOccurrence.getArgs().forEach(a -> {
			final Value value = (Value) a.getValue();
			if (value instanceof SingleReferenceValue) {
				final SingleReferenceValue v = ((SingleReferenceValue) value);
				final EObject parameter = v.getReferenceValue();
				final Resource parameterResource = parameter.eResource();
				final String uriFragment = parameterResource.getURIFragment(parameter);
				final EObject effectiveParameter = executedResource.getEObject(uriFragment);
				v.setReferenceValue(effectiveParameter);
			} else if (value instanceof SingleObjectValue) {
				final SingleObjectValue v = ((SingleObjectValue) value);
				final EObject parameter = v.getObjectValue();
				convertReferencesToExecutedResource(parameter, executedResource, executedResourceURI);
			} else if (value instanceof ManyReferenceValue) {
				final ManyReferenceValue v = ((ManyReferenceValue) value);
				final List<EObject> parameters = v.getReferenceValues();
				final List<EObject> effectiveParameters = parameters.stream().map(p -> {
					final Resource parameterResource = p.eResource();
					final String uriFragment = parameterResource.getURIFragment(p);
					return executedResource.getEObject(uriFragment);
				}).collect(Collectors.toList());
				parameters.clear();
				parameters.addAll(effectiveParameters);
			}
		});
	}

	@Override
	public void emitEventOccurrence(EventOccurrence eventOccurrence) {
		final Event event = eventOccurrence.getEvent();
		if (eventOccurrence.getType() == EventOccurrenceType.EXPOSED) {
			final BehavioralInterface behavioralInterface = (BehavioralInterface) event.eContainer();
			listeners.getOrDefault(behavioralInterface, Collections.emptyList())
					.forEach(l -> l.eventReceived(eventOccurrence));
		}
	}

	@Override
	public void processCallRequest(ICallRequest callRequest) {
		callRequestQueue.put(callRequest);
	}
}
