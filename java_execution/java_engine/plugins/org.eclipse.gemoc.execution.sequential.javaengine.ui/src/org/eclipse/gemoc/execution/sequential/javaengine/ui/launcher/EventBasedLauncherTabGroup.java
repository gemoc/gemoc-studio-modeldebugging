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
package org.eclipse.gemoc.execution.sequential.javaengine.ui.launcher;

import org.eclipse.debug.ui.AbstractLaunchConfigurationTabGroup;
import org.eclipse.debug.ui.CommonTab;
import org.eclipse.debug.ui.ILaunchConfigurationDialog;
import org.eclipse.debug.ui.ILaunchConfigurationTab;
import org.eclipse.gemoc.execution.sequential.javaengine.ui.launcher.tabs.EventBasedLaunchConfigurationAddonsTab2;
import org.eclipse.gemoc.execution.sequential.javaengine.ui.launcher.tabs.EventBasedLaunchConfigurationMainTab;

public class EventBasedLauncherTabGroup extends AbstractLaunchConfigurationTabGroup {

	public EventBasedLauncherTabGroup() {
	}

	@Override
	public void createTabs(ILaunchConfigurationDialog dialog, String mode) {
		ILaunchConfigurationTab[] tabs = new ILaunchConfigurationTab[] { new EventBasedLaunchConfigurationMainTab(),
				new EventBasedLaunchConfigurationAddonsTab2(), new CommonTab() };
		setTabs(tabs);
	}

}
