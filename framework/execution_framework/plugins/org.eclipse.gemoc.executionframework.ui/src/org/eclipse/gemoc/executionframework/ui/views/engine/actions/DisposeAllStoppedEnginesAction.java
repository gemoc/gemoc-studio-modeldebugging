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

import org.eclipse.gemoc.executionframework.ui.Activator;
import org.eclipse.gemoc.xdsmlframework.api.core.EngineStatus.RunStatus;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.ISharedImages;

public class DisposeAllStoppedEnginesAction extends AbstractEngineAction {

	public DisposeAllStoppedEnginesAction() {
		setText("Dispose stopped engines");
		setToolTipText("Dispose all stopped engines");
		ImageDescriptor id = Activator.imageDescriptorFromPlugin(Activator.PLUGIN_ID, ISharedImages.IMG_ELCL_REMOVEALL);
		setImageDescriptor(id);
	}

	@Override
	public void run() {
		org.eclipse.gemoc.executionframework.engine.Activator.getDefault().gemocRunningEngineRegistry.getRunningEngines().forEach((name, engine) -> {
			switch (engine.getRunningStatus()) {
			case Stopped:
				engine.dispose();
				break;
			default:
			}
		});
	}

	@Override
	public void updateButton() {
		setEnabled(org.eclipse.gemoc.executionframework.engine.Activator.getDefault().gemocRunningEngineRegistry.getRunningEngines().entrySet().stream()
				.anyMatch(e -> e.getValue().getRunningStatus().equals(RunStatus.Stopped)));
	}
}
