/**
 */
package org.eclipse.gemoc.executionframework.event.model.genericevent.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EFactoryImpl;

import org.eclipse.emf.ecore.plugin.EcorePlugin;

import org.eclipse.gemoc.executionframework.event.model.genericevent.*;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class GenericeventFactoryImpl extends EFactoryImpl implements GenericeventFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static GenericeventFactory init() {
		try {
			GenericeventFactory theGenericeventFactory = (GenericeventFactory)EPackage.Registry.INSTANCE.getEFactory(GenericeventPackage.eNS_URI);
			if (theGenericeventFactory != null) {
				return theGenericeventFactory;
			}
		}
		catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new GenericeventFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public GenericeventFactoryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EObject create(EClass eClass) {
		switch (eClass.getClassifierID()) {
			case GenericeventPackage.GENERIC_EVENT: return createGenericEvent();
			case GenericeventPackage.RULE_ID: return createRuleID();
			case GenericeventPackage.RULE_FQN: return createRuleFQN();
			case GenericeventPackage.RULE_REF: return createRuleRef();
			default:
				throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public GenericEvent createGenericEvent() {
		GenericEventImpl genericEvent = new GenericEventImpl();
		return genericEvent;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public RuleID createRuleID() {
		RuleIDImpl ruleID = new RuleIDImpl();
		return ruleID;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public RuleFQN createRuleFQN() {
		RuleFQNImpl ruleFQN = new RuleFQNImpl();
		return ruleFQN;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public RuleRef createRuleRef() {
		RuleRefImpl ruleRef = new RuleRefImpl();
		return ruleRef;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public GenericeventPackage getGenericeventPackage() {
		return (GenericeventPackage)getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static GenericeventPackage getPackage() {
		return GenericeventPackage.eINSTANCE;
	}

} //GenericeventFactoryImpl
