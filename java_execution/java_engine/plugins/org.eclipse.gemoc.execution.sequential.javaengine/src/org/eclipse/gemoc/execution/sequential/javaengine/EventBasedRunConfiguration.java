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

import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.gemoc.executionframework.engine.core.RunConfiguration;
import org.eclipse.gemoc.executionframework.event.model.event.EventOccurrence;

public class EventBasedRunConfiguration extends RunConfiguration implements IEventBasedRunConfiguration {

	private EventOccurrence _startEventOccurrence;

	private boolean _waitForEvent;

	public EventBasedRunConfiguration(ILaunchConfiguration launchConfiguration) throws CoreException {
		super(launchConfiguration);
	}

	protected void extractInformation() throws CoreException {
		final String startEventOccurrenceURI = getAttribute(START_EVENT_URI, "");
		_waitForEvent = getAttribute(WAIT_FOR_EVENT, false);
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
