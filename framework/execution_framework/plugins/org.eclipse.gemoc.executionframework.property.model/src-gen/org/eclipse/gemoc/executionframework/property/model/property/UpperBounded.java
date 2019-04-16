/**
 */
package org.eclipse.gemoc.executionframework.property.model.property;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Upper Bounded</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.gemoc.executionframework.property.model.property.UpperBounded#getUpperBoundPattern <em>Upper Bound Pattern</em>}</li>
 * </ul>
 *
 * @see org.eclipse.gemoc.executionframework.property.model.property.PropertyPackage#getUpperBounded()
 * @model abstract="true"
 * @generated
 */
public interface UpperBounded extends Scope {
	/**
	 * Returns the value of the '<em><b>Upper Bound Pattern</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Upper Bound Pattern</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Upper Bound Pattern</em>' attribute.
	 * @see #setUpperBoundPattern(String)
	 * @see org.eclipse.gemoc.executionframework.property.model.property.PropertyPackage#getUpperBounded_UpperBoundPattern()
	 * @model required="true"
	 * @generated
	 */
	String getUpperBoundPattern();

	/**
	 * Sets the value of the '{@link org.eclipse.gemoc.executionframework.property.model.property.UpperBounded#getUpperBoundPattern <em>Upper Bound Pattern</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Upper Bound Pattern</em>' attribute.
	 * @see #getUpperBoundPattern()
	 * @generated
	 */
	void setUpperBoundPattern(String value);

} // UpperBounded
