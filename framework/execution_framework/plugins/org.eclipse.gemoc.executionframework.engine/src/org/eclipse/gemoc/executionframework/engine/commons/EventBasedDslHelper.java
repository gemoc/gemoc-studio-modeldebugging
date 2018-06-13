/*******************************************************************************
 * Copyright (c) 2018 Inria and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Inria - initial API and implementation
 *******************************************************************************/
package org.eclipse.gemoc.executionframework.engine.commons;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.gemoc.dsl.Dsl;
import org.eclipse.gemoc.xdsmlframework.behavioralinterface.behavioralInterface.BehavioralInterface;

/**
 * Helper for dsl file that contains K3 aspects
 *
 */
public class EventBasedDslHelper{

	public static BehavioralInterface getBehavioralInterface(String languageName) {
		Dsl dsl = DslHelper.load(languageName);
		if(dsl != null) {
			return dsl.getEntries()
				.stream()
				.filter(entry -> entry.getKey().equals("behavioralInterface"))
				.findFirst().map(itf -> {
					final URI uri = URI.createURI("platform:/plugin/" + itf.getValue());
					ResourceSet resSet = new ResourceSetImpl();
					return (BehavioralInterface) resSet.getResource(uri, false).getContents().get(0);
				}).orElse(null);
		}
		return null;
	}
	
}
