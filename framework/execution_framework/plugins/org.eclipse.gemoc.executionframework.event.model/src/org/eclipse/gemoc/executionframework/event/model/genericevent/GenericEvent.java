/**
 */
package org.eclipse.gemoc.executionframework.event.model.genericevent;

import org.eclipse.emf.common.util.EList;

import org.eclipse.gemoc.commons.value.model.value.Value;

import org.eclipse.gemoc.executionframework.event.model.event.Event;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Generic Event</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.gemoc.executionframework.event.model.genericevent.GenericEvent#getRuleID <em>Rule ID</em>}</li>
 *   <li>{@link org.eclipse.gemoc.executionframework.event.model.genericevent.GenericEvent#getValues <em>Values</em>}</li>
 * </ul>
 *
 * @see org.eclipse.gemoc.executionframework.event.model.genericevent.GenericeventPackage#getGenericEvent()
 * @model
 * @generated
 */
public interface GenericEvent extends Event {
	/**
	 * Returns the value of the '<em><b>Rule ID</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Rule ID</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Rule ID</em>' containment reference.
	 * @see #setRuleID(RuleID)
	 * @see org.eclipse.gemoc.executionframework.event.model.genericevent.GenericeventPackage#getGenericEvent_RuleID()
	 * @model containment="true"
	 * @generated
	 */
	RuleID getRuleID();

	/**
	 * Sets the value of the '{@link org.eclipse.gemoc.executionframework.event.model.genericevent.GenericEvent#getRuleID <em>Rule ID</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Rule ID</em>' containment reference.
	 * @see #getRuleID()
	 * @generated
	 */
	void setRuleID(RuleID value);

	/**
	 * Returns the value of the '<em><b>Values</b></em>' reference list.
	 * The list contents are of type {@link org.eclipse.gemoc.commons.value.model.value.Value}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Values</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Values</em>' reference list.
	 * @see org.eclipse.gemoc.executionframework.event.model.genericevent.GenericeventPackage#getGenericEvent_Values()
	 * @model
	 * @generated
	 */
	EList<Value> getValues();

} // GenericEvent
