/**
 */
package org.eclipse.gemoc.executionframework.property.model.property;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Ordered Temporal Property</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.gemoc.executionframework.property.model.property.OrderedTemporalProperty#getOtherPattern <em>Other Pattern</em>}</li>
 * </ul>
 *
 * @see org.eclipse.gemoc.executionframework.property.model.property.PropertyPackage#getOrderedTemporalProperty()
 * @model abstract="true"
 * @generated
 */
public interface OrderedTemporalProperty extends TemporalProperty {
	/**
	 * Returns the value of the '<em><b>Other Pattern</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Other Pattern</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Other Pattern</em>' attribute.
	 * @see #setOtherPattern(String)
	 * @see org.eclipse.gemoc.executionframework.property.model.property.PropertyPackage#getOrderedTemporalProperty_OtherPattern()
	 * @model required="true"
	 * @generated
	 */
	String getOtherPattern();

	/**
	 * Sets the value of the '{@link org.eclipse.gemoc.executionframework.property.model.property.OrderedTemporalProperty#getOtherPattern <em>Other Pattern</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Other Pattern</em>' attribute.
	 * @see #getOtherPattern()
	 * @generated
	 */
	void setOtherPattern(String value);

} // OrderedTemporalProperty
