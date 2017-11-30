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

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
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
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EStructuralFeature;
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
import org.eclipse.gemoc.dsl.Dsl;
import org.eclipse.gemoc.dsl.SimpleValue;
import org.eclipse.gemoc.dsl.debug.ide.launch.AbstractDSLLaunchConfigurationDelegate;
import org.eclipse.gemoc.dsl.debug.ide.sirius.ui.launch.AbstractDSLLaunchConfigurationDelegateSiriusUI;
import org.eclipse.gemoc.execution.sequential.javaengine.PlainK3ExecutionEngine;
import org.eclipse.gemoc.execution.sequential.javaengine.ui.Activator;
import org.eclipse.gemoc.execution.sequential.javaengine.ui.launcher.LauncherMessages;
import org.eclipse.gemoc.executionframework.engine.annotations.EventHandler;
import org.eclipse.gemoc.executionframework.engine.commons.DslHelper;
import org.eclipse.gemoc.executionframework.engine.commons.MelangeHelper;
import org.eclipse.gemoc.executionframework.engine.ui.commons.RunConfiguration;
import org.eclipse.gemoc.executionframework.event.manager.IBehavioralAPI;
import org.eclipse.gemoc.executionframework.event.model.event.Event;
import org.eclipse.gemoc.executionframework.event.model.genericevent.GenericEvent;
import org.eclipse.gemoc.executionframework.event.model.genericevent.RuleFQN;
import org.eclipse.gemoc.executionframework.ui.utils.ENamedElementQualifiedNameLabelProvider;
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
public class LaunchConfigurationMainTab extends LaunchConfigurationTab {

	protected Composite _parent;

	protected Text _modelLocationText;
	protected Text _siriusRepresentationLocationText;
	protected Button _animateButton;
	protected Text _delayText;
	protected Text _melangeQueryText;
	protected Button _animationFirstBreak;
	protected Button _waitForEvents;
	protected Text _startEventText;
	protected Combo _languageCombo;

	/**
	 * default width for the grids
	 */
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

		Group modelArea = createGroup(area, "Model:");
		createModelLayout(modelArea, null);

		Group languageArea = createGroup(area, "Language:");
		createLanguageLayout(languageArea, null);

		Group debugArea = createGroup(area, "Animation:");
		createAnimationLayout(debugArea, null);

		Group executionArea = createGroup(area, "Execution:");
		createK3Layout(executionArea, null);

	}

	@Override
	public void setDefaults(ILaunchConfigurationWorkingCopy configuration) {
		configuration.setAttribute(RunConfiguration.LAUNCH_SELECTED_LANGUAGE, "");
	}

	@Override
	public void initializeFrom(ILaunchConfiguration configuration) {
		try {
			RunConfiguration runConfiguration = new RunConfiguration(configuration);
			_modelLocationText.setText(URIHelper.removePlatformScheme(runConfiguration.getExecutedModelURI()));

			if (runConfiguration.getAnimatorURI() != null)
				_siriusRepresentationLocationText
						.setText(URIHelper.removePlatformScheme(runConfiguration.getAnimatorURI()));
			else
				_siriusRepresentationLocationText.setText("");

			_delayText.setText(Integer.toString(runConfiguration.getAnimationDelay()));
			_animationFirstBreak.setSelection(runConfiguration.getBreakStart());
			_waitForEvents.setSelection(runConfiguration.getWaitForEvents());

			_languageCombo.setText(runConfiguration.getLanguageName());
			loadLanguage(runConfiguration.getLanguageName());
			final Event startEvent = runConfiguration.getStartEvent();
			if (startEvent != null) {
				if (startEvent instanceof GenericEvent) {
					loadEventParameters(_startEventParametersGroup, (GenericEvent) startEvent);
				} else {
					loadEventParameters(_startEventParametersGroup, (EObject) startEvent);
				}
			}
		} catch (CoreException e) {
			Activator.error(e.getMessage(), e);
		}
	}

	@Override
	public void performApply(ILaunchConfigurationWorkingCopy configuration) {
		configuration.setAttribute(AbstractDSLLaunchConfigurationDelegate.RESOURCE_URI,
				this._modelLocationText.getText());
		configuration.setAttribute(AbstractDSLLaunchConfigurationDelegateSiriusUI.SIRIUS_RESOURCE_URI,
				this._siriusRepresentationLocationText.getText());
		final String delayText = _delayText.getText();
		if (!delayText.isEmpty()) {
			configuration.setAttribute(RunConfiguration.LAUNCH_DELAY, Integer.parseInt(delayText));
		}
		configuration.setAttribute(RunConfiguration.LAUNCH_SELECTED_LANGUAGE, _languageCombo.getText());
		configuration.setAttribute(RunConfiguration.LAUNCH_MELANGE_QUERY, _melangeQueryText.getText());
		configuration.setAttribute(RunConfiguration.LAUNCH_BREAK_START, _animationFirstBreak.getSelection());
		configuration.setAttribute(RunConfiguration.LAUNCH_WAIT_FOR_EVENTS, _waitForEvents.getSelection());
		configuration.setAttribute(RunConfiguration.LAUNCH_START_EVENT, _startEventText.getText());
		configuration.setAttribute(RunConfiguration.LAUNCH_START_EVENT_PARAMETERS,
				_startEventParameters.stream().map(t -> t.getText()).collect(Collectors.toList()));
		// DebugModelID for sequential engine
		configuration.setAttribute(RunConfiguration.DEBUG_MODEL_ID, Activator.DEBUG_MODEL_ID);
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
		public void modifyText(ModifyEvent arg0) {
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
				// handleModelLocationButtonSelected();
				// TODO launch the appropriate selector
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

		_siriusRepresentationLocationText = new Text(parent, SWT.SINGLE | SWT.BORDER);
		_siriusRepresentationLocationText.setLayoutData(createStandardLayout());
		_siriusRepresentationLocationText.setFont(font);
		_siriusRepresentationLocationText.addModifyListener(fBasicModifyListener);
		Button siriusRepresentationLocationButton = createPushButton(parent, "Browse", null);
		siriusRepresentationLocationButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent evt) {
				// handleModelLocationButtonSelected();
				// TODO launch the appropriate selector
				SelectAIRDIFileDialog dialog = new SelectAIRDIFileDialog();
				if (dialog.open() == Dialog.OK) {
					String modelPath = ((IResource) dialog.getResult()[0]).getFullPath().toPortableString();
					_siriusRepresentationLocationText.setText(modelPath);
					updateLaunchConfigurationDialog();
				}
			}
		});

		createTextLabelLayout(parent, "Delay");
		_delayText = new Text(parent, SWT.SINGLE | SWT.BORDER);
		_delayText.setLayoutData(createStandardLayout());
		_delayText.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent e) {
				updateLaunchConfigurationDialog();
			}
		});
		createTextLabelLayout(parent, "(in milliseconds)");

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

	private Dsl dsl;
	private Bundle bundle;
	private List<EPackage> packages;
	private List<Class<?>> aspects;
	private IBehavioralAPI behavioralAPI;

	private void loadBehavioralAPI(String languageName) {
		IConfigurationElement[] confElts = Platform.getExtensionRegistry()
				.getConfigurationElementsFor("org.eclipse.gemoc.executionframework.event.api");
		for (IConfigurationElement confElt : confElts) {
			String xdsmlName = confElt.getAttribute("language");
			if (xdsmlName.equals(languageName)) {
				try {
					behavioralAPI = (IBehavioralAPI) confElt.createExecutableExtension("class");
					break;
				} catch (CoreException e) {
					e.printStackTrace();
				}
			}
		}
	}

	private void loadLanguage(String languageName) {
		if (languageName.length() > 0) {
			loadBehavioralAPI(languageName);
			final ResourceSet resSet = new ResourceSetImpl();
			IConfigurationElement[] languages = Platform.getExtensionRegistry()
					.getConfigurationElementsFor("org.eclipse.gemoc.gemoc_language_workbench.sequential.xdsml");
			for (IConfigurationElement lang : languages) {
				String xdsmlPath = lang.getAttribute("xdsmlFilePath");
				String xdsmlName = lang.getAttribute("name");
				if (xdsmlName.equals(languageName) && xdsmlPath.endsWith(".dsl")) {
					Resource res = resSet.getResource(URI.createURI(xdsmlPath), true);
					dsl = (Dsl) res.getContents().get(0);
					bundle = Platform.getBundle(lang.getContributor().getName());
				}
			}

			if (dsl != null) {
				final List<String> nsURIs = dsl.getAbstractSyntax().getValues().stream()
						.flatMap(v -> ((SimpleValue) v).getValues().stream())
						.map(s -> URI.createURI(s.replace("platform:/resource", "platform:/plugin"), true))
						.map(uri -> ((EPackage) resSet.getResource(uri, true).getContents().get(0)).getNsURI())
						.collect(Collectors.toList());
				packages = nsURIs.stream().map(uri -> {
					return Arrays
							.asList(Platform.getExtensionRegistry()
									.getConfigurationElementsFor("org.eclipse.emf.ecore.generated_package"))
							.stream().filter(c -> c.getAttribute("uri").equals(uri)).map(c -> {
								EPackage result = null;
								try {
									List<Field> fields = Arrays.asList(Platform.getBundle(c.getContributor().getName())
											.loadClass(c.getAttribute("class")).getFields());
									result = fields.stream().filter(f -> f.getName().equals("eINSTANCE")).findFirst()
											.map(f -> {
												try {
													return (EPackage) f.get(null);
												} catch (IllegalArgumentException e) {
													e.printStackTrace();
												} catch (IllegalAccessException e) {
													e.printStackTrace();
												}
												return null;
											}).orElse(null);
								} catch (SecurityException e1) {
									e1.printStackTrace();
								} catch (ClassNotFoundException e1) {
									e1.printStackTrace();
								}
								return result;
							}).filter(p -> p != null).findFirst().orElse(null);
				}).collect(Collectors.toList());
				if (behavioralAPI == null) {
					final List<String> classNames = dsl.getSemantic().getValues().stream()
							.filter(v -> v.getName().equals("k3")).flatMap(v -> ((SimpleValue) v).getValues().stream())
							.collect(Collectors.toList());
					aspects = classNames.stream().map(cn -> {
						Class<?> result = null;
						try {
							result = bundle.loadClass(cn);
						} catch (ClassNotFoundException e) {
							e.printStackTrace();
						}
						return result;
					}).filter(c -> c != null).collect(Collectors.toList());
				}
			} else {
				// TODO error no dsl found
			}

		}
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
		_languageCombo = new Combo(parent, SWT.READ_ONLY);
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

	private ElementListSelectionDialog getStartEventSelectionDialog() {
		final Set<Object> events = new HashSet<>();
		if (behavioralAPI != null) {
			events.addAll(behavioralAPI.getStartEventClasses());
			if (events.isEmpty()) {
				events.addAll(behavioralAPI.getEventClasses());
			}
		} else if (aspects == null || aspects.isEmpty()) {
			// TODO error
		} else {
			events.addAll(aspects.stream().flatMap(c -> Arrays.asList(c.getMethods()).stream()).filter(m -> {
				final EventHandler annotation = m.getAnnotation(EventHandler.class);
				return annotation != null && annotation.startEvent();
			}).collect(Collectors.toList()));
			if (events.isEmpty()) {
				events.addAll(aspects.stream().flatMap(c -> Arrays.asList(c.getMethods()).stream())
						.filter(m -> m.getAnnotation(EventHandler.class) != null).collect(Collectors.toList()));
			}
		}

		final ILabelProvider labelProvider = new LabelProvider() {
			@Override
			public String getText(Object element) {
				if (element instanceof EClass) {
					final EClass event = (EClass) element;
					return event.getName();
				} else if (element instanceof Method) {
					final Method method = (Method) element;
					final Class<?> clazz = method.getDeclaringClass();
					return clazz.getPackage().getName() + "." + clazz.getSimpleName() + "." + method.getName();
				} else {
					return super.getText(element);
				}
			};
		};

		final ElementListSelectionDialog dialog = new ElementListSelectionDialog(
				PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), labelProvider);
		dialog.setElements(events.toArray());

		return dialog;
	}

	private void doStuff(Composite parent, String name, Class<?> type, String param) {
		if (type == null) {
			// TODO error
		} else {
			createTextLabelLayout(parent, name);
			final Text text = new Text(parent, SWT.SINGLE | SWT.BORDER);
			_startEventParameters.add(text);
			text.setLayoutData(createStandardLayout());
			if (type.isPrimitive() || type.equals(String.class)) {
				text.setEditable(true);
				createTextLabelLayout(parent, "");
			} else {
				text.setEditable(false);
				final Button parameterBrowseButton = createPushButton(parent, "Browse", null);
				parameterBrowseButton.addSelectionListener(new SelectionAdapter() {
					public void widgetSelected(SelectionEvent e) {
						// ModelExecutionContext executionContext = null;
						// try {
						// executionContext = new SequentialModelExecutionContext(
						// new ModelLoadingRunConfiguration(
						// URI.createPlatformResourceURI(_modelLocationText.getText(), true),
						// _melangeQueryText.getText()), ExecutionMode.Run);
						// } catch (EngineContextException e1) {
						// e1.printStackTrace();
						// }
						// Resource model = executionContext == null ? null :
						// modelLoader.loadModel(executionContext);
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
			text.setText(param);
			text.addModifyListener(fBasicModifyListener);
		}
	}

	private Object _startEventHandler = null;
	private Group _startEventParametersGroup;
	private final List<Text> _startEventParameters = new ArrayList<>();

	private void loadEventParameters(Composite parent, EObject event) {
		_startEventHandler = event.eClass();
		final EClass handler = (EClass) _startEventHandler;
		_startEventText.setText(handler.getName());
		final List<EStructuralFeature> parameters = handler.getEAllStructuralFeatures();
		for (int i = 0; i < parameters.size(); i++) {
			final EStructuralFeature p = parameters.get(i);
			String param = "";
			if (p instanceof EAttribute) {
				final Object parameter = event.eGet(p);
				if (parameter != null) {
					param = parameter.toString();
				}
			} else {
				final EObject o = (EObject) event.eGet(p);
				if (o != null) {
					param = o.eResource().getURIFragment(o);
				}
			}
			Class<?> type = p.getEType().getInstanceClass();
			if (type == null) {
				type = packages.stream().map(pkg -> pkg.getEClassifier(p.getEType().getName())).filter(cl -> cl != null)
						.findFirst().map(cl -> cl.getInstanceClass()).orElse(null);
			}
			doStuff(parent, p.getName() + ": " + type.getSimpleName(), type, param);
		}
		final GridData gridData = createStandardLayout();
		gridData.horizontalSpan = 3;
		parent.setLayoutData(gridData);
	}

	private void loadEventParameters(Composite parent, GenericEvent event) {
		if (event.getRuleID() instanceof RuleFQN) {
			final String fqn = ((RuleFQN) event.getRuleID()).getFqn();
			final int lastSeparator = fqn.lastIndexOf(".");
			if (lastSeparator == -1) {
				System.out.println(event);
			} else {
				try {
					Class<?> clazz = bundle.loadClass(fqn.substring(0, fqn.lastIndexOf(".")));
					final Method handler = Arrays.asList(clazz.getMethods()).stream().filter(m -> {
						return m.getAnnotation(EventHandler.class) != null
								&& m.getName() == fqn.substring(fqn.lastIndexOf(".") + 1);
					}).findFirst().orElse(null);
					_startEventHandler = handler;
					if (handler != null) {
						_startEventText.setText(clazz.getSimpleName() + "." + handler.getName());
						final List<Class<?>> parameters = Arrays.asList(handler.getParameterTypes());
						for (int i = 0; i < parameters.size(); i++) {
							final Class<?> type = parameters.get(i);
							final Value v = event.getValues().get(i);
							String param = "";
							switch (v.eClass().getClassifierID()) {
							case ValuePackage.SINGLE_REFERENCE_VALUE:
								final EObject o = ((SingleReferenceValue) v).getReferenceValue();
								param = "" + o.eResource().getURIFragment(o);
								break;
							case ValuePackage.BOOLEAN_ATTRIBUTE_VALUE:
								param = "" + ((BooleanAttributeValue) v).isAttributeValue();
								break;
							case ValuePackage.BOOLEAN_OBJECT_ATTRIBUTE_VALUE:
								param = "" + ((BooleanObjectAttributeValue) v).getAttributeValue();
								break;
							case ValuePackage.INTEGER_ATTRIBUTE_VALUE:
								param = "" + ((IntegerAttributeValue) v).getAttributeValue();
								break;
							case ValuePackage.INTEGER_OBJECT_ATTRIBUTE_VALUE:
								param = "" + ((IntegerObjectAttributeValue) v).getAttributeValue();
								break;
							case ValuePackage.FLOAT_ATTRIBUTE_VALUE:
								param = "" + ((FloatAttributeValue) v).getAttributeValue();
								break;
							case ValuePackage.FLOAT_OBJECT_ATTRIBUTE_VALUE:
								param = "" + ((FloatObjectAttributeValue) v).getAttributeValue();
								break;
							case ValuePackage.STRING_ATTRIBUTE_VALUE:
								param = "" + ((StringAttributeValue) v).getAttributeValue();
								break;
							}

							doStuff(parent, type.getSimpleName(), type, param);
						}
						final GridData gridData = createStandardLayout();
						gridData.horizontalSpan = 3;
						parent.setLayoutData(gridData);
					}

				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	private void loadEventParameters(Composite parent) {
		if (behavioralAPI != null) {
			final EClass handler = (EClass) _startEventHandler;
			_startEventText.setText(handler.getName());
			final List<EStructuralFeature> parameters = handler.getEAllStructuralFeatures();
			for (int i = 0; i < parameters.size(); i++) {
				final EStructuralFeature p = parameters.get(i);
				Class<?> type = p.getEType().getInstanceClass();
				if (type == null) {
					type = packages.stream().map(pkg -> pkg.getEClassifier(p.getEType().getName()))
							.filter(cl -> cl != null).findFirst().map(cl -> cl.getInstanceClass()).orElse(null);
				}
				doStuff(parent, p.getName() + ": " + type.getSimpleName(), type, "");
			}
		} else {
			final Method handler = (Method) _startEventHandler;
			final Class<?> clazz = handler.getDeclaringClass();
			_startEventText.setText(clazz.getSimpleName() + "." + handler.getName());
			final List<Class<?>> parameters = Arrays.asList(handler.getParameterTypes());
			for (int i = 0; i < parameters.size(); i++) {
				final Class<?> type = parameters.get(i);
				doStuff(parent, type.getSimpleName(), type, "");
			}
		}
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
						if (_startEventHandler == null || !_startEventHandler.equals(dialog.getFirstResult())) {
							Arrays.asList(_startEventParametersGroup.getChildren()).forEach(c -> c.dispose());
							_startEventParameters.clear();
							_startEventHandler = dialog.getFirstResult();
							loadEventParameters(_startEventParametersGroup);
							parent.requestLayout();
							updateLaunchConfigurationDialog();
						}
					}
				}
			}
		});

		return parent;
	}

	@Override
	protected void updateLaunchConfigurationDialog() {
		super.updateLaunchConfigurationDialog();
		_melangeQueryText.setText(computeMelangeQuery());
	}

	/**
	 * compute the Melange query for loading the given model as the requested
	 * language If the language is already the good one, the query will be empty.
	 * (ie. melange downcast is not used)
	 * 
	 * @return
	 */
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
		URI modelURI = URI.createPlatformResourceURI(_modelLocationText.getText(), true);
		if (currentModelResource == null || !currentModelResource.getURI().equals(modelURI)) {
			currentModelResource = PlainK3ExecutionEngine.loadModel(modelURI);
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
