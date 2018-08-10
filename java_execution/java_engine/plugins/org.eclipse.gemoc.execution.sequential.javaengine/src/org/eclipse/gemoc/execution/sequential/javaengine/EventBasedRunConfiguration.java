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
package org.eclipse.gemoc.execution.sequential.javaengine;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.gemoc.commons.value.model.value.BooleanAttributeValue;
import org.eclipse.gemoc.commons.value.model.value.FloatObjectAttributeValue;
import org.eclipse.gemoc.commons.value.model.value.IntegerObjectAttributeValue;
import org.eclipse.gemoc.commons.value.model.value.SingleReferenceValue;
import org.eclipse.gemoc.commons.value.model.value.StringAttributeValue;
import org.eclipse.gemoc.commons.value.model.value.Value;
import org.eclipse.gemoc.commons.value.model.value.ValuePackage;
import org.eclipse.gemoc.executionframework.engine.commons.EventBasedDslHelper;
import org.eclipse.gemoc.executionframework.engine.core.RunConfiguration;
import org.eclipse.gemoc.executionframework.event.model.event.EventOccurrence;
import org.eclipse.gemoc.executionframework.event.model.event.EventOccurrenceArgument;
import org.eclipse.gemoc.xdsmlframework.behavioralinterface.behavioralInterface.BehavioralInterface;

public class EventBasedRunConfiguration extends RunConfiguration implements IEventBasedRunConfiguration {

	private EventOccurrence _startEventOccurrence;

	private boolean _waitForEvent;

	public EventBasedRunConfiguration(ILaunchConfiguration launchConfiguration) throws CoreException {
		super(launchConfiguration);
	}

	protected void extractInformation() throws CoreException {
		super.extractInformation();
		final String languageName = getLanguageName();
		final BehavioralInterface behavioralInterface = EventBasedDslHelper.getBehavioralInterface(languageName);
		final String startEvent = getAttribute(START_EVENT, "");
		final Map<String, String> paramToArg = getAttribute(START_EVENT_OCCURRENCE_ARGS, Collections.emptyMap());
		behavioralInterface.getEvents().stream().filter(e -> e.getName().equals(startEvent)).findFirst().ifPresent(event -> {
			_startEventOccurrence = EventBasedDslHelper.createEventOccurrence(event);
			final List<EventOccurrenceArgument> args = _startEventOccurrence.getArgs();
			event.getParams().forEach(p -> {
				args.stream().filter(a -> p == a.getParameter()).findFirst().ifPresent(a -> {
					Value value = a.getValue();
					String arg = paramToArg.get(p.getName());
					switch (value.eClass().getClassifierID()) {
					case ValuePackage.SINGLE_REFERENCE_VALUE:
						if (arg != null) {
							if (arg.startsWith("/")) {
								Resource model = getModel();
								((SingleReferenceValue) value).setReferenceValue(model.getEObject(arg));
							} else if (arg.startsWith("platform:/resource")) {
								URI objectURI = URI.createURI(arg);
								ResourceSet resourceSet = new ResourceSetImpl();
								Resource resource = resourceSet.createResource(objectURI);
								EObject argObject = null;
								try {
									resource.load(Collections.emptyMap());
									argObject = resource.getContents().stream().findFirst().orElse(null);
								} catch (IOException e) {
									e.printStackTrace();
									resource = null;
								}
								((SingleReferenceValue) value).setReferenceValue(argObject);
							}
						}
						break;
					case ValuePackage.BOOLEAN_ATTRIBUTE_VALUE:
					case ValuePackage.BOOLEAN_OBJECT_ATTRIBUTE_VALUE:
						((BooleanAttributeValue) value).setAttributeValue(Boolean.parseBoolean(arg));
						break;
					case ValuePackage.INTEGER_ATTRIBUTE_VALUE:
					case ValuePackage.INTEGER_OBJECT_ATTRIBUTE_VALUE:
						((IntegerObjectAttributeValue) value).setAttributeValue(Integer.parseInt(arg));
						break;
					case ValuePackage.FLOAT_ATTRIBUTE_VALUE:
					case ValuePackage.FLOAT_OBJECT_ATTRIBUTE_VALUE:
						((FloatObjectAttributeValue) value).setAttributeValue(Float.parseFloat(arg));
						break;
					case ValuePackage.STRING_ATTRIBUTE_VALUE:
						((StringAttributeValue) value).setAttributeValue(arg);
						break;
					}
				});
			});
		});
		_waitForEvent = getAttribute(WAIT_FOR_EVENT, false);
	}
	

			
	
	private Resource getModel() {
		Resource resource = null;
		URI modelURI = getExecutedModelURI();
		if (!modelURI.isEmpty()) {
			ResourceSet resourceSet = new ResourceSetImpl();
			resource = resourceSet.createResource(modelURI);
			try {
				resource.load(Collections.emptyMap());
			} catch (IOException e) {
				e.printStackTrace();
				resource = null;
			}
		}
		return resource;
	}

	@Override
	public EventOccurrence getStartEventOccurrence() {
		return _startEventOccurrence;
	}

	@Override
	public boolean getWaitForEvent() {
		return _waitForEvent;
	}

}
