/**
 */
package org.eclipse.gemoc.executionframework.event.model.genericevent.impl;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.gemoc.executionframework.event.model.genericevent.GenericeventPackage;
import org.eclipse.gemoc.executionframework.event.model.genericevent.RuleFQN;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Rule FQN</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.gemoc.executionframework.event.model.genericevent.impl.RuleFQNImpl#getFqn <em>Fqn</em>}</li>
 * </ul>
 *
 * @generated
 */
public class RuleFQNImpl extends RuleIDImpl implements RuleFQN {
	/**
	 * The default value of the '{@link #getFqn() <em>Fqn</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFqn()
	 * @generated
	 * @ordered
	 */
	protected static final String FQN_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getFqn() <em>Fqn</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFqn()
	 * @generated
	 * @ordered
	 */
	protected String fqn = FQN_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected RuleFQNImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return GenericeventPackage.Literals.RULE_FQN;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getFqn() {
		return fqn;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setFqn(String newFqn) {
		String oldFqn = fqn;
		fqn = newFqn;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, GenericeventPackage.RULE_FQN__FQN, oldFqn, fqn));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case GenericeventPackage.RULE_FQN__FQN:
				return getFqn();
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
			case GenericeventPackage.RULE_FQN__FQN:
				setFqn((String)newValue);
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
			case GenericeventPackage.RULE_FQN__FQN:
				setFqn(FQN_EDEFAULT);
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
			case GenericeventPackage.RULE_FQN__FQN:
				return FQN_EDEFAULT == null ? fqn != null : !FQN_EDEFAULT.equals(fqn);
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
		if (eIsProxy()) return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (fqn: ");
		result.append(fqn);
		result.append(')');
		return result.toString();
	}

} //RuleFQNImpl
