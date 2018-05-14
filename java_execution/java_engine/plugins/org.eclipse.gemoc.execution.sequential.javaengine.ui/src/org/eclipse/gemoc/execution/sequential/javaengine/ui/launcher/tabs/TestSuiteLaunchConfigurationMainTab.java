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

import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.gemoc.commons.eclipse.ui.dialogs.SelectAnyIFileDialog;
import org.eclipse.gemoc.execution.sequential.javaengine.PlainK3ExecutionEngine;
import org.eclipse.gemoc.execution.sequential.javaengine.ui.Activator;
import org.eclipse.gemoc.execution.sequential.javaengine.ui.launcher.LauncherMessages;
import org.eclipse.gemoc.executionframework.engine.commons.DslHelper;
import org.eclipse.gemoc.executionframework.event.testsuite.TestSuite;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Text;

public class TestSuiteLaunchConfigurationMainTab extends LaunchConfigurationTab {

	protected Composite _parent;

	protected Text _testSuiteLocationText;
	protected Combo _languageCombo;

	public int gridDefaultWidth = 200;

	@Override
	public void createControl(Composite parent) {
		_parent = parent;
		Composite area = new Composite(parent, SWT.NULL);
		GridLayout gl = new GridLayout(1, false);
		gl.marginHeight = 0;
		area.setLayout(gl);
		area.layout();
		setControl(area);

		Group modelArea = createGroup(area, "Test Suite:");
		createModelLayout(modelArea, null);

		Group languageArea = createGroup(area, "Language:");
		createLanguageLayout(languageArea, null);
	}

	@Override
	public void setDefaults(ILaunchConfigurationWorkingCopy configuration) {
		configuration.setAttribute("TEST_SUITE_URI", "");
		configuration.setAttribute("LANGUAGE_NAME", "");
	}

	@Override
	public void initializeFrom(ILaunchConfiguration configuration) {
		try {
			_testSuiteLocationText.setText(configuration.getAttribute("TEST_SUITE_URI", ""));
			_languageCombo.setText(configuration.getAttribute("LANGUAGE_NAME", ""));
		} catch (CoreException e) {
			Activator.error(e.getMessage(), e);
		}
	}

	@Override
	public void performApply(ILaunchConfigurationWorkingCopy configuration) {
		configuration.setAttribute("TEST_SUITE_URI", _testSuiteLocationText.getText());
		configuration.setAttribute("LANGUAGE_NAME", _languageCombo.getText());
	}

	@Override
	public String getName() {
		return "Main";
	}

	// -----------------------------------

	private ModifyListener fBasicModifyListener = new ModifyListener() {
		@Override
		public void modifyText(ModifyEvent arg0) {
			updateLaunchConfigurationDialog();
		}
	};

	// -----------------------------------

	public Composite createModelLayout(Composite parent, Font font) {
		createTextLabelLayout(parent, "Model to execute");
		// Model location text
		_testSuiteLocationText = new Text(parent, SWT.SINGLE | SWT.BORDER);
		_testSuiteLocationText.setLayoutData(createStandardLayout());
		_testSuiteLocationText.setFont(font);
		_testSuiteLocationText.addModifyListener(fBasicModifyListener);
		Button modelLocationButton = createPushButton(parent, "Browse", null);
		modelLocationButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent evt) {
				SelectAnyIFileDialog dialog = new SelectAnyIFileDialog();
				if (dialog.open() == Dialog.OK) {
					String modelPath = ((IResource) dialog.getResult()[0]).getFullPath().toPortableString();
					_testSuiteLocationText.setText(modelPath);
					updateLaunchConfigurationDialog();
				}
			}
		});
		createTextLabelLayout(parent, "");

		return parent;
	}

	private GridData createStandardLayout() {
		return new GridData(SWT.FILL, SWT.CENTER, true, false);
	}

	public Composite createLanguageLayout(Composite parent, Font font) {
		// Language
		createTextLabelLayout(parent, "Languages");
		_languageCombo = new Combo(parent, SWT.READ_ONLY);
		_languageCombo.setLayoutData(createStandardLayout());

		List<String> languagesNames = DslHelper.getAllLanguages();
		String[] empty = {};
		_languageCombo.setItems(languagesNames.toArray(empty));
		_languageCombo.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				updateLaunchConfigurationDialog();
			}
		});
		createTextLabelLayout(parent, "");

		return parent;
	}

	@Override
	protected void updateLaunchConfigurationDialog() {
		super.updateLaunchConfigurationDialog();
	}

	private Resource currentModelResource;

	private Resource getModel() {
		URI modelURI = URI.createPlatformResourceURI(_testSuiteLocationText.getText(), true);
		if (currentModelResource == null || !currentModelResource.getURI().equals(modelURI)) {
			currentModelResource = PlainK3ExecutionEngine.loadModel(modelURI);
		}
		return currentModelResource;
	}

	@Override
	public boolean isValid(ILaunchConfiguration config) {
		setErrorMessage(null);
		setMessage(null);
		IWorkspace workspace = ResourcesPlugin.getWorkspace();
		String modelName = _testSuiteLocationText.getText().trim();
		if (modelName.length() > 0) {

			IResource modelIResource = workspace.getRoot().findMember(modelName);
			if (modelIResource == null || !modelIResource.exists()) {
				setErrorMessage(
						NLS.bind(LauncherMessages.SequentialMainTab_model_doesnt_exist, new String[] { modelName }));
				return false;
			}
			if (modelName.equals("/")) {
				setErrorMessage(LauncherMessages.SequentialMainTab_Model_not_specified);
				return false;
			}
			if (!(modelIResource instanceof IFile)) {
				setErrorMessage(
						NLS.bind(LauncherMessages.SequentialMainTab_invalid_model_file, new String[] { modelName }));
				return false;
			}
			if (!getModel().getContents().stream().anyMatch(o -> o instanceof TestSuite)) {
				setErrorMessage("Selected model is not a Test Suite");
				return false;
			}
		}
		if (modelName.length() == 0) {
			setErrorMessage(LauncherMessages.SequentialMainTab_Model_not_specified);
			return false;
		}

		String languageName = _languageCombo.getText().trim();
		if (languageName.length() == 0) {
			setErrorMessage(LauncherMessages.SequentialMainTab_Language_not_specified);
			return false;
		}

		return true;
	}
}
