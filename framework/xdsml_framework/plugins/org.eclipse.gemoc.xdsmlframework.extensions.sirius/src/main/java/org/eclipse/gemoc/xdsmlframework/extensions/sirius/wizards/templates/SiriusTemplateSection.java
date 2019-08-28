/*******************************************************************************
 * Copyright (c) 2000, 2017 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Inria - adaptation for K3
 *******************************************************************************/

package org.eclipse.gemoc.xdsmlframework.extensions.sirius.wizards.templates;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

import org.eclipse.core.runtime.*;
import org.osgi.framework.Bundle;

import org.eclipse.gemoc.commons.eclipse.pde.wizards.pages.pde.ui.templates.OptionTemplateSection;
import org.eclipse.gemoc.xdsmlframework.extensions.sirius.Activator;

/**
 * Common TemplateSection for templates applicable to Sirius projects
 *
 */
public abstract class SiriusTemplateSection extends OptionTemplateSection {

	protected ResourceBundle getPluginResourceBundle() {
		Bundle bundle = Platform.getBundle(Activator.PLUGIN_ID);
		return Platform.getResourceBundle(bundle);
	}

	protected URL getInstallURL() {
		return Activator.getDefault().getInstallURL();
	}

	public URL getTemplateLocation() {
		try {
			String[] candidates = getDirectoryCandidates();
			for (int i = 0; i < candidates.length; i++) {
				if (Activator.getDefault().getBundle().getEntry(candidates[i]) != null) {
					URL candidate = new URL(getInstallURL(), candidates[i]);
					return candidate;
				}
			}
		} catch (MalformedURLException e) { // do nothing
		}
		return null;
	}

	private String[] getDirectoryCandidates() {
		ArrayList<String> result = new ArrayList<String>();
		result.add("templates" + "/" + getSectionId() + "/"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$		
		return (String[]) result.toArray(new String[result.size()]);
	}

	/* (non-Javadoc)
	* @see org.eclipse.pde.ui.templates.ITemplateSection#getFoldersToInclude()
	*/
	public String[] getNewFiles() {
		return new String[0];
	}


	protected void generateFiles(IProgressMonitor monitor) throws CoreException {
		super.generateFiles(monitor);
	
	}

}
