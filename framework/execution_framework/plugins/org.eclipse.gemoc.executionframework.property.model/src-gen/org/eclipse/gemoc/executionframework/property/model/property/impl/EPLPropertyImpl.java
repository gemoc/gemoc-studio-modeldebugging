/**
 */
package org.eclipse.gemoc.executionframework.property.model.property.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.emf.ecore.util.EObjectResolvingEList;

import org.eclipse.gemoc.executionframework.property.model.property.EPLProperty;
import org.eclipse.gemoc.executionframework.property.model.property.PropertyPackage;

import org.eclipse.viatra.query.patternlanguage.emf.vql.Pattern;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>EPL Property</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.gemoc.executionframework.property.model.property.impl.EPLPropertyImpl#getStatement <em>Statement</em>}</li>
 *   <li>{@link org.eclipse.gemoc.executionframework.property.model.property.impl.EPLPropertyImpl#getPatterns <em>Patterns</em>}</li>
 *   <li>{@link org.eclipse.gemoc.executionframework.property.model.property.impl.EPLPropertyImpl#isInitialTruthValue <em>Initial Truth Value</em>}</li>
 *   <li>{@link org.eclipse.gemoc.executionframework.property.model.property.impl.EPLPropertyImpl#getName <em>Name</em>}</li>
 * </ul>
 *
 * @generated
 */
public class EPLPropertyImpl extends MinimalEObjectImpl.Container implements EPLProperty {
	/**
	 * The default value of the '{@link #getStatement() <em>Statement</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getStatement()
	 * @generated
	 * @ordered
	 */
	protected static final String STATEMENT_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getStatement() <em>Statement</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getStatement()
	 * @generated
	 * @ordered
	 */
	protected String statement = STATEMENT_EDEFAULT;

	/**
	 * The cached value of the '{@link #getPatterns() <em>Patterns</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPatterns()
	 * @generated
	 * @ordered
	 */
	protected EList<Pattern> patterns;

	/**
	 * The default value of the '{@link #isInitialTruthValue() <em>Initial Truth Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isInitialTruthValue()
	 * @generated
	 * @ordered
	 */
	protected static final boolean INITIAL_TRUTH_VALUE_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isInitialTruthValue() <em>Initial Truth Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isInitialTruthValue()
	 * @generated
	 * @ordered
	 */
	protected boolean initialTruthValue = INITIAL_TRUTH_VALUE_EDEFAULT;

	/**
	 * The default value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected static final String NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected String name = NAME_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EPLPropertyImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return PropertyPackage.Literals.EPL_PROPERTY;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getStatement() {
		return statement;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setStatement(String newStatement) {
		String oldStatement = statement;
		statement = newStatement;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, PropertyPackage.EPL_PROPERTY__STATEMENT, oldStatement,
					statement));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Pattern> getPatterns() {
		if (patterns == null) {
			patterns = new EObjectResolvingEList<Pattern>(Pattern.class, this, PropertyPackage.EPL_PROPERTY__PATTERNS);
		}
		return patterns;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isInitialTruthValue() {
		return initialTruthValue;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setInitialTruthValue(boolean newInitialTruthValue) {
		boolean oldInitialTruthValue = initialTruthValue;
		initialTruthValue = newInitialTruthValue;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, PropertyPackage.EPL_PROPERTY__INITIAL_TRUTH_VALUE,
					oldInitialTruthValue, initialTruthValue));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getName() {
		return name;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setName(String newName) {
		String oldName = name;
		name = newName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, PropertyPackage.EPL_PROPERTY__NAME, oldName, name));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
		case PropertyPackage.EPL_PROPERTY__STATEMENT:
			return getStatement();
		case PropertyPackage.EPL_PROPERTY__PATTERNS:
			return getPatterns();
		case PropertyPackage.EPL_PROPERTY__INITIAL_TRUTH_VALUE:
			return isInitialTruthValue();
		case PropertyPackage.EPL_PROPERTY__NAME:
			return getName();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
		case PropertyPackage.EPL_PROPERTY__STATEMENT:
			setStatement((String) newValue);
			return;
		case PropertyPackage.EPL_PROPERTY__PATTERNS:
			getPatterns().clear();
			getPatterns().addAll((Collection<? extends Pattern>) newValue);
			return;
		case PropertyPackage.EPL_PROPERTY__INITIAL_TRUTH_VALUE:
			setInitialTruthValue((Boolean) newValue);
			return;
		case PropertyPackage.EPL_PROPERTY__NAME:
			setName((String) newValue);
			return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
		case PropertyPackage.EPL_PROPERTY__STATEMENT:
			setStatement(STATEMENT_EDEFAULT);
			return;
		case PropertyPackage.EPL_PROPERTY__PATTERNS:
			getPatterns().clear();
			return;
		case PropertyPackage.EPL_PROPERTY__INITIAL_TRUTH_VALUE:
			setInitialTruthValue(INITIAL_TRUTH_VALUE_EDEFAULT);
			return;
		case PropertyPackage.EPL_PROPERTY__NAME:
			setName(NAME_EDEFAULT);
			return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
		case PropertyPackage.EPL_PROPERTY__STATEMENT:
			return STATEMENT_EDEFAULT == null ? statement != null : !STATEMENT_EDEFAULT.equals(statement);
		case PropertyPackage.EPL_PROPERTY__PATTERNS:
			return patterns != null && !patterns.isEmpty();
		case PropertyPackage.EPL_PROPERTY__INITIAL_TRUTH_VALUE:
			return initialTruthValue != INITIAL_TRUTH_VALUE_EDEFAULT;
		case PropertyPackage.EPL_PROPERTY__NAME:
			return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy())
			return super.toString();

		StringBuilder result = new StringBuilder(super.toString());
		result.append(" (statement: ");
		result.append(statement);
		result.append(", initialTruthValue: ");
		result.append(initialTruthValue);
		result.append(", name: ");
		result.append(name);
		result.append(')');
		return result.toString();
	}

} //EPLPropertyImpl
