/**
 */
package org.eclipse.gemoc.executionframework.property.model.property;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Existence</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.gemoc.executionframework.property.model.property.Existence#getN <em>N</em>}</li>
 *   <li>{@link org.eclipse.gemoc.executionframework.property.model.property.Existence#getBoundType <em>Bound Type</em>}</li>
 * </ul>
 *
 * @see org.eclipse.gemoc.executionframework.property.model.property.PropertyPackage#getExistence()
 * @model
 * @generated
 */
public interface Existence extends TemporalProperty {
	/**
	 * Returns the value of the '<em><b>N</b></em>' attribute.
	 * The default value is <code>"1"</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>N</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>N</em>' attribute.
	 * @see #setN(int)
	 * @see org.eclipse.gemoc.executionframework.property.model.property.PropertyPackage#getExistence_N()
	 * @model default="1" required="true"
	 * @generated
	 */
	int getN();

	/**
	 * Sets the value of the '{@link org.eclipse.gemoc.executionframework.property.model.property.Existence#getN <em>N</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>N</em>' attribute.
	 * @see #getN()
	 * @generated
	 */
	void setN(int value);

	/**
	 * Returns the value of the '<em><b>Bound Type</b></em>' attribute.
	 * The literals are from the enumeration {@link org.eclipse.gemoc.executionframework.property.model.property.BoundType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Bound Type</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Bound Type</em>' attribute.
	 * @see org.eclipse.gemoc.executionframework.property.model.property.BoundType
	 * @see #setBoundType(BoundType)
	 * @see org.eclipse.gemoc.executionframework.property.model.property.PropertyPackage#getExistence_BoundType()
	 * @model
	 * @generated
	 */
	BoundType getBoundType();

	/**
	 * Sets the value of the '{@link org.eclipse.gemoc.executionframework.property.model.property.Existence#getBoundType <em>Bound Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Bound Type</em>' attribute.
	 * @see org.eclipse.gemoc.executionframework.property.model.property.BoundType
	 * @see #getBoundType()
	 * @generated
	 */
	void setBoundType(BoundType value);

} // Existence
