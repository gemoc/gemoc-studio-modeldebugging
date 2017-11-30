/**
 */
package org.eclipse.gemoc.executionframework.event.model.genericevent;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see org.eclipse.gemoc.executionframework.event.model.genericevent.GenericeventPackage
 * @generated
 */
public interface GenericeventFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	GenericeventFactory eINSTANCE = org.eclipse.gemoc.executionframework.event.model.genericevent.impl.GenericeventFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>Generic Event</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Generic Event</em>'.
	 * @generated
	 */
	GenericEvent createGenericEvent();

	/**
	 * Returns a new object of class '<em>Rule ID</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Rule ID</em>'.
	 * @generated
	 */
	RuleID createRuleID();

	/**
	 * Returns a new object of class '<em>Rule FQN</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Rule FQN</em>'.
	 * @generated
	 */
	RuleFQN createRuleFQN();

	/**
	 * Returns a new object of class '<em>Rule Ref</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Rule Ref</em>'.
	 * @generated
	 */
	RuleRef createRuleRef();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	GenericeventPackage getGenericeventPackage();

} //GenericeventFactory
