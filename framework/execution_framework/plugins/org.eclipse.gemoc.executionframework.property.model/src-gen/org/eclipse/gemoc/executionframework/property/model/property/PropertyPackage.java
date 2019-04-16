/**
 */
package org.eclipse.gemoc.executionframework.property.model.property;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnum;
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
 * @see org.eclipse.gemoc.executionframework.property.model.property.PropertyFactory
 * @model kind="package"
 * @generated
 */
public interface PropertyPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "property";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://www.eclipse.gemoc.org/property";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "property";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	PropertyPackage eINSTANCE = org.eclipse.gemoc.executionframework.property.model.property.impl.PropertyPackageImpl
			.init();

	/**
	 * The meta object id for the '{@link org.eclipse.gemoc.executionframework.property.model.property.impl.PropertyImpl <em>Property</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.gemoc.executionframework.property.model.property.impl.PropertyImpl
	 * @see org.eclipse.gemoc.executionframework.property.model.property.impl.PropertyPackageImpl#getProperty()
	 * @generated
	 */
	int PROPERTY = 0;

	/**
	 * The feature id for the '<em><b>Pattern</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY__PATTERN = 0;

	/**
	 * The number of structural features of the '<em>Property</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY_FEATURE_COUNT = 1;

	/**
	 * The number of operations of the '<em>Property</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.eclipse.gemoc.executionframework.property.model.property.impl.QuantifiedPropertyImpl <em>Quantified Property</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.gemoc.executionframework.property.model.property.impl.QuantifiedPropertyImpl
	 * @see org.eclipse.gemoc.executionframework.property.model.property.impl.PropertyPackageImpl#getQuantifiedProperty()
	 * @generated
	 */
	int QUANTIFIED_PROPERTY = 1;

	/**
	 * The feature id for the '<em><b>Pattern</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int QUANTIFIED_PROPERTY__PATTERN = PROPERTY__PATTERN;

	/**
	 * The feature id for the '<em><b>Quantifier</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int QUANTIFIED_PROPERTY__QUANTIFIER = PROPERTY_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Property</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int QUANTIFIED_PROPERTY__PROPERTY = PROPERTY_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Quantified Property</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int QUANTIFIED_PROPERTY_FEATURE_COUNT = PROPERTY_FEATURE_COUNT + 2;

	/**
	 * The number of operations of the '<em>Quantified Property</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int QUANTIFIED_PROPERTY_OPERATION_COUNT = PROPERTY_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.gemoc.executionframework.property.model.property.impl.TemporalPropertyImpl <em>Temporal Property</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.gemoc.executionframework.property.model.property.impl.TemporalPropertyImpl
	 * @see org.eclipse.gemoc.executionframework.property.model.property.impl.PropertyPackageImpl#getTemporalProperty()
	 * @generated
	 */
	int TEMPORAL_PROPERTY = 2;

	/**
	 * The feature id for the '<em><b>Pattern</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEMPORAL_PROPERTY__PATTERN = PROPERTY__PATTERN;

	/**
	 * The feature id for the '<em><b>Scope</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEMPORAL_PROPERTY__SCOPE = PROPERTY_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Temporal Property</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEMPORAL_PROPERTY_FEATURE_COUNT = PROPERTY_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>Temporal Property</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEMPORAL_PROPERTY_OPERATION_COUNT = PROPERTY_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.gemoc.executionframework.property.model.property.impl.EPLPropertyImpl <em>EPL Property</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.gemoc.executionframework.property.model.property.impl.EPLPropertyImpl
	 * @see org.eclipse.gemoc.executionframework.property.model.property.impl.PropertyPackageImpl#getEPLProperty()
	 * @generated
	 */
	int EPL_PROPERTY = 3;

	/**
	 * The feature id for the '<em><b>Statement</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EPL_PROPERTY__STATEMENT = 0;

	/**
	 * The feature id for the '<em><b>Patterns</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EPL_PROPERTY__PATTERNS = 1;

	/**
	 * The feature id for the '<em><b>Initial Truth Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EPL_PROPERTY__INITIAL_TRUTH_VALUE = 2;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EPL_PROPERTY__NAME = 3;

	/**
	 * The number of structural features of the '<em>EPL Property</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EPL_PROPERTY_FEATURE_COUNT = 4;

	/**
	 * The number of operations of the '<em>EPL Property</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EPL_PROPERTY_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.eclipse.gemoc.executionframework.property.model.property.impl.AbsenceImpl <em>Absence</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.gemoc.executionframework.property.model.property.impl.AbsenceImpl
	 * @see org.eclipse.gemoc.executionframework.property.model.property.impl.PropertyPackageImpl#getAbsence()
	 * @generated
	 */
	int ABSENCE = 4;

	/**
	 * The feature id for the '<em><b>Pattern</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSENCE__PATTERN = TEMPORAL_PROPERTY__PATTERN;

	/**
	 * The feature id for the '<em><b>Scope</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSENCE__SCOPE = TEMPORAL_PROPERTY__SCOPE;

	/**
	 * The number of structural features of the '<em>Absence</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSENCE_FEATURE_COUNT = TEMPORAL_PROPERTY_FEATURE_COUNT + 0;

	/**
	 * The number of operations of the '<em>Absence</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSENCE_OPERATION_COUNT = TEMPORAL_PROPERTY_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.gemoc.executionframework.property.model.property.impl.UniversalityImpl <em>Universality</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.gemoc.executionframework.property.model.property.impl.UniversalityImpl
	 * @see org.eclipse.gemoc.executionframework.property.model.property.impl.PropertyPackageImpl#getUniversality()
	 * @generated
	 */
	int UNIVERSALITY = 5;

	/**
	 * The feature id for the '<em><b>Pattern</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UNIVERSALITY__PATTERN = TEMPORAL_PROPERTY__PATTERN;

	/**
	 * The feature id for the '<em><b>Scope</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UNIVERSALITY__SCOPE = TEMPORAL_PROPERTY__SCOPE;

	/**
	 * The number of structural features of the '<em>Universality</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UNIVERSALITY_FEATURE_COUNT = TEMPORAL_PROPERTY_FEATURE_COUNT + 0;

	/**
	 * The number of operations of the '<em>Universality</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UNIVERSALITY_OPERATION_COUNT = TEMPORAL_PROPERTY_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.gemoc.executionframework.property.model.property.impl.ExistenceImpl <em>Existence</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.gemoc.executionframework.property.model.property.impl.ExistenceImpl
	 * @see org.eclipse.gemoc.executionframework.property.model.property.impl.PropertyPackageImpl#getExistence()
	 * @generated
	 */
	int EXISTENCE = 6;

	/**
	 * The feature id for the '<em><b>Pattern</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXISTENCE__PATTERN = TEMPORAL_PROPERTY__PATTERN;

	/**
	 * The feature id for the '<em><b>Scope</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXISTENCE__SCOPE = TEMPORAL_PROPERTY__SCOPE;

	/**
	 * The number of structural features of the '<em>Existence</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXISTENCE_FEATURE_COUNT = TEMPORAL_PROPERTY_FEATURE_COUNT + 0;

	/**
	 * The number of operations of the '<em>Existence</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXISTENCE_OPERATION_COUNT = TEMPORAL_PROPERTY_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.gemoc.executionframework.property.model.property.impl.BoundedExistenceImpl <em>Bounded Existence</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.gemoc.executionframework.property.model.property.impl.BoundedExistenceImpl
	 * @see org.eclipse.gemoc.executionframework.property.model.property.impl.PropertyPackageImpl#getBoundedExistence()
	 * @generated
	 */
	int BOUNDED_EXISTENCE = 7;

	/**
	 * The feature id for the '<em><b>Pattern</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BOUNDED_EXISTENCE__PATTERN = TEMPORAL_PROPERTY__PATTERN;

	/**
	 * The feature id for the '<em><b>Scope</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BOUNDED_EXISTENCE__SCOPE = TEMPORAL_PROPERTY__SCOPE;

	/**
	 * The feature id for the '<em><b>N</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BOUNDED_EXISTENCE__N = TEMPORAL_PROPERTY_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Bounded Existence</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BOUNDED_EXISTENCE_FEATURE_COUNT = TEMPORAL_PROPERTY_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>Bounded Existence</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BOUNDED_EXISTENCE_OPERATION_COUNT = TEMPORAL_PROPERTY_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.gemoc.executionframework.property.model.property.impl.OrderedTemporalPropertyImpl <em>Ordered Temporal Property</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.gemoc.executionframework.property.model.property.impl.OrderedTemporalPropertyImpl
	 * @see org.eclipse.gemoc.executionframework.property.model.property.impl.PropertyPackageImpl#getOrderedTemporalProperty()
	 * @generated
	 */
	int ORDERED_TEMPORAL_PROPERTY = 8;

	/**
	 * The feature id for the '<em><b>Pattern</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ORDERED_TEMPORAL_PROPERTY__PATTERN = TEMPORAL_PROPERTY__PATTERN;

	/**
	 * The feature id for the '<em><b>Scope</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ORDERED_TEMPORAL_PROPERTY__SCOPE = TEMPORAL_PROPERTY__SCOPE;

	/**
	 * The feature id for the '<em><b>Other Pattern</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ORDERED_TEMPORAL_PROPERTY__OTHER_PATTERN = TEMPORAL_PROPERTY_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Ordered Temporal Property</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ORDERED_TEMPORAL_PROPERTY_FEATURE_COUNT = TEMPORAL_PROPERTY_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>Ordered Temporal Property</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ORDERED_TEMPORAL_PROPERTY_OPERATION_COUNT = TEMPORAL_PROPERTY_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.gemoc.executionframework.property.model.property.impl.ResponseImpl <em>Response</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.gemoc.executionframework.property.model.property.impl.ResponseImpl
	 * @see org.eclipse.gemoc.executionframework.property.model.property.impl.PropertyPackageImpl#getResponse()
	 * @generated
	 */
	int RESPONSE = 9;

	/**
	 * The feature id for the '<em><b>Pattern</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RESPONSE__PATTERN = ORDERED_TEMPORAL_PROPERTY__PATTERN;

	/**
	 * The feature id for the '<em><b>Scope</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RESPONSE__SCOPE = ORDERED_TEMPORAL_PROPERTY__SCOPE;

	/**
	 * The feature id for the '<em><b>Other Pattern</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RESPONSE__OTHER_PATTERN = ORDERED_TEMPORAL_PROPERTY__OTHER_PATTERN;

	/**
	 * The number of structural features of the '<em>Response</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RESPONSE_FEATURE_COUNT = ORDERED_TEMPORAL_PROPERTY_FEATURE_COUNT + 0;

	/**
	 * The number of operations of the '<em>Response</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RESPONSE_OPERATION_COUNT = ORDERED_TEMPORAL_PROPERTY_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.gemoc.executionframework.property.model.property.impl.PrecedenceImpl <em>Precedence</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.gemoc.executionframework.property.model.property.impl.PrecedenceImpl
	 * @see org.eclipse.gemoc.executionframework.property.model.property.impl.PropertyPackageImpl#getPrecedence()
	 * @generated
	 */
	int PRECEDENCE = 10;

	/**
	 * The feature id for the '<em><b>Pattern</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PRECEDENCE__PATTERN = ORDERED_TEMPORAL_PROPERTY__PATTERN;

	/**
	 * The feature id for the '<em><b>Scope</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PRECEDENCE__SCOPE = ORDERED_TEMPORAL_PROPERTY__SCOPE;

	/**
	 * The feature id for the '<em><b>Other Pattern</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PRECEDENCE__OTHER_PATTERN = ORDERED_TEMPORAL_PROPERTY__OTHER_PATTERN;

	/**
	 * The number of structural features of the '<em>Precedence</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PRECEDENCE_FEATURE_COUNT = ORDERED_TEMPORAL_PROPERTY_FEATURE_COUNT + 0;

	/**
	 * The number of operations of the '<em>Precedence</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PRECEDENCE_OPERATION_COUNT = ORDERED_TEMPORAL_PROPERTY_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.gemoc.executionframework.property.model.property.impl.ScopeImpl <em>Scope</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.gemoc.executionframework.property.model.property.impl.ScopeImpl
	 * @see org.eclipse.gemoc.executionframework.property.model.property.impl.PropertyPackageImpl#getScope()
	 * @generated
	 */
	int SCOPE = 11;

	/**
	 * The number of structural features of the '<em>Scope</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SCOPE_FEATURE_COUNT = 0;

	/**
	 * The number of operations of the '<em>Scope</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SCOPE_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.eclipse.gemoc.executionframework.property.model.property.impl.GloballyImpl <em>Globally</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.gemoc.executionframework.property.model.property.impl.GloballyImpl
	 * @see org.eclipse.gemoc.executionframework.property.model.property.impl.PropertyPackageImpl#getGlobally()
	 * @generated
	 */
	int GLOBALLY = 12;

	/**
	 * The number of structural features of the '<em>Globally</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GLOBALLY_FEATURE_COUNT = SCOPE_FEATURE_COUNT + 0;

	/**
	 * The number of operations of the '<em>Globally</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GLOBALLY_OPERATION_COUNT = SCOPE_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.gemoc.executionframework.property.model.property.impl.LowerBoundedImpl <em>Lower Bounded</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.gemoc.executionframework.property.model.property.impl.LowerBoundedImpl
	 * @see org.eclipse.gemoc.executionframework.property.model.property.impl.PropertyPackageImpl#getLowerBounded()
	 * @generated
	 */
	int LOWER_BOUNDED = 13;

	/**
	 * The feature id for the '<em><b>Lower Bound Pattern</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LOWER_BOUNDED__LOWER_BOUND_PATTERN = SCOPE_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Lower Bounded</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LOWER_BOUNDED_FEATURE_COUNT = SCOPE_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>Lower Bounded</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LOWER_BOUNDED_OPERATION_COUNT = SCOPE_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.gemoc.executionframework.property.model.property.impl.UpperBoundedImpl <em>Upper Bounded</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.gemoc.executionframework.property.model.property.impl.UpperBoundedImpl
	 * @see org.eclipse.gemoc.executionframework.property.model.property.impl.PropertyPackageImpl#getUpperBounded()
	 * @generated
	 */
	int UPPER_BOUNDED = 14;

	/**
	 * The feature id for the '<em><b>Upper Bound Pattern</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UPPER_BOUNDED__UPPER_BOUND_PATTERN = SCOPE_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Upper Bounded</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UPPER_BOUNDED_FEATURE_COUNT = SCOPE_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>Upper Bounded</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UPPER_BOUNDED_OPERATION_COUNT = SCOPE_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.gemoc.executionframework.property.model.property.impl.BeforeImpl <em>Before</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.gemoc.executionframework.property.model.property.impl.BeforeImpl
	 * @see org.eclipse.gemoc.executionframework.property.model.property.impl.PropertyPackageImpl#getBefore()
	 * @generated
	 */
	int BEFORE = 15;

	/**
	 * The feature id for the '<em><b>Lower Bound Pattern</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BEFORE__LOWER_BOUND_PATTERN = LOWER_BOUNDED__LOWER_BOUND_PATTERN;

	/**
	 * The number of structural features of the '<em>Before</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BEFORE_FEATURE_COUNT = LOWER_BOUNDED_FEATURE_COUNT + 0;

	/**
	 * The number of operations of the '<em>Before</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BEFORE_OPERATION_COUNT = LOWER_BOUNDED_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.gemoc.executionframework.property.model.property.impl.BetweenImpl <em>Between</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.gemoc.executionframework.property.model.property.impl.BetweenImpl
	 * @see org.eclipse.gemoc.executionframework.property.model.property.impl.PropertyPackageImpl#getBetween()
	 * @generated
	 */
	int BETWEEN = 16;

	/**
	 * The feature id for the '<em><b>Lower Bound Pattern</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BETWEEN__LOWER_BOUND_PATTERN = LOWER_BOUNDED__LOWER_BOUND_PATTERN;

	/**
	 * The feature id for the '<em><b>Upper Bound Pattern</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BETWEEN__UPPER_BOUND_PATTERN = LOWER_BOUNDED_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Between</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BETWEEN_FEATURE_COUNT = LOWER_BOUNDED_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>Between</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BETWEEN_OPERATION_COUNT = LOWER_BOUNDED_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.gemoc.executionframework.property.model.property.impl.AfterUntilImpl <em>After Until</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.gemoc.executionframework.property.model.property.impl.AfterUntilImpl
	 * @see org.eclipse.gemoc.executionframework.property.model.property.impl.PropertyPackageImpl#getAfterUntil()
	 * @generated
	 */
	int AFTER_UNTIL = 17;

	/**
	 * The feature id for the '<em><b>Lower Bound Pattern</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AFTER_UNTIL__LOWER_BOUND_PATTERN = LOWER_BOUNDED__LOWER_BOUND_PATTERN;

	/**
	 * The feature id for the '<em><b>Upper Bound Pattern</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AFTER_UNTIL__UPPER_BOUND_PATTERN = LOWER_BOUNDED_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>After Until</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AFTER_UNTIL_FEATURE_COUNT = LOWER_BOUNDED_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>After Until</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AFTER_UNTIL_OPERATION_COUNT = LOWER_BOUNDED_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.gemoc.executionframework.property.model.property.impl.AfterImpl <em>After</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.gemoc.executionframework.property.model.property.impl.AfterImpl
	 * @see org.eclipse.gemoc.executionframework.property.model.property.impl.PropertyPackageImpl#getAfter()
	 * @generated
	 */
	int AFTER = 18;

	/**
	 * The feature id for the '<em><b>Upper Bound Pattern</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AFTER__UPPER_BOUND_PATTERN = UPPER_BOUNDED__UPPER_BOUND_PATTERN;

	/**
	 * The number of structural features of the '<em>After</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AFTER_FEATURE_COUNT = UPPER_BOUNDED_FEATURE_COUNT + 0;

	/**
	 * The number of operations of the '<em>After</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AFTER_OPERATION_COUNT = UPPER_BOUNDED_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.gemoc.executionframework.property.model.property.Quantifier <em>Quantifier</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.gemoc.executionframework.property.model.property.Quantifier
	 * @see org.eclipse.gemoc.executionframework.property.model.property.impl.PropertyPackageImpl#getQuantifier()
	 * @generated
	 */
	int QUANTIFIER = 19;

	/**
	 * Returns the meta object for class '{@link org.eclipse.gemoc.executionframework.property.model.property.Property <em>Property</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Property</em>'.
	 * @see org.eclipse.gemoc.executionframework.property.model.property.Property
	 * @generated
	 */
	EClass getProperty();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.gemoc.executionframework.property.model.property.Property#getPattern <em>Pattern</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Pattern</em>'.
	 * @see org.eclipse.gemoc.executionframework.property.model.property.Property#getPattern()
	 * @see #getProperty()
	 * @generated
	 */
	EAttribute getProperty_Pattern();

	/**
	 * Returns the meta object for class '{@link org.eclipse.gemoc.executionframework.property.model.property.QuantifiedProperty <em>Quantified Property</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Quantified Property</em>'.
	 * @see org.eclipse.gemoc.executionframework.property.model.property.QuantifiedProperty
	 * @generated
	 */
	EClass getQuantifiedProperty();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.gemoc.executionframework.property.model.property.QuantifiedProperty#getQuantifier <em>Quantifier</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Quantifier</em>'.
	 * @see org.eclipse.gemoc.executionframework.property.model.property.QuantifiedProperty#getQuantifier()
	 * @see #getQuantifiedProperty()
	 * @generated
	 */
	EAttribute getQuantifiedProperty_Quantifier();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.gemoc.executionframework.property.model.property.QuantifiedProperty#getProperty <em>Property</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Property</em>'.
	 * @see org.eclipse.gemoc.executionframework.property.model.property.QuantifiedProperty#getProperty()
	 * @see #getQuantifiedProperty()
	 * @generated
	 */
	EReference getQuantifiedProperty_Property();

	/**
	 * Returns the meta object for class '{@link org.eclipse.gemoc.executionframework.property.model.property.TemporalProperty <em>Temporal Property</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Temporal Property</em>'.
	 * @see org.eclipse.gemoc.executionframework.property.model.property.TemporalProperty
	 * @generated
	 */
	EClass getTemporalProperty();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.gemoc.executionframework.property.model.property.TemporalProperty#getScope <em>Scope</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Scope</em>'.
	 * @see org.eclipse.gemoc.executionframework.property.model.property.TemporalProperty#getScope()
	 * @see #getTemporalProperty()
	 * @generated
	 */
	EReference getTemporalProperty_Scope();

	/**
	 * Returns the meta object for class '{@link org.eclipse.gemoc.executionframework.property.model.property.EPLProperty <em>EPL Property</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>EPL Property</em>'.
	 * @see org.eclipse.gemoc.executionframework.property.model.property.EPLProperty
	 * @generated
	 */
	EClass getEPLProperty();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.gemoc.executionframework.property.model.property.EPLProperty#getStatement <em>Statement</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Statement</em>'.
	 * @see org.eclipse.gemoc.executionframework.property.model.property.EPLProperty#getStatement()
	 * @see #getEPLProperty()
	 * @generated
	 */
	EAttribute getEPLProperty_Statement();

	/**
	 * Returns the meta object for the reference list '{@link org.eclipse.gemoc.executionframework.property.model.property.EPLProperty#getPatterns <em>Patterns</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Patterns</em>'.
	 * @see org.eclipse.gemoc.executionframework.property.model.property.EPLProperty#getPatterns()
	 * @see #getEPLProperty()
	 * @generated
	 */
	EReference getEPLProperty_Patterns();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.gemoc.executionframework.property.model.property.EPLProperty#isInitialTruthValue <em>Initial Truth Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Initial Truth Value</em>'.
	 * @see org.eclipse.gemoc.executionframework.property.model.property.EPLProperty#isInitialTruthValue()
	 * @see #getEPLProperty()
	 * @generated
	 */
	EAttribute getEPLProperty_InitialTruthValue();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.gemoc.executionframework.property.model.property.EPLProperty#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.eclipse.gemoc.executionframework.property.model.property.EPLProperty#getName()
	 * @see #getEPLProperty()
	 * @generated
	 */
	EAttribute getEPLProperty_Name();

	/**
	 * Returns the meta object for class '{@link org.eclipse.gemoc.executionframework.property.model.property.Absence <em>Absence</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Absence</em>'.
	 * @see org.eclipse.gemoc.executionframework.property.model.property.Absence
	 * @generated
	 */
	EClass getAbsence();

	/**
	 * Returns the meta object for class '{@link org.eclipse.gemoc.executionframework.property.model.property.Universality <em>Universality</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Universality</em>'.
	 * @see org.eclipse.gemoc.executionframework.property.model.property.Universality
	 * @generated
	 */
	EClass getUniversality();

	/**
	 * Returns the meta object for class '{@link org.eclipse.gemoc.executionframework.property.model.property.Existence <em>Existence</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Existence</em>'.
	 * @see org.eclipse.gemoc.executionframework.property.model.property.Existence
	 * @generated
	 */
	EClass getExistence();

	/**
	 * Returns the meta object for class '{@link org.eclipse.gemoc.executionframework.property.model.property.BoundedExistence <em>Bounded Existence</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Bounded Existence</em>'.
	 * @see org.eclipse.gemoc.executionframework.property.model.property.BoundedExistence
	 * @generated
	 */
	EClass getBoundedExistence();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.gemoc.executionframework.property.model.property.BoundedExistence#getN <em>N</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>N</em>'.
	 * @see org.eclipse.gemoc.executionframework.property.model.property.BoundedExistence#getN()
	 * @see #getBoundedExistence()
	 * @generated
	 */
	EAttribute getBoundedExistence_N();

	/**
	 * Returns the meta object for class '{@link org.eclipse.gemoc.executionframework.property.model.property.OrderedTemporalProperty <em>Ordered Temporal Property</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Ordered Temporal Property</em>'.
	 * @see org.eclipse.gemoc.executionframework.property.model.property.OrderedTemporalProperty
	 * @generated
	 */
	EClass getOrderedTemporalProperty();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.gemoc.executionframework.property.model.property.OrderedTemporalProperty#getOtherPattern <em>Other Pattern</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Other Pattern</em>'.
	 * @see org.eclipse.gemoc.executionframework.property.model.property.OrderedTemporalProperty#getOtherPattern()
	 * @see #getOrderedTemporalProperty()
	 * @generated
	 */
	EAttribute getOrderedTemporalProperty_OtherPattern();

	/**
	 * Returns the meta object for class '{@link org.eclipse.gemoc.executionframework.property.model.property.Response <em>Response</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Response</em>'.
	 * @see org.eclipse.gemoc.executionframework.property.model.property.Response
	 * @generated
	 */
	EClass getResponse();

	/**
	 * Returns the meta object for class '{@link org.eclipse.gemoc.executionframework.property.model.property.Precedence <em>Precedence</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Precedence</em>'.
	 * @see org.eclipse.gemoc.executionframework.property.model.property.Precedence
	 * @generated
	 */
	EClass getPrecedence();

	/**
	 * Returns the meta object for class '{@link org.eclipse.gemoc.executionframework.property.model.property.Scope <em>Scope</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Scope</em>'.
	 * @see org.eclipse.gemoc.executionframework.property.model.property.Scope
	 * @generated
	 */
	EClass getScope();

	/**
	 * Returns the meta object for class '{@link org.eclipse.gemoc.executionframework.property.model.property.Globally <em>Globally</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Globally</em>'.
	 * @see org.eclipse.gemoc.executionframework.property.model.property.Globally
	 * @generated
	 */
	EClass getGlobally();

	/**
	 * Returns the meta object for class '{@link org.eclipse.gemoc.executionframework.property.model.property.LowerBounded <em>Lower Bounded</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Lower Bounded</em>'.
	 * @see org.eclipse.gemoc.executionframework.property.model.property.LowerBounded
	 * @generated
	 */
	EClass getLowerBounded();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.gemoc.executionframework.property.model.property.LowerBounded#getLowerBoundPattern <em>Lower Bound Pattern</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Lower Bound Pattern</em>'.
	 * @see org.eclipse.gemoc.executionframework.property.model.property.LowerBounded#getLowerBoundPattern()
	 * @see #getLowerBounded()
	 * @generated
	 */
	EAttribute getLowerBounded_LowerBoundPattern();

	/**
	 * Returns the meta object for class '{@link org.eclipse.gemoc.executionframework.property.model.property.UpperBounded <em>Upper Bounded</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Upper Bounded</em>'.
	 * @see org.eclipse.gemoc.executionframework.property.model.property.UpperBounded
	 * @generated
	 */
	EClass getUpperBounded();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.gemoc.executionframework.property.model.property.UpperBounded#getUpperBoundPattern <em>Upper Bound Pattern</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Upper Bound Pattern</em>'.
	 * @see org.eclipse.gemoc.executionframework.property.model.property.UpperBounded#getUpperBoundPattern()
	 * @see #getUpperBounded()
	 * @generated
	 */
	EAttribute getUpperBounded_UpperBoundPattern();

	/**
	 * Returns the meta object for class '{@link org.eclipse.gemoc.executionframework.property.model.property.Before <em>Before</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Before</em>'.
	 * @see org.eclipse.gemoc.executionframework.property.model.property.Before
	 * @generated
	 */
	EClass getBefore();

	/**
	 * Returns the meta object for class '{@link org.eclipse.gemoc.executionframework.property.model.property.Between <em>Between</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Between</em>'.
	 * @see org.eclipse.gemoc.executionframework.property.model.property.Between
	 * @generated
	 */
	EClass getBetween();

	/**
	 * Returns the meta object for class '{@link org.eclipse.gemoc.executionframework.property.model.property.AfterUntil <em>After Until</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>After Until</em>'.
	 * @see org.eclipse.gemoc.executionframework.property.model.property.AfterUntil
	 * @generated
	 */
	EClass getAfterUntil();

	/**
	 * Returns the meta object for class '{@link org.eclipse.gemoc.executionframework.property.model.property.After <em>After</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>After</em>'.
	 * @see org.eclipse.gemoc.executionframework.property.model.property.After
	 * @generated
	 */
	EClass getAfter();

	/**
	 * Returns the meta object for enum '{@link org.eclipse.gemoc.executionframework.property.model.property.Quantifier <em>Quantifier</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Quantifier</em>'.
	 * @see org.eclipse.gemoc.executionframework.property.model.property.Quantifier
	 * @generated
	 */
	EEnum getQuantifier();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	PropertyFactory getPropertyFactory();

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
		 * The meta object literal for the '{@link org.eclipse.gemoc.executionframework.property.model.property.impl.PropertyImpl <em>Property</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.gemoc.executionframework.property.model.property.impl.PropertyImpl
		 * @see org.eclipse.gemoc.executionframework.property.model.property.impl.PropertyPackageImpl#getProperty()
		 * @generated
		 */
		EClass PROPERTY = eINSTANCE.getProperty();

		/**
		 * The meta object literal for the '<em><b>Pattern</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PROPERTY__PATTERN = eINSTANCE.getProperty_Pattern();

		/**
		 * The meta object literal for the '{@link org.eclipse.gemoc.executionframework.property.model.property.impl.QuantifiedPropertyImpl <em>Quantified Property</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.gemoc.executionframework.property.model.property.impl.QuantifiedPropertyImpl
		 * @see org.eclipse.gemoc.executionframework.property.model.property.impl.PropertyPackageImpl#getQuantifiedProperty()
		 * @generated
		 */
		EClass QUANTIFIED_PROPERTY = eINSTANCE.getQuantifiedProperty();

		/**
		 * The meta object literal for the '<em><b>Quantifier</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute QUANTIFIED_PROPERTY__QUANTIFIER = eINSTANCE.getQuantifiedProperty_Quantifier();

		/**
		 * The meta object literal for the '<em><b>Property</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference QUANTIFIED_PROPERTY__PROPERTY = eINSTANCE.getQuantifiedProperty_Property();

		/**
		 * The meta object literal for the '{@link org.eclipse.gemoc.executionframework.property.model.property.impl.TemporalPropertyImpl <em>Temporal Property</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.gemoc.executionframework.property.model.property.impl.TemporalPropertyImpl
		 * @see org.eclipse.gemoc.executionframework.property.model.property.impl.PropertyPackageImpl#getTemporalProperty()
		 * @generated
		 */
		EClass TEMPORAL_PROPERTY = eINSTANCE.getTemporalProperty();

		/**
		 * The meta object literal for the '<em><b>Scope</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TEMPORAL_PROPERTY__SCOPE = eINSTANCE.getTemporalProperty_Scope();

		/**
		 * The meta object literal for the '{@link org.eclipse.gemoc.executionframework.property.model.property.impl.EPLPropertyImpl <em>EPL Property</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.gemoc.executionframework.property.model.property.impl.EPLPropertyImpl
		 * @see org.eclipse.gemoc.executionframework.property.model.property.impl.PropertyPackageImpl#getEPLProperty()
		 * @generated
		 */
		EClass EPL_PROPERTY = eINSTANCE.getEPLProperty();

		/**
		 * The meta object literal for the '<em><b>Statement</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute EPL_PROPERTY__STATEMENT = eINSTANCE.getEPLProperty_Statement();

		/**
		 * The meta object literal for the '<em><b>Patterns</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference EPL_PROPERTY__PATTERNS = eINSTANCE.getEPLProperty_Patterns();

		/**
		 * The meta object literal for the '<em><b>Initial Truth Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute EPL_PROPERTY__INITIAL_TRUTH_VALUE = eINSTANCE.getEPLProperty_InitialTruthValue();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute EPL_PROPERTY__NAME = eINSTANCE.getEPLProperty_Name();

		/**
		 * The meta object literal for the '{@link org.eclipse.gemoc.executionframework.property.model.property.impl.AbsenceImpl <em>Absence</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.gemoc.executionframework.property.model.property.impl.AbsenceImpl
		 * @see org.eclipse.gemoc.executionframework.property.model.property.impl.PropertyPackageImpl#getAbsence()
		 * @generated
		 */
		EClass ABSENCE = eINSTANCE.getAbsence();

		/**
		 * The meta object literal for the '{@link org.eclipse.gemoc.executionframework.property.model.property.impl.UniversalityImpl <em>Universality</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.gemoc.executionframework.property.model.property.impl.UniversalityImpl
		 * @see org.eclipse.gemoc.executionframework.property.model.property.impl.PropertyPackageImpl#getUniversality()
		 * @generated
		 */
		EClass UNIVERSALITY = eINSTANCE.getUniversality();

		/**
		 * The meta object literal for the '{@link org.eclipse.gemoc.executionframework.property.model.property.impl.ExistenceImpl <em>Existence</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.gemoc.executionframework.property.model.property.impl.ExistenceImpl
		 * @see org.eclipse.gemoc.executionframework.property.model.property.impl.PropertyPackageImpl#getExistence()
		 * @generated
		 */
		EClass EXISTENCE = eINSTANCE.getExistence();

		/**
		 * The meta object literal for the '{@link org.eclipse.gemoc.executionframework.property.model.property.impl.BoundedExistenceImpl <em>Bounded Existence</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.gemoc.executionframework.property.model.property.impl.BoundedExistenceImpl
		 * @see org.eclipse.gemoc.executionframework.property.model.property.impl.PropertyPackageImpl#getBoundedExistence()
		 * @generated
		 */
		EClass BOUNDED_EXISTENCE = eINSTANCE.getBoundedExistence();

		/**
		 * The meta object literal for the '<em><b>N</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute BOUNDED_EXISTENCE__N = eINSTANCE.getBoundedExistence_N();

		/**
		 * The meta object literal for the '{@link org.eclipse.gemoc.executionframework.property.model.property.impl.OrderedTemporalPropertyImpl <em>Ordered Temporal Property</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.gemoc.executionframework.property.model.property.impl.OrderedTemporalPropertyImpl
		 * @see org.eclipse.gemoc.executionframework.property.model.property.impl.PropertyPackageImpl#getOrderedTemporalProperty()
		 * @generated
		 */
		EClass ORDERED_TEMPORAL_PROPERTY = eINSTANCE.getOrderedTemporalProperty();

		/**
		 * The meta object literal for the '<em><b>Other Pattern</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ORDERED_TEMPORAL_PROPERTY__OTHER_PATTERN = eINSTANCE.getOrderedTemporalProperty_OtherPattern();

		/**
		 * The meta object literal for the '{@link org.eclipse.gemoc.executionframework.property.model.property.impl.ResponseImpl <em>Response</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.gemoc.executionframework.property.model.property.impl.ResponseImpl
		 * @see org.eclipse.gemoc.executionframework.property.model.property.impl.PropertyPackageImpl#getResponse()
		 * @generated
		 */
		EClass RESPONSE = eINSTANCE.getResponse();

		/**
		 * The meta object literal for the '{@link org.eclipse.gemoc.executionframework.property.model.property.impl.PrecedenceImpl <em>Precedence</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.gemoc.executionframework.property.model.property.impl.PrecedenceImpl
		 * @see org.eclipse.gemoc.executionframework.property.model.property.impl.PropertyPackageImpl#getPrecedence()
		 * @generated
		 */
		EClass PRECEDENCE = eINSTANCE.getPrecedence();

		/**
		 * The meta object literal for the '{@link org.eclipse.gemoc.executionframework.property.model.property.impl.ScopeImpl <em>Scope</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.gemoc.executionframework.property.model.property.impl.ScopeImpl
		 * @see org.eclipse.gemoc.executionframework.property.model.property.impl.PropertyPackageImpl#getScope()
		 * @generated
		 */
		EClass SCOPE = eINSTANCE.getScope();

		/**
		 * The meta object literal for the '{@link org.eclipse.gemoc.executionframework.property.model.property.impl.GloballyImpl <em>Globally</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.gemoc.executionframework.property.model.property.impl.GloballyImpl
		 * @see org.eclipse.gemoc.executionframework.property.model.property.impl.PropertyPackageImpl#getGlobally()
		 * @generated
		 */
		EClass GLOBALLY = eINSTANCE.getGlobally();

		/**
		 * The meta object literal for the '{@link org.eclipse.gemoc.executionframework.property.model.property.impl.LowerBoundedImpl <em>Lower Bounded</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.gemoc.executionframework.property.model.property.impl.LowerBoundedImpl
		 * @see org.eclipse.gemoc.executionframework.property.model.property.impl.PropertyPackageImpl#getLowerBounded()
		 * @generated
		 */
		EClass LOWER_BOUNDED = eINSTANCE.getLowerBounded();

		/**
		 * The meta object literal for the '<em><b>Lower Bound Pattern</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute LOWER_BOUNDED__LOWER_BOUND_PATTERN = eINSTANCE.getLowerBounded_LowerBoundPattern();

		/**
		 * The meta object literal for the '{@link org.eclipse.gemoc.executionframework.property.model.property.impl.UpperBoundedImpl <em>Upper Bounded</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.gemoc.executionframework.property.model.property.impl.UpperBoundedImpl
		 * @see org.eclipse.gemoc.executionframework.property.model.property.impl.PropertyPackageImpl#getUpperBounded()
		 * @generated
		 */
		EClass UPPER_BOUNDED = eINSTANCE.getUpperBounded();

		/**
		 * The meta object literal for the '<em><b>Upper Bound Pattern</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute UPPER_BOUNDED__UPPER_BOUND_PATTERN = eINSTANCE.getUpperBounded_UpperBoundPattern();

		/**
		 * The meta object literal for the '{@link org.eclipse.gemoc.executionframework.property.model.property.impl.BeforeImpl <em>Before</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.gemoc.executionframework.property.model.property.impl.BeforeImpl
		 * @see org.eclipse.gemoc.executionframework.property.model.property.impl.PropertyPackageImpl#getBefore()
		 * @generated
		 */
		EClass BEFORE = eINSTANCE.getBefore();

		/**
		 * The meta object literal for the '{@link org.eclipse.gemoc.executionframework.property.model.property.impl.BetweenImpl <em>Between</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.gemoc.executionframework.property.model.property.impl.BetweenImpl
		 * @see org.eclipse.gemoc.executionframework.property.model.property.impl.PropertyPackageImpl#getBetween()
		 * @generated
		 */
		EClass BETWEEN = eINSTANCE.getBetween();

		/**
		 * The meta object literal for the '{@link org.eclipse.gemoc.executionframework.property.model.property.impl.AfterUntilImpl <em>After Until</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.gemoc.executionframework.property.model.property.impl.AfterUntilImpl
		 * @see org.eclipse.gemoc.executionframework.property.model.property.impl.PropertyPackageImpl#getAfterUntil()
		 * @generated
		 */
		EClass AFTER_UNTIL = eINSTANCE.getAfterUntil();

		/**
		 * The meta object literal for the '{@link org.eclipse.gemoc.executionframework.property.model.property.impl.AfterImpl <em>After</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.gemoc.executionframework.property.model.property.impl.AfterImpl
		 * @see org.eclipse.gemoc.executionframework.property.model.property.impl.PropertyPackageImpl#getAfter()
		 * @generated
		 */
		EClass AFTER = eINSTANCE.getAfter();

		/**
		 * The meta object literal for the '{@link org.eclipse.gemoc.executionframework.property.model.property.Quantifier <em>Quantifier</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.gemoc.executionframework.property.model.property.Quantifier
		 * @see org.eclipse.gemoc.executionframework.property.model.property.impl.PropertyPackageImpl#getQuantifier()
		 * @generated
		 */
		EEnum QUANTIFIER = eINSTANCE.getQuantifier();

	}

} //PropertyPackage
