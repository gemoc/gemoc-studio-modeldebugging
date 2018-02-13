/**
 */
package org.eclipse.gemoc.trace.virtual.virtualtrace;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

import org.eclipse.gemoc.trace.commons.model.trace.State;
import org.eclipse.gemoc.trace.commons.model.trace.Step;
import org.eclipse.gemoc.trace.commons.model.trace.Value;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>State Group</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.gemoc.trace.virtual.virtualtrace.StateGroup#getTrace <em>Trace</em>}</li>
 *   <li>{@link org.eclipse.gemoc.trace.virtual.virtualtrace.StateGroup#getValues <em>Values</em>}</li>
 *   <li>{@link org.eclipse.gemoc.trace.virtual.virtualtrace.StateGroup#getStates <em>States</em>}</li>
 *   <li>{@link org.eclipse.gemoc.trace.virtual.virtualtrace.StateGroup#getStartingSteps <em>Starting Steps</em>}</li>
 *   <li>{@link org.eclipse.gemoc.trace.virtual.virtualtrace.StateGroup#getEndingSteps <em>Ending Steps</em>}</li>
 * </ul>
 *
 * @see org.eclipse.gemoc.trace.virtual.virtualtrace.VirtualtracePackage#getStateGroup()
 * @model
 * @generated
 */
public interface StateGroup extends EObject {
	/**
	 * Returns the value of the '<em><b>Trace</b></em>' container reference.
	 * It is bidirectional and its opposite is '{@link org.eclipse.gemoc.trace.virtual.virtualtrace.VirtualTrace#getStateGroups <em>State Groups</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Trace</em>' container reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Trace</em>' container reference.
	 * @see #setTrace(VirtualTrace)
	 * @see org.eclipse.gemoc.trace.virtual.virtualtrace.VirtualtracePackage#getStateGroup_Trace()
	 * @see org.eclipse.gemoc.trace.virtual.virtualtrace.VirtualTrace#getStateGroups
	 * @model opposite="stateGroups" transient="false"
	 * @generated
	 */
	VirtualTrace getTrace();

	/**
	 * Sets the value of the '{@link org.eclipse.gemoc.trace.virtual.virtualtrace.StateGroup#getTrace <em>Trace</em>}' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Trace</em>' container reference.
	 * @see #getTrace()
	 * @generated
	 */
	void setTrace(VirtualTrace value);

	/**
	 * Returns the value of the '<em><b>Values</b></em>' reference list.
	 * The list contents are of type {@link org.eclipse.gemoc.trace.commons.model.trace.Value}<code>&lt;?&gt;</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Values</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Values</em>' reference list.
	 * @see org.eclipse.gemoc.trace.virtual.virtualtrace.VirtualtracePackage#getStateGroup_Values()
	 * @model transient="true" changeable="false" volatile="true" derived="true"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel get='if (!getStates().isEmpty()) {\n\tfinal State&lt;?, ?&gt; referenceState = getStates().get(0);\n\tfinal EList&lt;Value&lt;?&gt;&gt; result = new org.eclipse.emf.common.util.BasicEList&lt;&gt;();\n\treferenceState.getValues().stream()\n\t\t\t.filter(v -&gt; getTrace().getSelectedDimensions().contains(v.eContainer()))\n\t\t\t.forEach(v -&gt; result.add(v));\n\treturn result;\n} else {\n\treturn new org.eclipse.emf.common.util.BasicEList&lt;&gt;();\n}'"
	 * @generated
	 */
	EList<Value<?>> getValues();

	/**
	 * Returns the value of the '<em><b>States</b></em>' reference list.
	 * The list contents are of type {@link org.eclipse.gemoc.trace.commons.model.trace.State}<code>&lt;?, ?&gt;</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>States</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>States</em>' reference list.
	 * @see org.eclipse.gemoc.trace.virtual.virtualtrace.VirtualtracePackage#getStateGroup_States()
	 * @model
	 * @generated
	 */
	EList<State<?, ?>> getStates();

	/**
	 * Returns the value of the '<em><b>Starting Steps</b></em>' reference list.
	 * The list contents are of type {@link org.eclipse.gemoc.trace.commons.model.trace.Step}<code>&lt;?&gt;</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Starting Steps</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Starting Steps</em>' reference list.
	 * @see org.eclipse.gemoc.trace.virtual.virtualtrace.VirtualtracePackage#getStateGroup_StartingSteps()
	 * @model transient="true" changeable="false" volatile="true" derived="true"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel get='final java.util.List&lt;State&lt;?, ?&gt;&gt; states = getStates();\nif (!states.isEmpty()) {\n\tfinal EList&lt;Step&lt;?&gt;&gt; result = new org.eclipse.emf.common.util.BasicEList&lt;&gt;();\n\tfinal java.util.List&lt;Step&lt;?&gt;&gt; steps = states.stream().flatMap(state -&gt; {\n\t\treturn state.getStartedSteps().stream().map(step -&gt; (Step&lt;?&gt;) step);\n\t}).collect(java.util.stream.Collectors.toList());\n\tresult.addAll(steps);\n\treturn result;\n} else {\n\treturn new org.eclipse.emf.common.util.BasicEList&lt;&gt;();\n}'"
	 * @generated
	 */
	EList<Step<?>> getStartingSteps();

	/**
	 * Returns the value of the '<em><b>Ending Steps</b></em>' reference list.
	 * The list contents are of type {@link org.eclipse.gemoc.trace.commons.model.trace.Step}<code>&lt;?&gt;</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Ending Steps</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Ending Steps</em>' reference list.
	 * @see org.eclipse.gemoc.trace.virtual.virtualtrace.VirtualtracePackage#getStateGroup_EndingSteps()
	 * @model transient="true" changeable="false" volatile="true" derived="true"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel get='final java.util.List&lt;State&lt;?, ?&gt;&gt; states = getStates();\nif (!states.isEmpty()) {\n\tfinal EList&lt;Step&lt;?&gt;&gt; result = new org.eclipse.emf.common.util.BasicEList&lt;&gt;();\n\tfinal java.util.List&lt;Step&lt;?&gt;&gt; steps = states.stream().flatMap(state -&gt; {\n\t\treturn state.getEndedSteps().stream().map(step -&gt; (Step&lt;?&gt;) step);\n\t}).collect(java.util.stream.Collectors.toList());\n\tresult.addAll(steps);\n\treturn result;\n} else {\n\treturn new org.eclipse.emf.common.util.BasicEList&lt;&gt;();\n}'"
	 * @generated
	 */
	EList<Step<?>> getEndingSteps();

} // StateGroup
