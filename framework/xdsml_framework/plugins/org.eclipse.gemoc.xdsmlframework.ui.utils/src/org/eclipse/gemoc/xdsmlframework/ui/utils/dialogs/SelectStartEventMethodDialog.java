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
package org.eclipse.gemoc.xdsmlframework.ui.utils.dialogs;

import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.gemoc.executionframework.engine.annotations.EventHandler;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.dialogs.ElementListSelectionDialog;


public class SelectStartEventMethodDialog extends ElementListSelectionDialog  {

	/**
	 * Create a selection dialog displaying all available methods from 'aspects'
	 * with @Start if any, and with @EventHandler otherwise
	 */
	public SelectStartEventMethodDialog(Set<Class<?>> aspects, ILabelProvider renderer) {
		super(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), renderer);
		if(aspects != null) {
			Set<Method> start = new HashSet<Method>();
			Set<Method> handlers = new HashSet<Method>();
			for (Class<?> asp : aspects) {
				for (Method m : asp.getMethods()) {
					if (isAnnotedStart(m)) {
						start.add(m);
					}
					if (isAnnotedEventHandler(m)) {
						handlers.add(m);
					}
				}
			}
			if (start.isEmpty()) {
				setElements(handlers.toArray());
			} else {
				setElements(start.toArray());
			}
		}
	}

	/**
	 * Return true is there is @Start
	 */
	private boolean isAnnotedStart(Method m){
		final EventHandler annotation = m.getAnnotation(EventHandler.class);
		return annotation != null && annotation.startEvent();
	}

	/**
	 * Return true is there is @EventHandler
	 */
	private boolean isAnnotedEventHandler(Method m){
		return m.isAnnotationPresent(EventHandler.class);
	}
}

