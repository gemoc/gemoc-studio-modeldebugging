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
package org.eclipse.gemoc.executionframework.ui.views.engine.actions;

import java.util.Map.Entry;

import org.eclipse.gemoc.executionframework.ui.Activator;
import org.eclipse.gemoc.xdsmlframework.api.core.IExecutionEngine;
import org.eclipse.gemoc.xdsmlframework.api.core.EngineStatus.RunStatus;
import org.eclipse.jface.resource.ImageDescriptor;

public class StopAllEnginesAction extends AbstractEngineAction {

	public StopAllEnginesAction() {
		super();
	}

	@Override
	protected void init() {
		super.init();
		setText("Stop all");
		setToolTipText("Stop all engines");
		ImageDescriptor id = Activator.imageDescriptorFromPlugin("org.eclipse.debug.ui", "/icons/full/elcl16/terminate_all_co.png");
		setImageDescriptor(id);
	}

	@Override
	public void updateButton() {
		setEnabled(org.eclipse.gemoc.executionframework.engine.Activator.getDefault().gemocRunningEngineRegistry.getRunningEngines().entrySet().stream()
				.anyMatch(e -> !e.getValue().getRunningStatus().equals(RunStatus.Stopped)));
	}

	@Override
	public void run() {
		for (Entry<String, IExecutionEngine<?>> engineEntry : org.eclipse.gemoc.executionframework.engine.Activator.getDefault().gemocRunningEngineRegistry.getRunningEngines().entrySet()) {
			switch (engineEntry.getValue().getRunningStatus()) {
			case Running:
			case WaitingForEvent:
				engineEntry.getValue().stop();
				break;
			default:
			}
		}
	}
}
