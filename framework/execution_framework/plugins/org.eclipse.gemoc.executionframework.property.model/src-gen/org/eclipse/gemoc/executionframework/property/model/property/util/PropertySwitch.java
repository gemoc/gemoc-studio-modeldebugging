/**
 */
package org.eclipse.gemoc.executionframework.property.model.property.util;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.util.Switch;

import org.eclipse.gemoc.executionframework.property.model.property.*;

/**
 * <!-- begin-user-doc -->
 * The <b>Switch</b> for the model's inheritance hierarchy.
 * It supports the call {@link #doSwitch(EObject) doSwitch(object)}
 * to invoke the <code>caseXXX</code> method for each class of the model,
 * starting with the actual class of the object
 * and proceeding up the inheritance hierarchy
 * until a non-null result is returned,
 * which is the result of the switch.
 * <!-- end-user-doc -->
 * @see org.eclipse.gemoc.executionframework.property.model.property.PropertyPackage
 * @generated
 */
public class PropertySwitch<T> extends Switch<T> {
	/**
	 * The cached model package
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static PropertyPackage modelPackage;

	/**
	 * Creates an instance of the switch.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PropertySwitch() {
		if (modelPackage == null) {
			modelPackage = PropertyPackage.eINSTANCE;
		}
	}

	/**
	 * Checks whether this is a switch for the given package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param ePackage the package in question.
	 * @return whether this is a switch for the given package.
	 * @generated
	 */
	@Override
	protected boolean isSwitchFor(EPackage ePackage) {
		return ePackage == modelPackage;
	}

	/**
	 * Calls <code>caseXXX</code> for each class of the model until one returns a non null result; it yields that result.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the first non-null result returned by a <code>caseXXX</code> call.
	 * @generated
	 */
	@Override
	protected T doSwitch(int classifierID, EObject theEObject) {
		switch (classifierID) {
		case PropertyPackage.PROPERTY: {
			Property property = (Property) theEObject;
			T result = caseProperty(property);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case PropertyPackage.QUANTIFIED_PROPERTY: {
			QuantifiedProperty quantifiedProperty = (QuantifiedProperty) theEObject;
			T result = caseQuantifiedProperty(quantifiedProperty);
			if (result == null)
				result = caseProperty(quantifiedProperty);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case PropertyPackage.TEMPORAL_PROPERTY: {
			TemporalProperty temporalProperty = (TemporalProperty) theEObject;
			T result = caseTemporalProperty(temporalProperty);
			if (result == null)
				result = caseProperty(temporalProperty);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case PropertyPackage.EPL_PROPERTY: {
			EPLProperty eplProperty = (EPLProperty) theEObject;
			T result = caseEPLProperty(eplProperty);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case PropertyPackage.ABSENCE: {
			Absence absence = (Absence) theEObject;
			T result = caseAbsence(absence);
			if (result == null)
				result = caseTemporalProperty(absence);
			if (result == null)
				result = caseProperty(absence);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case PropertyPackage.UNIVERSALITY: {
			Universality universality = (Universality) theEObject;
			T result = caseUniversality(universality);
			if (result == null)
				result = caseTemporalProperty(universality);
			if (result == null)
				result = caseProperty(universality);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case PropertyPackage.EXISTENCE: {
			Existence existence = (Existence) theEObject;
			T result = caseExistence(existence);
			if (result == null)
				result = caseTemporalProperty(existence);
			if (result == null)
				result = caseProperty(existence);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case PropertyPackage.ORDERED_TEMPORAL_PROPERTY: {
			OrderedTemporalProperty orderedTemporalProperty = (OrderedTemporalProperty) theEObject;
			T result = caseOrderedTemporalProperty(orderedTemporalProperty);
			if (result == null)
				result = caseTemporalProperty(orderedTemporalProperty);
			if (result == null)
				result = caseProperty(orderedTemporalProperty);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case PropertyPackage.RESPONSE: {
			Response response = (Response) theEObject;
			T result = caseResponse(response);
			if (result == null)
				result = caseOrderedTemporalProperty(response);
			if (result == null)
				result = caseTemporalProperty(response);
			if (result == null)
				result = caseProperty(response);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case PropertyPackage.PRECEDENCE: {
			Precedence precedence = (Precedence) theEObject;
			T result = casePrecedence(precedence);
			if (result == null)
				result = caseOrderedTemporalProperty(precedence);
			if (result == null)
				result = caseTemporalProperty(precedence);
			if (result == null)
				result = caseProperty(precedence);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case PropertyPackage.SCOPE: {
			Scope scope = (Scope) theEObject;
			T result = caseScope(scope);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case PropertyPackage.GLOBALLY: {
			Globally globally = (Globally) theEObject;
			T result = caseGlobally(globally);
			if (result == null)
				result = caseScope(globally);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case PropertyPackage.LOWER_BOUNDED: {
			LowerBounded lowerBounded = (LowerBounded) theEObject;
			T result = caseLowerBounded(lowerBounded);
			if (result == null)
				result = caseScope(lowerBounded);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case PropertyPackage.UPPER_BOUNDED: {
			UpperBounded upperBounded = (UpperBounded) theEObject;
			T result = caseUpperBounded(upperBounded);
			if (result == null)
				result = caseScope(upperBounded);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case PropertyPackage.BEFORE: {
			Before before = (Before) theEObject;
			T result = caseBefore(before);
			if (result == null)
				result = caseUpperBounded(before);
			if (result == null)
				result = caseScope(before);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case PropertyPackage.BETWEEN: {
			Between between = (Between) theEObject;
			T result = caseBetween(between);
			if (result == null)
				result = caseLowerBounded(between);
			if (result == null)
				result = caseUpperBounded(between);
			if (result == null)
				result = caseScope(between);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case PropertyPackage.AFTER_UNTIL: {
			AfterUntil afterUntil = (AfterUntil) theEObject;
			T result = caseAfterUntil(afterUntil);
			if (result == null)
				result = caseLowerBounded(afterUntil);
			if (result == null)
				result = caseUpperBounded(afterUntil);
			if (result == null)
				result = caseScope(afterUntil);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case PropertyPackage.AFTER: {
			After after = (After) theEObject;
			T result = caseAfter(after);
			if (result == null)
				result = caseLowerBounded(after);
			if (result == null)
				result = caseScope(after);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		default:
			return defaultCase(theEObject);
		}
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Property</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Property</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseProperty(Property object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Quantified Property</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Quantified Property</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseQuantifiedProperty(QuantifiedProperty object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Temporal Property</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Temporal Property</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseTemporalProperty(TemporalProperty object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>EPL Property</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>EPL Property</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseEPLProperty(EPLProperty object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Absence</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Absence</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseAbsence(Absence object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Universality</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Universality</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseUniversality(Universality object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Existence</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Existence</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseExistence(Existence object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Ordered Temporal Property</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Ordered Temporal Property</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseOrderedTemporalProperty(OrderedTemporalProperty object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Response</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Response</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseResponse(Response object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Precedence</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Precedence</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T casePrecedence(Precedence object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Scope</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Scope</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseScope(Scope object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Globally</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Globally</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseGlobally(Globally object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Lower Bounded</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Lower Bounded</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseLowerBounded(LowerBounded object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Upper Bounded</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Upper Bounded</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseUpperBounded(UpperBounded object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Before</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Before</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseBefore(Before object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Between</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Between</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseBetween(Between object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>After Until</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>After Until</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseAfterUntil(AfterUntil object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>After</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>After</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseAfter(After object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>EObject</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch, but this is the last case anyway.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>EObject</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject)
	 * @generated
	 */
	@Override
	public T defaultCase(EObject object) {
		return null;
	}

} //PropertySwitch
