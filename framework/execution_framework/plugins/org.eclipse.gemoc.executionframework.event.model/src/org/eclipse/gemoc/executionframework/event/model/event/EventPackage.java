/**
 */
package org.eclipse.gemoc.executionframework.event.model.event;

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
 * @see org.eclipse.gemoc.executionframework.event.model.event.EventFactory
 * @model kind="package"
 * @generated
 */
public interface EventPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "event";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://www.eclipse.org/gemoc/event";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "event";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	EventPackage eINSTANCE = org.eclipse.gemoc.executionframework.event.model.event.impl.EventPackageImpl.init();

	/**
	 * The meta object id for the '{@link org.eclipse.gemoc.executionframework.event.model.event.impl.EventOccurrenceImpl <em>Occurrence</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.gemoc.executionframework.event.model.event.impl.EventOccurrenceImpl
	 * @see org.eclipse.gemoc.executionframework.event.model.event.impl.EventPackageImpl#getEventOccurrence()
	 * @generated
	 */
	int EVENT_OCCURRENCE = 0;

	/**
	 * The feature id for the '<em><b>Event</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EVENT_OCCURRENCE__EVENT = 0;

	/**
	 * The number of structural features of the '<em>Occurrence</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EVENT_OCCURRENCE_FEATURE_COUNT = 1;

	/**
	 * The number of operations of the '<em>Occurrence</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EVENT_OCCURRENCE_OPERATION_COUNT = 0;


	/**
	 * The meta object id for the '{@link org.eclipse.gemoc.executionframework.event.model.event.impl.StopEventOccurrenceImpl <em>Stop Event Occurrence</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.gemoc.executionframework.event.model.event.impl.StopEventOccurrenceImpl
	 * @see org.eclipse.gemoc.executionframework.event.model.event.impl.EventPackageImpl#getStopEventOccurrence()
	 * @generated
	 */
	int STOP_EVENT_OCCURRENCE = 1;

	/**
	 * The feature id for the '<em><b>Event</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STOP_EVENT_OCCURRENCE__EVENT = EVENT_OCCURRENCE__EVENT;

	/**
	 * The number of structural features of the '<em>Stop Event Occurrence</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STOP_EVENT_OCCURRENCE_FEATURE_COUNT = EVENT_OCCURRENCE_FEATURE_COUNT + 0;

	/**
	 * The number of operations of the '<em>Stop Event Occurrence</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STOP_EVENT_OCCURRENCE_OPERATION_COUNT = EVENT_OCCURRENCE_OPERATION_COUNT + 0;


	/**
	 * Returns the meta object for class '{@link org.eclipse.gemoc.executionframework.event.model.event.EventOccurrence <em>Occurrence</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Occurrence</em>'.
	 * @see org.eclipse.gemoc.executionframework.event.model.event.EventOccurrence
	 * @generated
	 */
	EClass getEventOccurrence();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.gemoc.executionframework.event.model.event.EventOccurrence#getEvent <em>Event</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Event</em>'.
	 * @see org.eclipse.gemoc.executionframework.event.model.event.EventOccurrence#getEvent()
	 * @see #getEventOccurrence()
	 * @generated
	 */
	EReference getEventOccurrence_Event();

	/**
	 * Returns the meta object for class '{@link org.eclipse.gemoc.executionframework.event.model.event.StopEventOccurrence <em>Stop Event Occurrence</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Stop Event Occurrence</em>'.
	 * @see org.eclipse.gemoc.executionframework.event.model.event.StopEventOccurrence
	 * @generated
	 */
	EClass getStopEventOccurrence();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	EventFactory getEventFactory();

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
		 * The meta object literal for the '{@link org.eclipse.gemoc.executionframework.event.model.event.impl.EventOccurrenceImpl <em>Occurrence</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.gemoc.executionframework.event.model.event.impl.EventOccurrenceImpl
		 * @see org.eclipse.gemoc.executionframework.event.model.event.impl.EventPackageImpl#getEventOccurrence()
		 * @generated
		 */
		EClass EVENT_OCCURRENCE = eINSTANCE.getEventOccurrence();

		/**
		 * The meta object literal for the '<em><b>Event</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference EVENT_OCCURRENCE__EVENT = eINSTANCE.getEventOccurrence_Event();

		/**
		 * The meta object literal for the '{@link org.eclipse.gemoc.executionframework.event.model.event.impl.StopEventOccurrenceImpl <em>Stop Event Occurrence</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.gemoc.executionframework.event.model.event.impl.StopEventOccurrenceImpl
		 * @see org.eclipse.gemoc.executionframework.event.model.event.impl.EventPackageImpl#getStopEventOccurrence()
		 * @generated
		 */
		EClass STOP_EVENT_OCCURRENCE = eINSTANCE.getStopEventOccurrence();

	}

} //EventPackage
