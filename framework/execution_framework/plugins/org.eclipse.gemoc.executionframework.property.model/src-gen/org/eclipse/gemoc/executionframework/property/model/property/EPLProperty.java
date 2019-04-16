/**
 */
package org.eclipse.gemoc.executionframework.property.model.property;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

import org.eclipse.viatra.query.patternlanguage.emf.vql.Pattern;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>EPL Property</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.gemoc.executionframework.property.model.property.EPLProperty#getStatement <em>Statement</em>}</li>
 *   <li>{@link org.eclipse.gemoc.executionframework.property.model.property.EPLProperty#getPatterns <em>Patterns</em>}</li>
 *   <li>{@link org.eclipse.gemoc.executionframework.property.model.property.EPLProperty#isInitialTruthValue <em>Initial Truth Value</em>}</li>
 *   <li>{@link org.eclipse.gemoc.executionframework.property.model.property.EPLProperty#getName <em>Name</em>}</li>
 * </ul>
 *
 * @see org.eclipse.gemoc.executionframework.property.model.property.PropertyPackage#getEPLProperty()
 * @model
 * @generated
 */
public interface EPLProperty extends EObject {
	/**
	 * Returns the value of the '<em><b>Statement</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Statement</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Statement</em>' attribute.
	 * @see #setStatement(String)
	 * @see org.eclipse.gemoc.executionframework.property.model.property.PropertyPackage#getEPLProperty_Statement()
	 * @model required="true"
	 * @generated
	 */
	String getStatement();

	/**
	 * Sets the value of the '{@link org.eclipse.gemoc.executionframework.property.model.property.EPLProperty#getStatement <em>Statement</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Statement</em>' attribute.
	 * @see #getStatement()
	 * @generated
	 */
	void setStatement(String value);

	/**
	 * Returns the value of the '<em><b>Patterns</b></em>' reference list.
	 * The list contents are of type {@link org.eclipse.viatra.query.patternlanguage.emf.vql.Pattern}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Patterns</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Patterns</em>' reference list.
	 * @see org.eclipse.gemoc.executionframework.property.model.property.PropertyPackage#getEPLProperty_Patterns()
	 * @model
	 * @generated
	 */
	EList<Pattern> getPatterns();

	/**
	 * Returns the value of the '<em><b>Initial Truth Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Initial Truth Value</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Initial Truth Value</em>' attribute.
	 * @see #setInitialTruthValue(boolean)
	 * @see org.eclipse.gemoc.executionframework.property.model.property.PropertyPackage#getEPLProperty_InitialTruthValue()
	 * @model
	 * @generated
	 */
	boolean isInitialTruthValue();

	/**
	 * Sets the value of the '{@link org.eclipse.gemoc.executionframework.property.model.property.EPLProperty#isInitialTruthValue <em>Initial Truth Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Initial Truth Value</em>' attribute.
	 * @see #isInitialTruthValue()
	 * @generated
	 */
	void setInitialTruthValue(boolean value);

	/**
	 * Returns the value of the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Name</em>' attribute.
	 * @see #setName(String)
	 * @see org.eclipse.gemoc.executionframework.property.model.property.PropertyPackage#getEPLProperty_Name()
	 * @model
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link org.eclipse.gemoc.executionframework.property.model.property.EPLProperty#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

} // EPLProperty
