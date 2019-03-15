/**
 * generated by Xtext 2.14.0
 */
package org.eclipse.gemoc.xdsmlframework.behavioralinterface.behavioralInterface;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see org.eclipse.gemoc.xdsmlframework.behavioralinterface.behavioralInterface.BehavioralInterfacePackage
 * @generated
 */
public interface BehavioralInterfaceFactory extends EFactory
{
  /**
   * The singleton instance of the factory.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  BehavioralInterfaceFactory eINSTANCE = org.eclipse.gemoc.xdsmlframework.behavioralinterface.behavioralInterface.impl.BehavioralInterfaceFactoryImpl.init();

  /**
   * Returns a new object of class '<em>Behavioral Interface</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Behavioral Interface</em>'.
   * @generated
   */
  BehavioralInterface createBehavioralInterface();

  /**
   * Returns a new object of class '<em>Event</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Event</em>'.
   * @generated
   */
  Event createEvent();

  /**
   * Returns a new object of class '<em>Event Parameter</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Event Parameter</em>'.
   * @generated
   */
  EventParameter createEventParameter();

  /**
   * Returns the package supported by this factory.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the package supported by this factory.
   * @generated
   */
  BehavioralInterfacePackage getBehavioralInterfacePackage();

} //BehavioralInterfaceFactory
