/**
 */
package org.eclipse.gemoc.executionframework.property.model.property.impl;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.gemoc.executionframework.property.model.property.OrderedTemporalProperty;
import org.eclipse.gemoc.executionframework.property.model.property.PropertyPackage;

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
	 * The default value of the '{@link #getOtherPattern() <em>Other Pattern</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOtherPattern()
	 * @generated
	 * @ordered
	 */
	protected static final String OTHER_PATTERN_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getOtherPattern() <em>Other Pattern</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOtherPattern()
	 * @generated
	 * @ordered
	 */
	protected String otherPattern = OTHER_PATTERN_EDEFAULT;

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
	public String getOtherPattern() {
		return otherPattern;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setOtherPattern(String newOtherPattern) {
		String oldOtherPattern = otherPattern;
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
			return getOtherPattern();
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
			setOtherPattern((String) newValue);
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
			setOtherPattern(OTHER_PATTERN_EDEFAULT);
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
			return OTHER_PATTERN_EDEFAULT == null ? otherPattern != null : !OTHER_PATTERN_EDEFAULT.equals(otherPattern);
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy())
			return super.toString();

		StringBuilder result = new StringBuilder(super.toString());
		result.append(" (otherPattern: ");
		result.append(otherPattern);
		result.append(')');
		return result.toString();
	}

} //OrderedTemporalPropertyImpl
