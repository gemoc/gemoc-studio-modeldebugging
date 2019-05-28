/**
 */
package org.eclipse.gemoc.executionframework.property.model.property.impl;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.gemoc.executionframework.property.model.property.LowerBounded;
import org.eclipse.gemoc.executionframework.property.model.property.PropertyPackage;

import org.eclipse.viatra.query.patternlanguage.emf.vql.Pattern;

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
	 * The cached value of the '{@link #getLowerBoundPattern() <em>Lower Bound Pattern</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLowerBoundPattern()
	 * @generated
	 * @ordered
	 */
	protected Pattern lowerBoundPattern;

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
	public Pattern getLowerBoundPattern() {
		if (lowerBoundPattern != null && lowerBoundPattern.eIsProxy()) {
			InternalEObject oldLowerBoundPattern = (InternalEObject) lowerBoundPattern;
			lowerBoundPattern = (Pattern) eResolveProxy(oldLowerBoundPattern);
			if (lowerBoundPattern != oldLowerBoundPattern) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE,
							PropertyPackage.LOWER_BOUNDED__LOWER_BOUND_PATTERN, oldLowerBoundPattern,
							lowerBoundPattern));
			}
		}
		return lowerBoundPattern;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Pattern basicGetLowerBoundPattern() {
		return lowerBoundPattern;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setLowerBoundPattern(Pattern newLowerBoundPattern) {
		Pattern oldLowerBoundPattern = lowerBoundPattern;
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
			if (resolve)
				return getLowerBoundPattern();
			return basicGetLowerBoundPattern();
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
			setLowerBoundPattern((Pattern) newValue);
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
			setLowerBoundPattern((Pattern) null);
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
			return lowerBoundPattern != null;
		}
		return super.eIsSet(featureID);
	}

} //LowerBoundedImpl
