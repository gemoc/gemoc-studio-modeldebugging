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
package org.eclipse.gemoc.executionframework.event.ui.views;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EFactory;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.gemoc.executionframework.event.manager.IEventManager;
import org.eclipse.gemoc.executionframework.event.model.event.Event;
import org.eclipse.xtext.naming.DefaultDeclarativeQualifiedNameProvider;
import org.eclipse.xtext.naming.QualifiedName;

import javafx.application.Platform;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.Callback;
import javafx.util.converter.BooleanStringConverter;
import javafx.util.converter.FloatStringConverter;
import javafx.util.converter.IntegerStringConverter;

public class EventTableView extends TableView<Event> {

	private final EClass eventClass;

	private final EFactory factory;

	private final Resource executedModel;

	private final Map<EReference, EClass> referenceToParameterClass = new HashMap<>();

	private final Map<EReference, List<EObject>> referenceToMatchingModelElements = new HashMap<>();

	private final ObservableList<Event> events = FXCollections.observableArrayList();

	private final Function<Event, Boolean> canDisplayEventFunction;

	public EventTableView(final EClass eventClass, final Resource executedModel, final IEventManager eventManager) {
		this.eventClass = eventClass;
		this.factory = eventClass.getEPackage().getEFactoryInstance();
		this.executedModel = executedModel;
		extractEventParameters();
		setItems(events);
		setEditable(true);

		canDisplayEventFunction = (event) -> {
			return eventManager.canSendEvent(event);
		};

		final List<TableColumn<Event, ?>> columns = new ArrayList<>();
		eventClass.getEAllStructuralFeatures().stream().forEach(f -> {
			final String name = f.getName();
			if (f instanceof EReference) {
				final TableColumn<Event, String> col = new TableColumn<Event, String>(name);
				col.setCellValueFactory(new EventReferencePropertyValueFactory((EReference) f));
				columns.add(col);
			} else {
				EClassifier classifier = f.getEType();
				switch (classifier.getInstanceClassName()) {
				case "java.lang.Boolean":
				case "boolean": {
					final TableColumn<Event, Boolean> col = new TableColumn<Event, Boolean>(name);
					col.setEditable(true);
					col.setCellValueFactory(new EventAttributePropertyValueFactory<Boolean>((EAttribute) f));
					col.setCellFactory(TextFieldTableCell.forTableColumn(new BooleanStringConverter()));
					col.setOnEditCommit(editEvent -> {
						Event event = editEvent.getRowValue();
						event.eSet(f, editEvent.getNewValue());
					});
					columns.add(col);
				}
					break;
				case "java.lang.Float":
				case "float": {
					final TableColumn<Event, Float> col = new TableColumn<Event, Float>(name);
					col.setEditable(true);
					col.setCellValueFactory(new EventAttributePropertyValueFactory<Float>((EAttribute) f));
					col.setCellFactory(TextFieldTableCell.forTableColumn(new FloatStringConverter()));
					col.setOnEditCommit(editEvent -> {
						Event event = editEvent.getRowValue();
						event.eSet(f, editEvent.getNewValue());
					});
					columns.add(col);
				}
					break;
				case "java.lang.Integer":
				case "int": {
					final TableColumn<Event, Integer> col = new TableColumn<Event, Integer>(name);
					col.setEditable(true);
					col.setCellValueFactory(new EventAttributePropertyValueFactory<Integer>((EAttribute) f));
					col.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
					col.setOnEditCommit(editEvent -> {
						Event event = editEvent.getRowValue();
						event.eSet(f, editEvent.getNewValue());
					});
					columns.add(col);
				}
					break;
				case "java.lang.String": {
					final TableColumn<Event, String> col = new TableColumn<Event, String>(name);
					col.setEditable(true);
					col.setCellValueFactory(new EventAttributePropertyValueFactory<String>((EAttribute) f));
					col.setCellFactory(TextFieldTableCell.forTableColumn());
					col.setOnEditCommit(editEvent -> {
						Event event = editEvent.getRowValue();
						event.eSet(f, editEvent.getNewValue());
					});
					columns.add(col);
				}
					break;
				}
			}
		});
		getColumns().setAll(columns);
	}

	public void refreshEvents() {
		Platform.runLater(() -> {
			referenceToMatchingModelElements.clear();
			gatherPotentialParameters();

			final List<Event> newEvents = computeAllPossibleEvents().stream().filter(m -> {
				return !events.stream().anyMatch(event -> {
					return m.entrySet().stream().allMatch(entry -> {
						final EStructuralFeature ref = entry.getKey();
						final Object val1 = event.eGet(ref);
						final Object val2 = entry.getValue();
						return val1 == val2;
					});
				});
			}).map(m -> {
				// TODO ?
				final Event event = (Event) factory.create(eventClass);
				m.forEach((f, v) -> {
					event.eSet(f, v);
				});
				return event;
			}).filter(event -> canDisplayEventFunction.apply(event)).collect(Collectors.toList());

			final List<Event> toRemove = events.stream().filter(event -> !canDisplayEventFunction.apply(event))
					.collect(Collectors.toList());
			events.removeAll(toRemove);
			events.addAll(newEvents);
		});
	}

	private void extractEventParameters() {
		eventClass.getEAllReferences().forEach(f -> {
			final EClass type = (EClass) f.getEType();
			referenceToParameterClass.put(f, type);
		});
	}

	private void gatherPotentialParameters() {
		final Set<EReference> eventParameters = referenceToParameterClass.keySet();
		executedModel.getAllContents().forEachRemaining(modelElement -> {
			final EClass elementClass = modelElement.eClass();
			eventParameters.stream().filter(r -> {
				final EClass parameterClass = referenceToParameterClass.get(r);
				return (parameterClass.getEPackage() == elementClass.getEPackage() && elementClass.getClassifierID() == parameterClass.getClassifierID())
						|| elementClass.getEAllSuperTypes().contains(parameterClass);
			}).forEach(r -> {
				List<EObject> elements = referenceToMatchingModelElements.get(r);
				if (elements == null) {
					elements = new ArrayList<>();
					referenceToMatchingModelElements.put(r, elements);
				}
				elements.add(modelElement);
			});
		});
	}

	private List<Map<EStructuralFeature, Object>> computeAllPossibleEvents() {
		final List<Map<EStructuralFeature, Object>> result = new ArrayList<>();
		final int nbEvents = referenceToMatchingModelElements.values().stream().map(l -> l.size())
				.reduce((i1, i2) -> i1 * i2).orElse(0);
		final List<Map.Entry<EReference, List<EObject>>> entries = new ArrayList<>(
				referenceToMatchingModelElements.entrySet());
		for (int i = 0; i < nbEvents; i++) {
			int j = 1;
			final Map<EStructuralFeature, Object> parametersAssociation = new HashMap<>();
			for (Map.Entry<EReference, List<EObject>> entry : entries) {
				final List<EObject> modelElements = entry.getValue();
				parametersAssociation.put(entry.getKey(), modelElements.get((i / j) % modelElements.size()));
				j *= modelElements.size();
			}
			result.add(parametersAssociation);
		}
		return result;
	}

	static class EventReferencePropertyValueFactory
			implements Callback<CellDataFeatures<Event, String>, ObservableValue<String>> {

		private final EReference reference;
		private final DefaultDeclarativeQualifiedNameProvider nameprovider = new DefaultDeclarativeQualifiedNameProvider();
		private final Function<Object, String> stringGetter = (o) -> {
			if (o instanceof EObject) {
				QualifiedName qname = nameprovider.getFullyQualifiedName((EObject) o);
				if (qname != null) {
					return qname.toString();
				}
			}
			final String string = o.toString();
			return string.substring(string.lastIndexOf(".") + 1);
		};

		public EventReferencePropertyValueFactory(EReference reference) {
			this.reference = reference;
		}

		@Override
		public ObservableValue<String> call(CellDataFeatures<Event, String> p) {
			Event event = p.getValue();
			Object refValue = event.eGet(reference);
			if (refValue != null) {
				String string = stringGetter.apply(refValue);
				ObservableValue<String> result = new ReadOnlyObjectWrapper<String>(string);
				return result;
			}
			return null;
		}
	}

	static class EventAttributePropertyValueFactory<T>
			implements Callback<CellDataFeatures<Event, T>, ObservableValue<T>> {

		private final EAttribute attribute;

		public EventAttributePropertyValueFactory(EAttribute attribute) {
			this.attribute = attribute;
		}

		@SuppressWarnings("unchecked")
		@Override
		public ObservableValue<T> call(CellDataFeatures<Event, T> p) {
			Event event = p.getValue();
			Object refValue = event.eGet(attribute);
			if (refValue != null) {
				ObservableValue<T> result = new ReadOnlyObjectWrapper<T>((T) refValue);
				return result;
			}
			return null;
		}
	}
}
