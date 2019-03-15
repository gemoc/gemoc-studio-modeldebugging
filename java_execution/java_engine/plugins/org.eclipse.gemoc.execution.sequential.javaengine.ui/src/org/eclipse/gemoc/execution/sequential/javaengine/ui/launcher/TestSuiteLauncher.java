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
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.concurrent.LinkedTransferQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TransferQueue;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationType;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.debug.ui.DebugUITools;
import org.eclipse.debug.ui.ILaunchGroup;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.gemoc.commons.eclipse.messagingsystem.api.MessagingSystem;
import org.eclipse.gemoc.commons.eclipse.ui.ViewHelper;
import org.eclipse.gemoc.dsl.debug.ide.IDSLDebugger;
import org.eclipse.gemoc.dsl.debug.ide.adapter.IDSLCurrentInstructionListener;
import org.eclipse.gemoc.dsl.debug.ide.event.DSLDebugEventDispatcher;
import org.eclipse.gemoc.dsl.debug.ide.launch.AbstractDSLLaunchConfigurationDelegate;
import org.eclipse.gemoc.execution.sequential.javaengine.EventBasedExecutionEngine;
import org.eclipse.gemoc.execution.sequential.javaengine.EventBasedModelExecutionContext;
import org.eclipse.gemoc.execution.sequential.javaengine.EventBasedRunConfiguration;
import org.eclipse.gemoc.execution.sequential.javaengine.ui.Activator;
import org.eclipse.gemoc.executionframework.engine.commons.EngineContextException;
import org.eclipse.gemoc.executionframework.engine.ui.launcher.AbstractGemocLauncher;
import org.eclipse.gemoc.executionframework.event.manager.GenericEventManager;
import org.eclipse.gemoc.executionframework.event.manager.IEventManagerListener;
import org.eclipse.gemoc.executionframework.event.model.event.EventOccurrence;
import org.eclipse.gemoc.executionframework.event.testsuite.TestCase;
import org.eclipse.gemoc.executionframework.event.testsuite.TestCaseReport;
import org.eclipse.gemoc.executionframework.event.testsuite.TestSuite;
import org.eclipse.gemoc.executionframework.event.testsuite.TestSuiteReport;
import org.eclipse.gemoc.executionframework.event.testsuite.TestsuiteFactory;
import org.eclipse.gemoc.executionframework.extensions.sirius.services.AbstractGemocDebuggerServices;
import org.eclipse.gemoc.executionframework.ui.views.engine.EnginesStatusView;
import org.eclipse.gemoc.xdsmlframework.api.core.ExecutionMode;
import org.eclipse.gemoc.xdsmlframework.api.core.IExecutionEngine;
import org.eclipse.gemoc.xdsmlframework.api.engine_addon.IEngineAddon;
import org.eclipse.gemoc.xdsmlframework.behavioralinterface.behavioralInterface.BehavioralInterface;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.PlatformUI;

public class TestSuiteLauncher extends AbstractGemocLauncher<EventBasedModelExecutionContext> {

	public final static String TYPE_ID = Activator.PLUGIN_ID + ".testsuite.launcher";

	// warning this MODEL_ID must be the same as the one in the ModelLoader in order
	// to enable correctly the breakpoints
	public final static String MODEL_ID = org.eclipse.gemoc.executionframework.extensions.sirius.Activator.PLUGIN_ID
			+ ".debugModel";

	protected final List<EventBasedExecutionEngine> _executionEngines = new ArrayList<>();

	// progress monitor used during launch; useful for operations that wish to
	// contribute to the progress bar
	protected IProgressMonitor launchProgressMonitor = null;

	protected final static String executionStartedMessage = "Execution started successfully.";

	private final ILaunchConfigurationType launchType = DebugPlugin.getDefault().getLaunchManager()
			.getLaunchConfigurationType("org.eclipse.gemoc.execution.sequential.javaengine.ui.launcher");

	private EventBasedExecutionEngine createExecutionEngine(EventBasedRunConfiguration runConfiguration, ExecutionMode executionMode)
			throws CoreException, EngineContextException {
		// create and initialize engine
		EventBasedExecutionEngine executionEngine = new EventBasedExecutionEngine();
		EventBasedModelExecutionContext executioncontext = new EventBasedModelExecutionContext(runConfiguration, executionMode);
		executioncontext.getExecutionPlatform().getModelLoader().setProgressMonitor(this.launchProgressMonitor);
		executioncontext.initializeResourceModel();
		executionEngine.initialize(executioncontext);
		return executionEngine;
	}

	private MessagingSystem getMessagingSystem() {
		return Activator.getDefault().getMessaggingSystem();
	}

	private ILaunchConfiguration getLaunchConfiguration(TestCase testCase, String languageName, Set<String> implRelIds, Set<String> subtypeRelIds) throws CoreException {
		final ILaunchConfigurationWorkingCopy configuration = launchType.newInstance(null, testCase.getName());
		final String modelLocation;
		if (testCase.getModel().eIsProxy()) {
			modelLocation = testCase.getModel().eResource().getURI().toPlatformString(true);
		} else {
			modelLocation = testCase.getModel().eResource().getURI().toPlatformString(true);
		}
		configuration.setAttribute(AbstractDSLLaunchConfigurationDelegate.RESOURCE_URI, modelLocation);
		configuration.setAttribute(EventBasedRunConfiguration.LAUNCH_SELECTED_LANGUAGE, languageName);
		configuration.setAttribute(EventBasedRunConfiguration.WAIT_FOR_EVENT, true);
		configuration.setAttribute(EventBasedRunConfiguration.IMPL_REL_IDS, implRelIds);
		configuration.setAttribute(EventBasedRunConfiguration.SUBTYPE_REL_IDS, subtypeRelIds);
		configuration.setAttribute(EventBasedRunConfiguration.DEBUG_MODEL_ID, Activator.DEBUG_MODEL_ID);
		return configuration;
	}

	@Override
	public final void launch(final ILaunchConfiguration configuration, final String mode, final ILaunch launch,
			IProgressMonitor monitor) throws CoreException {
		try {
			final URI testSuiteURI = URI.createPlatformResourceURI(configuration.getAttribute("TEST_SUITE_URI", ""), true);
			final String testReportFileName = testSuiteURI.trimFileExtension().lastSegment() + "Report";
			final ResourceSet resSet = new ResourceSetImpl();
			final TestSuite testSuite = resSet.getResource(testSuiteURI, true).getContents().stream()
					.filter(o -> o instanceof TestSuite).findFirst().map(o -> (TestSuite) o).orElse(null);
			EcoreUtil.resolveAll(testSuite);
			final Resource reportResource = resSet.createResource(testSuiteURI.trimSegments(1).appendSegment(testReportFileName).appendFileExtension("xmi"));
			final String languageName = configuration.getAttribute("LANGUAGE_NAME", "");
			final Set<String> implRelIds = configuration.getAttribute("IMPL_REL_IDS", Collections.emptySet());
			final Set<String> subtypeRelIds = configuration.getAttribute("SUBTYPE_REL_IDS", Collections.emptySet());

			// make sure to have the engine view when starting the engine
			PlatformUI.getWorkbench().getDisplay().syncExec(new Runnable() {
				@Override
				public void run() {
					ViewHelper.retrieveView(EnginesStatusView.ID);
				}
			});
			
			final TestSuiteReport report = TestsuiteFactory.eINSTANCE.createTestSuiteReport();
			reportResource.getContents().add(report);
			
			for (TestCase testCase : testSuite.getTestCases()) {
				try {
					final ILaunchConfiguration launchConf = getLaunchConfiguration(testCase, languageName, implRelIds, subtypeRelIds);
					final EventBasedRunConfiguration runConf = new EventBasedRunConfiguration(launchConf);
					final EventBasedExecutionEngine engine = createExecutionEngine(runConf, ExecutionMode.Run);
					final LinkedTransferQueue<EventOccurrence> eventOccurrences = new LinkedTransferQueue<>();
					
					Job job = new Job(getDebugJobName(configuration, getFirstInstruction(configuration))) {
						@Override
						protected IStatus run(IProgressMonitor monitor) {
							engine.startSynchronous();
							return new Status(IStatus.OK, getPluginID(), executionStartedMessage);
						}
					};
					final TransferQueue<Object> queue = new LinkedTransferQueue<>();
					engine.getExecutionContext().getExecutionPlatform().addEngineAddon(new IEngineAddon() {
						@Override
						public void engineInitialized(IExecutionEngine<?> executionEngine) {
							queue.add(new Object());
						}
					});
					job.schedule();
					boolean result = true;
					if (queue.poll(5000, TimeUnit.MILLISECONDS) != null) {
						final GenericEventManager eventManager = engine.getAddon(GenericEventManager.class);
						eventManager.addListener(new IEventManagerListener() {
							@Override
							public void eventReceived(EventOccurrence e) {
								eventOccurrences.add(e);
							}
							@Override
							public Set<BehavioralInterface> getBehavioralInterfaces() {
								return eventManager.getBehavioralInterfaces();
							}
						});
						result = testCase.getScenario().stream().map(eventOccurrence -> {
							switch (eventOccurrence.getType()) {
							case ACCEPTED:
								eventManager.processEventOccurrence(eventOccurrence);
								return true;
							case EXPOSED:
								try {
									EventOccurrence occ = eventOccurrences.poll(10000, TimeUnit.MILLISECONDS);
									while (occ != null && !occ.getEvent().getName().equals(eventOccurrence.getEvent().getName())) {
										occ = eventOccurrences.poll(5000, TimeUnit.MILLISECONDS);
									}
									return occ != null && occ.getEvent().getName().equals(eventOccurrence.getEvent().getName());
								} catch (InterruptedException e) {
									e.printStackTrace();
								}
								break;
							default:
								break;
							}
							return false;
						}).reduce((b1, b2) -> b1 && b2).orElse(false);
					}
					job.join();

					final TestCaseReport caseReport;
					if (result) {
						caseReport = TestsuiteFactory.eINSTANCE.createTestCaseSuccess();
					} else {
						caseReport = TestsuiteFactory.eINSTANCE.createTestCaseFailure();
					}
					caseReport.setTestCase(testCase);
					report.getTestCaseReports().add(caseReport);
				} catch (CoreException e) {
					e.printStackTrace();
				} catch (EngineContextException e) {
					e.printStackTrace();
				}
			}
			
			reportResource.save(Collections.emptyMap());
		} catch (Exception e) {
			String message = "Error occured when starting execution engine: " + e.getMessage()
					+ " (see inner exception).";
			throw new CoreException(new Status(Status.ERROR, getPluginID(), message, e));
		}
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

				String selectedLanguage = configuration.getAttribute(EventBasedRunConfiguration.LAUNCH_SELECTED_LANGUAGE, "");
				if (selectedLanguage.equals("")) {
					final ILaunchGroup group = DebugUITools.getLaunchGroup(configuration, mode);
					if (group != null) {
						ILaunchConfiguration savedLaunchConfig = configuration.doSave();
						DebugUITools.openLaunchConfigurationDialogOnGroup(
								PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(),
								new StructuredSelection(savedLaunchConfig), group.getIdentifier(), null);
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

	@Override
	public EventBasedExecutionEngine getExecutionEngine() {
		return null;
	}

}
