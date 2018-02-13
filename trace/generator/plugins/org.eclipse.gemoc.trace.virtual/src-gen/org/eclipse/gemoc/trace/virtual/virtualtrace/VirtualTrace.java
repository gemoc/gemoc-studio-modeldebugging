/**
 */
package org.eclipse.gemoc.trace.virtual.virtualtrace;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

import org.eclipse.gemoc.trace.commons.model.trace.Dimension;
import org.eclipse.gemoc.trace.commons.model.trace.Trace;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Virtual Trace</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.gemoc.trace.virtual.virtualtrace.VirtualTrace#getTrace <em>Trace</em>}</li>
 *   <li>{@link org.eclipse.gemoc.trace.virtual.virtualtrace.VirtualTrace#getSelectedDimensions <em>Selected Dimensions</em>}</li>
 *   <li>{@link org.eclipse.gemoc.trace.virtual.virtualtrace.VirtualTrace#getStateGroups <em>State Groups</em>}</li>
 * </ul>
 *
 * @see org.eclipse.gemoc.trace.virtual.virtualtrace.VirtualtracePackage#getVirtualTrace()
 * @model
 * @generated
 */
public interface VirtualTrace extends EObject {
	/**
	 * Returns the value of the '<em><b>Trace</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Trace</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Trace</em>' reference.
	 * @see #setTrace(Trace)
	 * @see org.eclipse.gemoc.trace.virtual.virtualtrace.VirtualtracePackage#getVirtualTrace_Trace()
	 * @model required="true"
	 * @generated
	 */
	Trace<?, ?, ?> getTrace();

	/**
	 * Sets the value of the '{@link org.eclipse.gemoc.trace.virtual.virtualtrace.VirtualTrace#getTrace <em>Trace</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Trace</em>' reference.
	 * @see #getTrace()
	 * @generated
	 */
	void setTrace(Trace<?, ?, ?> value);

	/**
	 * Returns the value of the '<em><b>Selected Dimensions</b></em>' reference list.
	 * The list contents are of type {@link org.eclipse.gemoc.trace.commons.model.trace.Dimension}<code>&lt;?&gt;</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Selected Dimensions</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Selected Dimensions</em>' reference list.
	 * @see org.eclipse.gemoc.trace.virtual.virtualtrace.VirtualtracePackage#getVirtualTrace_SelectedDimensions()
	 * @model
	 * @generated
	 */
	EList<Dimension<?>> getSelectedDimensions();

	/**
	 * Returns the value of the '<em><b>State Groups</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.gemoc.trace.virtual.virtualtrace.StateGroup}.
	 * It is bidirectional and its opposite is '{@link org.eclipse.gemoc.trace.virtual.virtualtrace.StateGroup#getTrace <em>Trace</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>State Groups</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>State Groups</em>' containment reference list.
	 * @see org.eclipse.gemoc.trace.virtual.virtualtrace.VirtualtracePackage#getVirtualTrace_StateGroups()
	 * @see org.eclipse.gemoc.trace.virtual.virtualtrace.StateGroup#getTrace
	 * @model opposite="trace" containment="true"
	 * @generated
	 */
	EList<StateGroup> getStateGroups();

} // VirtualTrace
