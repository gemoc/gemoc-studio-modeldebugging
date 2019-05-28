/**
 */
package org.eclipse.gemoc.executionframework.property.model.property;

import org.eclipse.viatra.query.patternlanguage.emf.vql.Pattern;

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
	 * Returns the value of the '<em><b>Other Pattern</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Other Pattern</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Other Pattern</em>' reference.
	 * @see #setOtherPattern(Pattern)
	 * @see org.eclipse.gemoc.executionframework.property.model.property.PropertyPackage#getOrderedTemporalProperty_OtherPattern()
	 * @model required="true"
	 * @generated
	 */
	Pattern getOtherPattern();

	/**
	 * Sets the value of the '{@link org.eclipse.gemoc.executionframework.property.model.property.OrderedTemporalProperty#getOtherPattern <em>Other Pattern</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Other Pattern</em>' reference.
	 * @see #getOtherPattern()
	 * @generated
	 */
	void setOtherPattern(Pattern value);

} // OrderedTemporalProperty
