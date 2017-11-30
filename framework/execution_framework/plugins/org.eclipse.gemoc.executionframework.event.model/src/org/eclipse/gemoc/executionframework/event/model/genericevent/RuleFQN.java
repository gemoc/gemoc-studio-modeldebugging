/**
 */
package org.eclipse.gemoc.executionframework.event.model.genericevent;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Rule FQN</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.gemoc.executionframework.event.model.genericevent.RuleFQN#getFqn <em>Fqn</em>}</li>
 * </ul>
 *
 * @see org.eclipse.gemoc.executionframework.event.model.genericevent.GenericeventPackage#getRuleFQN()
 * @model
 * @generated
 */
public interface RuleFQN extends RuleID {
	/**
	 * Returns the value of the '<em><b>Fqn</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Fqn</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Fqn</em>' attribute.
	 * @see #setFqn(String)
	 * @see org.eclipse.gemoc.executionframework.event.model.genericevent.GenericeventPackage#getRuleFQN_Fqn()
	 * @model
	 * @generated
	 */
	String getFqn();

	/**
	 * Sets the value of the '{@link org.eclipse.gemoc.executionframework.event.model.genericevent.RuleFQN#getFqn <em>Fqn</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Fqn</em>' attribute.
	 * @see #getFqn()
	 * @generated
	 */
	void setFqn(String value);

} // RuleFQN
