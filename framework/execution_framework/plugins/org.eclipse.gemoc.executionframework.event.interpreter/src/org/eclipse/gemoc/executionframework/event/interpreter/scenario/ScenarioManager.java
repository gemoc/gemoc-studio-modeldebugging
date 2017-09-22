package org.eclipse.gemoc.executionframework.event.interpreter.scenario;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.gemoc.executionframework.event.interpreter.EventInstance;
import org.eclipse.gemoc.executionframework.event.interpreter.IEventInterpreter;
import org.eclipse.gemoc.executionframework.event.interpreter.property.IPropertyListener;
import org.eclipse.gemoc.executionframework.event.interpreter.property.IPropertyMonitor;
import org.eclipse.gemoc.executionframework.event.model.property.CompositeProperty;
import org.eclipse.gemoc.executionframework.event.model.property.EventPrecondition;
import org.eclipse.gemoc.executionframework.event.model.property.Property;
import org.eclipse.gemoc.executionframework.event.model.property.PropertyFactory;
import org.eclipse.gemoc.executionframework.event.model.property.PropertyReference;
import org.eclipse.gemoc.executionframework.event.model.scenario.ElementProvider;
import org.eclipse.gemoc.executionframework.event.model.scenario.Event;
import org.eclipse.gemoc.executionframework.event.model.scenario.EventOccurrence;
import org.eclipse.gemoc.executionframework.event.model.scenario.Scenario;
import org.eclipse.gemoc.executionframework.event.model.scenario.ScenarioElement;
import org.eclipse.gemoc.executionframework.event.model.scenario.ScenarioFSM;
import org.eclipse.gemoc.executionframework.event.model.scenario.ScenarioFSMState;

public class ScenarioManager implements IScenarioManager {

	private Scenario<?> scenario;
	private final Resource executedModel;
	private final IEventInterpreter eventManager;
	private final IPropertyMonitor propertyMonitor;
	private final List<ScenarioElement<?>> currentElements = new ArrayList<>();
	private final PropertyFactory propertyFactory = PropertyFactory.eINSTANCE;

	public ScenarioManager(Resource executedModel, IEventInterpreter eventManager, IPropertyMonitor propertyMonitor) {
		this.executedModel = executedModel;
		this.eventManager = eventManager;
		this.propertyMonitor = propertyMonitor;
	}

	public void loadScenario(Scenario<?> scenario) {
		this.scenario = scenario;
		this.scenario.getElements().forEach(element -> {
			setupScenarioElementPropertyListeners(element);
		});
	}

	private void setupScenarioElementPropertyListeners(ScenarioElement<?> element) {
		currentElements.add(element);
		final boolean isEvent = element instanceof EventOccurrence<?, ?>;
		if (isEvent) {
			handleEventOccurrence((EventOccurrence<?, ?>) element);
		} else {
			handleFSM((ScenarioFSM<?, ?, ?, ?>) element);
		}
	}

	private void setupFSMStatePropertyListeners(ScenarioFSM<?, ?, ?, ?> fsm, ScenarioFSMState<?, ?> state) {
		final Map<Property, IPropertyListener> fsmGuards = new HashMap<>();
		state.getOutgoingTransitions().forEach(t -> {
			final Property property = t.getGuard();
			final Event event = t.getTarget().getEvent();
			if (property != null && event != null) {
				// If the FSM state sends an event, we add the event
				// precondition to the guard of the transition by
				// using a composite property.
				final CompositeProperty<Property> compositeProperty = propertyFactory.createCompositeProperty();
				final PropertyReference<Property> propertyReference = propertyFactory.createPropertyReference();
				final EventPrecondition<Event> precondition = propertyFactory.createEventPrecondition();
				propertyReference.setReferencedProperty(property);
				precondition.setEvent(event);
				compositeProperty.getProperties().add(propertyReference);
				compositeProperty.getProperties().add(precondition);
				IPropertyListener listener = new FSMGuardListener(fsm, t.getTarget(), compositeProperty, fsmGuards);
				fsmGuards.put(compositeProperty, listener);
			} else if (property != null) {
				IPropertyListener listener = new FSMGuardListener(fsm, t.getTarget(), property, fsmGuards);
				fsmGuards.put(property, listener);
			} else if (event != null) {
				final EventPrecondition<Event> precondition = propertyFactory.createEventPrecondition();
				precondition.setEvent(event);
				IPropertyListener listener = new FSMGuardListener(fsm, t.getTarget(), precondition, fsmGuards);
				fsmGuards.put(precondition, listener);
			} else {
				// Degenerate case: no guard, no event precondition.
				// Go directly to the target state?
				fsmGuards.clear();
				setupFSMStatePropertyListeners(fsm, t.getTarget());
			}
		});
		new HashSet<>(fsmGuards.keySet()).forEach(p -> {
			IPropertyListener l = fsmGuards.get(p);
			if (l != null) {
				propertyMonitor.monitorProperty(p, l);
			}
		});
	}

	private void handleEventOccurrence(EventOccurrence<?, ?> eventOccurrence) {
		final EventPrecondition<Event> precondition = propertyFactory.createEventPrecondition();
		precondition.setEvent((eventOccurrence).getEvent());
		final Property property = eventOccurrence.getGuard();
		if (property != null) {
			// We create a composite property containing
			// both the guard of the event and the event precondition.
			final CompositeProperty<Property> compositeProperty = propertyFactory.createCompositeProperty();
			final PropertyReference<Property> propertyReference = propertyFactory.createPropertyReference();
			propertyReference.setReferencedProperty(property);
			compositeProperty.getProperties().add(propertyReference);
			compositeProperty.getProperties().add(precondition);
			propertyMonitor.monitorProperty(compositeProperty, new ScenarioGuardListener(eventOccurrence, compositeProperty));
		} else {
			propertyMonitor.monitorProperty(precondition, new ScenarioGuardListener(eventOccurrence, precondition));
		}
	}
	
	private void handleFSM(ScenarioFSM<?, ?, ?, ?> fsm) {
		final ScenarioFSMState<?, ?> initialState = fsm.getInitialState();
		final Property property = fsm.getGuard();
		final Event event = initialState.getEvent();
		if (event != null && property != null) {
			// We create a composite property containing
			// both the guard of the fsm and the event precondition
			final CompositeProperty<Property> compositeProperty = propertyFactory.createCompositeProperty();
			final PropertyReference<Property> propertyReference = propertyFactory.createPropertyReference();
			final EventPrecondition<Event> precondition = propertyFactory.createEventPrecondition();
			propertyReference.setReferencedProperty(property);
			precondition.setEvent(event);
			compositeProperty.getProperties().add(propertyReference);
			compositeProperty.getProperties().add(precondition);
			propertyMonitor.monitorProperty(compositeProperty, new ScenarioGuardListener(fsm, compositeProperty));
		} else if (event != null) {
			final EventPrecondition<Event> precondition = propertyFactory.createEventPrecondition();
			precondition.setEvent(event);
			propertyMonitor.monitorProperty(precondition, new ScenarioGuardListener(fsm, precondition));
		} else if (property != null) {
			propertyMonitor.monitorProperty(property, new ScenarioGuardListener(fsm, property));
		} else {
			setupFSMStatePropertyListeners(fsm, initialState);
		}
	}

	private EventInstance createEvent(Event originalEvent) {
		final List<EObject> eventParameterMatches = new ArrayList<>();
		final Map<EStructuralFeature, Object> parameters = new HashMap<>();
		for (EStructuralFeature f : originalEvent.eClass().getEStructuralFeatures()) {
			if (f instanceof EAttribute) {
				parameters.put(f, originalEvent.eGet(f));
			} else {
				final ElementProvider<?> paramProvider = (ElementProvider<?>) originalEvent.eGet(f);
				final EObject parameter = ElementProviderAspect.resolve(paramProvider, executedModel);
				if (parameter != null) {
					parameters.put(f, parameter);
					eventParameterMatches.add(parameter);
				}
			}
		}
		return new EventInstance(originalEvent, parameters);
	}

	@Override
	public boolean isScenarioComplete() {
		return currentElements.isEmpty();
	}

	private class ScenarioGuardListener implements IPropertyListener {

		private final ScenarioElement<?> element;
		private final Property property;

		public ScenarioGuardListener(ScenarioElement<?> element, Property property) {
			this.element = element;
			this.property = property;
		}

		@Override
		public void update(boolean propertyValue) {
			if (propertyValue) {
				// We stop monitoring the guard of this scenario element.
				propertyMonitor.unmonitorProperty(property, this);
				if (element instanceof EventOccurrence<?, ?>) {
					currentElements.remove(element);
					currentElements.addAll(element.getNextElements());
					final EventOccurrence<?, ?> eventOccurrence = (EventOccurrence<?, ?>) element;
					eventManager.queueEvent(createEvent(eventOccurrence.getEvent()));
					// We start monitoring the guards of the next elements in
					// the scenario tree.
					eventOccurrence.getNextElements().stream().forEach(e -> {
						setupScenarioElementPropertyListeners(e);
					});
				} else {
					// We do not monitor the guards of the next elements in the
					// scenario tree yet because they must only be evaluated once the
					// FSM reaches an accepting state.
					final ScenarioFSM<?, ?, ?, ?> fsm = (ScenarioFSM<?, ?, ?, ?>) element;
					final Event event = fsm.getInitialState().getEvent();
					if (event != null) {
						eventManager.queueEvent(createEvent(event));
					}
					setupFSMStatePropertyListeners(fsm, fsm.getInitialState());
				}
			}
		}
	}

	private class FSMGuardListener implements IPropertyListener {

		private final ScenarioFSM<?, ?, ?, ?> fsm;
		private final ScenarioFSMState<?, ?> state;
		private final Property property;
		private Map<Property, IPropertyListener> fsmGuards = new HashMap<>();

		public FSMGuardListener(ScenarioFSM<?, ?, ?, ?> fsm, ScenarioFSMState<?, ?> state, Property property,
				Map<Property, IPropertyListener> fsmGuards) {
			this.fsm = fsm;
			this.state = state;
			this.property = property;
			this.fsmGuards = fsmGuards;
		}

		@Override
		public void update(boolean propertyValue) {
			if (propertyValue) {
				// We send the event associated to the newly reached state of
				// the FSM, if any. We do not have to check if it can be sent
				// as this check is part of the guard of the incoming transition.
				final Event event = state.getEvent();
				if (event != null) {
					eventManager.queueEvent(createEvent(event));
				}
				// We stop monitoring the current guard as well as
				// the guards of the other outgoing transitions of
				// the previous state of the FSM.
				propertyMonitor.unmonitorProperty(property, this);
				fsmGuards.forEach((p, l) -> propertyMonitor.unmonitorProperty(p, l));
				fsmGuards.clear();
				if (fsm.getAcceptingStates().contains(state)) {
					// The FSM has reached an accepting state, thus we start
					// monitoring the guards of the next elements in the
					// scenario tree.
					currentElements.remove(fsm);
					currentElements.addAll(fsm.getNextElements());
					fsm.getNextElements().stream().forEach(e -> {
						setupScenarioElementPropertyListeners(e);
					});
				} else {
					// Otherwise we start monitoring the guards of the outgoing
					// transitions.
					setupFSMStatePropertyListeners(fsm, state);
				}
			}
		}
	}
}
