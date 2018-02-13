/**
 */
package org.eclipse.gemoc.trace.virtual.virtualtrace;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

/**
 * <!-- begin-user-doc -->
 * The <b>Package</b> for the model.
 * It contains accessors for the meta objects to represent
 * <ul>
 *   <li>each class,</li>
 *   <li>each feature of each class,</li>
 *   <li>each operation of each class,</li>
 *   <li>each enum,</li>
 *   <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * @see org.eclipse.gemoc.trace.virtual.virtualtrace.VirtualtraceFactory
 * @model kind="package"
 * @generated
 */
public interface VirtualtracePackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "virtualtrace";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://www.gemoc.org/virtualtrace";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "virtualtrace";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	VirtualtracePackage eINSTANCE = org.eclipse.gemoc.trace.virtual.virtualtrace.impl.VirtualtracePackageImpl.init();

	/**
	 * The meta object id for the '{@link org.eclipse.gemoc.trace.virtual.virtualtrace.impl.VirtualTraceImpl <em>Virtual Trace</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.gemoc.trace.virtual.virtualtrace.impl.VirtualTraceImpl
	 * @see org.eclipse.gemoc.trace.virtual.virtualtrace.impl.VirtualtracePackageImpl#getVirtualTrace()
	 * @generated
	 */
	int VIRTUAL_TRACE = 0;

	/**
	 * The feature id for the '<em><b>Trace</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VIRTUAL_TRACE__TRACE = 0;

	/**
	 * The feature id for the '<em><b>Selected Dimensions</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VIRTUAL_TRACE__SELECTED_DIMENSIONS = 1;

	/**
	 * The feature id for the '<em><b>State Groups</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VIRTUAL_TRACE__STATE_GROUPS = 2;

	/**
	 * The number of structural features of the '<em>Virtual Trace</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VIRTUAL_TRACE_FEATURE_COUNT = 3;

	/**
	 * The number of operations of the '<em>Virtual Trace</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VIRTUAL_TRACE_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.eclipse.gemoc.trace.virtual.virtualtrace.impl.StateGroupImpl <em>State Group</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.gemoc.trace.virtual.virtualtrace.impl.StateGroupImpl
	 * @see org.eclipse.gemoc.trace.virtual.virtualtrace.impl.VirtualtracePackageImpl#getStateGroup()
	 * @generated
	 */
	int STATE_GROUP = 1;

	/**
	 * The feature id for the '<em><b>Trace</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_GROUP__TRACE = 0;

	/**
	 * The feature id for the '<em><b>Values</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_GROUP__VALUES = 1;

	/**
	 * The feature id for the '<em><b>States</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_GROUP__STATES = 2;

	/**
	 * The feature id for the '<em><b>Starting Steps</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_GROUP__STARTING_STEPS = 3;

	/**
	 * The feature id for the '<em><b>Ending Steps</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_GROUP__ENDING_STEPS = 4;

	/**
	 * The number of structural features of the '<em>State Group</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_GROUP_FEATURE_COUNT = 5;

	/**
	 * The number of operations of the '<em>State Group</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_GROUP_OPERATION_COUNT = 0;

	/**
	 * Returns the meta object for class '{@link org.eclipse.gemoc.trace.virtual.virtualtrace.VirtualTrace <em>Virtual Trace</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Virtual Trace</em>'.
	 * @see org.eclipse.gemoc.trace.virtual.virtualtrace.VirtualTrace
	 * @generated
	 */
	EClass getVirtualTrace();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.gemoc.trace.virtual.virtualtrace.VirtualTrace#getTrace <em>Trace</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Trace</em>'.
	 * @see org.eclipse.gemoc.trace.virtual.virtualtrace.VirtualTrace#getTrace()
	 * @see #getVirtualTrace()
	 * @generated
	 */
	EReference getVirtualTrace_Trace();

	/**
	 * Returns the meta object for the reference list '{@link org.eclipse.gemoc.trace.virtual.virtualtrace.VirtualTrace#getSelectedDimensions <em>Selected Dimensions</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Selected Dimensions</em>'.
	 * @see org.eclipse.gemoc.trace.virtual.virtualtrace.VirtualTrace#getSelectedDimensions()
	 * @see #getVirtualTrace()
	 * @generated
	 */
	EReference getVirtualTrace_SelectedDimensions();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.gemoc.trace.virtual.virtualtrace.VirtualTrace#getStateGroups <em>State Groups</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>State Groups</em>'.
	 * @see org.eclipse.gemoc.trace.virtual.virtualtrace.VirtualTrace#getStateGroups()
	 * @see #getVirtualTrace()
	 * @generated
	 */
	EReference getVirtualTrace_StateGroups();

	/**
	 * Returns the meta object for class '{@link org.eclipse.gemoc.trace.virtual.virtualtrace.StateGroup <em>State Group</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>State Group</em>'.
	 * @see org.eclipse.gemoc.trace.virtual.virtualtrace.StateGroup
	 * @generated
	 */
	EClass getStateGroup();

	/**
	 * Returns the meta object for the container reference '{@link org.eclipse.gemoc.trace.virtual.virtualtrace.StateGroup#getTrace <em>Trace</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the container reference '<em>Trace</em>'.
	 * @see org.eclipse.gemoc.trace.virtual.virtualtrace.StateGroup#getTrace()
	 * @see #getStateGroup()
	 * @generated
	 */
	EReference getStateGroup_Trace();

	/**
	 * Returns the meta object for the reference list '{@link org.eclipse.gemoc.trace.virtual.virtualtrace.StateGroup#getValues <em>Values</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Values</em>'.
	 * @see org.eclipse.gemoc.trace.virtual.virtualtrace.StateGroup#getValues()
	 * @see #getStateGroup()
	 * @generated
	 */
	EReference getStateGroup_Values();

	/**
	 * Returns the meta object for the reference list '{@link org.eclipse.gemoc.trace.virtual.virtualtrace.StateGroup#getStates <em>States</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>States</em>'.
	 * @see org.eclipse.gemoc.trace.virtual.virtualtrace.StateGroup#getStates()
	 * @see #getStateGroup()
	 * @generated
	 */
	EReference getStateGroup_States();

	/**
	 * Returns the meta object for the reference list '{@link org.eclipse.gemoc.trace.virtual.virtualtrace.StateGroup#getStartingSteps <em>Starting Steps</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Starting Steps</em>'.
	 * @see org.eclipse.gemoc.trace.virtual.virtualtrace.StateGroup#getStartingSteps()
	 * @see #getStateGroup()
	 * @generated
	 */
	EReference getStateGroup_StartingSteps();

	/**
	 * Returns the meta object for the reference list '{@link org.eclipse.gemoc.trace.virtual.virtualtrace.StateGroup#getEndingSteps <em>Ending Steps</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Ending Steps</em>'.
	 * @see org.eclipse.gemoc.trace.virtual.virtualtrace.StateGroup#getEndingSteps()
	 * @see #getStateGroup()
	 * @generated
	 */
	EReference getStateGroup_EndingSteps();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	VirtualtraceFactory getVirtualtraceFactory();

	/**
	 * <!-- begin-user-doc -->
	 * Defines literals for the meta objects that represent
	 * <ul>
	 *   <li>each class,</li>
	 *   <li>each feature of each class,</li>
	 *   <li>each operation of each class,</li>
	 *   <li>each enum,</li>
	 *   <li>and each data type</li>
	 * </ul>
	 * <!-- end-user-doc -->
	 * @generated
	 */
	interface Literals {
		/**
		 * The meta object literal for the '{@link org.eclipse.gemoc.trace.virtual.virtualtrace.impl.VirtualTraceImpl <em>Virtual Trace</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.gemoc.trace.virtual.virtualtrace.impl.VirtualTraceImpl
		 * @see org.eclipse.gemoc.trace.virtual.virtualtrace.impl.VirtualtracePackageImpl#getVirtualTrace()
		 * @generated
		 */
		EClass VIRTUAL_TRACE = eINSTANCE.getVirtualTrace();

		/**
		 * The meta object literal for the '<em><b>Trace</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference VIRTUAL_TRACE__TRACE = eINSTANCE.getVirtualTrace_Trace();

		/**
		 * The meta object literal for the '<em><b>Selected Dimensions</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference VIRTUAL_TRACE__SELECTED_DIMENSIONS = eINSTANCE.getVirtualTrace_SelectedDimensions();

		/**
		 * The meta object literal for the '<em><b>State Groups</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference VIRTUAL_TRACE__STATE_GROUPS = eINSTANCE.getVirtualTrace_StateGroups();

		/**
		 * The meta object literal for the '{@link org.eclipse.gemoc.trace.virtual.virtualtrace.impl.StateGroupImpl <em>State Group</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.gemoc.trace.virtual.virtualtrace.impl.StateGroupImpl
		 * @see org.eclipse.gemoc.trace.virtual.virtualtrace.impl.VirtualtracePackageImpl#getStateGroup()
		 * @generated
		 */
		EClass STATE_GROUP = eINSTANCE.getStateGroup();

		/**
		 * The meta object literal for the '<em><b>Trace</b></em>' container reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference STATE_GROUP__TRACE = eINSTANCE.getStateGroup_Trace();

		/**
		 * The meta object literal for the '<em><b>Values</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference STATE_GROUP__VALUES = eINSTANCE.getStateGroup_Values();

		/**
		 * The meta object literal for the '<em><b>States</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference STATE_GROUP__STATES = eINSTANCE.getStateGroup_States();

		/**
		 * The meta object literal for the '<em><b>Starting Steps</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference STATE_GROUP__STARTING_STEPS = eINSTANCE.getStateGroup_StartingSteps();

		/**
		 * The meta object literal for the '<em><b>Ending Steps</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference STATE_GROUP__ENDING_STEPS = eINSTANCE.getStateGroup_EndingSteps();

	}

} //VirtualtracePackage
