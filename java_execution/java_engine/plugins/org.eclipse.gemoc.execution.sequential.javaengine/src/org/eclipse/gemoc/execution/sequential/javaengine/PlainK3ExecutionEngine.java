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
package org.eclipse.gemoc.execution.sequential.javaengine;

import java.io.IOException;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.transaction.impl.InternalTransactionalEditingDomain;
import org.eclipse.gemoc.executionframework.engine.core.AbstractCommandBasedSequentialExecutionEngine;
import org.eclipse.gemoc.executionframework.engine.core.EngineStoppedException;
import org.eclipse.gemoc.executionframework.event.manager.EventManager;
import org.eclipse.gemoc.executionframework.event.model.event.Event;
import org.eclipse.gemoc.trace.commons.model.launchconfiguration.AddonExtensionParameter;
import org.eclipse.gemoc.trace.commons.model.launchconfiguration.AnimatorURIParameter;
import org.eclipse.gemoc.trace.commons.model.launchconfiguration.LanguageNameParameter;
import org.eclipse.gemoc.trace.commons.model.launchconfiguration.LaunchConfiguration;
import org.eclipse.gemoc.trace.commons.model.launchconfiguration.LaunchconfigurationFactory;
import org.eclipse.gemoc.trace.commons.model.launchconfiguration.ModelURIParameter;
import org.eclipse.gemoc.xdsmlframework.api.core.IRunConfiguration;

import fr.inria.diverse.k3.al.annotationprocessor.stepmanager.IStepManager;
import fr.inria.diverse.k3.al.annotationprocessor.stepmanager.StepCommand;
import fr.inria.diverse.k3.al.annotationprocessor.stepmanager.StepManagerRegistry;

/**
 * Implementation of the GEMOC Execution engine dedicated to run Kermeta 3 operational semantic
 * 
 * @author Didier Vojtisek<didier.vojtisek@inria.fr>
 *
 */
public class PlainK3ExecutionEngine extends AbstractCommandBasedSequentialExecutionEngine implements IStepManager {

	private static final String LAUNCH_CONFIGURATION_TYPE = "org.eclipse.gemoc.execution.sequential.javaengine.ui.launcher";

	private EventManager eventManager = null;
	
	@Override
	public String engineKindName() {
		return "GEMOC Event Driven Execution Engine";
	}

	@Override
	protected void executeEntryPoint() {
		StepManagerRegistry.getInstance().registerManager(PlainK3ExecutionEngine.this);
		try {
			eventManager = getAddonsTypedBy(EventManager.class).stream().findFirst().orElse(null);
			if (eventManager != null) {
				final Event startEvent = getExecutionContext().getRunConfiguration().getStartEvent();
				final boolean waitForEvents = getExecutionContext().getRunConfiguration().getWaitForEvents();
				if (startEvent != null) {
					convertEventToExecutedResource(startEvent, getExecutionContext().getResourceModel());
					eventManager.queueEvent(startEvent);
				}
				if (waitForEvents) {
					// We wait for new events to process until we receive a stop event (TODO?).
					// If a start event has been queued, it will be processed first.
					// We then wait for further events.
					while (true) {
						eventManager.waitForEvents();
						eventManager.processEvents();
					}
				} else {
					// The start event has been queued, so we process it
					// and let the execution unfold from there.
					eventManager.processEvents();
				}
			}
		} catch (EngineStoppedException stopException) {
			throw stopException;
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			StepManagerRegistry.getInstance().unregisterManager(PlainK3ExecutionEngine.this);
		}
	}
	
	private void processEvents() {
		if (eventManager != null) {
			eventManager.processEvents();
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

	@Override
	/*
	 * This is the operation called from K3 code. We use this callback to pass
	 * the command to the generic executeOperation operation. (non-Javadoc)
	 * 
	 * @see fr.inria.diverse.k3.al.annotationprocessor.stepmanager.IStepManager#
	 * executeStep(java.lang.Object,
	 * fr.inria.diverse.k3.al.annotationprocessor.stepmanager.StepCommand,
	 * java.lang.String)
	 */
	public void executeStep(Object caller, Object[] parameters, final StepCommand command, String className, String methodName) {
		processEvents();
		executeOperation(caller, parameters, className, methodName, new Runnable() {
			@Override
			public void run() {
				command.execute();
			}
		});
		processEvents();
	}

	@Override
	/*
	 * This is the operation used to act as a StepManager in K3. We return true
	 * if we have the same editing domain as the object. (non-Javadoc)
	 * 
	 * @see fr.inria.diverse.k3.al.annotationprocessor.stepmanager.IStepManager#
	 * canHandle (java.lang.Object)
	 */
	public boolean canHandle(Object caller) {
		if (caller instanceof EObject) {
			EObject eObj = (EObject) caller;
			org.eclipse.emf.transaction.TransactionalEditingDomain editingDomain = getEditingDomain(eObj);
			return editingDomain == this.editingDomain;

		}
		return false;
	}

	private static TransactionalEditingDomain getEditingDomain(EObject o) {
		return getEditingDomain(o.eResource().getResourceSet());
	}

	private static InternalTransactionalEditingDomain getEditingDomain(ResourceSet rs) {
		TransactionalEditingDomain edomain = org.eclipse.emf.transaction.TransactionalEditingDomain.Factory.INSTANCE
				.getEditingDomain(rs);
		if (edomain instanceof InternalTransactionalEditingDomain)
			return (InternalTransactionalEditingDomain) edomain;
		else
			return null;
	}

	/**
	 * Load the model for the given URI
	 * @param modelURI to load
	 * @return the loaded resource
	 */
	public static Resource loadModel(URI modelURI) {
		ResourceSet resourceSet = new ResourceSetImpl();
		Resource resource = resourceSet.createResource(modelURI);
		try {
			resource.load(null);
		} catch (IOException e) {
			// chut
		}
		return resource;
	}

	@Override
	public LaunchConfiguration extractLaunchConfiguration() {
		final IRunConfiguration configuration = getExecutionContext().getRunConfiguration();
		final LaunchConfiguration launchConfiguration = LaunchconfigurationFactory.eINSTANCE
				.createLaunchConfiguration();
		launchConfiguration.setType(LAUNCH_CONFIGURATION_TYPE);
		if (configuration.getLanguageName() != "") {
			final LanguageNameParameter languageNameParam = LaunchconfigurationFactory.eINSTANCE.createLanguageNameParameter();
			languageNameParam.setValue(configuration.getLanguageName());
			launchConfiguration.getParameters().add(languageNameParam);
		}
		final URI modelURI = configuration.getExecutedModelURI();
		if (modelURI != null) {
			final String scheme = modelURI.scheme() + ":/resource";
			final ModelURIParameter modelURIParam = LaunchconfigurationFactory.eINSTANCE.createModelURIParameter();
			modelURIParam.setValue(modelURI.toString().substring(scheme.length()));
			launchConfiguration.getParameters().add(modelURIParam);
		}
		final URI animatorURI = configuration.getAnimatorURI();
		if (configuration.getAnimatorURI() != null) {
			final String scheme = animatorURI.scheme() + ":/resource";
			final AnimatorURIParameter animatorURIParam = LaunchconfigurationFactory.eINSTANCE.createAnimatorURIParameter();
			animatorURIParam.setValue(animatorURI.toString().substring(scheme.length()));
			launchConfiguration.getParameters().add(animatorURIParam);
		}
		configuration.getEngineAddonExtensions().forEach(extensionPoint -> {
			final AddonExtensionParameter addonExtensionParam = LaunchconfigurationFactory.eINSTANCE.createAddonExtensionParameter();
			addonExtensionParam.setValue(extensionPoint.getName());
			launchConfiguration.getParameters().add(addonExtensionParam);
		});
		return launchConfiguration;
	}
	
	@Override
	public void executeStep(Object caller, StepCommand command, String className, String methodName) {
		executeStep(caller, new Object[] {}, command, className, methodName);
	}
}
