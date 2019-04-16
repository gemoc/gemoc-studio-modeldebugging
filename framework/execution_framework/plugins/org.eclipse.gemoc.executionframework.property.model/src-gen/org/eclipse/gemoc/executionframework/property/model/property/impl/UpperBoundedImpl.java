/**
 */
package org.eclipse.gemoc.executionframework.property.model.property.impl;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.gemoc.executionframework.property.model.property.PropertyPackage;
import org.eclipse.gemoc.executionframework.property.model.property.UpperBounded;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Upper Bounded</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.gemoc.executionframework.property.model.property.impl.UpperBoundedImpl#getUpperBoundPattern <em>Upper Bound Pattern</em>}</li>
 * </ul>
 *
 * @generated
 */
public abstract class UpperBoundedImpl extends ScopeImpl implements UpperBounded {
	/**
	 * The default value of the '{@link #getUpperBoundPattern() <em>Upper Bound Pattern</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getUpperBoundPattern()
	 * @generated
	 * @ordered
	 */
	protected static final String UPPER_BOUND_PATTERN_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getUpperBoundPattern() <em>Upper Bound Pattern</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getUpperBoundPattern()
	 * @generated
	 * @ordered
	 */
	protected String upperBoundPattern = UPPER_BOUND_PATTERN_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected UpperBoundedImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return PropertyPackage.Literals.UPPER_BOUNDED;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getUpperBoundPattern() {
		return upperBoundPattern;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setUpperBoundPattern(String newUpperBoundPattern) {
		String oldUpperBoundPattern = upperBoundPattern;
		upperBoundPattern = newUpperBoundPattern;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, PropertyPackage.UPPER_BOUNDED__UPPER_BOUND_PATTERN,
					oldUpperBoundPattern, upperBoundPattern));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
		case PropertyPackage.UPPER_BOUNDED__UPPER_BOUND_PATTERN:
			return getUpperBoundPattern();
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
		case PropertyPackage.UPPER_BOUNDED__UPPER_BOUND_PATTERN:
			setUpperBoundPattern((String) newValue);
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
		case PropertyPackage.UPPER_BOUNDED__UPPER_BOUND_PATTERN:
			setUpperBoundPattern(UPPER_BOUND_PATTERN_EDEFAULT);
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
		case PropertyPackage.UPPER_BOUNDED__UPPER_BOUND_PATTERN:
			return UPPER_BOUND_PATTERN_EDEFAULT == null ? upperBoundPattern != null
					: !UPPER_BOUND_PATTERN_EDEFAULT.equals(upperBoundPattern);
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
		result.append(" (upperBoundPattern: ");
		result.append(upperBoundPattern);
		result.append(')');
		return result.toString();
	}

} //UpperBoundedImpl
