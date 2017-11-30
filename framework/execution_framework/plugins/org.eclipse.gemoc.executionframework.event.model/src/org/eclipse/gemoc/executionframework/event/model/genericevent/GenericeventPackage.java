/**
 */
package org.eclipse.gemoc.executionframework.event.model.genericevent;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

import org.eclipse.gemoc.executionframework.event.model.event.EventPackage;

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
 * @see org.eclipse.gemoc.executionframework.event.model.genericevent.GenericeventFactory
 * @model kind="package"
 * @generated
 */
public interface GenericeventPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "genericevent";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://www.eclipse.org/gemoc/event/generic";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "genericevent";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	GenericeventPackage eINSTANCE = org.eclipse.gemoc.executionframework.event.model.genericevent.impl.GenericeventPackageImpl.init();

	/**
	 * The meta object id for the '{@link org.eclipse.gemoc.executionframework.event.model.genericevent.impl.GenericEventImpl <em>Generic Event</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.gemoc.executionframework.event.model.genericevent.impl.GenericEventImpl
	 * @see org.eclipse.gemoc.executionframework.event.model.genericevent.impl.GenericeventPackageImpl#getGenericEvent()
	 * @generated
	 */
	int GENERIC_EVENT = 0;

	/**
	 * The feature id for the '<em><b>Rule ID</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GENERIC_EVENT__RULE_ID = EventPackage.EVENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Values</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GENERIC_EVENT__VALUES = EventPackage.EVENT_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Generic Event</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GENERIC_EVENT_FEATURE_COUNT = EventPackage.EVENT_FEATURE_COUNT + 2;

	/**
	 * The number of operations of the '<em>Generic Event</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GENERIC_EVENT_OPERATION_COUNT = EventPackage.EVENT_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.gemoc.executionframework.event.model.genericevent.impl.RuleIDImpl <em>Rule ID</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.gemoc.executionframework.event.model.genericevent.impl.RuleIDImpl
	 * @see org.eclipse.gemoc.executionframework.event.model.genericevent.impl.GenericeventPackageImpl#getRuleID()
	 * @generated
	 */
	int RULE_ID = 1;

	/**
	 * The number of structural features of the '<em>Rule ID</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_ID_FEATURE_COUNT = 0;

	/**
	 * The number of operations of the '<em>Rule ID</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_ID_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.eclipse.gemoc.executionframework.event.model.genericevent.impl.RuleFQNImpl <em>Rule FQN</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.gemoc.executionframework.event.model.genericevent.impl.RuleFQNImpl
	 * @see org.eclipse.gemoc.executionframework.event.model.genericevent.impl.GenericeventPackageImpl#getRuleFQN()
	 * @generated
	 */
	int RULE_FQN = 2;

	/**
	 * The feature id for the '<em><b>Fqn</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_FQN__FQN = RULE_ID_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Rule FQN</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_FQN_FEATURE_COUNT = RULE_ID_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>Rule FQN</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_FQN_OPERATION_COUNT = RULE_ID_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.gemoc.executionframework.event.model.genericevent.impl.RuleRefImpl <em>Rule Ref</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.gemoc.executionframework.event.model.genericevent.impl.RuleRefImpl
	 * @see org.eclipse.gemoc.executionframework.event.model.genericevent.impl.GenericeventPackageImpl#getRuleRef()
	 * @generated
	 */
	int RULE_REF = 3;

	/**
	 * The number of structural features of the '<em>Rule Ref</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_REF_FEATURE_COUNT = RULE_ID_FEATURE_COUNT + 0;

	/**
	 * The number of operations of the '<em>Rule Ref</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_REF_OPERATION_COUNT = RULE_ID_OPERATION_COUNT + 0;


	/**
	 * Returns the meta object for class '{@link org.eclipse.gemoc.executionframework.event.model.genericevent.GenericEvent <em>Generic Event</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Generic Event</em>'.
	 * @see org.eclipse.gemoc.executionframework.event.model.genericevent.GenericEvent
	 * @generated
	 */
	EClass getGenericEvent();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.gemoc.executionframework.event.model.genericevent.GenericEvent#getRuleID <em>Rule ID</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Rule ID</em>'.
	 * @see org.eclipse.gemoc.executionframework.event.model.genericevent.GenericEvent#getRuleID()
	 * @see #getGenericEvent()
	 * @generated
	 */
	EReference getGenericEvent_RuleID();

	/**
	 * Returns the meta object for the reference list '{@link org.eclipse.gemoc.executionframework.event.model.genericevent.GenericEvent#getValues <em>Values</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Values</em>'.
	 * @see org.eclipse.gemoc.executionframework.event.model.genericevent.GenericEvent#getValues()
	 * @see #getGenericEvent()
	 * @generated
	 */
	EReference getGenericEvent_Values();

	/**
	 * Returns the meta object for class '{@link org.eclipse.gemoc.executionframework.event.model.genericevent.RuleID <em>Rule ID</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Rule ID</em>'.
	 * @see org.eclipse.gemoc.executionframework.event.model.genericevent.RuleID
	 * @generated
	 */
	EClass getRuleID();

	/**
	 * Returns the meta object for class '{@link org.eclipse.gemoc.executionframework.event.model.genericevent.RuleFQN <em>Rule FQN</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Rule FQN</em>'.
	 * @see org.eclipse.gemoc.executionframework.event.model.genericevent.RuleFQN
	 * @generated
	 */
	EClass getRuleFQN();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.gemoc.executionframework.event.model.genericevent.RuleFQN#getFqn <em>Fqn</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Fqn</em>'.
	 * @see org.eclipse.gemoc.executionframework.event.model.genericevent.RuleFQN#getFqn()
	 * @see #getRuleFQN()
	 * @generated
	 */
	EAttribute getRuleFQN_Fqn();

	/**
	 * Returns the meta object for class '{@link org.eclipse.gemoc.executionframework.event.model.genericevent.RuleRef <em>Rule Ref</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Rule Ref</em>'.
	 * @see org.eclipse.gemoc.executionframework.event.model.genericevent.RuleRef
	 * @generated
	 */
	EClass getRuleRef();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	GenericeventFactory getGenericeventFactory();

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
		 * The meta object literal for the '{@link org.eclipse.gemoc.executionframework.event.model.genericevent.impl.GenericEventImpl <em>Generic Event</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.gemoc.executionframework.event.model.genericevent.impl.GenericEventImpl
		 * @see org.eclipse.gemoc.executionframework.event.model.genericevent.impl.GenericeventPackageImpl#getGenericEvent()
		 * @generated
		 */
		EClass GENERIC_EVENT = eINSTANCE.getGenericEvent();

		/**
		 * The meta object literal for the '<em><b>Rule ID</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference GENERIC_EVENT__RULE_ID = eINSTANCE.getGenericEvent_RuleID();

		/**
		 * The meta object literal for the '<em><b>Values</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference GENERIC_EVENT__VALUES = eINSTANCE.getGenericEvent_Values();

		/**
		 * The meta object literal for the '{@link org.eclipse.gemoc.executionframework.event.model.genericevent.impl.RuleIDImpl <em>Rule ID</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.gemoc.executionframework.event.model.genericevent.impl.RuleIDImpl
		 * @see org.eclipse.gemoc.executionframework.event.model.genericevent.impl.GenericeventPackageImpl#getRuleID()
		 * @generated
		 */
		EClass RULE_ID = eINSTANCE.getRuleID();

		/**
		 * The meta object literal for the '{@link org.eclipse.gemoc.executionframework.event.model.genericevent.impl.RuleFQNImpl <em>Rule FQN</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.gemoc.executionframework.event.model.genericevent.impl.RuleFQNImpl
		 * @see org.eclipse.gemoc.executionframework.event.model.genericevent.impl.GenericeventPackageImpl#getRuleFQN()
		 * @generated
		 */
		EClass RULE_FQN = eINSTANCE.getRuleFQN();

		/**
		 * The meta object literal for the '<em><b>Fqn</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute RULE_FQN__FQN = eINSTANCE.getRuleFQN_Fqn();

		/**
		 * The meta object literal for the '{@link org.eclipse.gemoc.executionframework.event.model.genericevent.impl.RuleRefImpl <em>Rule Ref</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.gemoc.executionframework.event.model.genericevent.impl.RuleRefImpl
		 * @see org.eclipse.gemoc.executionframework.event.model.genericevent.impl.GenericeventPackageImpl#getRuleRef()
		 * @generated
		 */
		EClass RULE_REF = eINSTANCE.getRuleRef();

	}

} //GenericeventPackage
