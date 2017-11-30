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
package org.eclipse.gemoc.executionframework.engine.ui.commons;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Platform;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.gemoc.commons.value.model.value.BooleanAttributeValue;
import org.eclipse.gemoc.commons.value.model.value.FloatAttributeValue;
import org.eclipse.gemoc.commons.value.model.value.IntegerAttributeValue;
import org.eclipse.gemoc.commons.value.model.value.SingleReferenceValue;
import org.eclipse.gemoc.commons.value.model.value.StringAttributeValue;
import org.eclipse.gemoc.commons.value.model.value.ValueFactory;
import org.eclipse.gemoc.dsl.debug.ide.launch.AbstractDSLLaunchConfigurationDelegate;
import org.eclipse.gemoc.executionframework.event.manager.IBehavioralAPI;
import org.eclipse.gemoc.executionframework.event.model.event.Event;
import org.eclipse.gemoc.executionframework.event.model.genericevent.GenericEvent;
import org.eclipse.gemoc.executionframework.event.model.genericevent.GenericeventFactory;
import org.eclipse.gemoc.executionframework.event.model.genericevent.RuleFQN;
import org.eclipse.gemoc.xdsmlframework.api.core.IRunConfiguration;
import org.eclipse.gemoc.xdsmlframework.api.extensions.engine_addon.EngineAddonSpecificationExtension;
import org.eclipse.gemoc.xdsmlframework.api.extensions.engine_addon.EngineAddonSpecificationExtensionPoint;

public class RunConfiguration implements IRunConfiguration {

	protected ILaunchConfiguration _launchConfiguration;

	public RunConfiguration(ILaunchConfiguration launchConfiguration) throws CoreException {
		_launchConfiguration = launchConfiguration;
		extractInformation();
	}

	protected void extractInformation() throws CoreException {
		_languageName = getAttribute(LAUNCH_SELECTED_LANGUAGE, "");
		_modelURI = URI.createPlatformResourceURI(getAttribute(AbstractDSLLaunchConfigurationDelegate.RESOURCE_URI, ""),
				true);
		String animatorURIAsString = getAttribute("airdResource", "");
		if (animatorURIAsString != null && !animatorURIAsString.equals("")) {
			_animatorURI = URI.createPlatformResourceURI(animatorURIAsString, true);
			_animationDelay = getAttribute(LAUNCH_DELAY, 0);
		}
		_deadlockDetectionDepth = getAttribute(LAUNCH_DEADLOCK_DETECTION_DEPTH, 10);
		_melangeQuery = getAttribute(LAUNCH_MELANGE_QUERY, "");

		for (EngineAddonSpecificationExtension extension : EngineAddonSpecificationExtensionPoint.getSpecifications()) {
			_engineAddonExtensions.put(extension, getAttribute(extension.getName(), false));
		}
		
		String startEvent = getAttribute(LAUNCH_START_EVENT, "");
		if (!startEvent.isEmpty()) {
			List<String> startEventParameters = getAttribute(LAUNCH_START_EVENT_PARAMETERS, new ArrayList<String>());
			IBehavioralAPI behavioralAPI = null;
			
			IConfigurationElement[] confElts = Platform.getExtensionRegistry().getConfigurationElementsFor("org.eclipse.gemoc.executionframework.event.api");
			for (IConfigurationElement confElt : confElts) {
				String xdsmlName = confElt.getAttribute("language");
				if (xdsmlName.equals(_languageName)) {
					try {
						behavioralAPI = (IBehavioralAPI) confElt.createExecutableExtension("class");
						break;
					} catch (CoreException e) {
						e.printStackTrace();
					}
				}
			}
			
			ResourceSet resourceSet = new ResourceSetImpl();
			Resource resource = resourceSet.createResource(_modelURI);
			try {
				resource.load(null);
			} catch (IOException e) {
			}
			
			if (behavioralAPI != null) {
				final EClass eventClass = behavioralAPI.getEventClasses().stream().filter(c -> c.getName().equals(startEvent)).findFirst().orElse(null);
				if (eventClass != null) {
					_startEvent = (Event) eventClass.getEPackage().getEFactoryInstance().create(eventClass);
					final List<EStructuralFeature> structuralFeatures = eventClass.getEAllStructuralFeatures();
					if (startEventParameters.size() != structuralFeatures.size()) {
						//Invalid (usually empty) parameters config
						_startEvent = null;
					} else {
						for (int i = 0; i < structuralFeatures.size(); i++) {
							final String param = startEventParameters.get(i);
							if (param.length() > 0) {
								final EStructuralFeature f = structuralFeatures.get(i);
								if (f instanceof EReference) {
									_startEvent.eSet(f, resource.getEObject(param));
								} else {
									switch (((EAttribute) f).getEAttributeType().getInstanceClassName()) {
									case "boolean":
									case "java.lang.Boolean":
										_startEvent.eSet(f, Boolean.parseBoolean(param));
										break;
									case "int":
									case "java.lang.Integer":
										try {
											_startEvent.eSet(f, Integer.parseInt(param));
										} catch (NumberFormatException e) {}
										break;
									case "float":
									case "java.lang.Float":
										try {
											_startEvent.eSet(f, Float.parseFloat(param));
										} catch (NumberFormatException e) {}
										break;
									case "java.lang.String":
										_startEvent.eSet(f, param);
										break;
									};
								}
							}
						}
					}
				}
			} else {
				if (startEvent.lastIndexOf(".") != -1) {
					_startEvent = GenericeventFactory.eINSTANCE.createGenericEvent();
					final GenericEvent event = ((GenericEvent) _startEvent);
					final RuleFQN ruleID = GenericeventFactory.eINSTANCE.createRuleFQN();
					ruleID.setFqn(startEvent);
					event.setRuleID(ruleID);
					for (int i = 0; i < startEventParameters.size(); i++) {
						final String param = startEventParameters.get(i);
						if (param.startsWith("/")) {
							SingleReferenceValue refVal = ValueFactory.eINSTANCE.createSingleReferenceValue();
							refVal.setReferenceValue(resource.getEObject(param));
							event.getValues().add(refVal);
						} else {
							if (param.equals("true")) {
								BooleanAttributeValue attVal = ValueFactory.eINSTANCE.createBooleanAttributeValue();
								attVal.setAttributeValue(true);
								event.getValues().add(attVal);
							} else if (param.equals("false")) {
								BooleanAttributeValue attVal = ValueFactory.eINSTANCE.createBooleanAttributeValue();
								attVal.setAttributeValue(false);
								event.getValues().add(attVal);
							} else {
								try {
									int v = Integer.parseInt(param);
									IntegerAttributeValue attVal = ValueFactory.eINSTANCE.createIntegerAttributeValue();
									attVal.setAttributeValue(v);
									event.getValues().add(attVal);
									continue;
								} catch (NumberFormatException e) {}
								try {
									float v = Float.parseFloat(param);
									FloatAttributeValue attVal = ValueFactory.eINSTANCE.createFloatAttributeValue();
									attVal.setAttributeValue(v);
									event.getValues().add(attVal);
									continue;
								} catch (NumberFormatException e) {}
								StringAttributeValue attVal = ValueFactory.eINSTANCE.createStringAttributeValue();
								attVal.setAttributeValue(param);
								event.getValues().add(attVal);
							}
						}
					}
				}
			}
		}

		_waitForEvents = getAttribute(LAUNCH_WAIT_FOR_EVENTS, Boolean.FALSE);
		_breakStart = getAttribute(LAUNCH_BREAK_START, Boolean.FALSE);
		_debugModelID = getAttribute(DEBUG_MODEL_ID, ".debugModel");
	}

	protected String getAttribute(String attributeName, String defaultValue) throws CoreException {
		return _launchConfiguration.getAttribute(attributeName, defaultValue);
	}

	protected List<String> getAttribute(String attributeName, List<String> defaultValue) throws CoreException {
		return _launchConfiguration.getAttribute(attributeName, defaultValue);
	}

	protected Integer getAttribute(String attributeName, Integer defaultValue) throws CoreException {
		return _launchConfiguration.getAttribute(attributeName, defaultValue);
	}

	protected Boolean getAttribute(String attributeName, Boolean defaultValue) throws CoreException {
		return _launchConfiguration.getAttribute(attributeName, defaultValue);
	}

	private int _animationDelay = 0;

	public int getAnimationDelay() {
		return _animationDelay;
	}

	private URI _modelURI;

	@Override
	public URI getExecutedModelURI() {
		return _modelURI;
	}

	private String _melangeQuery = "";

	@Override
	public String getMelangeQuery() {
		return _melangeQuery;
	}

	@Override
	public URI getExecutedModelAsMelangeURI() {
		if (_melangeQuery.isEmpty())
			return _modelURI;
		String melangeURIString = _modelURI.toString().replace("platform:/", "melange:/") + _melangeQuery;
		return URI.createURI(melangeURIString);
	}

	private URI _animatorURI;

	@Override
	public URI getAnimatorURI() {
		return _animatorURI;
	}

	private int _deadlockDetectionDepth = 0;

	@Override
	public int getDeadlockDetectionDepth() {
		return _deadlockDetectionDepth;
	}

	private HashMap<EngineAddonSpecificationExtension, Boolean> _engineAddonExtensions = new HashMap<>();

	@Override
	public Collection<EngineAddonSpecificationExtension> getEngineAddonExtensions() {
		ArrayList<EngineAddonSpecificationExtension> result = new ArrayList<EngineAddonSpecificationExtension>();
		for (Entry<EngineAddonSpecificationExtension, Boolean> e : _engineAddonExtensions.entrySet()) {
			if (e.getValue())
				result.add(e.getKey());
		}
		return result;
	}

	private String _languageName;

	@Override
	public String getLanguageName() {
		return _languageName;
	}

	private boolean _breakStart;

	@Override
	public boolean getBreakStart() {
		return _breakStart;
	}

	private String _debugModelID;

	@Override
	public String getDebugModelID() {
		return _debugModelID;
	}
	
	private Event _startEvent;
	private boolean _waitForEvents;
	
	@Override
	public Event getStartEvent() {
		return _startEvent;
	}
	
	public void clearStartEvent() {
		_startEvent = null;
	}
	
	@Override
	public boolean getWaitForEvents() {
		return _waitForEvents;
	}

}
