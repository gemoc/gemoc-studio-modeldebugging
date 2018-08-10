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
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.gemoc.commons.eclipse.emf.URIHelper;
import org.eclipse.gemoc.commons.eclipse.ui.dialogs.SelectAnyIFileDialog;
import org.eclipse.gemoc.commons.value.model.value.BooleanAttributeValue;
import org.eclipse.gemoc.commons.value.model.value.BooleanObjectAttributeValue;
import org.eclipse.gemoc.commons.value.model.value.FloatAttributeValue;
import org.eclipse.gemoc.commons.value.model.value.FloatObjectAttributeValue;
import org.eclipse.gemoc.commons.value.model.value.IntegerAttributeValue;
import org.eclipse.gemoc.commons.value.model.value.IntegerObjectAttributeValue;
import org.eclipse.gemoc.commons.value.model.value.SingleReferenceValue;
import org.eclipse.gemoc.commons.value.model.value.StringAttributeValue;
import org.eclipse.gemoc.commons.value.model.value.Value;
import org.eclipse.gemoc.commons.value.model.value.ValuePackage;
import org.eclipse.gemoc.dsl.debug.ide.launch.AbstractDSLLaunchConfigurationDelegate;
import org.eclipse.gemoc.dsl.debug.ide.sirius.ui.launch.AbstractDSLLaunchConfigurationDelegateSiriusUI;
import org.eclipse.gemoc.execution.sequential.javaengine.EventBasedRunConfiguration;
import org.eclipse.gemoc.execution.sequential.javaengine.ui.Activator;
import org.eclipse.gemoc.execution.sequential.javaengine.ui.launcher.LauncherMessages;
import org.eclipse.gemoc.executionframework.engine.commons.DslHelper;
import org.eclipse.gemoc.executionframework.engine.commons.EventBasedDslHelper;
import org.eclipse.gemoc.executionframework.engine.commons.MelangeHelper;
import org.eclipse.gemoc.executionframework.event.model.event.EventOccurrence;
import org.eclipse.gemoc.executionframework.event.model.event.EventOccurrenceArgument;
import org.eclipse.gemoc.executionframework.ui.utils.ENamedElementQualifiedNameLabelProvider;
import org.eclipse.gemoc.xdsmlframework.behavioralinterface.behavioralInterface.BehavioralInterface;
import org.eclipse.gemoc.xdsmlframework.behavioralinterface.behavioralInterface.Event;
import org.eclipse.gemoc.xdsmlframework.behavioralinterface.behavioralInterface.EventParameter;
import org.eclipse.gemoc.xdsmlframework.behavioralinterface.behavioralInterface.InputEvent;
import org.eclipse.gemoc.xdsmlframework.ui.utils.dialogs.SelectAIRDIFileDialog;
import org.eclipse.gemoc.xdsmlframework.ui.utils.dialogs.SelectAnyEObjectDialog;
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
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.dialogs.ElementListSelectionDialog;
import org.osgi.framework.Bundle;

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
	private Text _startEventText;
	private Combo _languageCombo;

	private BehavioralInterface _behavioralInterface;
	private Bundle _bundle;

	private EventOccurrence _startEventOccurrence = null;
	private Group _startEventParametersGroup;
	private final List<Text> _startEventParameters = new ArrayList<>();

	private final Map<String, String> paramToArg = new HashMap<>();
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
			
			clearStartEventGroup();
			
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
				loadLanguage(languageName);
				if (_behavioralInterface != null) {
					_startEventOccurrence = runConfiguration.getStartEventOccurrence();
					if (_startEventOccurrence != null) {
						_startEventText.setText(_startEventOccurrence.getEvent().getName());
						loadEventOccurrenceParameters(_startEventParametersGroup, _startEventOccurrence);
					}
				}
			} else {
				_languageCombo.setText("");
			}

			_animationFirstBreak.setSelection(runConfiguration.getBreakStart());
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
		if (_startEventOccurrence != null) {
			final Event event = _startEventOccurrence.getEvent();
			configuration.setAttribute(EventBasedRunConfiguration.START_EVENT, event.getName());
			configuration.setAttribute(EventBasedRunConfiguration.START_EVENT_OCCURRENCE_ARGS, paramToArg);
		}
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
	 * @param parent
	 *            container composite
	 * @param font
	 *            used font
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
	 * @param parent
	 *            container composite
	 * @param font
	 *            used font
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
				// String selection = _languageCombo.getText();
				// List<String> modelTypeNames = MelangeHelper.getModelTypes(selection);
				loadLanguage(_languageCombo.getText());
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

	private void loadLanguage(String languageName) {
		_behavioralInterface = EventBasedDslHelper.getBehavioralInterface(languageName);
		_bundle = DslHelper.getDslBundle(languageName);
	}

	private ElementListSelectionDialog getStartEventSelectionDialog() {
		final Set<Event> events = new HashSet<>();
		if (_behavioralInterface != null) {
			events.addAll(_behavioralInterface.getEvents());
		}

		final ILabelProvider labelProvider = new LabelProvider() {
			@Override
			public String getText(Object element) {
				if (element instanceof Event) {
					final Event event = (Event) element;
					return event.getName();
				}
				return "";
			};
		};

		final ElementListSelectionDialog dialog = new ElementListSelectionDialog(
				PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), labelProvider);
		dialog.setElements(events.toArray());

		return dialog;
	}

	private void addStartEventParameterRow(Composite parent, EventParameter param, Class<?> type, String arg) {
		if (type == null) {
			// TODO error
		} else {
			final String name = param.getName() + ": " + type.getSimpleName();
			createTextLabelLayout(parent, name);
			final Text text = new Text(parent, SWT.SINGLE | SWT.BORDER);
			_startEventParameters.add(text);
			text.setLayoutData(createStandardLayout());
			if (type.isPrimitive() || type.equals(String.class)) {
				text.setEditable(true);
				createTextLabelLayout(parent, "");
			} else {
				text.setEditable(false);
				final Button parameterBrowseButton = createPushButton(parent, "Browse Model", null);
				parameterBrowseButton.addSelectionListener(new SelectionAdapter() {
					public void widgetSelected(SelectionEvent e) {
						Resource model = getModel();
						if (model == null) {
							setErrorMessage("Please select a model to execute.");
						} else {
							final Shell shell = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();
							final ResourceSet resourceSet = model.getResourceSet();
							final ILabelProvider labelProvider = new ENamedElementQualifiedNameLabelProvider();
							SelectAnyEObjectDialog dialog = new SelectAnyEObjectDialog(shell, resourceSet,
									labelProvider) {
								@Override
								protected boolean select(EObject obj) {
									return obj.eClass().getInstanceClass().isAssignableFrom(type);
								}
							};
							int res = dialog.open();
							if (res == WizardDialog.OK) {
								EObject selection = (EObject) dialog.getFirstResult();
								String uriFragment = selection.eResource().getURIFragment(selection);
								text.setText(uriFragment);
								updateLaunchConfigurationDialog();
							}
						}
					}
				});
			}
			text.setText(arg);
			paramToArg.put(param.getName(), arg);
			
			ModifyListener argModifyListener = new ModifyListener() {
				@Override
				public void modifyText(ModifyEvent e) {
					paramToArg.put(param.getName(), text.getText());
					updateLaunchConfigurationDialog();
				}
			};
			
			text.addModifyListener(argModifyListener);
		}
	}

	private void loadEventOccurrenceParameters(Composite parent, EventOccurrence eventOccurrence) {
		final Event event = eventOccurrence.getEvent();
		final List<EventOccurrenceArgument> args = eventOccurrence.getArgs();
		event.getParams().forEach(p -> {
			args.stream().filter(a -> p == a.getParameter()).findFirst().ifPresent(a -> {
				Value value = a.getValue();
				String arg = "";
				Class<?> type = null;
				switch (value.eClass().getClassifierID()) {
				case ValuePackage.SINGLE_REFERENCE_VALUE:
					final EObject o = ((SingleReferenceValue) value).getReferenceValue();
					if (o == null) {
						arg = "";
						try {
							type = _bundle.loadClass(p.getType());
						} catch (ClassNotFoundException e) {
							e.printStackTrace();
						}
					} else {
						arg = "" + o.eResource().getURIFragment(o);
						type = o.eClass().getInstanceClass();
					}
					break;
				case ValuePackage.BOOLEAN_ATTRIBUTE_VALUE:
					arg = "" + ((BooleanAttributeValue) value).isAttributeValue();
					type = Boolean.class;
					break;
				case ValuePackage.BOOLEAN_OBJECT_ATTRIBUTE_VALUE:
					arg = "" + ((BooleanObjectAttributeValue) value).getAttributeValue();
					type = Boolean.class;
					break;
				case ValuePackage.INTEGER_ATTRIBUTE_VALUE:
					arg = "" + ((IntegerAttributeValue) value).getAttributeValue();
					type = Integer.class;
					break;
				case ValuePackage.INTEGER_OBJECT_ATTRIBUTE_VALUE:
					arg = "" + ((IntegerObjectAttributeValue) value).getAttributeValue();
					type = Integer.class;
					break;
				case ValuePackage.FLOAT_ATTRIBUTE_VALUE:
					arg = "" + ((FloatAttributeValue) value).getAttributeValue();
					type = Float.class;
					break;
				case ValuePackage.FLOAT_OBJECT_ATTRIBUTE_VALUE:
					arg = "" + ((FloatObjectAttributeValue) value).getAttributeValue();
					type = Float.class;
					break;
				case ValuePackage.STRING_ATTRIBUTE_VALUE:
					arg = "" + ((StringAttributeValue) value).getAttributeValue();
					type = String.class;
					break;
				}
				addStartEventParameterRow(parent, p, type, arg);
			});
		});
		final GridData gridData = createStandardLayout();
		gridData.horizontalSpan = 3;
		parent.setLayoutData(gridData);
	}

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
		createTextLabelLayout(parent, "Start event");
		_startEventText = new Text(parent, SWT.SINGLE | SWT.BORDER);
		_startEventText.setLayoutData(createStandardLayout());
		_startEventText.setFont(font);
		_startEventText.setEditable(false);
		_startEventText.addModifyListener(fBasicModifyListener);
		final Button startEventBrowseButton = createPushButton(parent, "Browse", null);

		_startEventParametersGroup = createGroup(parent, "Start Event Parameters");
		final GridData gridData = createStandardLayout();
		gridData.horizontalSpan = 3;
		_startEventParametersGroup.setLayoutData(gridData);
		startEventBrowseButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				if (_languageCombo.getText() == null) {
					setErrorMessage("Please select a language.");
				} else {
					final ElementListSelectionDialog dialog = getStartEventSelectionDialog();
					int res = dialog.open();
					if (res == WizardDialog.OK) {
						final InputEvent event = (InputEvent) (dialog.getFirstResult());
						if (_startEventOccurrence == null || !_startEventOccurrence.getEvent().equals(event)) {
							clearStartEventGroup();
							_startEventText.setText(event.getName());
							_startEventOccurrence = EventBasedDslHelper.createEventOccurrence(event);
							loadEventOccurrenceParameters(_startEventParametersGroup, _startEventOccurrence);
							parent.requestLayout();
							updateLaunchConfigurationDialog();
						}
					}
				}
			}
		});

		return parent;
	}

	private void clearStartEventGroup() {
		Arrays.asList(_startEventParametersGroup.getChildren()).forEach(c -> c.dispose());
		_startEventParameters.clear();
		_startEventText.setText("");
	}

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
