/**
 */
package org.eclipse.gemoc.executionframework.property.model.property.impl;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

import org.eclipse.emf.ecore.impl.EPackageImpl;

import org.eclipse.gemoc.executionframework.property.model.property.Absence;
import org.eclipse.gemoc.executionframework.property.model.property.After;
import org.eclipse.gemoc.executionframework.property.model.property.AfterUntil;
import org.eclipse.gemoc.executionframework.property.model.property.Before;
import org.eclipse.gemoc.executionframework.property.model.property.Between;
import org.eclipse.gemoc.executionframework.property.model.property.BoundType;
import org.eclipse.gemoc.executionframework.property.model.property.EPLProperty;
import org.eclipse.gemoc.executionframework.property.model.property.Existence;
import org.eclipse.gemoc.executionframework.property.model.property.Globally;
import org.eclipse.gemoc.executionframework.property.model.property.LowerBounded;
import org.eclipse.gemoc.executionframework.property.model.property.OrderedTemporalProperty;
import org.eclipse.gemoc.executionframework.property.model.property.Precedence;
import org.eclipse.gemoc.executionframework.property.model.property.Property;
import org.eclipse.gemoc.executionframework.property.model.property.PropertyFactory;
import org.eclipse.gemoc.executionframework.property.model.property.PropertyPackage;
import org.eclipse.gemoc.executionframework.property.model.property.QuantifiedProperty;
import org.eclipse.gemoc.executionframework.property.model.property.Quantifier;
import org.eclipse.gemoc.executionframework.property.model.property.Response;
import org.eclipse.gemoc.executionframework.property.model.property.Scope;
import org.eclipse.gemoc.executionframework.property.model.property.TemporalProperty;
import org.eclipse.gemoc.executionframework.property.model.property.Universality;
import org.eclipse.gemoc.executionframework.property.model.property.UpperBounded;

import org.eclipse.viatra.query.patternlanguage.emf.vql.PatternLanguagePackage;

import org.eclipse.xtext.common.types.TypesPackage;

import org.eclipse.xtext.xbase.XbasePackage;

import org.eclipse.xtext.xtype.XtypePackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class PropertyPackageImpl extends EPackageImpl implements PropertyPackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass propertyEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass quantifiedPropertyEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass temporalPropertyEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass eplPropertyEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass absenceEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass universalityEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass existenceEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass orderedTemporalPropertyEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass responseEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass precedenceEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass scopeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass globallyEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass lowerBoundedEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass upperBoundedEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass beforeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass betweenEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass afterUntilEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass afterEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum quantifierEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum boundTypeEEnum = null;

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
	 * @see org.eclipse.gemoc.executionframework.property.model.property.PropertyPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private PropertyPackageImpl() {
		super(eNS_URI, PropertyFactory.eINSTANCE);
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
	 * <p>This method is used to initialize {@link PropertyPackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static PropertyPackage init() {
		if (isInited)
			return (PropertyPackage) EPackage.Registry.INSTANCE.getEPackage(PropertyPackage.eNS_URI);

		// Obtain or create and register package
		Object registeredPropertyPackage = EPackage.Registry.INSTANCE.get(eNS_URI);
		PropertyPackageImpl thePropertyPackage = registeredPropertyPackage instanceof PropertyPackageImpl
				? (PropertyPackageImpl) registeredPropertyPackage
				: new PropertyPackageImpl();

		isInited = true;

		// Initialize simple dependencies
		PatternLanguagePackage.eINSTANCE.eClass();
		TypesPackage.eINSTANCE.eClass();
		XtypePackage.eINSTANCE.eClass();
		XbasePackage.eINSTANCE.eClass();

		// Create package meta-data objects
		thePropertyPackage.createPackageContents();

		// Initialize created meta-data
		thePropertyPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		thePropertyPackage.freeze();

		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(PropertyPackage.eNS_URI, thePropertyPackage);
		return thePropertyPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getProperty() {
		return propertyEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getProperty_Pattern() {
		return (EReference) propertyEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getQuantifiedProperty() {
		return quantifiedPropertyEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getQuantifiedProperty_Quantifier() {
		return (EAttribute) quantifiedPropertyEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getQuantifiedProperty_Property() {
		return (EReference) quantifiedPropertyEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getTemporalProperty() {
		return temporalPropertyEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getTemporalProperty_Scope() {
		return (EReference) temporalPropertyEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getEPLProperty() {
		return eplPropertyEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getEPLProperty_Statement() {
		return (EAttribute) eplPropertyEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getEPLProperty_Patterns() {
		return (EReference) eplPropertyEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getEPLProperty_InitialTruthValue() {
		return (EAttribute) eplPropertyEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getEPLProperty_Name() {
		return (EAttribute) eplPropertyEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getAbsence() {
		return absenceEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getUniversality() {
		return universalityEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getExistence() {
		return existenceEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getExistence_N() {
		return (EAttribute) existenceEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getExistence_BoundType() {
		return (EAttribute) existenceEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getOrderedTemporalProperty() {
		return orderedTemporalPropertyEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getOrderedTemporalProperty_OtherPattern() {
		return (EReference) orderedTemporalPropertyEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getResponse() {
		return responseEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getPrecedence() {
		return precedenceEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getScope() {
		return scopeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getGlobally() {
		return globallyEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getLowerBounded() {
		return lowerBoundedEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getLowerBounded_LowerBoundPattern() {
		return (EReference) lowerBoundedEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getUpperBounded() {
		return upperBoundedEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getUpperBounded_UpperBoundPattern() {
		return (EReference) upperBoundedEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getBefore() {
		return beforeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getBetween() {
		return betweenEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getAfterUntil() {
		return afterUntilEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getAfter() {
		return afterEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EEnum getQuantifier() {
		return quantifierEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EEnum getBoundType() {
		return boundTypeEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PropertyFactory getPropertyFactory() {
		return (PropertyFactory) getEFactoryInstance();
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
		propertyEClass = createEClass(PROPERTY);
		createEReference(propertyEClass, PROPERTY__PATTERN);

		quantifiedPropertyEClass = createEClass(QUANTIFIED_PROPERTY);
		createEAttribute(quantifiedPropertyEClass, QUANTIFIED_PROPERTY__QUANTIFIER);
		createEReference(quantifiedPropertyEClass, QUANTIFIED_PROPERTY__PROPERTY);

		temporalPropertyEClass = createEClass(TEMPORAL_PROPERTY);
		createEReference(temporalPropertyEClass, TEMPORAL_PROPERTY__SCOPE);

		eplPropertyEClass = createEClass(EPL_PROPERTY);
		createEAttribute(eplPropertyEClass, EPL_PROPERTY__STATEMENT);
		createEReference(eplPropertyEClass, EPL_PROPERTY__PATTERNS);
		createEAttribute(eplPropertyEClass, EPL_PROPERTY__INITIAL_TRUTH_VALUE);
		createEAttribute(eplPropertyEClass, EPL_PROPERTY__NAME);

		absenceEClass = createEClass(ABSENCE);

		universalityEClass = createEClass(UNIVERSALITY);

		existenceEClass = createEClass(EXISTENCE);
		createEAttribute(existenceEClass, EXISTENCE__N);
		createEAttribute(existenceEClass, EXISTENCE__BOUND_TYPE);

		orderedTemporalPropertyEClass = createEClass(ORDERED_TEMPORAL_PROPERTY);
		createEReference(orderedTemporalPropertyEClass, ORDERED_TEMPORAL_PROPERTY__OTHER_PATTERN);

		responseEClass = createEClass(RESPONSE);

		precedenceEClass = createEClass(PRECEDENCE);

		scopeEClass = createEClass(SCOPE);

		globallyEClass = createEClass(GLOBALLY);

		lowerBoundedEClass = createEClass(LOWER_BOUNDED);
		createEReference(lowerBoundedEClass, LOWER_BOUNDED__LOWER_BOUND_PATTERN);

		upperBoundedEClass = createEClass(UPPER_BOUNDED);
		createEReference(upperBoundedEClass, UPPER_BOUNDED__UPPER_BOUND_PATTERN);

		beforeEClass = createEClass(BEFORE);

		betweenEClass = createEClass(BETWEEN);

		afterUntilEClass = createEClass(AFTER_UNTIL);

		afterEClass = createEClass(AFTER);

		// Create enums
		quantifierEEnum = createEEnum(QUANTIFIER);
		boundTypeEEnum = createEEnum(BOUND_TYPE);
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
		PatternLanguagePackage thePatternLanguagePackage = (PatternLanguagePackage) EPackage.Registry.INSTANCE
				.getEPackage(PatternLanguagePackage.eNS_URI);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes
		quantifiedPropertyEClass.getESuperTypes().add(this.getProperty());
		temporalPropertyEClass.getESuperTypes().add(this.getProperty());
		absenceEClass.getESuperTypes().add(this.getTemporalProperty());
		universalityEClass.getESuperTypes().add(this.getTemporalProperty());
		existenceEClass.getESuperTypes().add(this.getTemporalProperty());
		orderedTemporalPropertyEClass.getESuperTypes().add(this.getTemporalProperty());
		responseEClass.getESuperTypes().add(this.getOrderedTemporalProperty());
		precedenceEClass.getESuperTypes().add(this.getOrderedTemporalProperty());
		globallyEClass.getESuperTypes().add(this.getScope());
		lowerBoundedEClass.getESuperTypes().add(this.getScope());
		upperBoundedEClass.getESuperTypes().add(this.getScope());
		beforeEClass.getESuperTypes().add(this.getUpperBounded());
		betweenEClass.getESuperTypes().add(this.getLowerBounded());
		betweenEClass.getESuperTypes().add(this.getUpperBounded());
		afterUntilEClass.getESuperTypes().add(this.getLowerBounded());
		afterUntilEClass.getESuperTypes().add(this.getUpperBounded());
		afterEClass.getESuperTypes().add(this.getLowerBounded());

		// Initialize classes, features, and operations; add parameters
		initEClass(propertyEClass, Property.class, "Property", !IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);
		initEReference(getProperty_Pattern(), thePatternLanguagePackage.getPattern(), null, "pattern", null, 1, 1,
				Property.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(quantifiedPropertyEClass, QuantifiedProperty.class, "QuantifiedProperty", !IS_ABSTRACT,
				!IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getQuantifiedProperty_Quantifier(), this.getQuantifier(), "quantifier", null, 1, 1,
				QuantifiedProperty.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
				!IS_DERIVED, IS_ORDERED);
		initEReference(getQuantifiedProperty_Property(), this.getProperty(), null, "property", null, 1, 1,
				QuantifiedProperty.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(temporalPropertyEClass, TemporalProperty.class, "TemporalProperty", IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);
		initEReference(getTemporalProperty_Scope(), this.getScope(), null, "scope", null, 1, 1, TemporalProperty.class,
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(eplPropertyEClass, EPLProperty.class, "EPLProperty", !IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getEPLProperty_Statement(), ecorePackage.getEString(), "statement", null, 1, 1,
				EPLProperty.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
				!IS_DERIVED, IS_ORDERED);
		initEReference(getEPLProperty_Patterns(), thePatternLanguagePackage.getPattern(), null, "patterns", null, 0, -1,
				EPLProperty.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getEPLProperty_InitialTruthValue(), ecorePackage.getEBoolean(), "initialTruthValue", null, 0, 1,
				EPLProperty.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
				!IS_DERIVED, IS_ORDERED);
		initEAttribute(getEPLProperty_Name(), ecorePackage.getEString(), "name", null, 0, 1, EPLProperty.class,
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(absenceEClass, Absence.class, "Absence", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(universalityEClass, Universality.class, "Universality", !IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);

		initEClass(existenceEClass, Existence.class, "Existence", !IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getExistence_N(), ecorePackage.getEInt(), "n", "1", 1, 1, Existence.class, !IS_TRANSIENT,
				!IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getExistence_BoundType(), this.getBoundType(), "boundType", null, 0, 1, Existence.class,
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(orderedTemporalPropertyEClass, OrderedTemporalProperty.class, "OrderedTemporalProperty", IS_ABSTRACT,
				!IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getOrderedTemporalProperty_OtherPattern(), thePatternLanguagePackage.getPattern(), null,
				"otherPattern", null, 1, 1, OrderedTemporalProperty.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
				!IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(responseEClass, Response.class, "Response", !IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);

		initEClass(precedenceEClass, Precedence.class, "Precedence", !IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);

		initEClass(scopeEClass, Scope.class, "Scope", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(globallyEClass, Globally.class, "Globally", !IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);

		initEClass(lowerBoundedEClass, LowerBounded.class, "LowerBounded", IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);
		initEReference(getLowerBounded_LowerBoundPattern(), thePatternLanguagePackage.getPattern(), null,
				"lowerBoundPattern", null, 1, 1, LowerBounded.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
				!IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(upperBoundedEClass, UpperBounded.class, "UpperBounded", IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);
		initEReference(getUpperBounded_UpperBoundPattern(), thePatternLanguagePackage.getPattern(), null,
				"upperBoundPattern", null, 1, 1, UpperBounded.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
				!IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(beforeEClass, Before.class, "Before", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(betweenEClass, Between.class, "Between", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(afterUntilEClass, AfterUntil.class, "AfterUntil", !IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);

		initEClass(afterEClass, After.class, "After", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		// Initialize enums and add enum literals
		initEEnum(quantifierEEnum, Quantifier.class, "Quantifier");
		addEEnumLiteral(quantifierEEnum, Quantifier.FORALL);
		addEEnumLiteral(quantifierEEnum, Quantifier.EXISTS);

		initEEnum(boundTypeEEnum, BoundType.class, "BoundType");
		addEEnumLiteral(boundTypeEEnum, BoundType.EXACT);
		addEEnumLiteral(boundTypeEEnum, BoundType.LOWER_BOUND);
		addEEnumLiteral(boundTypeEEnum, BoundType.UPPER_BOUND);

		// Create resource
		createResource(eNS_URI);
	}

} //PropertyPackageImpl
