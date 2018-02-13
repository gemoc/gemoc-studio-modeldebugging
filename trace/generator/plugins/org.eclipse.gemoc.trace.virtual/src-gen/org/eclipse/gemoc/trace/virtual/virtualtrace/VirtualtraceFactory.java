/**
 */
package org.eclipse.gemoc.trace.virtual.virtualtrace;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see org.eclipse.gemoc.trace.virtual.virtualtrace.VirtualtracePackage
 * @generated
 */
public interface VirtualtraceFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	VirtualtraceFactory eINSTANCE = org.eclipse.gemoc.trace.virtual.virtualtrace.impl.VirtualtraceFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>Virtual Trace</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Virtual Trace</em>'.
	 * @generated
	 */
	VirtualTrace createVirtualTrace();

	/**
	 * Returns a new object of class '<em>State Group</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>State Group</em>'.
	 * @generated
	 */
	StateGroup createStateGroup();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	VirtualtracePackage getVirtualtracePackage();

} //VirtualtraceFactory
