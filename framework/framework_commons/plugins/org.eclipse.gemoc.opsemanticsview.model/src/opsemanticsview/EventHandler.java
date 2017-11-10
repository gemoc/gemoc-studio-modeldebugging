/**
 */
package opsemanticsview;

import org.eclipse.emf.ecore.EOperation;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Event Handler</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link opsemanticsview.EventHandler#getCondition <em>Condition</em>}</li>
 *   <li>{@link opsemanticsview.EventHandler#isInterruptible <em>Interruptible</em>}</li>
 * </ul>
 *
 * @see opsemanticsview.OpsemanticsviewPackage#getEventHandler()
 * @model
 * @generated
 */
public interface EventHandler extends Rule {
	/**
	 * Returns the value of the '<em><b>Condition</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Condition</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Condition</em>' containment reference.
	 * @see #setCondition(EOperation)
	 * @see opsemanticsview.OpsemanticsviewPackage#getEventHandler_Condition()
	 * @model containment="true"
	 * @generated
	 */
	EOperation getCondition();

	/**
	 * Sets the value of the '{@link opsemanticsview.EventHandler#getCondition <em>Condition</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Condition</em>' containment reference.
	 * @see #getCondition()
	 * @generated
	 */
	void setCondition(EOperation value);

	/**
	 * Returns the value of the '<em><b>Interruptible</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Interruptible</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Interruptible</em>' attribute.
	 * @see #setInterruptible(boolean)
	 * @see opsemanticsview.OpsemanticsviewPackage#getEventHandler_Interruptible()
	 * @model
	 * @generated
	 */
	boolean isInterruptible();

	/**
	 * Sets the value of the '{@link opsemanticsview.EventHandler#isInterruptible <em>Interruptible</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Interruptible</em>' attribute.
	 * @see #isInterruptible()
	 * @generated
	 */
	void setInterruptible(boolean value);

} // EventHandler
