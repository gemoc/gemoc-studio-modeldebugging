/**
 */
package org.eclipse.gemoc.executionframework.property.model.property;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Quantified Property</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.gemoc.executionframework.property.model.property.QuantifiedProperty#getQuantifier <em>Quantifier</em>}</li>
 *   <li>{@link org.eclipse.gemoc.executionframework.property.model.property.QuantifiedProperty#getProperty <em>Property</em>}</li>
 * </ul>
 *
 * @see org.eclipse.gemoc.executionframework.property.model.property.PropertyPackage#getQuantifiedProperty()
 * @model
 * @generated
 */
public interface QuantifiedProperty extends Property {
	/**
	 * Returns the value of the '<em><b>Quantifier</b></em>' attribute.
	 * The literals are from the enumeration {@link org.eclipse.gemoc.executionframework.property.model.property.Quantifier}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Quantifier</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Quantifier</em>' attribute.
	 * @see org.eclipse.gemoc.executionframework.property.model.property.Quantifier
	 * @see #setQuantifier(Quantifier)
	 * @see org.eclipse.gemoc.executionframework.property.model.property.PropertyPackage#getQuantifiedProperty_Quantifier()
	 * @model required="true"
	 * @generated
	 */
	Quantifier getQuantifier();

	/**
	 * Sets the value of the '{@link org.eclipse.gemoc.executionframework.property.model.property.QuantifiedProperty#getQuantifier <em>Quantifier</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Quantifier</em>' attribute.
	 * @see org.eclipse.gemoc.executionframework.property.model.property.Quantifier
	 * @see #getQuantifier()
	 * @generated
	 */
	void setQuantifier(Quantifier value);

	/**
	 * Returns the value of the '<em><b>Property</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Property</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Property</em>' containment reference.
	 * @see #setProperty(Property)
	 * @see org.eclipse.gemoc.executionframework.property.model.property.PropertyPackage#getQuantifiedProperty_Property()
	 * @model containment="true" required="true"
	 * @generated
	 */
	Property getProperty();

	/**
	 * Sets the value of the '{@link org.eclipse.gemoc.executionframework.property.model.property.QuantifiedProperty#getProperty <em>Property</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Property</em>' containment reference.
	 * @see #getProperty()
	 * @generated
	 */
	void setProperty(Property value);

} // QuantifiedProperty
