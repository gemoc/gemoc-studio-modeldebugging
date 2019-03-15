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

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Platform;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.gemoc.commons.eclipse.emf.URIHelper;
import org.eclipse.gemoc.commons.eclipse.ui.dialogs.SelectAnyIFileDialog;
import org.eclipse.gemoc.dsl.debug.ide.launch.AbstractDSLLaunchConfigurationDelegate;
import org.eclipse.gemoc.dsl.debug.ide.sirius.ui.launch.AbstractDSLLaunchConfigurationDelegateSiriusUI;
import org.eclipse.gemoc.execution.sequential.javaengine.EventBasedRunConfiguration;
import org.eclipse.gemoc.execution.sequential.javaengine.ui.Activator;
import org.eclipse.gemoc.execution.sequential.javaengine.ui.launcher.LauncherMessages;
import org.eclipse.gemoc.executionframework.engine.commons.DslHelper;
import org.eclipse.gemoc.executionframework.engine.commons.MelangeHelper;
import org.eclipse.gemoc.xdsmlframework.ui.utils.dialogs.SelectAIRDIFileDialog;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.wizard.WizardDialog;
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
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.dialogs.ElementListSelectionDialog;

/**
 * Sequential engine launch configuration main tab
 * 
 * @author Didier Vojtisek<didier.vojtisek@inria.fr>
 */
public class EventBasedLaunchConfigurationMainTab extends LaunchConfigurationTab {

	private Text _modelLocationText;
	private Text _animatorLocationText;
	private Text _melangeQueryText;
	private Button _animationFirstBreak;
	private Button _waitForEvents;
	private org.eclipse.swt.widgets.List _implementationRelationshipList;
	private org.eclipse.swt.widgets.List _subtypingRelationshipList;
	private Combo _languageCombo;

	private final Set<String> selectedEventEmitters = new HashSet<>();

	@Override
	public void createControl(Composite parent) {
		Composite area = new Composite(parent, SWT.NULL);
		GridLayout gl = new GridLayout(1, false);
		gl.marginHeight = 0;
		area.setLayout(gl);
		area.layout();
		setControl(area);

		Group modelArea = createGroup(area, "Model:");
		createModelLayout(modelArea, null);

		Group languageArea = createGroup(area, "Language:");
		createLanguageLayout(languageArea, null);

		Group debugArea = createGroup(area, "Animation:");
		createAnimationLayout(debugArea, null);

		Group executionArea = createGroup(area, "Execution:");
		createK3Layout(executionArea, null);

		IConfigurationElement[] eventEmittersConfElts = Platform.getExtensionRegistry()
				.getConfigurationElementsFor("org.eclipse.gemoc.executionframework.event.event_emitter");
		if (eventEmittersConfElts.length > 0) {
			Group eventEmittersArea = createGroup(area, "Event Emitters:");
			createEventEmittersLayout(eventEmittersArea, null, eventEmittersConfElts);
		}
	}

	@Override
	public void setDefaults(ILaunchConfigurationWorkingCopy configuration) {
		configuration.setAttribute(EventBasedRunConfiguration.LAUNCH_SELECTED_LANGUAGE, "");
	}

	@Override
	public void initializeFrom(ILaunchConfiguration configuration) {
		try {

//			clearStartEventGroup();

			final EventBasedRunConfiguration runConfiguration = new EventBasedRunConfiguration(configuration);
			final URI modelUri = runConfiguration.getExecutedModelURI();
			final URI animatorUri = runConfiguration.getAnimatorURI();
			final String languageName = runConfiguration.getLanguageName();

			if (modelUri != null) {
				_modelLocationText.setText(URIHelper.removePlatformScheme(modelUri));
			} else {
				_modelLocationText.setText("");
			}

			if (animatorUri != null) {
				_animatorLocationText.setText(URIHelper.removePlatformScheme(animatorUri));
			} else {
				_animatorLocationText.setText("");
			}

			if (languageName != null) {
				_languageCombo.setText(languageName);
			} else {
				_languageCombo.setText("");
			}

			_animationFirstBreak.setSelection(runConfiguration.getBreakStart());

			configuration.getAttribute(EventBasedRunConfiguration.IMPL_REL_IDS, Collections.emptySet()).stream()
					.forEach(id -> {
						final IConfigurationElement[] relationships = Platform.getExtensionRegistry()
								.getConfigurationElementsFor(implemRelId);
						Arrays.asList(relationships).stream().filter(r -> r.getAttribute("id").equals(id))
								.forEach(r -> {
									_implementationRelationshipList.add(r.getAttribute("name"));
									selectedImplementationRelationships.add(r);
								});
					});

			configuration.getAttribute(EventBasedRunConfiguration.SUBTYPE_REL_IDS, Collections.emptySet()).stream()
					.forEach(id -> {
						final IConfigurationElement[] relationships = Platform.getExtensionRegistry()
								.getConfigurationElementsFor(subtypeRelId);
						Arrays.asList(relationships).stream().filter(r -> r.getAttribute("id").equals(id))
								.forEach(r -> {
									_subtypingRelationshipList.add(r.getAttribute("name"));
									selectedSubtypingRelationships.add(r);
								});
					});

			_waitForEvents.setSelection(runConfiguration.getWaitForEvent());

		} catch (CoreException e) {
			Activator.error(e.getMessage(), e);
		}
	}

	@Override
	public void performApply(ILaunchConfigurationWorkingCopy configuration) {
		configuration.setAttribute(EventBasedRunConfiguration.DEBUG_MODEL_ID, Activator.DEBUG_MODEL_ID);
		configuration.setAttribute(EventBasedRunConfiguration.LAUNCH_SELECTED_LANGUAGE, _languageCombo.getText());
		configuration.setAttribute(EventBasedRunConfiguration.LAUNCH_MELANGE_QUERY, _melangeQueryText.getText());
		configuration.setAttribute(EventBasedRunConfiguration.LAUNCH_BREAK_START, _animationFirstBreak.getSelection());
		configuration.setAttribute(EventBasedRunConfiguration.WAIT_FOR_EVENT, _waitForEvents.getSelection());
		configuration.setAttribute(AbstractDSLLaunchConfigurationDelegate.RESOURCE_URI,
				this._modelLocationText.getText());
		configuration.setAttribute(AbstractDSLLaunchConfigurationDelegateSiriusUI.SIRIUS_RESOURCE_URI,
				this._animatorLocationText.getText());
		configuration.setAttribute(EventBasedRunConfiguration.IMPL_REL_IDS, selectedImplementationRelationships.stream()
				.map(r -> r.getAttribute("id")).collect(Collectors.toSet()));
		configuration.setAttribute(EventBasedRunConfiguration.SUBTYPE_REL_IDS, selectedSubtypingRelationships.stream()
				.map(r -> r.getAttribute("id")).collect(Collectors.toSet()));
	}

	@Override
	public String getName() {
		return "Main";
	}

	// -----------------------------------

	/**
	 * Basic modify listener that can be reused if there is no more precise need
	 */
	private ModifyListener fBasicModifyListener = new ModifyListener() {
		@Override
		public void modifyText(ModifyEvent e) {
			updateLaunchConfigurationDialog();
		}
	};

	// -----------------------------------

	/***
	 * Create the Fields where user enters model to execute
	 * 
	 * @param parent container composite
	 * @param font   used font
	 * @return the created composite containing the fields
	 */
	public Composite createModelLayout(Composite parent, Font font) {
		createTextLabelLayout(parent, "Model to execute");
		// Model location text
		_modelLocationText = new Text(parent, SWT.SINGLE | SWT.BORDER);
		_modelLocationText.setLayoutData(createStandardLayout());
		_modelLocationText.setFont(font);
		_modelLocationText.addModifyListener(fBasicModifyListener);
		Button modelLocationButton = createPushButton(parent, "Browse", null);
		modelLocationButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent evt) {
				SelectAnyIFileDialog dialog = new SelectAnyIFileDialog();
				if (dialog.open() == Dialog.OK) {
					String modelPath = ((IResource) dialog.getResult()[0]).getFullPath().toPortableString();
					_modelLocationText.setText(modelPath);
					updateLaunchConfigurationDialog();
				}
			}
		});
		createTextLabelLayout(parent, "");

		return parent;
	}

	private Composite createAnimationLayout(Composite parent, Font font) {
		createTextLabelLayout(parent, "Animator");

		_animatorLocationText = new Text(parent, SWT.SINGLE | SWT.BORDER);
		_animatorLocationText.setLayoutData(createStandardLayout());
		_animatorLocationText.setFont(font);
		_animatorLocationText.addModifyListener(fBasicModifyListener);
		Button siriusRepresentationLocationButton = createPushButton(parent, "Browse", null);
		siriusRepresentationLocationButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent evt) {
				SelectAIRDIFileDialog dialog = new SelectAIRDIFileDialog();
				if (dialog.open() == Dialog.OK) {
					String modelPath = ((IResource) dialog.getResult()[0]).getFullPath().toPortableString();
					_animatorLocationText.setText(modelPath);
					updateLaunchConfigurationDialog();
				}
			}
		});

		new Label(parent, SWT.NONE).setText("");
		_animationFirstBreak = new Button(parent, SWT.CHECK);
		_animationFirstBreak.setText("Break at start");
		_animationFirstBreak.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent event) {
				updateLaunchConfigurationDialog();
			}
		});

		return parent;
	}

	private GridData createStandardLayout() {
		return new GridData(SWT.FILL, SWT.CENTER, true, false);
	}

	/***
	 * Create the Field where user enters the language used to execute
	 * 
	 * @param parent container composite
	 * @param font   used font
	 * @return the created composite containing the fields
	 */
	public Composite createLanguageLayout(Composite parent, Font font) {
		// Language
		createTextLabelLayout(parent, "Languages");
		_languageCombo = new Combo(parent, SWT.NONE);
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

		createTextLabelLayout(parent, "Melange resource adapter query");
		_melangeQueryText = new Text(parent, SWT.SINGLE | SWT.BORDER);
		_melangeQueryText.setLayoutData(createStandardLayout());
		_melangeQueryText.setFont(font);
		_melangeQueryText.setEditable(false);
		createTextLabelLayout(parent, "");

		return parent;
	}

	private final String implemRelId = "org.eclipse.gemoc.executionframework.event.implementationrelationship";

	private final String subtypeRelId = "org.eclipse.gemoc.executionframework.event.subtypingrelationship";

	private ElementListSelectionDialog getRelationshipSelectionDialog(String extensionPointId,
			List<IConfigurationElement> preselected) {
		final IConfigurationElement[] relationships = Platform.getExtensionRegistry()
				.getConfigurationElementsFor(extensionPointId);
		final ILabelProvider labelProvider = new LabelProvider() {
			@Override
			public String getText(Object element) {
				if (element instanceof IConfigurationElement) {
					return ((IConfigurationElement) element).getAttribute("name");
				}
				return "";
			};
		};

		final ElementListSelectionDialog dialog = new ElementListSelectionDialog(
				PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), labelProvider);
		dialog.setElements(relationships);
		dialog.setMultipleSelection(true);
		dialog.setInitialSelections(preselected);

		return dialog;
	}

	private final List<IConfigurationElement> selectedImplementationRelationships = new ArrayList<>();

	private final List<IConfigurationElement> selectedSubtypingRelationships = new ArrayList<>();

	private Composite createK3Layout(Composite parent, Font font) {
		new Label(parent, SWT.NONE).setText("");
		_waitForEvents = new Button(parent, SWT.CHECK);
		_waitForEvents.setText("Wait for events");
		_waitForEvents.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent event) {
				updateLaunchConfigurationDialog();
			}
		});
		new Label(parent, SWT.NONE).setText("");

		_implementationRelationshipList = new org.eclipse.swt.widgets.List(parent, SWT.V_SCROLL);
		_implementationRelationshipList.setLayoutData(new GridData(GridData.FILL_BOTH));
		_implementationRelationshipList.setFont(parent.getFont());

		final Button implementationRelationshipsBrowseButton = createPushButton(parent, "Browse", null);
		implementationRelationshipsBrowseButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				if (_languageCombo.getText() == null) {
					setErrorMessage("Please select a language.");
				} else {
					final ElementListSelectionDialog dialog = getRelationshipSelectionDialog(implemRelId,
							selectedImplementationRelationships);
					int res = dialog.open();
					if (res == WizardDialog.OK) {
						selectedImplementationRelationships.clear();
						_implementationRelationshipList.removeAll();
						Arrays.asList(dialog.getResult()).stream().filter(o -> o instanceof IConfigurationElement)
								.forEach(o -> {
									final IConfigurationElement r = (IConfigurationElement) o;
									selectedImplementationRelationships.add(r);
									_implementationRelationshipList.add((r).getAttribute("name"));
								});
						updateLaunchConfigurationDialog();
					}
				}
			}
		});
		
		new Label(parent, SWT.NONE).setText("");
		
		_subtypingRelationshipList = new org.eclipse.swt.widgets.List(parent, SWT.V_SCROLL);
		_subtypingRelationshipList.setLayoutData(new GridData(GridData.FILL_BOTH));
		_subtypingRelationshipList.setFont(parent.getFont());

		final Button subtypingRelationshipsBrowseButton = createPushButton(parent, "Browse", null);
		subtypingRelationshipsBrowseButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				if (_languageCombo.getText() == null) {
					setErrorMessage("Please select a language.");
				} else {
					final ElementListSelectionDialog dialog = getRelationshipSelectionDialog(subtypeRelId,
							selectedSubtypingRelationships);
					int res = dialog.open();
					if (res == WizardDialog.OK) {
						selectedSubtypingRelationships.clear();
						_subtypingRelationshipList.removeAll();
						Arrays.asList(dialog.getResult()).stream().filter(o -> o instanceof IConfigurationElement)
								.forEach(o -> {
									final IConfigurationElement r = (IConfigurationElement) o;
									selectedSubtypingRelationships.add(r);
									_subtypingRelationshipList.add((r).getAttribute("name"));
								});
						updateLaunchConfigurationDialog();
					}
				}
			}
		});
		
		new Label(parent, SWT.NONE).setText("");

		return parent;
	}

//		createTextLabelLayout(parent, "Start event");
//		_startEventText = new Text(parent, SWT.SINGLE | SWT.BORDER);
//		_startEventText.setLayoutData(createStandardLayout());
//		_startEventText.setFont(font);
//		_startEventText.setEditable(false);
//		_startEventText.addModifyListener(fBasicModifyListener);
//		final Button startEventBrowseButton = createPushButton(parent, "Browse", null);
//
//		_startEventParametersGroup = createGroup(parent, "Start Event Parameters");
//		final GridData gridData = createStandardLayout();
//		gridData.horizontalSpan = 3;
//		_startEventParametersGroup.setLayoutData(gridData);
//		startEventBrowseButton.addSelectionListener(new SelectionAdapter() {
//			public void widgetSelected(SelectionEvent e) {
//				if (_languageCombo.getText() == null) {
//					setErrorMessage("Please select a language.");
//				} else {
//					final ElementListSelectionDialog dialog = getStartEventSelectionDialog();
//					int res = dialog.open();
//					if (res == WizardDialog.OK) {
//						final Event event = (Event) (dialog.getFirstResult());
//						if (_startEventOccurrence == null || !_startEventOccurrence.getEvent().equals(event)) {
//							clearStartEventGroup();
//							_startEventText.setText(event.getName());
//							_startEventOccurrence = EventBasedDslHelper.createEventOccurrence(event);
//							loadEventOccurrenceParameters(_startEventParametersGroup, _startEventOccurrence);
//							parent.requestLayout();
//							updateLaunchConfigurationDialog();
//						}
//					}
//				}
//			}
//		});

//	private void clearStartEventGroup() {
//		Arrays.asList(_startEventParametersGroup.getChildren()).forEach(c -> c.dispose());
//		_startEventParameters.clear();
//		_startEventText.setText("");
//	}

	private Composite createEventEmittersLayout(Composite parent, Font font,
			IConfigurationElement[] eventEmittersConfElts) {
		Arrays.asList(eventEmittersConfElts).forEach(elt -> {
			final String name = elt.getAttribute("Name");
			final String id = elt.getAttribute("id");
			final Button eventEmitter = new Button(parent, SWT.CHECK);
			eventEmitter.setText(name);
			eventEmitter.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent event) {
					if (eventEmitter.getSelection()) {
						selectedEventEmitters.add(id);
					} else {
						selectedEventEmitters.remove(id);
					}
					updateLaunchConfigurationDialog();
				}
			});
		});

		if (eventEmittersConfElts.length < 3) {
			for (int i = eventEmittersConfElts.length; i < 3; i++) {
				new Label(parent, SWT.NONE).setText("");
			}
		}

		return parent;
	}

	@Override
	protected void updateLaunchConfigurationDialog() {
		super.updateLaunchConfigurationDialog();
		_melangeQueryText.setText(computeMelangeQuery());
	}

	protected String computeMelangeQuery() {
		String result = "";
		String languageName = this._languageCombo.getText();
		if (!this._modelLocationText.getText().isEmpty() && !languageName.isEmpty()) {
			Resource model = getModel();
			List<String> modelNativeLanguages = MelangeHelper.getNativeLanguagesUsedByResource(model);
			if (!modelNativeLanguages.isEmpty() && !modelNativeLanguages.get(0).equals(languageName)) {
				// TODO this version consider only the first native language, we need to think
				// about models containing elements coming from several languages
				String languageMT = MelangeHelper.getModelType(languageName);
				if (languageMT == null) {
					languageMT = languageName + "MT";
				}

				// result="?lang="+languageName+"&mt="+languageMT;
				result = "?lang=" + languageName; // we need a simple downcast without adapter
			}
		}
		return result;
	}

	/**
	 * caches the current model resource in order to avoid to reload it many times
	 * use {@link getModel()} in order to access it.
	 */
	private Resource currentModelResource;

	private Resource getModel() {
		final String uri = _modelLocationText.getText();
		if (uri.isEmpty()) {
			currentModelResource = null;
		} else {
			URI modelURI = URI.createPlatformResourceURI(_modelLocationText.getText(), true);
			if (currentModelResource == null || !currentModelResource.getURI().equals(modelURI)) {
				ResourceSet resourceSet = new ResourceSetImpl();
				currentModelResource = resourceSet.createResource(modelURI);
				try {
					currentModelResource.load(Collections.emptyMap());
				} catch (IOException e) {
					e.printStackTrace();
					currentModelResource = null;
				}
			}
		}
		return currentModelResource;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.debug.ui.AbstractLaunchConfigurationTab#isValid(org.eclipse.debug
	 * .core.ILaunchConfiguration)
	 */
	@Override
	public boolean isValid(ILaunchConfiguration config) {
		setErrorMessage(null);
		setMessage(null);
		IWorkspace workspace = ResourcesPlugin.getWorkspace();
		String modelName = _modelLocationText.getText().trim();
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
