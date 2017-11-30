/*******************************************************************************
 * Copyright (c) 2016 Inria and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Inria - initial API and implementation
 *******************************************************************************/
package org.eclipse.gemoc.execution.sequential.javaengine.ui.launcher;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationType;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.debug.ui.DebugUITools;
import org.eclipse.debug.ui.ILaunchGroup;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.gemoc.commons.eclipse.messagingsystem.api.MessagingSystem;
import org.eclipse.gemoc.commons.eclipse.ui.ViewHelper;
import org.eclipse.gemoc.dsl.debug.ide.IDSLDebugger;
import org.eclipse.gemoc.dsl.debug.ide.adapter.IDSLCurrentInstructionListener;
import org.eclipse.gemoc.dsl.debug.ide.event.DSLDebugEventDispatcher;
import org.eclipse.gemoc.execution.sequential.javaengine.PlainK3ExecutionEngine;
import org.eclipse.gemoc.execution.sequential.javaengine.SequentialModelExecutionContext;
import org.eclipse.gemoc.execution.sequential.javaengine.ui.Activator;
import org.eclipse.gemoc.executionframework.engine.commons.EngineContextException;
import org.eclipse.gemoc.executionframework.engine.commons.ModelExecutionContext;
import org.eclipse.gemoc.executionframework.engine.ui.commons.RunConfiguration;
import org.eclipse.gemoc.executionframework.engine.ui.launcher.AbstractGemocLauncher;
import org.eclipse.gemoc.executionframework.event.bus.DefaultEventBus;
import org.eclipse.gemoc.executionframework.event.bus.DefaultEventBusListener;
import org.eclipse.gemoc.executionframework.event.bus.IEventBus;
import org.eclipse.gemoc.executionframework.event.bus.IEventBusListener;
import org.eclipse.gemoc.executionframework.event.bus.IEventTranslator;
import org.eclipse.gemoc.executionframework.event.manager.EventManager;
import org.eclipse.gemoc.executionframework.event.manager.IEventManager;
import org.eclipse.gemoc.executionframework.event.model.event.Event;
import org.eclipse.gemoc.executionframework.extensions.sirius.services.AbstractGemocDebuggerServices;
import org.eclipse.gemoc.executionframework.ui.views.engine.EnginesStatusView;
import org.eclipse.gemoc.xdsmlframework.api.core.EngineStatus.RunStatus;
import org.eclipse.gemoc.xdsmlframework.api.core.ExecutionMode;
import org.eclipse.gemoc.xdsmlframework.api.core.IExecutionEngine;
import org.eclipse.gemoc.xdsmlframework.api.core.IRunConfiguration;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.PlatformUI;

public class DistributedLauncher extends AbstractGemocLauncher {

	public final static String TYPE_ID = Activator.PLUGIN_ID + ".distributed.launcher";

	// warning this MODEL_ID must be the same as the one in the ModelLoader in order
	// to enable correctly the breakpoints
	public final static String MODEL_ID = org.eclipse.gemoc.executionframework.extensions.sirius.Activator.PLUGIN_ID
			+ ".debugModel";

	protected final List<IExecutionEngine> _executionEngines = new ArrayList<>();

	// progress monitor used during launch; useful for operations that wish to
	// contribute to the progress bar
	protected IProgressMonitor launchProgressMonitor = null;

	protected final static String executionStartedMessage = "Execution started successfully.";

	private IExecutionEngine createExecutionEngine(RunConfiguration runConfiguration, ExecutionMode executionMode)
			throws CoreException, EngineContextException {
		// create and initialize engine
		IExecutionEngine executionEngine = new PlainK3ExecutionEngine();
		ModelExecutionContext executioncontext = new SequentialModelExecutionContext(runConfiguration, executionMode);
		executioncontext.getExecutionPlatform().getModelLoader().setProgressMonitor(this.launchProgressMonitor);
		executioncontext.initializeResourceModel();
		executionEngine.initialize(executioncontext);
		return executionEngine;
	}

	private MessagingSystem getMessagingSystem() {
		return Activator.getDefault().getMessaggingSystem();
	}

	@Override
	public final void launch(final ILaunchConfiguration configuration, final String mode, final ILaunch launch,
			IProgressMonitor monitor) throws CoreException {
		try {

			debug("About to initialize and run the GEMOC Execution Engine...");
			launchProgressMonitor = monitor;
			_executionEngines.clear();

			// make sure to have the engine view when starting the engine
			PlatformUI.getWorkbench().getDisplay().syncExec(new Runnable() {
				@Override
				public void run() {
					ViewHelper.retrieveView(EnginesStatusView.ID);
				}
			});

			// start by fetching all sequential launch configurations
			final ILaunchConfigurationType launchType = DebugPlugin.getDefault().getLaunchManager()
					.getLaunchConfigurationType("org.eclipse.gemoc.execution.sequential.javaengine.ui.launcher");
			final List<ILaunchConfiguration> launchConfigurations = new ArrayList<>(
					Arrays.asList(DebugPlugin.getDefault().getLaunchManager().getLaunchConfigurations(launchType)));
			// fetch the names of all sequential launch configurations that will take part
			// in the execution
			final Set<String> launchConfigurationNames = configuration
					.getAttribute(IRunConfiguration.DISTRIBUTED_LAUNCH_CONFIGURATIONS, Collections.emptySet());
			launchConfigurations.removeIf(c -> !launchConfigurationNames.contains(c.getName()));

			// traceability stuff
			final Map<IExecutionEngine, Event> engineToStartEvent = new HashMap<>();
			final Map<IExecutionEngine, IEventManager> engineToEventManager = new HashMap<>();
			final Map<String, IExecutionEngine> launchConfigurationNameToExecutionEngine = new HashMap<>();

			// create an execution engine for each sequential launch configuration
			_executionEngines.addAll(launchConfigurations.stream().map(c -> {
				try {
					final RunConfiguration runConf = new RunConfiguration(c);
					final Event event = runConf.getStartEvent();
					runConf.clearStartEvent();
					final IExecutionEngine engine = createExecutionEngine(runConf, ExecutionMode.Run);
					engineToEventManager.put(engine, engine.getAddon(EventManager.class));
					if (event != null) {
						convertEventToExecutedResource(event, engine.getExecutionContext().getResourceModel());
						engineToStartEvent.put(engine, event);
					}
					launchConfigurationNameToExecutionEngine.put(c.getName(), engine);
					return engine;
				} catch (CoreException e) {
					e.printStackTrace();
				} catch (EngineContextException e) {
					e.printStackTrace();
				}
				return null;
			}).collect(Collectors.toList()));

			// Instantiate the event bus and connect each execution engine to it.
			final IEventBus eventBus = new DefaultEventBus();
			final List<IConfigurationElement> availableConnectors = Arrays.asList(Platform.getExtensionRegistry()
					.getConfigurationElementsFor("org.eclipse.gemoc.executionframework.event.translator"));
			launchConfigurationNames.forEach(l -> {
				final IExecutionEngine engine = launchConfigurationNameToExecutionEngine.get(l);
				try {
					final String connectorFqn = configuration.getAttribute(l, "");
					final IConfigurationElement confElt = availableConnectors.stream()
							.filter(cElt -> cElt.getAttribute("class").equals(connectorFqn)).findFirst().orElse(null);
					if (confElt != null) {
						final IEventTranslator<?> eventTranslator = (IEventTranslator<?>) confElt
								.createExecutableExtension("class");
						final IEventBusListener listener = new DefaultEventBusListener(engine, eventTranslator);
						eventBus.addListener(listener);
						final IEventManager eventManager = engineToEventManager.get(engine);
						eventManager.addListener((evt) -> {
							eventBus.emitEvent(listener, evt);
						});
					} else {
						final IEventManager eventManager = engineToEventManager.get(engine);
						eventManager.addListener((evt) -> eventBus.emitEvent(null, evt));
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			});

			Job job = new Job(getDebugJobName(configuration, getFirstInstruction(configuration))) {
				@Override
				protected IStatus run(IProgressMonitor monitor) {
					// start all execution engines
					List<Thread> engineThreads = _executionEngines.stream()
							.map(e -> new Thread(() -> e.startSynchronous())).collect(Collectors.toList());

					engineThreads.forEach(t -> t.start());

					// wait for all engines to be waiting for events
					while (!_executionEngines.stream().allMatch(e -> {
						return e.getRunningStatus() == RunStatus.WaitingForEvent;
					})) {
						try {
							Thread.sleep(100);
						} catch (InterruptedException e1) {
							e1.printStackTrace();
						}
					}
					;

					// and start the execution by queuing all start events from all launch
					// configurations
					engineToStartEvent.forEach((engine, event) -> {
						final IEventManager eventManager = engineToEventManager.get(engine);
						eventManager.queueEvent(event);
					});

					return new Status(IStatus.OK, getPluginID(), executionStartedMessage);
				}
			};
			debug("Initialization done, starting engine...");
			job.schedule();

		} catch (Exception e) {
			String message = "Error occured when starting execution engine: " + e.getMessage()
					+ " (see inner exception).";
			// error(message);
			// error(message, e);
			throw new CoreException(new Status(Status.ERROR, getPluginID(), message, e));
		}
	}

	private void convertEventToExecutedResource(Event event, Resource executedResource) {
		event.eClass().getEAllReferences().forEach(r -> {
			final EObject parameter = (EObject) event.eGet(r);
			final Resource parameterResource = parameter.eResource();
			final String uriFragment = parameterResource.getURIFragment(parameter);
			final EObject effectiveParameter = executedResource.getEObject(uriFragment);
			event.eSet(r, effectiveParameter);
		});
	}

	protected final void debug(String message) {
		getMessagingSystem().debug(message, getPluginID());
	}

	protected final void info(String message) {
		getMessagingSystem().info(message, getPluginID());
	}

	protected final void warn(final String message) {
		getMessagingSystem().warn(message, getPluginID());
		PlatformUI.getWorkbench().getDisplay().asyncExec(new Runnable() {
			public void run() {
				MessageDialog.openWarning(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(),
						"GEMOC Engine Launcher", message);
			}
		});
	}

	protected final void error(final String message) {
		getMessagingSystem().error(message, getPluginID());
		PlatformUI.getWorkbench().getDisplay().asyncExec(new Runnable() {
			public void run() {
				MessageDialog.openError(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(),
						"GEMOC Engine Launcher", message);
			}
		});
	}

	@Override
	protected String getDebugTargetName(ILaunchConfiguration configuration, EObject firstInstruction) {
		return "Gemoc debug target";
	}

	@Override
	protected final List<IDSLCurrentInstructionListener> getCurrentInstructionListeners() {
		List<IDSLCurrentInstructionListener> result = super.getCurrentInstructionListeners();
		result.add(AbstractGemocDebuggerServices.LISTENER);
		return result;
	}

	@Override
	protected final EObject getFirstInstruction(ISelection selection) {
		return EcorePackage.eINSTANCE;
	}

	@Override
	protected final EObject getFirstInstruction(IEditorPart editor) {
		return EcorePackage.eINSTANCE;
	}

	@Override
	protected final EObject getFirstInstruction(ILaunchConfiguration configuration) {
		return EcorePackage.eINSTANCE;
	}

	@Override
	protected final ILaunchConfiguration[] createLaunchConfiguration(IResource file, EObject firstInstruction,
			String mode) throws CoreException {
		ILaunchConfiguration[] launchConfigs = super.createLaunchConfiguration(file, firstInstruction, mode);

		if (launchConfigs.length == 1) {
			// open configuration for further editing
			if (launchConfigs[0] instanceof ILaunchConfigurationWorkingCopy) {
				ILaunchConfigurationWorkingCopy configuration = (ILaunchConfigurationWorkingCopy) launchConfigs[0];

				String selectedLanguage = configuration.getAttribute(RunConfiguration.LAUNCH_SELECTED_LANGUAGE, "");
				if (selectedLanguage.equals("")) {

					// TODO try to infer possible language and other attribute
					// from project content and environment
					// setDefaultsLaunchConfiguration(configuration);

					final ILaunchGroup group = DebugUITools.getLaunchGroup(configuration, mode);
					if (group != null) {
						ILaunchConfiguration savedLaunchConfig = configuration.doSave();
						// open configuration for user validation and inputs
						DebugUITools.openLaunchConfigurationDialogOnGroup(
								PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(),
								new StructuredSelection(savedLaunchConfig), group.getIdentifier(), null);
						// DebugUITools.openLaunchConfigurationDialog(PlatformUI.getWorkbench()
						// .getActiveWorkbenchWindow().getShell(),
						// savedLaunchConfig, group.getIdentifier(), null);
					}
				}
			}
		}
		return launchConfigs;

	}

	@Override
	protected String getLaunchConfigurationTypeID() {
		return TYPE_ID;
	}

	@Override
	protected IDSLDebugger getDebugger(ILaunchConfiguration configuration, DSLDebugEventDispatcher dispatcher,
			EObject firstInstruction, IProgressMonitor monitor) {
		return null;
	}

	@Override
	protected String getDebugJobName(ILaunchConfiguration configuration, EObject firstInstruction) {
		return "Gemoc debug job";
	}

	@Override
	protected String getPluginID() {
		return Activator.PLUGIN_ID;
	}

	@Override
	public String getModelIdentifier() {
		return null;
	}

}
