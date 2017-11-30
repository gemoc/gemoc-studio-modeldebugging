package org.eclipse.gemoc.xdsmlframework.ui.utils.dialogs;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationType;
import org.eclipse.gemoc.xdsmlframework.ui.utils.Activator;
import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.dialogs.FilteredItemsSelectionDialog;

public class SelectAnyGemocSequentialLaunchConfigurationDialog extends FilteredItemsSelectionDialog {

	public SelectAnyGemocSequentialLaunchConfigurationDialog() {
		super(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell());
	}

	@Override
	protected Control createExtendedContentArea(Composite parent) {
		return null;
	}

	private static final String DIALOG_SETTINGS = "SelectAnyGemocSequentialLauncConfigurationSettings";

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
				return matches(((ILaunchConfiguration) item).getName());
			}

			@Override
			public boolean isConsistentItem(Object item) {
				return true;
			}
		};
	}

	@Override
	protected Comparator<ILaunchConfiguration> getItemsComparator() {
		return new Comparator<ILaunchConfiguration>() {
			public int compare(ILaunchConfiguration l1, ILaunchConfiguration l2) {
				return (l1.getName()).compareTo(l2.getName());
			}
		};
	}

	@Override
	protected void fillContentProvider(AbstractContentProvider contentProvider, ItemsFilter itemsFilter,
			IProgressMonitor progressMonitor) throws CoreException {
		final ILaunchConfigurationType launchType = DebugPlugin.getDefault().getLaunchManager()
				.getLaunchConfigurationType("org.eclipse.gemoc.execution.sequential.javaengine.ui.launcher");
		final List<ILaunchConfiguration> launchConfigurations = Arrays
				.asList(DebugPlugin.getDefault().getLaunchManager().getLaunchConfigurations(launchType));
		for (ILaunchConfiguration launchConfiguration : launchConfigurations) {
			contentProvider.add(launchConfiguration, itemsFilter);
		}
	}

	@Override
	public String getElementName(Object item) {
		return ((ILaunchConfiguration) item).getName();
	}

}
