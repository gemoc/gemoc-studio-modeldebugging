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

import org.eclipse.emf.ecore.util.EObjectContainmentWithInverseEList;
import org.eclipse.emf.ecore.util.EObjectResolvingEList;
import org.eclipse.emf.ecore.util.InternalEList;

import org.eclipse.gemoc.trace.commons.model.trace.Dimension;
import org.eclipse.gemoc.trace.commons.model.trace.Trace;

import org.eclipse.gemoc.trace.virtual.virtualtrace.StateGroup;
import org.eclipse.gemoc.trace.virtual.virtualtrace.VirtualTrace;
import org.eclipse.gemoc.trace.virtual.virtualtrace.VirtualtracePackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Virtual Trace</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.gemoc.trace.virtual.virtualtrace.impl.VirtualTraceImpl#getTrace <em>Trace</em>}</li>
 *   <li>{@link org.eclipse.gemoc.trace.virtual.virtualtrace.impl.VirtualTraceImpl#getSelectedDimensions <em>Selected Dimensions</em>}</li>
 *   <li>{@link org.eclipse.gemoc.trace.virtual.virtualtrace.impl.VirtualTraceImpl#getStateGroups <em>State Groups</em>}</li>
 * </ul>
 *
 * @generated
 */
public class VirtualTraceImpl extends MinimalEObjectImpl.Container implements VirtualTrace {
	/**
	 * The cached value of the '{@link #getTrace() <em>Trace</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTrace()
	 * @generated
	 * @ordered
	 */
	protected Trace<?, ?, ?> trace;

	/**
	 * The cached value of the '{@link #getSelectedDimensions() <em>Selected Dimensions</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSelectedDimensions()
	 * @generated
	 * @ordered
	 */
	protected EList<Dimension<?>> selectedDimensions;

	/**
	 * The cached value of the '{@link #getStateGroups() <em>State Groups</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getStateGroups()
	 * @generated
	 * @ordered
	 */
	protected EList<StateGroup> stateGroups;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected VirtualTraceImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return VirtualtracePackage.Literals.VIRTUAL_TRACE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Trace<?, ?, ?> getTrace() {
		if (trace != null && trace.eIsProxy()) {
			InternalEObject oldTrace = (InternalEObject) trace;
			trace = (Trace<?, ?, ?>) eResolveProxy(oldTrace);
			if (trace != oldTrace) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, VirtualtracePackage.VIRTUAL_TRACE__TRACE,
							oldTrace, trace));
			}
		}
		return trace;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Trace<?, ?, ?> basicGetTrace() {
		return trace;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setTrace(Trace<?, ?, ?> newTrace) {
		Trace<?, ?, ?> oldTrace = trace;
		trace = newTrace;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, VirtualtracePackage.VIRTUAL_TRACE__TRACE, oldTrace,
					trace));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Dimension<?>> getSelectedDimensions() {
		if (selectedDimensions == null) {
			selectedDimensions = new EObjectResolvingEList<Dimension<?>>(Dimension.class, this,
					VirtualtracePackage.VIRTUAL_TRACE__SELECTED_DIMENSIONS);
		}
		return selectedDimensions;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<StateGroup> getStateGroups() {
		if (stateGroups == null) {
			stateGroups = new EObjectContainmentWithInverseEList<StateGroup>(StateGroup.class, this,
					VirtualtracePackage.VIRTUAL_TRACE__STATE_GROUPS, VirtualtracePackage.STATE_GROUP__TRACE);
		}
		return stateGroups;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
		case VirtualtracePackage.VIRTUAL_TRACE__STATE_GROUPS:
			return ((InternalEList<InternalEObject>) (InternalEList<?>) getStateGroups()).basicAdd(otherEnd, msgs);
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
		case VirtualtracePackage.VIRTUAL_TRACE__STATE_GROUPS:
			return ((InternalEList<?>) getStateGroups()).basicRemove(otherEnd, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
		case VirtualtracePackage.VIRTUAL_TRACE__TRACE:
			if (resolve)
				return getTrace();
			return basicGetTrace();
		case VirtualtracePackage.VIRTUAL_TRACE__SELECTED_DIMENSIONS:
			return getSelectedDimensions();
		case VirtualtracePackage.VIRTUAL_TRACE__STATE_GROUPS:
			return getStateGroups();
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
		case VirtualtracePackage.VIRTUAL_TRACE__TRACE:
			setTrace((Trace<?, ?, ?>) newValue);
			return;
		case VirtualtracePackage.VIRTUAL_TRACE__SELECTED_DIMENSIONS:
			getSelectedDimensions().clear();
			getSelectedDimensions().addAll((Collection<? extends Dimension<?>>) newValue);
			return;
		case VirtualtracePackage.VIRTUAL_TRACE__STATE_GROUPS:
			getStateGroups().clear();
			getStateGroups().addAll((Collection<? extends StateGroup>) newValue);
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
		case VirtualtracePackage.VIRTUAL_TRACE__TRACE:
			setTrace((Trace<?, ?, ?>) null);
			return;
		case VirtualtracePackage.VIRTUAL_TRACE__SELECTED_DIMENSIONS:
			getSelectedDimensions().clear();
			return;
		case VirtualtracePackage.VIRTUAL_TRACE__STATE_GROUPS:
			getStateGroups().clear();
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
		case VirtualtracePackage.VIRTUAL_TRACE__TRACE:
			return trace != null;
		case VirtualtracePackage.VIRTUAL_TRACE__SELECTED_DIMENSIONS:
			return selectedDimensions != null && !selectedDimensions.isEmpty();
		case VirtualtracePackage.VIRTUAL_TRACE__STATE_GROUPS:
			return stateGroups != null && !stateGroups.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} //VirtualTraceImpl
