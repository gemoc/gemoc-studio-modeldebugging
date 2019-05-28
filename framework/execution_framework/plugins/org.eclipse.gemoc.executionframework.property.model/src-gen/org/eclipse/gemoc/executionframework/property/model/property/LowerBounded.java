/**
 */
package org.eclipse.gemoc.executionframework.property.model.property;

import org.eclipse.viatra.query.patternlanguage.emf.vql.Pattern;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Lower Bounded</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.gemoc.executionframework.property.model.property.LowerBounded#getLowerBoundPattern <em>Lower Bound Pattern</em>}</li>
 * </ul>
 *
 * @see org.eclipse.gemoc.executionframework.property.model.property.PropertyPackage#getLowerBounded()
 * @model abstract="true"
 * @generated
 */
public interface LowerBounded extends Scope {
	/**
	 * Returns the value of the '<em><b>Lower Bound Pattern</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Lower Bound Pattern</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Lower Bound Pattern</em>' reference.
	 * @see #setLowerBoundPattern(Pattern)
	 * @see org.eclipse.gemoc.executionframework.property.model.property.PropertyPackage#getLowerBounded_LowerBoundPattern()
	 * @model required="true"
	 * @generated
	 */
	Pattern getLowerBoundPattern();

	/**
	 * Sets the value of the '{@link org.eclipse.gemoc.executionframework.property.model.property.LowerBounded#getLowerBoundPattern <em>Lower Bound Pattern</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Lower Bound Pattern</em>' reference.
	 * @see #getLowerBoundPattern()
	 * @generated
	 */
	void setLowerBoundPattern(Pattern value);

} // LowerBounded
