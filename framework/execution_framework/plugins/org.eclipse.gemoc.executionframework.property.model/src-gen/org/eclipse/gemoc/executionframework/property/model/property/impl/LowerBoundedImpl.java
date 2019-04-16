/**
 */
package org.eclipse.gemoc.executionframework.property.model.property.impl;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.gemoc.executionframework.property.model.property.LowerBounded;
import org.eclipse.gemoc.executionframework.property.model.property.PropertyPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Lower Bounded</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.gemoc.executionframework.property.model.property.impl.LowerBoundedImpl#getLowerBoundPattern <em>Lower Bound Pattern</em>}</li>
 * </ul>
 *
 * @generated
 */
public abstract class LowerBoundedImpl extends ScopeImpl implements LowerBounded {
	/**
	 * The default value of the '{@link #getLowerBoundPattern() <em>Lower Bound Pattern</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLowerBoundPattern()
	 * @generated
	 * @ordered
	 */
	protected static final String LOWER_BOUND_PATTERN_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getLowerBoundPattern() <em>Lower Bound Pattern</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLowerBoundPattern()
	 * @generated
	 * @ordered
	 */
	protected String lowerBoundPattern = LOWER_BOUND_PATTERN_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected LowerBoundedImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return PropertyPackage.Literals.LOWER_BOUNDED;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getLowerBoundPattern() {
		return lowerBoundPattern;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setLowerBoundPattern(String newLowerBoundPattern) {
		String oldLowerBoundPattern = lowerBoundPattern;
		lowerBoundPattern = newLowerBoundPattern;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, PropertyPackage.LOWER_BOUNDED__LOWER_BOUND_PATTERN,
					oldLowerBoundPattern, lowerBoundPattern));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
		case PropertyPackage.LOWER_BOUNDED__LOWER_BOUND_PATTERN:
			return getLowerBoundPattern();
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
		case PropertyPackage.LOWER_BOUNDED__LOWER_BOUND_PATTERN:
			setLowerBoundPattern((String) newValue);
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
		case PropertyPackage.LOWER_BOUNDED__LOWER_BOUND_PATTERN:
			setLowerBoundPattern(LOWER_BOUND_PATTERN_EDEFAULT);
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
		case PropertyPackage.LOWER_BOUNDED__LOWER_BOUND_PATTERN:
			return LOWER_BOUND_PATTERN_EDEFAULT == null ? lowerBoundPattern != null
					: !LOWER_BOUND_PATTERN_EDEFAULT.equals(lowerBoundPattern);
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
		result.append(" (lowerBoundPattern: ");
		result.append(lowerBoundPattern);
		result.append(')');
		return result.toString();
	}

} //LowerBoundedImpl
