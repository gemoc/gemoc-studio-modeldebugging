/**
 */
package org.eclipse.gemoc.executionframework.event.model.genericevent.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.emf.ecore.util.EObjectResolvingEList;

import org.eclipse.gemoc.commons.value.model.value.Value;

import org.eclipse.gemoc.executionframework.event.model.event.impl.EventImpl;

import org.eclipse.gemoc.executionframework.event.model.genericevent.GenericEvent;
import org.eclipse.gemoc.executionframework.event.model.genericevent.GenericeventPackage;
import org.eclipse.gemoc.executionframework.event.model.genericevent.RuleID;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Generic Event</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.gemoc.executionframework.event.model.genericevent.impl.GenericEventImpl#getRuleID <em>Rule ID</em>}</li>
 *   <li>{@link org.eclipse.gemoc.executionframework.event.model.genericevent.impl.GenericEventImpl#getValues <em>Values</em>}</li>
 * </ul>
 *
 * @generated
 */
public class GenericEventImpl extends EventImpl implements GenericEvent {
	/**
	 * The cached value of the '{@link #getRuleID() <em>Rule ID</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRuleID()
	 * @generated
	 * @ordered
	 */
	protected RuleID ruleID;

	/**
	 * The cached value of the '{@link #getValues() <em>Values</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getValues()
	 * @generated
	 * @ordered
	 */
	protected EList<Value> values;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected GenericEventImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return GenericeventPackage.Literals.GENERIC_EVENT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public RuleID getRuleID() {
		return ruleID;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetRuleID(RuleID newRuleID, NotificationChain msgs) {
		RuleID oldRuleID = ruleID;
		ruleID = newRuleID;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, GenericeventPackage.GENERIC_EVENT__RULE_ID, oldRuleID, newRuleID);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setRuleID(RuleID newRuleID) {
		if (newRuleID != ruleID) {
			NotificationChain msgs = null;
			if (ruleID != null)
				msgs = ((InternalEObject)ruleID).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - GenericeventPackage.GENERIC_EVENT__RULE_ID, null, msgs);
			if (newRuleID != null)
				msgs = ((InternalEObject)newRuleID).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - GenericeventPackage.GENERIC_EVENT__RULE_ID, null, msgs);
			msgs = basicSetRuleID(newRuleID, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, GenericeventPackage.GENERIC_EVENT__RULE_ID, newRuleID, newRuleID));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Value> getValues() {
		if (values == null) {
			values = new EObjectResolvingEList<Value>(Value.class, this, GenericeventPackage.GENERIC_EVENT__VALUES);
		}
		return values;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case GenericeventPackage.GENERIC_EVENT__RULE_ID:
				return basicSetRuleID(null, msgs);
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
			case GenericeventPackage.GENERIC_EVENT__RULE_ID:
				return getRuleID();
			case GenericeventPackage.GENERIC_EVENT__VALUES:
				return getValues();
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
			case GenericeventPackage.GENERIC_EVENT__RULE_ID:
				setRuleID((RuleID)newValue);
				return;
			case GenericeventPackage.GENERIC_EVENT__VALUES:
				getValues().clear();
				getValues().addAll((Collection<? extends Value>)newValue);
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
			case GenericeventPackage.GENERIC_EVENT__RULE_ID:
				setRuleID((RuleID)null);
				return;
			case GenericeventPackage.GENERIC_EVENT__VALUES:
				getValues().clear();
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
			case GenericeventPackage.GENERIC_EVENT__RULE_ID:
				return ruleID != null;
			case GenericeventPackage.GENERIC_EVENT__VALUES:
				return values != null && !values.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} //GenericEventImpl
