/**
 */
package org.eclipse.gemoc.executionframework.event.model.property;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>String Attribute Property</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.gemoc.executionframework.event.model.property.StringAttributeProperty#getValue <em>Value</em>}</li>
 *   <li>{@link org.eclipse.gemoc.executionframework.event.model.property.StringAttributeProperty#getOperator <em>Operator</em>}</li>
 * </ul>
 *
 * @see org.eclipse.gemoc.executionframework.event.model.property.PropertyPackage#getStringAttributeProperty()
 * @model abstract="true"
 * @generated
 */
public interface StringAttributeProperty<T> extends StateProperty<T> {
	/**
	 * Returns the value of the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Value</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Value</em>' attribute.
	 * @see #setValue(String)
	 * @see org.eclipse.gemoc.executionframework.event.model.property.PropertyPackage#getStringAttributeProperty_Value()
	 * @model
	 * @generated
	 */
	String getValue();

	/**
	 * Sets the value of the '{@link org.eclipse.gemoc.executionframework.event.model.property.StringAttributeProperty#getValue <em>Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Value</em>' attribute.
	 * @see #getValue()
	 * @generated
	 */
	void setValue(String value);

	/**
	 * Returns the value of the '<em><b>Operator</b></em>' attribute.
	 * The literals are from the enumeration {@link org.eclipse.gemoc.executionframework.event.model.property.ComparisonOperator}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Operator</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Operator</em>' attribute.
	 * @see org.eclipse.gemoc.executionframework.event.model.property.ComparisonOperator
	 * @see #setOperator(ComparisonOperator)
	 * @see org.eclipse.gemoc.executionframework.event.model.property.PropertyPackage#getStringAttributeProperty_Operator()
	 * @model
	 * @generated
	 */
	ComparisonOperator getOperator();

	/**
	 * Sets the value of the '{@link org.eclipse.gemoc.executionframework.event.model.property.StringAttributeProperty#getOperator <em>Operator</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Operator</em>' attribute.
	 * @see org.eclipse.gemoc.executionframework.event.model.property.ComparisonOperator
	 * @see #getOperator()
	 * @generated
	 */
	void setOperator(ComparisonOperator value);

} // StringAttributeProperty
