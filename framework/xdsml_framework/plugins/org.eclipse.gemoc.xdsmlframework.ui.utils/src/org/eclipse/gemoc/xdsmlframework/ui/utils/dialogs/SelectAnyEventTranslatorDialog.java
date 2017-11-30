package org.eclipse.gemoc.xdsmlframework.ui.utils.dialogs;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.InvalidRegistryObjectException;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.gemoc.xdsmlframework.ui.utils.Activator;
import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.dialogs.FilteredItemsSelectionDialog;

public class SelectAnyEventTranslatorDialog extends FilteredItemsSelectionDialog {

	public SelectAnyEventTranslatorDialog() {
		super(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell());
	}

	@Override
	protected Control createExtendedContentArea(Composite parent) {
		return null;
	}

	private static final String DIALOG_SETTINGS = "SelectAnyConnectorSettings";

	protected IDialogSettings getDialogSettings() {
		IDialogSettings settings = Activator.getDefault().getDialogSettings().getSection(DIALOG_SETTINGS);
		if (settings == null) {
			settings = Activator.getDefault().getDialogSettings().addNewSection(DIALOG_SETTINGS);
		}
		return settings;
	}

	@Override
	protected IStatus validateItem(Object item) {
		return Status.OK_STATUS;
	}

	@Override
	protected ItemsFilter createFilter() {
		return new ItemsFilter() {
			{
				patternMatcher.setPattern("*");
			}
			
			@Override
			public boolean matchItem(Object item) {
				return matches(((Class<?>) item).getName());
			}

			@Override
			public boolean isConsistentItem(Object item) {
				return true;
			}
		};
	}

	@Override
	protected Comparator<Class<?>> getItemsComparator() {
		return new Comparator<Class<?>>() {
			public int compare(Class<?> c1, Class<?> c2) {
				return (c1.getName()).compareTo(c2.getName());
			}
		};
	}

	@Override
	protected void fillContentProvider(AbstractContentProvider contentProvider, ItemsFilter itemsFilter,
			IProgressMonitor progressMonitor) throws CoreException {
		final IConfigurationElement[] confElts = Platform.getExtensionRegistry().getConfigurationElementsFor("org.eclipse.gemoc.executionframework.event.translator");
		final List<Class<?>> classes = new ArrayList<>();
		for (IConfigurationElement confElt : confElts) {
			final String className = confElt.getAttribute("class");
			try {
				classes.add(Platform.getBundle(confElt.getContributor().getName()).loadClass(className));
			} catch (ClassNotFoundException | InvalidRegistryObjectException e) {
				e.printStackTrace();
			}
		}
		for (Class<?> clazz : classes) {
			contentProvider.add(clazz, itemsFilter);
		}
	}

	@Override
	public String getElementName(Object item) {
		return ((Class<?>) item).getName();
	}

}
