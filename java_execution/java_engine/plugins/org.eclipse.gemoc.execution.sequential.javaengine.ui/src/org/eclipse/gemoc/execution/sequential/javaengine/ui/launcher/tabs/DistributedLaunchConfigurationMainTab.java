/*******************************************************************************
 * Copyright (c) 2016, 2017 Inria and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Inria - initial API and implementation
 *******************************************************************************/
package org.eclipse.gemoc.execution.sequential.javaengine.ui.launcher.tabs;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.gemoc.xdsmlframework.api.core.IRunConfiguration;
import org.eclipse.gemoc.xdsmlframework.ui.utils.dialogs.SelectAnyEventTranslatorDialog;
import org.eclipse.gemoc.xdsmlframework.ui.utils.dialogs.SelectAnyGemocSequentialLaunchConfigurationDialog;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.dialogs.SelectionDialog;

/**
 * Distributed engine launch configuration main tab
 * 
 * @author dorian
 */
public class DistributedLaunchConfigurationMainTab extends LaunchConfigurationTab {

	protected Composite _parent;

	protected Group _connectorsArea;
	protected Button _addConnectionButton;

	private Map<String, String> connectorsMap = new HashMap<>();

	/**
	 * default width for the grids
	 */
	public int gridDefaultWidth = 200;

	protected IProject _modelProject;

	@Override
	public void createControl(Composite parent) {
		_parent = parent;
		Composite area = new Composite(parent, SWT.NULL);
		GridLayout gl = new GridLayout(1, false);
		gl.marginHeight = 0;
		area.setLayout(gl);
		area.layout();
		setControl(area);

		_connectorsArea = createGroup(area, "Models to execute:");
		createConnectorsLayout(_connectorsArea);

	}

	@Override
	public void setDefaults(ILaunchConfigurationWorkingCopy configuration) {
		System.out.println("Set defaults called on: " + configuration);
	}

	@Override
	public void initializeFrom(ILaunchConfiguration configuration) {
		if (connectorsMap.isEmpty()) {
			try {
				final Set<String> allLaunchConfigurations = configuration
						.getAttribute(IRunConfiguration.DISTRIBUTED_LAUNCH_CONFIGURATIONS, Collections.emptySet());
				allLaunchConfigurations.forEach(l -> {
					try {
						final String c = configuration.getAttribute(l, "");
						connectorsMap.put(l, c);
					} catch (CoreException e) {
						e.printStackTrace();
					}
				});
			} catch (CoreException e) {
				e.printStackTrace();
			}
			connectorsMap.forEach((l, c) -> {
				Text[] toSet = createConnectionConfigurator(_connectorsArea, _addConnectionButton);
				toSet[0].setText(l);
				toSet[1].setText(c);
			});
		}
	}

	@Override
	public void performApply(ILaunchConfigurationWorkingCopy configuration) {
		final List<String> toRemove = new ArrayList<>();
		final Set<String> allLaunchConfigurations = new HashSet<>();
		try {
			toRemove.addAll(configuration.getAttributes().keySet());
		} catch (CoreException e) {
			e.printStackTrace();
		}
		connectorsMap.forEach((l, c) -> {
			allLaunchConfigurations.add(l);
			toRemove.remove(l);
			configuration.setAttribute(l, c);
		});
		toRemove.forEach(s -> configuration.removeAttribute(s));
		configuration.setAttribute(IRunConfiguration.DISTRIBUTED_LAUNCH_CONFIGURATIONS, allLaunchConfigurations);
	}

	@Override
	public String getName() {
		return "Main";
	}

	public Composite createConnectorsLayout(Composite parent) {
		createTextLabelLayout(parent, "Launch Configuration");
		Label connectorLabel = createTextLabelLayout(parent, "Event Translator Class");
		createTextLabelLayout(parent, "");
		connectorLabel.setLayoutData(new GridData(SWT.CENTER, SWT.FILL, true, true));

		_addConnectionButton = createPushButton(parent, "Add a model", null);

		_addConnectionButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent evt) {
				createConnectionConfigurator(parent, _addConnectionButton);
				parent.requestLayout();
			}
		});

		return parent;
	}

	private Text[] createConnectionConfigurator(Composite parent, Control moveAbove) {
		final List<Control> connectionControls = new ArrayList<>();
		final Button launchConfButton = createPushButton(parent, "Browse", null);
		final Button connectorFqnButton = createPushButton(parent, "Browse", null);
		final Button deleteConnectionButton = createPushButton(parent, "X", null);
		final GridData gridData = new GridData();
		gridData.widthHint = 30;
		deleteConnectionButton.setLayoutData(gridData);

		final Text launchConfText = new Text(parent, SWT.SINGLE | SWT.BORDER);
		launchConfText.setLayoutData(createStandardLayout());
		final Text connectorFqnText = new Text(parent, SWT.SINGLE | SWT.BORDER);
		connectorFqnText.setLayoutData(createStandardLayout());
		final Label filler = createTextLabelLayout(parent, "");

		connectionControls.add(launchConfButton);
		connectionControls.add(connectorFqnButton);
		connectionControls.add(deleteConnectionButton);
		connectionControls.add(launchConfText);
		connectionControls.add(connectorFqnText);
		connectionControls.add(filler);

		launchConfButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent evt) {
				final SelectionDialog dialog = new SelectAnyGemocSequentialLaunchConfigurationDialog();
				if (dialog.open() == Dialog.OK) {
					final ILaunchConfiguration launchConfiguration = (ILaunchConfiguration) dialog.getResult()[0];
					launchConfText.setText(launchConfiguration.getName());
				}
			}
		});
		connectorFqnButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent evt) {
				final SelectAnyEventTranslatorDialog dialog = new SelectAnyEventTranslatorDialog();
				if (dialog.open() == Dialog.OK) {
					Class<?> connectorClass = (Class<?>) dialog.getResult()[0];
					connectorFqnText.setText(connectorClass.getName());
				}
			}
		});
		deleteConnectionButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent evt) {
				final String launchConf = launchConfText.getText();
				if (!launchConf.isEmpty()) {
					connectorsMap.remove(launchConf);
				}
				connectionControls.forEach(c -> c.dispose());
				updateLaunchConfigurationDialog();
			}
		});

		final ModifyListener modifyListener = new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent e) {
				final String firstLaunchConf = launchConfText.getText();
				final String connectorFqn = connectorFqnText.getText();
				if (!firstLaunchConf.isEmpty()) {
					connectorsMap.put(firstLaunchConf, connectorFqn);
				}
				updateLaunchConfigurationDialog();
			}
		};

		launchConfText.addModifyListener(modifyListener);
		connectorFqnText.addModifyListener(modifyListener);

		if (moveAbove != null) {
			connectionControls.forEach(c -> c.moveAbove(moveAbove));
		}

		return new Text[] { launchConfText, connectorFqnText };
	}

	private GridData createStandardLayout() {
		return new GridData(SWT.FILL, SWT.CENTER, true, false);
	}

	@Override
	public boolean isValid(ILaunchConfiguration config) {
		return true;
	}
}
