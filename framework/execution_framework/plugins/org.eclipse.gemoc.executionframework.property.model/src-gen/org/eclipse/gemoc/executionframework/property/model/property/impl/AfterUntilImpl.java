/**
 */
package org.eclipse.gemoc.executionframework.property.model.property.impl;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.gemoc.executionframework.property.model.property.AfterUntil;
import org.eclipse.gemoc.executionframework.property.model.property.PropertyPackage;
import org.eclipse.gemoc.executionframework.property.model.property.UpperBounded;

import org.eclipse.viatra.query.patternlanguage.emf.vql.Pattern;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>After Until</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.gemoc.executionframework.property.model.property.impl.AfterUntilImpl#getUpperBoundPattern <em>Upper Bound Pattern</em>}</li>
 * </ul>
 *
 * @generated
 */
public class AfterUntilImpl extends LowerBoundedImpl implements AfterUntil {
	/**
	 * The cached value of the '{@link #getUpperBoundPattern() <em>Upper Bound Pattern</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getUpperBoundPattern()
	 * @generated
	 * @ordered
	 */
	protected Pattern upperBoundPattern;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected AfterUntilImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return PropertyPackage.Literals.AFTER_UNTIL;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Pattern getUpperBoundPattern() {
		if (upperBoundPattern != null && upperBoundPattern.eIsProxy()) {
			InternalEObject oldUpperBoundPattern = (InternalEObject) upperBoundPattern;
			upperBoundPattern = (Pattern) eResolveProxy(oldUpperBoundPattern);
			if (upperBoundPattern != oldUpperBoundPattern) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE,
							PropertyPackage.AFTER_UNTIL__UPPER_BOUND_PATTERN, oldUpperBoundPattern, upperBoundPattern));
			}
		}
		return upperBoundPattern;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Pattern basicGetUpperBoundPattern() {
		return upperBoundPattern;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setUpperBoundPattern(Pattern newUpperBoundPattern) {
		Pattern oldUpperBoundPattern = upperBoundPattern;
		upperBoundPattern = newUpperBoundPattern;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, PropertyPackage.AFTER_UNTIL__UPPER_BOUND_PATTERN,
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
		case PropertyPackage.AFTER_UNTIL__UPPER_BOUND_PATTERN:
			if (resolve)
				return getUpperBoundPattern();
			return basicGetUpperBoundPattern();
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
		case PropertyPackage.AFTER_UNTIL__UPPER_BOUND_PATTERN:
			setUpperBoundPattern((Pattern) newValue);
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
		case PropertyPackage.AFTER_UNTIL__UPPER_BOUND_PATTERN:
			setUpperBoundPattern((Pattern) null);
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
		case PropertyPackage.AFTER_UNTIL__UPPER_BOUND_PATTERN:
			return upperBoundPattern != null;
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int eBaseStructuralFeatureID(int derivedFeatureID, Class<?> baseClass) {
		if (baseClass == UpperBounded.class) {
			switch (derivedFeatureID) {
			case PropertyPackage.AFTER_UNTIL__UPPER_BOUND_PATTERN:
				return PropertyPackage.UPPER_BOUNDED__UPPER_BOUND_PATTERN;
			default:
				return -1;
			}
		}
		return super.eBaseStructuralFeatureID(derivedFeatureID, baseClass);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int eDerivedStructuralFeatureID(int baseFeatureID, Class<?> baseClass) {
		if (baseClass == UpperBounded.class) {
			switch (baseFeatureID) {
			case PropertyPackage.UPPER_BOUNDED__UPPER_BOUND_PATTERN:
				return PropertyPackage.AFTER_UNTIL__UPPER_BOUND_PATTERN;
			default:
				return -1;
			}
		}
		return super.eDerivedStructuralFeatureID(baseFeatureID, baseClass);
	}

} //AfterUntilImpl
