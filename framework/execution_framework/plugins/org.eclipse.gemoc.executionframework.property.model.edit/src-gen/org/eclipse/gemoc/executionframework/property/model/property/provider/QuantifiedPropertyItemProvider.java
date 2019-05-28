/**
 */
package org.eclipse.gemoc.executionframework.property.model.property.provider;

import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EStructuralFeature;

import org.eclipse.emf.edit.provider.ComposeableAdapterFactory;
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.ItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.ViewerNotification;

import org.eclipse.gemoc.executionframework.property.model.property.PropertyFactory;
import org.eclipse.gemoc.executionframework.property.model.property.PropertyPackage;
import org.eclipse.gemoc.executionframework.property.model.property.QuantifiedProperty;
import org.eclipse.gemoc.executionframework.property.model.property.Quantifier;

/**
 * This is the item provider adapter for a {@link org.eclipse.gemoc.executionframework.property.model.property.QuantifiedProperty} object.
 * <!-- begin-user-doc -->
 * <!-- end-user-doc -->
 * @generated
 */
public class QuantifiedPropertyItemProvider extends PropertyItemProvider {
	/**
	 * This constructs an instance from a factory and a notifier.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public QuantifiedPropertyItemProvider(AdapterFactory adapterFactory) {
		super(adapterFactory);
	}

	/**
	 * This returns the property descriptors for the adapted class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public List<IItemPropertyDescriptor> getPropertyDescriptors(Object object) {
		if (itemPropertyDescriptors == null) {
			super.getPropertyDescriptors(object);

			addQuantifierPropertyDescriptor(object);
			addPropertyPropertyDescriptor(object);
		}
		return itemPropertyDescriptors;
	}

	/**
	 * This adds a property descriptor for the Quantifier feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addQuantifierPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add(createItemPropertyDescriptor(
				((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(), getResourceLocator(),
				getString("_UI_QuantifiedProperty_quantifier_feature"),
				getString("_UI_PropertyDescriptor_description", "_UI_QuantifiedProperty_quantifier_feature",
						"_UI_QuantifiedProperty_type"),
				PropertyPackage.Literals.QUANTIFIED_PROPERTY__QUANTIFIER, true, false, false,
				ItemPropertyDescriptor.GENERIC_VALUE_IMAGE, null, null));
	}

	/**
	 * This adds a property descriptor for the Property feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addPropertyPropertyDescriptor(Object object) {
		itemPropertyDescriptors
				.add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
						getResourceLocator(), getString("_UI_QuantifiedProperty_property_feature"),
						getString("_UI_PropertyDescriptor_description", "_UI_QuantifiedProperty_property_feature",
								"_UI_QuantifiedProperty_type"),
						PropertyPackage.Literals.QUANTIFIED_PROPERTY__PROPERTY, true, false, true, null, null, null));
	}

	/**
	 * This specifies how to implement {@link #getChildren} and is used to deduce an appropriate feature for an
	 * {@link org.eclipse.emf.edit.command.AddCommand}, {@link org.eclipse.emf.edit.command.RemoveCommand} or
	 * {@link org.eclipse.emf.edit.command.MoveCommand} in {@link #createCommand}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Collection<? extends EStructuralFeature> getChildrenFeatures(Object object) {
		if (childrenFeatures == null) {
			super.getChildrenFeatures(object);
			childrenFeatures.add(PropertyPackage.Literals.QUANTIFIED_PROPERTY__PROPERTY);
		}
		return childrenFeatures;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EStructuralFeature getChildFeature(Object object, Object child) {
		// Check the type of the specified child object and return the proper feature to use for
		// adding (see {@link AddCommand}) it as a child.

		return super.getChildFeature(object, child);
	}

	/**
	 * This returns QuantifiedProperty.gif.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object getImage(Object object) {
		return overlayImage(object, getResourceLocator().getImage("full/obj16/QuantifiedProperty"));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected boolean shouldComposeCreationImage() {
		return true;
	}

	/**
	 * This returns the label text for the adapted class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getText(Object object) {
		Quantifier labelValue = ((QuantifiedProperty) object).getQuantifier();
		String label = labelValue == null ? null : labelValue.toString();
		return label == null || label.length() == 0 ? getString("_UI_QuantifiedProperty_type")
				: getString("_UI_QuantifiedProperty_type") + " " + label;
	}

	/**
	 * This handles model notifications by calling {@link #updateChildren} to update any cached
	 * children and by creating a viewer notification, which it passes to {@link #fireNotifyChanged}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void notifyChanged(Notification notification) {
		updateChildren(notification);

		switch (notification.getFeatureID(QuantifiedProperty.class)) {
		case PropertyPackage.QUANTIFIED_PROPERTY__QUANTIFIER:
			fireNotifyChanged(new ViewerNotification(notification, notification.getNotifier(), false, true));
			return;
		case PropertyPackage.QUANTIFIED_PROPERTY__PROPERTY:
			fireNotifyChanged(new ViewerNotification(notification, notification.getNotifier(), true, false));
			return;
		}
		super.notifyChanged(notification);
	}

	/**
	 * This adds {@link org.eclipse.emf.edit.command.CommandParameter}s describing the children
	 * that can be created under this object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected void collectNewChildDescriptors(Collection<Object> newChildDescriptors, Object object) {
		super.collectNewChildDescriptors(newChildDescriptors, object);

		newChildDescriptors.add(createChildParameter(PropertyPackage.Literals.QUANTIFIED_PROPERTY__PROPERTY,
				PropertyFactory.eINSTANCE.createProperty()));

		newChildDescriptors.add(createChildParameter(PropertyPackage.Literals.QUANTIFIED_PROPERTY__PROPERTY,
				PropertyFactory.eINSTANCE.createQuantifiedProperty()));

		newChildDescriptors.add(createChildParameter(PropertyPackage.Literals.QUANTIFIED_PROPERTY__PROPERTY,
				PropertyFactory.eINSTANCE.createAbsence()));

		newChildDescriptors.add(createChildParameter(PropertyPackage.Literals.QUANTIFIED_PROPERTY__PROPERTY,
				PropertyFactory.eINSTANCE.createUniversality()));

		newChildDescriptors.add(createChildParameter(PropertyPackage.Literals.QUANTIFIED_PROPERTY__PROPERTY,
				PropertyFactory.eINSTANCE.createExistence()));

		newChildDescriptors.add(createChildParameter(PropertyPackage.Literals.QUANTIFIED_PROPERTY__PROPERTY,
				PropertyFactory.eINSTANCE.createResponse()));

		newChildDescriptors.add(createChildParameter(PropertyPackage.Literals.QUANTIFIED_PROPERTY__PROPERTY,
				PropertyFactory.eINSTANCE.createPrecedence()));
	}

}
