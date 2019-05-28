/**
 */
package org.eclipse.gemoc.executionframework.property.model.property.impl;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.gemoc.executionframework.property.model.property.OrderedTemporalProperty;
import org.eclipse.gemoc.executionframework.property.model.property.PropertyPackage;

import org.eclipse.viatra.query.patternlanguage.emf.vql.Pattern;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Ordered Temporal Property</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.gemoc.executionframework.property.model.property.impl.OrderedTemporalPropertyImpl#getOtherPattern <em>Other Pattern</em>}</li>
 * </ul>
 *
 * @generated
 */
public abstract class OrderedTemporalPropertyImpl extends TemporalPropertyImpl implements OrderedTemporalProperty {
	/**
	 * The cached value of the '{@link #getOtherPattern() <em>Other Pattern</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOtherPattern()
	 * @generated
	 * @ordered
	 */
	protected Pattern otherPattern;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected OrderedTemporalPropertyImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return PropertyPackage.Literals.ORDERED_TEMPORAL_PROPERTY;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Pattern getOtherPattern() {
		if (otherPattern != null && otherPattern.eIsProxy()) {
			InternalEObject oldOtherPattern = (InternalEObject) otherPattern;
			otherPattern = (Pattern) eResolveProxy(oldOtherPattern);
			if (otherPattern != oldOtherPattern) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE,
							PropertyPackage.ORDERED_TEMPORAL_PROPERTY__OTHER_PATTERN, oldOtherPattern, otherPattern));
			}
		}
		return otherPattern;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Pattern basicGetOtherPattern() {
		return otherPattern;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setOtherPattern(Pattern newOtherPattern) {
		Pattern oldOtherPattern = otherPattern;
		otherPattern = newOtherPattern;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					PropertyPackage.ORDERED_TEMPORAL_PROPERTY__OTHER_PATTERN, oldOtherPattern, otherPattern));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
		case PropertyPackage.ORDERED_TEMPORAL_PROPERTY__OTHER_PATTERN:
			if (resolve)
				return getOtherPattern();
			return basicGetOtherPattern();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
		case PropertyPackage.ORDERED_TEMPORAL_PROPERTY__OTHER_PATTERN:
			setOtherPattern((Pattern) newValue);
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
		case PropertyPackage.ORDERED_TEMPORAL_PROPERTY__OTHER_PATTERN:
			setOtherPattern((Pattern) null);
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
		case PropertyPackage.ORDERED_TEMPORAL_PROPERTY__OTHER_PATTERN:
			return otherPattern != null;
		}
		return super.eIsSet(featureID);
	}

} //OrderedTemporalPropertyImpl
