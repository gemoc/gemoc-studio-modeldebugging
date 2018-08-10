/**
 * generated by Xtext 2.12.0
 */
package org.eclipse.gemoc.xdsmlframework.behavioralinterface.behavioralInterface;

import org.eclipse.emf.ecore.EAttribute;
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
 *   <li>each enum,</li>
 *   <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * @see org.eclipse.gemoc.xdsmlframework.behavioralinterface.behavioralInterface.BehavioralInterfaceFactory
 * @model kind="package"
 * @generated
 */
public interface BehavioralInterfacePackage extends EPackage
{
  /**
   * The package name.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  String eNAME = "behavioralInterface";

  /**
   * The package namespace URI.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  String eNS_URI = "http://www.eclipse.org/gemoc/xdsmlframework/behavioralinterface/BehavioralInterface";

  /**
   * The package namespace name.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  String eNS_PREFIX = "behavioralInterface";

  /**
   * The singleton instance of the package.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  BehavioralInterfacePackage eINSTANCE = org.eclipse.gemoc.xdsmlframework.behavioralinterface.behavioralInterface.impl.BehavioralInterfacePackageImpl.init();

  /**
   * The meta object id for the '{@link org.eclipse.gemoc.xdsmlframework.behavioralinterface.behavioralInterface.impl.BehavioralInterfaceImpl <em>Behavioral Interface</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see org.eclipse.gemoc.xdsmlframework.behavioralinterface.behavioralInterface.impl.BehavioralInterfaceImpl
   * @see org.eclipse.gemoc.xdsmlframework.behavioralinterface.behavioralInterface.impl.BehavioralInterfacePackageImpl#getBehavioralInterface()
   * @generated
   */
  int BEHAVIORAL_INTERFACE = 0;

  /**
   * The feature id for the '<em><b>Dsl Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int BEHAVIORAL_INTERFACE__DSL_NAME = 0;

  /**
   * The feature id for the '<em><b>Events</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int BEHAVIORAL_INTERFACE__EVENTS = 1;

  /**
   * The number of structural features of the '<em>Behavioral Interface</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int BEHAVIORAL_INTERFACE_FEATURE_COUNT = 2;

  /**
   * The meta object id for the '{@link org.eclipse.gemoc.xdsmlframework.behavioralinterface.behavioralInterface.impl.EventImpl <em>Event</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see org.eclipse.gemoc.xdsmlframework.behavioralinterface.behavioralInterface.impl.EventImpl
   * @see org.eclipse.gemoc.xdsmlframework.behavioralinterface.behavioralInterface.impl.BehavioralInterfacePackageImpl#getEvent()
   * @generated
   */
  int EVENT = 1;

  /**
   * The feature id for the '<em><b>Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int EVENT__NAME = 0;

  /**
   * The feature id for the '<em><b>Rule Declaring Type</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int EVENT__RULE_DECLARING_TYPE = 1;

  /**
   * The feature id for the '<em><b>Rule</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int EVENT__RULE = 2;

  /**
   * The feature id for the '<em><b>Params</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int EVENT__PARAMS = 3;

  /**
   * The number of structural features of the '<em>Event</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int EVENT_FEATURE_COUNT = 4;

  /**
   * The meta object id for the '{@link org.eclipse.gemoc.xdsmlframework.behavioralinterface.behavioralInterface.impl.InputEventImpl <em>Input Event</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see org.eclipse.gemoc.xdsmlframework.behavioralinterface.behavioralInterface.impl.InputEventImpl
   * @see org.eclipse.gemoc.xdsmlframework.behavioralinterface.behavioralInterface.impl.BehavioralInterfacePackageImpl#getInputEvent()
   * @generated
   */
  int INPUT_EVENT = 2;

  /**
   * The feature id for the '<em><b>Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int INPUT_EVENT__NAME = EVENT__NAME;

  /**
   * The feature id for the '<em><b>Rule Declaring Type</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int INPUT_EVENT__RULE_DECLARING_TYPE = EVENT__RULE_DECLARING_TYPE;

  /**
   * The feature id for the '<em><b>Rule</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int INPUT_EVENT__RULE = EVENT__RULE;

  /**
   * The feature id for the '<em><b>Params</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int INPUT_EVENT__PARAMS = EVENT__PARAMS;

  /**
   * The feature id for the '<em><b>Interruptible</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int INPUT_EVENT__INTERRUPTIBLE = EVENT_FEATURE_COUNT + 0;

  /**
   * The feature id for the '<em><b>Precondition Declaring Type</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int INPUT_EVENT__PRECONDITION_DECLARING_TYPE = EVENT_FEATURE_COUNT + 1;

  /**
   * The feature id for the '<em><b>Precondition</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int INPUT_EVENT__PRECONDITION = EVENT_FEATURE_COUNT + 2;

  /**
   * The number of structural features of the '<em>Input Event</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int INPUT_EVENT_FEATURE_COUNT = EVENT_FEATURE_COUNT + 3;

  /**
   * The meta object id for the '{@link org.eclipse.gemoc.xdsmlframework.behavioralinterface.behavioralInterface.impl.OutputEventImpl <em>Output Event</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see org.eclipse.gemoc.xdsmlframework.behavioralinterface.behavioralInterface.impl.OutputEventImpl
   * @see org.eclipse.gemoc.xdsmlframework.behavioralinterface.behavioralInterface.impl.BehavioralInterfacePackageImpl#getOutputEvent()
   * @generated
   */
  int OUTPUT_EVENT = 3;

  /**
   * The feature id for the '<em><b>Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int OUTPUT_EVENT__NAME = EVENT__NAME;

  /**
   * The feature id for the '<em><b>Rule Declaring Type</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int OUTPUT_EVENT__RULE_DECLARING_TYPE = EVENT__RULE_DECLARING_TYPE;

  /**
   * The feature id for the '<em><b>Rule</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int OUTPUT_EVENT__RULE = EVENT__RULE;

  /**
   * The feature id for the '<em><b>Params</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int OUTPUT_EVENT__PARAMS = EVENT__PARAMS;

  /**
   * The number of structural features of the '<em>Output Event</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int OUTPUT_EVENT_FEATURE_COUNT = EVENT_FEATURE_COUNT + 0;

  /**
   * The meta object id for the '{@link org.eclipse.gemoc.xdsmlframework.behavioralinterface.behavioralInterface.impl.EventParameterImpl <em>Event Parameter</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see org.eclipse.gemoc.xdsmlframework.behavioralinterface.behavioralInterface.impl.EventParameterImpl
   * @see org.eclipse.gemoc.xdsmlframework.behavioralinterface.behavioralInterface.impl.BehavioralInterfacePackageImpl#getEventParameter()
   * @generated
   */
  int EVENT_PARAMETER = 4;

  /**
   * The feature id for the '<em><b>Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int EVENT_PARAMETER__NAME = 0;

  /**
   * The feature id for the '<em><b>Type</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int EVENT_PARAMETER__TYPE = 1;

  /**
   * The feature id for the '<em><b>Many</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int EVENT_PARAMETER__MANY = 2;

  /**
   * The number of structural features of the '<em>Event Parameter</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int EVENT_PARAMETER_FEATURE_COUNT = 3;


  /**
   * Returns the meta object for class '{@link org.eclipse.gemoc.xdsmlframework.behavioralinterface.behavioralInterface.BehavioralInterface <em>Behavioral Interface</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Behavioral Interface</em>'.
   * @see org.eclipse.gemoc.xdsmlframework.behavioralinterface.behavioralInterface.BehavioralInterface
   * @generated
   */
  EClass getBehavioralInterface();

  /**
   * Returns the meta object for the attribute '{@link org.eclipse.gemoc.xdsmlframework.behavioralinterface.behavioralInterface.BehavioralInterface#getDslName <em>Dsl Name</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Dsl Name</em>'.
   * @see org.eclipse.gemoc.xdsmlframework.behavioralinterface.behavioralInterface.BehavioralInterface#getDslName()
   * @see #getBehavioralInterface()
   * @generated
   */
  EAttribute getBehavioralInterface_DslName();

  /**
   * Returns the meta object for the containment reference list '{@link org.eclipse.gemoc.xdsmlframework.behavioralinterface.behavioralInterface.BehavioralInterface#getEvents <em>Events</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Events</em>'.
   * @see org.eclipse.gemoc.xdsmlframework.behavioralinterface.behavioralInterface.BehavioralInterface#getEvents()
   * @see #getBehavioralInterface()
   * @generated
   */
  EReference getBehavioralInterface_Events();

  /**
   * Returns the meta object for class '{@link org.eclipse.gemoc.xdsmlframework.behavioralinterface.behavioralInterface.Event <em>Event</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Event</em>'.
   * @see org.eclipse.gemoc.xdsmlframework.behavioralinterface.behavioralInterface.Event
   * @generated
   */
  EClass getEvent();

  /**
   * Returns the meta object for the attribute '{@link org.eclipse.gemoc.xdsmlframework.behavioralinterface.behavioralInterface.Event#getName <em>Name</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Name</em>'.
   * @see org.eclipse.gemoc.xdsmlframework.behavioralinterface.behavioralInterface.Event#getName()
   * @see #getEvent()
   * @generated
   */
  EAttribute getEvent_Name();

  /**
   * Returns the meta object for the attribute '{@link org.eclipse.gemoc.xdsmlframework.behavioralinterface.behavioralInterface.Event#getRuleDeclaringType <em>Rule Declaring Type</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Rule Declaring Type</em>'.
   * @see org.eclipse.gemoc.xdsmlframework.behavioralinterface.behavioralInterface.Event#getRuleDeclaringType()
   * @see #getEvent()
   * @generated
   */
  EAttribute getEvent_RuleDeclaringType();

  /**
   * Returns the meta object for the attribute '{@link org.eclipse.gemoc.xdsmlframework.behavioralinterface.behavioralInterface.Event#getRule <em>Rule</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Rule</em>'.
   * @see org.eclipse.gemoc.xdsmlframework.behavioralinterface.behavioralInterface.Event#getRule()
   * @see #getEvent()
   * @generated
   */
  EAttribute getEvent_Rule();

  /**
   * Returns the meta object for the containment reference list '{@link org.eclipse.gemoc.xdsmlframework.behavioralinterface.behavioralInterface.Event#getParams <em>Params</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Params</em>'.
   * @see org.eclipse.gemoc.xdsmlframework.behavioralinterface.behavioralInterface.Event#getParams()
   * @see #getEvent()
   * @generated
   */
  EReference getEvent_Params();

  /**
   * Returns the meta object for class '{@link org.eclipse.gemoc.xdsmlframework.behavioralinterface.behavioralInterface.InputEvent <em>Input Event</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Input Event</em>'.
   * @see org.eclipse.gemoc.xdsmlframework.behavioralinterface.behavioralInterface.InputEvent
   * @generated
   */
  EClass getInputEvent();

  /**
   * Returns the meta object for the attribute '{@link org.eclipse.gemoc.xdsmlframework.behavioralinterface.behavioralInterface.InputEvent#isInterruptible <em>Interruptible</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Interruptible</em>'.
   * @see org.eclipse.gemoc.xdsmlframework.behavioralinterface.behavioralInterface.InputEvent#isInterruptible()
   * @see #getInputEvent()
   * @generated
   */
  EAttribute getInputEvent_Interruptible();

  /**
   * Returns the meta object for the attribute '{@link org.eclipse.gemoc.xdsmlframework.behavioralinterface.behavioralInterface.InputEvent#getPreconditionDeclaringType <em>Precondition Declaring Type</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Precondition Declaring Type</em>'.
   * @see org.eclipse.gemoc.xdsmlframework.behavioralinterface.behavioralInterface.InputEvent#getPreconditionDeclaringType()
   * @see #getInputEvent()
   * @generated
   */
  EAttribute getInputEvent_PreconditionDeclaringType();

  /**
   * Returns the meta object for the attribute '{@link org.eclipse.gemoc.xdsmlframework.behavioralinterface.behavioralInterface.InputEvent#getPrecondition <em>Precondition</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Precondition</em>'.
   * @see org.eclipse.gemoc.xdsmlframework.behavioralinterface.behavioralInterface.InputEvent#getPrecondition()
   * @see #getInputEvent()
   * @generated
   */
  EAttribute getInputEvent_Precondition();

  /**
   * Returns the meta object for class '{@link org.eclipse.gemoc.xdsmlframework.behavioralinterface.behavioralInterface.OutputEvent <em>Output Event</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Output Event</em>'.
   * @see org.eclipse.gemoc.xdsmlframework.behavioralinterface.behavioralInterface.OutputEvent
   * @generated
   */
  EClass getOutputEvent();

  /**
   * Returns the meta object for class '{@link org.eclipse.gemoc.xdsmlframework.behavioralinterface.behavioralInterface.EventParameter <em>Event Parameter</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Event Parameter</em>'.
   * @see org.eclipse.gemoc.xdsmlframework.behavioralinterface.behavioralInterface.EventParameter
   * @generated
   */
  EClass getEventParameter();

  /**
   * Returns the meta object for the attribute '{@link org.eclipse.gemoc.xdsmlframework.behavioralinterface.behavioralInterface.EventParameter#getName <em>Name</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Name</em>'.
   * @see org.eclipse.gemoc.xdsmlframework.behavioralinterface.behavioralInterface.EventParameter#getName()
   * @see #getEventParameter()
   * @generated
   */
  EAttribute getEventParameter_Name();

  /**
   * Returns the meta object for the attribute '{@link org.eclipse.gemoc.xdsmlframework.behavioralinterface.behavioralInterface.EventParameter#getType <em>Type</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Type</em>'.
   * @see org.eclipse.gemoc.xdsmlframework.behavioralinterface.behavioralInterface.EventParameter#getType()
   * @see #getEventParameter()
   * @generated
   */
  EAttribute getEventParameter_Type();

  /**
   * Returns the meta object for the attribute '{@link org.eclipse.gemoc.xdsmlframework.behavioralinterface.behavioralInterface.EventParameter#isMany <em>Many</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Many</em>'.
   * @see org.eclipse.gemoc.xdsmlframework.behavioralinterface.behavioralInterface.EventParameter#isMany()
   * @see #getEventParameter()
   * @generated
   */
  EAttribute getEventParameter_Many();

  /**
   * Returns the factory that creates the instances of the model.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the factory that creates the instances of the model.
   * @generated
   */
  BehavioralInterfaceFactory getBehavioralInterfaceFactory();

  /**
   * <!-- begin-user-doc -->
   * Defines literals for the meta objects that represent
   * <ul>
   *   <li>each class,</li>
   *   <li>each feature of each class,</li>
   *   <li>each enum,</li>
   *   <li>and each data type</li>
   * </ul>
   * <!-- end-user-doc -->
   * @generated
   */
  interface Literals
  {
    /**
     * The meta object literal for the '{@link org.eclipse.gemoc.xdsmlframework.behavioralinterface.behavioralInterface.impl.BehavioralInterfaceImpl <em>Behavioral Interface</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.eclipse.gemoc.xdsmlframework.behavioralinterface.behavioralInterface.impl.BehavioralInterfaceImpl
     * @see org.eclipse.gemoc.xdsmlframework.behavioralinterface.behavioralInterface.impl.BehavioralInterfacePackageImpl#getBehavioralInterface()
     * @generated
     */
    EClass BEHAVIORAL_INTERFACE = eINSTANCE.getBehavioralInterface();

    /**
     * The meta object literal for the '<em><b>Dsl Name</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute BEHAVIORAL_INTERFACE__DSL_NAME = eINSTANCE.getBehavioralInterface_DslName();

    /**
     * The meta object literal for the '<em><b>Events</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference BEHAVIORAL_INTERFACE__EVENTS = eINSTANCE.getBehavioralInterface_Events();

    /**
     * The meta object literal for the '{@link org.eclipse.gemoc.xdsmlframework.behavioralinterface.behavioralInterface.impl.EventImpl <em>Event</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.eclipse.gemoc.xdsmlframework.behavioralinterface.behavioralInterface.impl.EventImpl
     * @see org.eclipse.gemoc.xdsmlframework.behavioralinterface.behavioralInterface.impl.BehavioralInterfacePackageImpl#getEvent()
     * @generated
     */
    EClass EVENT = eINSTANCE.getEvent();

    /**
     * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute EVENT__NAME = eINSTANCE.getEvent_Name();

    /**
     * The meta object literal for the '<em><b>Rule Declaring Type</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute EVENT__RULE_DECLARING_TYPE = eINSTANCE.getEvent_RuleDeclaringType();

    /**
     * The meta object literal for the '<em><b>Rule</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute EVENT__RULE = eINSTANCE.getEvent_Rule();

    /**
     * The meta object literal for the '<em><b>Params</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference EVENT__PARAMS = eINSTANCE.getEvent_Params();

    /**
     * The meta object literal for the '{@link org.eclipse.gemoc.xdsmlframework.behavioralinterface.behavioralInterface.impl.InputEventImpl <em>Input Event</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.eclipse.gemoc.xdsmlframework.behavioralinterface.behavioralInterface.impl.InputEventImpl
     * @see org.eclipse.gemoc.xdsmlframework.behavioralinterface.behavioralInterface.impl.BehavioralInterfacePackageImpl#getInputEvent()
     * @generated
     */
    EClass INPUT_EVENT = eINSTANCE.getInputEvent();

    /**
     * The meta object literal for the '<em><b>Interruptible</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute INPUT_EVENT__INTERRUPTIBLE = eINSTANCE.getInputEvent_Interruptible();

    /**
     * The meta object literal for the '<em><b>Precondition Declaring Type</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute INPUT_EVENT__PRECONDITION_DECLARING_TYPE = eINSTANCE.getInputEvent_PreconditionDeclaringType();

    /**
     * The meta object literal for the '<em><b>Precondition</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute INPUT_EVENT__PRECONDITION = eINSTANCE.getInputEvent_Precondition();

    /**
     * The meta object literal for the '{@link org.eclipse.gemoc.xdsmlframework.behavioralinterface.behavioralInterface.impl.OutputEventImpl <em>Output Event</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.eclipse.gemoc.xdsmlframework.behavioralinterface.behavioralInterface.impl.OutputEventImpl
     * @see org.eclipse.gemoc.xdsmlframework.behavioralinterface.behavioralInterface.impl.BehavioralInterfacePackageImpl#getOutputEvent()
     * @generated
     */
    EClass OUTPUT_EVENT = eINSTANCE.getOutputEvent();

    /**
     * The meta object literal for the '{@link org.eclipse.gemoc.xdsmlframework.behavioralinterface.behavioralInterface.impl.EventParameterImpl <em>Event Parameter</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.eclipse.gemoc.xdsmlframework.behavioralinterface.behavioralInterface.impl.EventParameterImpl
     * @see org.eclipse.gemoc.xdsmlframework.behavioralinterface.behavioralInterface.impl.BehavioralInterfacePackageImpl#getEventParameter()
     * @generated
     */
    EClass EVENT_PARAMETER = eINSTANCE.getEventParameter();

    /**
     * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute EVENT_PARAMETER__NAME = eINSTANCE.getEventParameter_Name();

    /**
     * The meta object literal for the '<em><b>Type</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute EVENT_PARAMETER__TYPE = eINSTANCE.getEventParameter_Type();

    /**
     * The meta object literal for the '<em><b>Many</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute EVENT_PARAMETER__MANY = eINSTANCE.getEventParameter_Many();

  }

} //BehavioralInterfacePackage
