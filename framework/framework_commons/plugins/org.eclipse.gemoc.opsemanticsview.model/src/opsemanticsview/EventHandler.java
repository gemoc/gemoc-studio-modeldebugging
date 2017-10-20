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
 * </ul>
 *
 * @see opsemanticsview.OpsemanticsviewPackage#getEventHandler()
 * @model
 * @generated
 */
public interface EventHandler extends Rule {
	/**
	 * Returns the value of the '<em><b>Condition</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Condition</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Condition</em>' reference.
	 * @see #setCondition(EOperation)
	 * @see opsemanticsview.OpsemanticsviewPackage#getEventHandler_Condition()
	 * @model
	 * @generated
	 */
	EOperation getCondition();

	/**
	 * Sets the value of the '{@link opsemanticsview.EventHandler#getCondition <em>Condition</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Condition</em>' reference.
	 * @see #getCondition()
	 * @generated
	 */
	void setCondition(EOperation value);

} // EventHandler
