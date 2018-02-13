/**
 */
package org.eclipse.gemoc.trace.virtual.virtualtrace.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EGenericType;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EcorePackage;

import org.eclipse.emf.ecore.impl.EPackageImpl;

import org.eclipse.gemoc.trace.commons.model.launchconfiguration.LaunchconfigurationPackage;

import org.eclipse.gemoc.trace.commons.model.trace.TracePackage;

import org.eclipse.gemoc.trace.virtual.virtualtrace.StateGroup;
import org.eclipse.gemoc.trace.virtual.virtualtrace.VirtualTrace;
import org.eclipse.gemoc.trace.virtual.virtualtrace.VirtualtraceFactory;
import org.eclipse.gemoc.trace.virtual.virtualtrace.VirtualtracePackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class VirtualtracePackageImpl extends EPackageImpl implements VirtualtracePackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass virtualTraceEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass stateGroupEClass = null;

	/**
	 * Creates an instance of the model <b>Package</b>, registered with
	 * {@link org.eclipse.emf.ecore.EPackage.Registry EPackage.Registry} by the package
	 * package URI value.
	 * <p>Note: the correct way to create the package is via the static
	 * factory method {@link #init init()}, which also performs
	 * initialization of the package, or returns the registered package,
	 * if one already exists.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.ecore.EPackage.Registry
	 * @see org.eclipse.gemoc.trace.virtual.virtualtrace.VirtualtracePackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private VirtualtracePackageImpl() {
		super(eNS_URI, VirtualtraceFactory.eINSTANCE);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static boolean isInited = false;

	/**
	 * Creates, registers, and initializes the <b>Package</b> for this model, and for any others upon which it depends.
	 * 
	 * <p>This method is used to initialize {@link VirtualtracePackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static VirtualtracePackage init() {
		if (isInited)
			return (VirtualtracePackage) EPackage.Registry.INSTANCE.getEPackage(VirtualtracePackage.eNS_URI);

		// Obtain or create and register package
		VirtualtracePackageImpl theVirtualtracePackage = (VirtualtracePackageImpl) (EPackage.Registry.INSTANCE
				.get(eNS_URI) instanceof VirtualtracePackageImpl ? EPackage.Registry.INSTANCE.get(eNS_URI)
						: new VirtualtracePackageImpl());

		isInited = true;

		// Initialize simple dependencies
		EcorePackage.eINSTANCE.eClass();
		TracePackage.eINSTANCE.eClass();
		LaunchconfigurationPackage.eINSTANCE.eClass();

		// Create package meta-data objects
		theVirtualtracePackage.createPackageContents();

		// Initialize created meta-data
		theVirtualtracePackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theVirtualtracePackage.freeze();

		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(VirtualtracePackage.eNS_URI, theVirtualtracePackage);
		return theVirtualtracePackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getVirtualTrace() {
		return virtualTraceEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getVirtualTrace_Trace() {
		return (EReference) virtualTraceEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getVirtualTrace_SelectedDimensions() {
		return (EReference) virtualTraceEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getVirtualTrace_StateGroups() {
		return (EReference) virtualTraceEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getStateGroup() {
		return stateGroupEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getStateGroup_Trace() {
		return (EReference) stateGroupEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getStateGroup_Values() {
		return (EReference) stateGroupEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getStateGroup_States() {
		return (EReference) stateGroupEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getStateGroup_StartingSteps() {
		return (EReference) stateGroupEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getStateGroup_EndingSteps() {
		return (EReference) stateGroupEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public VirtualtraceFactory getVirtualtraceFactory() {
		return (VirtualtraceFactory) getEFactoryInstance();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private boolean isCreated = false;

	/**
	 * Creates the meta-model objects for the package.  This method is
	 * guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void createPackageContents() {
		if (isCreated)
			return;
		isCreated = true;

		// Create classes and their features
		virtualTraceEClass = createEClass(VIRTUAL_TRACE);
		createEReference(virtualTraceEClass, VIRTUAL_TRACE__TRACE);
		createEReference(virtualTraceEClass, VIRTUAL_TRACE__SELECTED_DIMENSIONS);
		createEReference(virtualTraceEClass, VIRTUAL_TRACE__STATE_GROUPS);

		stateGroupEClass = createEClass(STATE_GROUP);
		createEReference(stateGroupEClass, STATE_GROUP__TRACE);
		createEReference(stateGroupEClass, STATE_GROUP__VALUES);
		createEReference(stateGroupEClass, STATE_GROUP__STATES);
		createEReference(stateGroupEClass, STATE_GROUP__STARTING_STEPS);
		createEReference(stateGroupEClass, STATE_GROUP__ENDING_STEPS);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private boolean isInitialized = false;

	/**
	 * Complete the initialization of the package and its meta-model.  This
	 * method is guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void initializePackageContents() {
		if (isInitialized)
			return;
		isInitialized = true;

		// Initialize package
		setName(eNAME);
		setNsPrefix(eNS_PREFIX);
		setNsURI(eNS_URI);

		// Obtain other dependent packages
		TracePackage theTracePackage = (TracePackage) EPackage.Registry.INSTANCE.getEPackage(TracePackage.eNS_URI);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes

		// Initialize classes, features, and operations; add parameters
		initEClass(virtualTraceEClass, VirtualTrace.class, "VirtualTrace", !IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);
		EGenericType g1 = createEGenericType(theTracePackage.getTrace());
		EGenericType g2 = createEGenericType();
		g1.getETypeArguments().add(g2);
		g2 = createEGenericType();
		g1.getETypeArguments().add(g2);
		g2 = createEGenericType();
		g1.getETypeArguments().add(g2);
		initEReference(getVirtualTrace_Trace(), g1, null, "trace", null, 1, 1, VirtualTrace.class, !IS_TRANSIENT,
				!IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED,
				IS_ORDERED);
		g1 = createEGenericType(theTracePackage.getDimension());
		g2 = createEGenericType();
		g1.getETypeArguments().add(g2);
		initEReference(getVirtualTrace_SelectedDimensions(), g1, null, "selectedDimensions", null, 0, -1,
				VirtualTrace.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getVirtualTrace_StateGroups(), this.getStateGroup(), this.getStateGroup_Trace(), "stateGroups",
				null, 0, -1, VirtualTrace.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
				!IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(stateGroupEClass, StateGroup.class, "StateGroup", !IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);
		initEReference(getStateGroup_Trace(), this.getVirtualTrace(), this.getVirtualTrace_StateGroups(), "trace", null,
				0, 1, StateGroup.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		g1 = createEGenericType(theTracePackage.getValue());
		g2 = createEGenericType();
		g1.getETypeArguments().add(g2);
		initEReference(getStateGroup_Values(), g1, null, "values", null, 0, -1, StateGroup.class, IS_TRANSIENT,
				IS_VOLATILE, !IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED,
				IS_ORDERED);
		g1 = createEGenericType(theTracePackage.getState());
		g2 = createEGenericType();
		g1.getETypeArguments().add(g2);
		g2 = createEGenericType();
		g1.getETypeArguments().add(g2);
		initEReference(getStateGroup_States(), g1, null, "states", null, 0, -1, StateGroup.class, !IS_TRANSIENT,
				!IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED,
				IS_ORDERED);
		g1 = createEGenericType(theTracePackage.getStep());
		g2 = createEGenericType();
		g1.getETypeArguments().add(g2);
		initEReference(getStateGroup_StartingSteps(), g1, null, "startingSteps", null, 0, -1, StateGroup.class,
				IS_TRANSIENT, IS_VOLATILE, !IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE,
				IS_DERIVED, IS_ORDERED);
		g1 = createEGenericType(theTracePackage.getStep());
		g2 = createEGenericType();
		g1.getETypeArguments().add(g2);
		initEReference(getStateGroup_EndingSteps(), g1, null, "endingSteps", null, 0, -1, StateGroup.class,
				IS_TRANSIENT, IS_VOLATILE, !IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE,
				IS_DERIVED, IS_ORDERED);

		// Create resource
		createResource(eNS_URI);
	}

} //VirtualtracePackageImpl
