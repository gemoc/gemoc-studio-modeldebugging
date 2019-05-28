/**
 */
package org.eclipse.gemoc.executionframework.property.model.property;

import org.eclipse.viatra.query.patternlanguage.emf.vql.Pattern;

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
	 * Returns the value of the '<em><b>Upper Bound Pattern</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Upper Bound Pattern</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Upper Bound Pattern</em>' reference.
	 * @see #setUpperBoundPattern(Pattern)
	 * @see org.eclipse.gemoc.executionframework.property.model.property.PropertyPackage#getUpperBounded_UpperBoundPattern()
	 * @model required="true"
	 * @generated
	 */
	Pattern getUpperBoundPattern();

	/**
	 * Sets the value of the '{@link org.eclipse.gemoc.executionframework.property.model.property.UpperBounded#getUpperBoundPattern <em>Upper Bound Pattern</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Upper Bound Pattern</em>' reference.
	 * @see #getUpperBoundPattern()
	 * @generated
	 */
	void setUpperBoundPattern(Pattern value);

} // UpperBounded
