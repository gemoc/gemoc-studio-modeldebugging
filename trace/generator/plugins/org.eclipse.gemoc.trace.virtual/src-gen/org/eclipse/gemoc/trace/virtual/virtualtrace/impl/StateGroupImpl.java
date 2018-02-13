/**
 */
package org.eclipse.gemoc.trace.virtual.virtualtrace.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.emf.ecore.util.EObjectResolvingEList;
import org.eclipse.emf.ecore.util.EcoreUtil;

import org.eclipse.gemoc.trace.commons.model.trace.State;
import org.eclipse.gemoc.trace.commons.model.trace.Step;
import org.eclipse.gemoc.trace.commons.model.trace.Value;

import org.eclipse.gemoc.trace.virtual.virtualtrace.StateGroup;
import org.eclipse.gemoc.trace.virtual.virtualtrace.VirtualTrace;
import org.eclipse.gemoc.trace.virtual.virtualtrace.VirtualtracePackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>State Group</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.gemoc.trace.virtual.virtualtrace.impl.StateGroupImpl#getTrace <em>Trace</em>}</li>
 *   <li>{@link org.eclipse.gemoc.trace.virtual.virtualtrace.impl.StateGroupImpl#getValues <em>Values</em>}</li>
 *   <li>{@link org.eclipse.gemoc.trace.virtual.virtualtrace.impl.StateGroupImpl#getStates <em>States</em>}</li>
 *   <li>{@link org.eclipse.gemoc.trace.virtual.virtualtrace.impl.StateGroupImpl#getStartingSteps <em>Starting Steps</em>}</li>
 *   <li>{@link org.eclipse.gemoc.trace.virtual.virtualtrace.impl.StateGroupImpl#getEndingSteps <em>Ending Steps</em>}</li>
 * </ul>
 *
 * @generated
 */
public class StateGroupImpl extends MinimalEObjectImpl.Container implements StateGroup {
	/**
	 * The cached value of the '{@link #getStates() <em>States</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getStates()
	 * @generated
	 * @ordered
	 */
	protected EList<State<?, ?>> states;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected StateGroupImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return VirtualtracePackage.Literals.STATE_GROUP;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public VirtualTrace getTrace() {
		if (eContainerFeatureID() != VirtualtracePackage.STATE_GROUP__TRACE)
			return null;
		return (VirtualTrace) eInternalContainer();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetTrace(VirtualTrace newTrace, NotificationChain msgs) {
		msgs = eBasicSetContainer((InternalEObject) newTrace, VirtualtracePackage.STATE_GROUP__TRACE, msgs);
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setTrace(VirtualTrace newTrace) {
		if (newTrace != eInternalContainer()
				|| (eContainerFeatureID() != VirtualtracePackage.STATE_GROUP__TRACE && newTrace != null)) {
			if (EcoreUtil.isAncestor(this, newTrace))
				throw new IllegalArgumentException("Recursive containment not allowed for " + toString());
			NotificationChain msgs = null;
			if (eInternalContainer() != null)
				msgs = eBasicRemoveFromContainer(msgs);
			if (newTrace != null)
				msgs = ((InternalEObject) newTrace).eInverseAdd(this, VirtualtracePackage.VIRTUAL_TRACE__STATE_GROUPS,
						VirtualTrace.class, msgs);
			msgs = basicSetTrace(newTrace, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, VirtualtracePackage.STATE_GROUP__TRACE, newTrace,
					newTrace));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Value<?>> getValues() {
		if (!getStates().isEmpty()) {
			final State<?, ?> referenceState = getStates().get(0);
			final EList<Value<?>> result = new org.eclipse.emf.common.util.BasicEList<>();
			referenceState.getValues().stream().filter(v -> getTrace().getSelectedDimensions().contains(v.eContainer()))
					.forEach(v -> result.add(v));
			return result;
		} else {
			return new org.eclipse.emf.common.util.BasicEList<>();
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<State<?, ?>> getStates() {
		if (states == null) {
			states = new EObjectResolvingEList<State<?, ?>>(State.class, this, VirtualtracePackage.STATE_GROUP__STATES);
		}
		return states;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Step<?>> getStartingSteps() {
		final java.util.List<State<?, ?>> states = getStates();
		if (!states.isEmpty()) {
			final EList<Step<?>> result = new org.eclipse.emf.common.util.BasicEList<>();
			final java.util.List<Step<?>> steps = states.stream().flatMap(state -> {
				return state.getStartedSteps().stream().map(step -> (Step<?>) step);
			}).collect(java.util.stream.Collectors.toList());
			result.addAll(steps);
			return result;
		} else {
			return new org.eclipse.emf.common.util.BasicEList<>();
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Step<?>> getEndingSteps() {
		final java.util.List<State<?, ?>> states = getStates();
		if (!states.isEmpty()) {
			final EList<Step<?>> result = new org.eclipse.emf.common.util.BasicEList<>();
			final java.util.List<Step<?>> steps = states.stream().flatMap(state -> {
				return state.getEndedSteps().stream().map(step -> (Step<?>) step);
			}).collect(java.util.stream.Collectors.toList());
			result.addAll(steps);
			return result;
		} else {
			return new org.eclipse.emf.common.util.BasicEList<>();
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
		case VirtualtracePackage.STATE_GROUP__TRACE:
			if (eInternalContainer() != null)
				msgs = eBasicRemoveFromContainer(msgs);
			return basicSetTrace((VirtualTrace) otherEnd, msgs);
		}
		return super.eInverseAdd(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
		case VirtualtracePackage.STATE_GROUP__TRACE:
			return basicSetTrace(null, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eBasicRemoveFromContainerFeature(NotificationChain msgs) {
		switch (eContainerFeatureID()) {
		case VirtualtracePackage.STATE_GROUP__TRACE:
			return eInternalContainer().eInverseRemove(this, VirtualtracePackage.VIRTUAL_TRACE__STATE_GROUPS,
					VirtualTrace.class, msgs);
		}
		return super.eBasicRemoveFromContainerFeature(msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
		case VirtualtracePackage.STATE_GROUP__TRACE:
			return getTrace();
		case VirtualtracePackage.STATE_GROUP__VALUES:
			return getValues();
		case VirtualtracePackage.STATE_GROUP__STATES:
			return getStates();
		case VirtualtracePackage.STATE_GROUP__STARTING_STEPS:
			return getStartingSteps();
		case VirtualtracePackage.STATE_GROUP__ENDING_STEPS:
			return getEndingSteps();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
		case VirtualtracePackage.STATE_GROUP__TRACE:
			setTrace((VirtualTrace) newValue);
			return;
		case VirtualtracePackage.STATE_GROUP__STATES:
			getStates().clear();
			getStates().addAll((Collection<? extends State<?, ?>>) newValue);
			return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
		case VirtualtracePackage.STATE_GROUP__TRACE:
			setTrace((VirtualTrace) null);
			return;
		case VirtualtracePackage.STATE_GROUP__STATES:
			getStates().clear();
			return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
		case VirtualtracePackage.STATE_GROUP__TRACE:
			return getTrace() != null;
		case VirtualtracePackage.STATE_GROUP__VALUES:
			return !getValues().isEmpty();
		case VirtualtracePackage.STATE_GROUP__STATES:
			return states != null && !states.isEmpty();
		case VirtualtracePackage.STATE_GROUP__STARTING_STEPS:
			return !getStartingSteps().isEmpty();
		case VirtualtracePackage.STATE_GROUP__ENDING_STEPS:
			return !getEndingSteps().isEmpty();
		}
		return super.eIsSet(featureID);
	}

} //StateGroupImpl
