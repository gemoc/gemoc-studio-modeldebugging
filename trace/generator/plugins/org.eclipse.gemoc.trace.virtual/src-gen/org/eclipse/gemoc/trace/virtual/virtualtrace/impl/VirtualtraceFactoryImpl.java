/**
 */
package org.eclipse.gemoc.trace.virtual.virtualtrace.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EFactoryImpl;

import org.eclipse.emf.ecore.plugin.EcorePlugin;

import org.eclipse.gemoc.trace.virtual.virtualtrace.*;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class VirtualtraceFactoryImpl extends EFactoryImpl implements VirtualtraceFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static VirtualtraceFactory init() {
		try {
			VirtualtraceFactory theVirtualtraceFactory = (VirtualtraceFactory) EPackage.Registry.INSTANCE
					.getEFactory(VirtualtracePackage.eNS_URI);
			if (theVirtualtraceFactory != null) {
				return theVirtualtraceFactory;
			}
		} catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new VirtualtraceFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public VirtualtraceFactoryImpl() {
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
		case VirtualtracePackage.VIRTUAL_TRACE:
			return createVirtualTrace();
		case VirtualtracePackage.STATE_GROUP:
			return createStateGroup();
		default:
			throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public VirtualTrace createVirtualTrace() {
		VirtualTraceImpl virtualTrace = new VirtualTraceImpl();
		return virtualTrace;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public StateGroup createStateGroup() {
		StateGroupImpl stateGroup = new StateGroupImpl();
		return stateGroup;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public VirtualtracePackage getVirtualtracePackage() {
		return (VirtualtracePackage) getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static VirtualtracePackage getPackage() {
		return VirtualtracePackage.eINSTANCE;
	}

} //VirtualtraceFactoryImpl
