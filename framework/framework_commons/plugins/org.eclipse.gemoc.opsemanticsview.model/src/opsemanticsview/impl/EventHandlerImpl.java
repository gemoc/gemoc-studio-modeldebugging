/**
 */
package opsemanticsview.impl;

import opsemanticsview.EventHandler;
import opsemanticsview.OpsemanticsviewPackage;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Event Handler</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link opsemanticsview.impl.EventHandlerImpl#getCondition <em>Condition</em>}</li>
 *   <li>{@link opsemanticsview.impl.EventHandlerImpl#isInterruptible <em>Interruptible</em>}</li>
 *   <li>{@link opsemanticsview.impl.EventHandlerImpl#isStart <em>Start</em>}</li>
 * </ul>
 *
 * @generated
 */
public class EventHandlerImpl extends RuleImpl implements EventHandler {
	/**
	 * The cached value of the '{@link #getCondition() <em>Condition</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCondition()
	 * @generated
	 * @ordered
	 */
	protected EOperation condition;

	/**
	 * The default value of the '{@link #isInterruptible() <em>Interruptible</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isInterruptible()
	 * @generated
	 * @ordered
	 */
	protected static final boolean INTERRUPTIBLE_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isInterruptible() <em>Interruptible</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isInterruptible()
	 * @generated
	 * @ordered
	 */
	protected boolean interruptible = INTERRUPTIBLE_EDEFAULT;

	/**
	 * The default value of the '{@link #isStart() <em>Start</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isStart()
	 * @generated
	 * @ordered
	 */
	protected static final boolean START_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isStart() <em>Start</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isStart()
	 * @generated
	 * @ordered
	 */
	protected boolean start = START_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EventHandlerImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return OpsemanticsviewPackage.Literals.EVENT_HANDLER;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getCondition() {
		return condition;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetCondition(EOperation newCondition, NotificationChain msgs) {
		EOperation oldCondition = condition;
		condition = newCondition;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, OpsemanticsviewPackage.EVENT_HANDLER__CONDITION, oldCondition, newCondition);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setCondition(EOperation newCondition) {
		if (newCondition != condition) {
			NotificationChain msgs = null;
			if (condition != null)
				msgs = ((InternalEObject)condition).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - OpsemanticsviewPackage.EVENT_HANDLER__CONDITION, null, msgs);
			if (newCondition != null)
				msgs = ((InternalEObject)newCondition).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - OpsemanticsviewPackage.EVENT_HANDLER__CONDITION, null, msgs);
			msgs = basicSetCondition(newCondition, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OpsemanticsviewPackage.EVENT_HANDLER__CONDITION, newCondition, newCondition));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isInterruptible() {
		return interruptible;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setInterruptible(boolean newInterruptible) {
		boolean oldInterruptible = interruptible;
		interruptible = newInterruptible;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OpsemanticsviewPackage.EVENT_HANDLER__INTERRUPTIBLE, oldInterruptible, interruptible));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isStart() {
		return start;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setStart(boolean newStart) {
		boolean oldStart = start;
		start = newStart;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OpsemanticsviewPackage.EVENT_HANDLER__START, oldStart, start));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case OpsemanticsviewPackage.EVENT_HANDLER__CONDITION:
				return basicSetCondition(null, msgs);
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
			case OpsemanticsviewPackage.EVENT_HANDLER__CONDITION:
				return getCondition();
			case OpsemanticsviewPackage.EVENT_HANDLER__INTERRUPTIBLE:
				return isInterruptible();
			case OpsemanticsviewPackage.EVENT_HANDLER__START:
				return isStart();
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
			case OpsemanticsviewPackage.EVENT_HANDLER__CONDITION:
				setCondition((EOperation)newValue);
				return;
			case OpsemanticsviewPackage.EVENT_HANDLER__INTERRUPTIBLE:
				setInterruptible((Boolean)newValue);
				return;
			case OpsemanticsviewPackage.EVENT_HANDLER__START:
				setStart((Boolean)newValue);
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
			case OpsemanticsviewPackage.EVENT_HANDLER__CONDITION:
				setCondition((EOperation)null);
				return;
			case OpsemanticsviewPackage.EVENT_HANDLER__INTERRUPTIBLE:
				setInterruptible(INTERRUPTIBLE_EDEFAULT);
				return;
			case OpsemanticsviewPackage.EVENT_HANDLER__START:
				setStart(START_EDEFAULT);
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
			case OpsemanticsviewPackage.EVENT_HANDLER__CONDITION:
				return condition != null;
			case OpsemanticsviewPackage.EVENT_HANDLER__INTERRUPTIBLE:
				return interruptible != INTERRUPTIBLE_EDEFAULT;
			case OpsemanticsviewPackage.EVENT_HANDLER__START:
				return start != START_EDEFAULT;
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
		result.append(" (interruptible: ");
		result.append(interruptible);
		result.append(", start: ");
		result.append(start);
		result.append(')');
		return result.toString();
	}

} //EventHandlerImpl
