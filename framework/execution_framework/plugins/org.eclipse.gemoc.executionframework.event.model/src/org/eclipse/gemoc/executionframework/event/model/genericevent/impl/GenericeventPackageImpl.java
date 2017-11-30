/**
 */
package org.eclipse.gemoc.executionframework.event.model.genericevent.impl;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

import org.eclipse.emf.ecore.impl.EPackageImpl;

import org.eclipse.gemoc.commons.value.model.value.ValuePackage;

import org.eclipse.gemoc.executionframework.event.model.event.EventPackage;

import org.eclipse.gemoc.executionframework.event.model.genericevent.GenericEvent;
import org.eclipse.gemoc.executionframework.event.model.genericevent.GenericeventFactory;
import org.eclipse.gemoc.executionframework.event.model.genericevent.GenericeventPackage;
import org.eclipse.gemoc.executionframework.event.model.genericevent.RuleFQN;
import org.eclipse.gemoc.executionframework.event.model.genericevent.RuleID;
import org.eclipse.gemoc.executionframework.event.model.genericevent.RuleRef;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class GenericeventPackageImpl extends EPackageImpl implements GenericeventPackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass genericEventEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass ruleIDEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass ruleFQNEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass ruleRefEClass = null;

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
	 * @see org.eclipse.gemoc.executionframework.event.model.genericevent.GenericeventPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private GenericeventPackageImpl() {
		super(eNS_URI, GenericeventFactory.eINSTANCE);
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
	 * <p>This method is used to initialize {@link GenericeventPackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static GenericeventPackage init() {
		if (isInited) return (GenericeventPackage)EPackage.Registry.INSTANCE.getEPackage(GenericeventPackage.eNS_URI);

		// Obtain or create and register package
		GenericeventPackageImpl theGenericeventPackage = (GenericeventPackageImpl)(EPackage.Registry.INSTANCE.get(eNS_URI) instanceof GenericeventPackageImpl ? EPackage.Registry.INSTANCE.get(eNS_URI) : new GenericeventPackageImpl());

		isInited = true;

		// Initialize simple dependencies
		EventPackage.eINSTANCE.eClass();
		ValuePackage.eINSTANCE.eClass();

		// Create package meta-data objects
		theGenericeventPackage.createPackageContents();

		// Initialize created meta-data
		theGenericeventPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theGenericeventPackage.freeze();

  
		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(GenericeventPackage.eNS_URI, theGenericeventPackage);
		return theGenericeventPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getGenericEvent() {
		return genericEventEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getGenericEvent_RuleID() {
		return (EReference)genericEventEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getGenericEvent_Values() {
		return (EReference)genericEventEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getRuleID() {
		return ruleIDEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getRuleFQN() {
		return ruleFQNEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getRuleFQN_Fqn() {
		return (EAttribute)ruleFQNEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getRuleRef() {
		return ruleRefEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public GenericeventFactory getGenericeventFactory() {
		return (GenericeventFactory)getEFactoryInstance();
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
		if (isCreated) return;
		isCreated = true;

		// Create classes and their features
		genericEventEClass = createEClass(GENERIC_EVENT);
		createEReference(genericEventEClass, GENERIC_EVENT__RULE_ID);
		createEReference(genericEventEClass, GENERIC_EVENT__VALUES);

		ruleIDEClass = createEClass(RULE_ID);

		ruleFQNEClass = createEClass(RULE_FQN);
		createEAttribute(ruleFQNEClass, RULE_FQN__FQN);

		ruleRefEClass = createEClass(RULE_REF);
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
		if (isInitialized) return;
		isInitialized = true;

		// Initialize package
		setName(eNAME);
		setNsPrefix(eNS_PREFIX);
		setNsURI(eNS_URI);

		// Obtain other dependent packages
		EventPackage theEventPackage = (EventPackage)EPackage.Registry.INSTANCE.getEPackage(EventPackage.eNS_URI);
		ValuePackage theValuePackage = (ValuePackage)EPackage.Registry.INSTANCE.getEPackage(ValuePackage.eNS_URI);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes
		genericEventEClass.getESuperTypes().add(theEventPackage.getEvent());
		ruleFQNEClass.getESuperTypes().add(this.getRuleID());
		ruleRefEClass.getESuperTypes().add(this.getRuleID());

		// Initialize classes, features, and operations; add parameters
		initEClass(genericEventEClass, GenericEvent.class, "GenericEvent", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getGenericEvent_RuleID(), this.getRuleID(), null, "ruleID", null, 0, 1, GenericEvent.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getGenericEvent_Values(), theValuePackage.getValue(), null, "values", null, 0, -1, GenericEvent.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(ruleIDEClass, RuleID.class, "RuleID", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(ruleFQNEClass, RuleFQN.class, "RuleFQN", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getRuleFQN_Fqn(), ecorePackage.getEString(), "fqn", null, 0, 1, RuleFQN.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(ruleRefEClass, RuleRef.class, "RuleRef", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		// Create resource
		createResource(eNS_URI);
	}

} //GenericeventPackageImpl
