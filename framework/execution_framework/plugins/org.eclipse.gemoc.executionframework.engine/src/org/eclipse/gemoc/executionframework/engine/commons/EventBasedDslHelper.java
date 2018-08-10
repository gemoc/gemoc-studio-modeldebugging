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

import java.io.IOException;
import java.util.Collections;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.gemoc.commons.value.model.value.Value;
import org.eclipse.gemoc.commons.value.model.value.ValueFactory;
import org.eclipse.gemoc.dsl.Dsl;
import org.eclipse.gemoc.executionframework.event.model.event.EventFactory;
import org.eclipse.gemoc.executionframework.event.model.event.EventOccurrence;
import org.eclipse.gemoc.executionframework.event.model.event.EventOccurrenceArgument;
import org.eclipse.gemoc.xdsmlframework.behavioralinterface.behavioralInterface.BehavioralInterface;
import org.eclipse.gemoc.xdsmlframework.behavioralinterface.behavioralInterface.Event;

/**
 * Helper for dsl file that contains K3 aspects
 *
 */
public class EventBasedDslHelper {

	public static BehavioralInterface getBehavioralInterface(String languageName) {
		Dsl dsl = DslHelper.load(languageName);
		if (dsl != null) {
			return dsl.getEntries().stream().filter(entry -> entry.getKey().equals("behavioral-interface")).findFirst().map(itf -> {
				final URI uri = URI.createURI(itf.getValue().replace("platform:/resource", "platform:/plugin"));
				final ResourceSet resourceSet = new ResourceSetImpl();
				final Resource res = resourceSet.getResource(uri, true);
				try {
					res.load(Collections.emptyMap());
				} catch (IOException e) {
					e.printStackTrace();
				}
				return (BehavioralInterface) res.getContents().get(0);
			}).orElse(null);
		}
		return null;
	}
	
	public static EventOccurrence createEventOccurrence(Event event) {
		final EventFactory eventFactory = EventFactory.eINSTANCE;
		final EventOccurrence result = eventFactory.createEventOccurrence();
		result.setEvent(event);
		event.getParams().forEach(p -> {
			final ValueFactory valueFactory = ValueFactory.eINSTANCE;
			final String type = p.getType();
			Value value = null;
			switch(type) {
			case "java.lang.Boolean":
				value = valueFactory.createBooleanObjectAttributeValue();
				break;
			case "boolean":
				value = valueFactory.createBooleanAttributeValue();
				break;
			case "java.lang.Integer":
				value = valueFactory.createIntegerObjectAttributeValue();
				break;
			case "int":
				value = valueFactory.createIntegerAttributeValue();
				break;
			case "java.lang.Float":
				value = valueFactory.createFloatObjectAttributeValue();
				break;
			case "float":
				value = valueFactory.createFloatAttributeValue();
				break;
			case "java.lang.String":
				value = valueFactory.createStringAttributeValue();
				break;
			default:
				value = valueFactory.createSingleReferenceValue();
			}
			final EventOccurrenceArgument arg = eventFactory.createEventOccurrenceArgument();
			arg.setParameter(p);
			arg.setValue(value);
			result.getArgs().add(arg);
		});
		return result;
	}

}
