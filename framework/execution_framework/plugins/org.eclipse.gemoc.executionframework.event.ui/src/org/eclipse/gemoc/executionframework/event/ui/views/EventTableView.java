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

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.gemoc.commons.value.model.value.BooleanAttributeValue;
import org.eclipse.gemoc.commons.value.model.value.BooleanObjectAttributeValue;
import org.eclipse.gemoc.commons.value.model.value.FloatAttributeValue;
import org.eclipse.gemoc.commons.value.model.value.FloatObjectAttributeValue;
import org.eclipse.gemoc.commons.value.model.value.IntegerAttributeValue;
import org.eclipse.gemoc.commons.value.model.value.IntegerObjectAttributeValue;
import org.eclipse.gemoc.commons.value.model.value.SingleReferenceValue;
import org.eclipse.gemoc.commons.value.model.value.StringAttributeValue;
import org.eclipse.gemoc.commons.value.model.value.Value;
import org.eclipse.gemoc.commons.value.model.value.ValueFactory;
import org.eclipse.gemoc.commons.value.model.value.ValuePackage;
import org.eclipse.gemoc.executionframework.event.manager.IEventManager;
import org.eclipse.gemoc.executionframework.event.model.event.EventFactory;
import org.eclipse.gemoc.executionframework.event.model.event.EventOccurrence;
import org.eclipse.gemoc.executionframework.event.model.event.EventOccurrenceArgument;
import org.eclipse.gemoc.xdsmlframework.behavioralinterface.behavioralInterface.Event;
import org.eclipse.gemoc.xdsmlframework.behavioralinterface.behavioralInterface.EventParameter;
import org.eclipse.xtext.naming.DefaultDeclarativeQualifiedNameProvider;
import org.eclipse.xtext.naming.QualifiedName;

import javafx.application.Platform;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.converter.BooleanStringConverter;
import javafx.util.converter.FloatStringConverter;
import javafx.util.converter.IntegerStringConverter;

public class EventTableView extends TableView<EventOccurrence> {

	private final Event event;

	private final EventFactory factory = EventFactory.eINSTANCE;

	private final Resource executedModel;

	private final EPackage ePackage;

	private final Map<EventParameter, TableColumn<EventOccurrence, Object>> parameterToColumn = new HashMap<>();

	private final Map<EventOccurrence, Map<EventParameter, SimpleObjectProperty<Object>>> eventParameterToProperty = new HashMap<>();

	private final Map<EventParameter, EClass> parameterToParameterClass = new HashMap<>();

	private final Map<EventParameter, List<EObject>> parameterToMatchingModelElements = new HashMap<>();

	private final ObservableList<EventOccurrence> eventOccurrences = FXCollections.observableArrayList();

	private final Function<EventOccurrence, Boolean> canDisplayEventOccurrenceFunction;

	private final DefaultDeclarativeQualifiedNameProvider nameprovider = new DefaultDeclarativeQualifiedNameProvider();

	public EventTableView(final Event event, final Resource executedModel, final IEventManager eventManager) {
		this.event = event;
		this.executedModel = executedModel;
		ePackage = this.executedModel.getContents().stream().findFirst().map(o -> o.eClass().getEPackage())
				.orElse(null);
		extractEventParameters();
		setItems(eventOccurrences);
		setEditable(true);

		canDisplayEventOccurrenceFunction = (eventOccurrence) -> {
			return eventManager.canSendEvent(eventOccurrence);
		};

		final List<TableColumn<EventOccurrence, ?>> columns = new ArrayList<>();
		this.event.getParams().stream().forEach(p -> {
			final String name = p.getName();
			final String type = p.getType();
			switch (type) {
			case "java.lang.Boolean":
			case "boolean": {
				final TableColumn<EventOccurrence, Boolean> col = new TableColumn<EventOccurrence, Boolean>(name);
				col.setEditable(true);
				col.setCellValueFactory(new EventAttributePropertyValueFactory<Boolean>(p));
				col.setCellFactory(TextFieldTableCell.forTableColumn(new BooleanStringConverter()));
				col.setOnEditCommit(editEvent -> {
					EventOccurrence eventOccurrence = editEvent.getRowValue();
					eventOccurrence.getArgs().stream().filter(a -> a.getParameter() == p).findFirst().ifPresent(a -> {
						BooleanAttributeValue v = ValueFactory.eINSTANCE.createBooleanAttributeValue();
						v.setAttributeValue(editEvent.getNewValue());
						a.setValue(v);
					});
				});
				columns.add(col);
			}
				break;
			case "java.lang.Float":
			case "float": {
				final TableColumn<EventOccurrence, Float> col = new TableColumn<EventOccurrence, Float>(name);
				col.setEditable(true);
				col.setCellValueFactory(new EventAttributePropertyValueFactory<Float>(p));
				col.setCellFactory(TextFieldTableCell.forTableColumn(new FloatStringConverter()));
				col.setOnEditCommit(editEvent -> {
					EventOccurrence eventOccurrence = editEvent.getRowValue();
					eventOccurrence.getArgs().stream().filter(a -> a.getParameter() == p).findFirst().ifPresent(a -> {
						FloatAttributeValue v = ValueFactory.eINSTANCE.createFloatAttributeValue();
						v.setAttributeValue(editEvent.getNewValue());
						a.setValue(v);
					});
				});
				columns.add(col);
			}
				break;
			case "java.lang.Integer":
			case "int": {
				final TableColumn<EventOccurrence, Integer> col = new TableColumn<EventOccurrence, Integer>(name);
				col.setEditable(true);
				col.setCellValueFactory(new EventAttributePropertyValueFactory<Integer>(p));
				col.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
				col.setOnEditCommit(editEvent -> {
					EventOccurrence eventOccurrence = editEvent.getRowValue();
					eventOccurrence.getArgs().stream().filter(a -> a.getParameter() == p).findFirst().ifPresent(a -> {
						IntegerAttributeValue v = ValueFactory.eINSTANCE.createIntegerAttributeValue();
						v.setAttributeValue(editEvent.getNewValue());
						a.setValue(v);
					});
				});
				columns.add(col);
			}
				break;
			case "java.lang.String": {
				final TableColumn<EventOccurrence, String> col = new TableColumn<EventOccurrence, String>(name);
				col.setEditable(true);
				col.setCellValueFactory(new EventAttributePropertyValueFactory<String>(p));
				col.setCellFactory(TextFieldTableCell.forTableColumn());
				col.setOnEditCommit(editEvent -> {
					EventOccurrence eventOccurrence = editEvent.getRowValue();
					eventOccurrence.getArgs().stream().filter(a -> a.getParameter() == p).findFirst().ifPresent(a -> {
						StringAttributeValue v = ValueFactory.eINSTANCE.createStringAttributeValue();
						v.setAttributeValue(editEvent.getNewValue());
						a.setValue(v);
					});
				});
				columns.add(col);
			}
				break;
			default: {
				final TableColumn<EventOccurrence, Object> col = new TableColumn<EventOccurrence, Object>(name);
				parameterToColumn.put(p, col);
				columns.add(col);
			}
			}
		});
		getColumns().setAll(columns);
	}

	public void refreshEvents() {
		Platform.runLater(() -> {
			parameterToMatchingModelElements.clear();
			gatherPotentialParameters();

			final List<EventOccurrence> newEventOccurrences = computeAllPossibleEventOccurrences().stream()
					.filter(m -> {
						return !eventOccurrences.stream().anyMatch(eventOccurrence -> {
							return m.entrySet().stream().allMatch(entry -> {
								final EventParameter ref = entry.getKey();
								final Object val1 = getArgumentValue(eventOccurrence, ref);
								final Object val2 = entry.getValue();
								return val1 == val2;
							});
						});
					}).map(m -> {
						final EventOccurrence eventOccurrence = factory.createEventOccurrence();
						eventOccurrence.setEvent(event);
						event.getParams().forEach(p -> {
							eventOccurrence.getArgs().add(createArgument(p, m.get(p)));
						});
						return eventOccurrence;
					}).filter(eventOccurrence -> canDisplayEventOccurrenceFunction.apply(eventOccurrence))
					.collect(Collectors.toList());

			parameterToColumn.forEach((p, col) -> {
				final List<EObject> elements = parameterToMatchingModelElements.get(p);
				if (elements == null || elements.isEmpty()) {
					col.setCellValueFactory(new EventReferencePropertyValueFactory(p));
					col.setCellFactory(fileChooserTableCellForTableColumn(p, f -> {
						if (f != null) {
							final URI uri = URI.createFileURI(f.getAbsolutePath());
							Resource res = null;
							try {
								res = executedModel.getResourceSet().getResource(uri, true);
							} catch (Exception e) {
							}
							if (res != null && !res.getContents().isEmpty()) {
								final EObject element = res.getContents().get(0);
								final EClass parameterType = parameterToParameterClass.get(p);
								if (element.eClass() == parameterType
										|| element.eClass().getEAllSuperTypes().contains(parameterType)) {
									eventOccurrences.forEach(o -> {
										o.getArgs().removeAll(o.getArgs().stream().filter(a -> a.getParameter() == p)
												.collect(Collectors.toList()));
										o.getArgs().add(createArgument(p, element));
									});
									return element;
								} else {
									return null;
								}
							}
						}
						return null;
					}));
				} else {
					col.setCellValueFactory(new EventReferencePropertyValueFactory(p));
					col.setCellFactory(tableCellForTableColumn());
				}
			});

			final List<EventOccurrence> toRemove = eventOccurrences.stream()
					.filter(eventOccurrence -> !canDisplayEventOccurrenceFunction.apply(eventOccurrence))
					.collect(Collectors.toList());
			eventOccurrences.removeAll(toRemove);
			eventOccurrences.addAll(newEventOccurrences);
		});
	}

	private Object getArgumentValue(EventOccurrence eventOccurrence, EventParameter eventParameter) {
		return eventOccurrence.getArgs().stream().filter(a -> a.getParameter() == eventParameter).findFirst()
				.map(a -> a.getValue()).map(v -> {
					Object result = null;
					switch (v.eClass().getClassifierID()) {
					case ValuePackage.SINGLE_REFERENCE_VALUE:
						result = ((SingleReferenceValue) v).getReferenceValue();
						break;
					case ValuePackage.BOOLEAN_ATTRIBUTE_VALUE:
						result = ((BooleanAttributeValue) v).isAttributeValue();
						break;
					case ValuePackage.BOOLEAN_OBJECT_ATTRIBUTE_VALUE:
						result = ((BooleanObjectAttributeValue) v).getAttributeValue();
						break;
					case ValuePackage.INTEGER_ATTRIBUTE_VALUE:
						result = ((IntegerAttributeValue) v).getAttributeValue();
						break;
					case ValuePackage.INTEGER_OBJECT_ATTRIBUTE_VALUE:
						result = ((IntegerObjectAttributeValue) v).getAttributeValue();
						break;
					case ValuePackage.FLOAT_ATTRIBUTE_VALUE:
						result = ((FloatAttributeValue) v).getAttributeValue();
						break;
					case ValuePackage.FLOAT_OBJECT_ATTRIBUTE_VALUE:
						result = ((FloatObjectAttributeValue) v).getAttributeValue();
						break;
					case ValuePackage.STRING_ATTRIBUTE_VALUE:
						result = ((StringAttributeValue) v).getAttributeValue();
						break;
					}
					return result;
				}).orElse(null);
	}

	private EventOccurrenceArgument createArgument(EventParameter eventParameter, EObject value) {
		final EventOccurrenceArgument result = factory.createEventOccurrenceArgument();
		result.setParameter(eventParameter);
		if (value != null) {
			final SingleReferenceValue val = ValueFactory.eINSTANCE.createSingleReferenceValue();
			val.setReferenceValue(value);
			result.setValue(val);
		} else {
			switch (eventParameter.getType()) {
			case "java.lang.Boolean": {
				final BooleanObjectAttributeValue val = ValueFactory.eINSTANCE.createBooleanObjectAttributeValue();
				result.setValue(val);
			}
				break;
			case "boolean": {
				final BooleanAttributeValue val = ValueFactory.eINSTANCE.createBooleanAttributeValue();
				result.setValue(val);
			}
				break;
			case "java.lang.Float": {
				final FloatObjectAttributeValue val = ValueFactory.eINSTANCE.createFloatObjectAttributeValue();
				result.setValue(val);
			}
				break;
			case "float": {
				final FloatAttributeValue val = ValueFactory.eINSTANCE.createFloatAttributeValue();
				result.setValue(val);
			}
				break;
			case "java.lang.Integer": {
				final IntegerObjectAttributeValue val = ValueFactory.eINSTANCE.createIntegerObjectAttributeValue();
				result.setValue(val);
			}
				break;
			case "int": {
				final IntegerAttributeValue val = ValueFactory.eINSTANCE.createIntegerAttributeValue();
				result.setValue(val);
			}
				break;
			case "java.lang.String": {
				final StringAttributeValue val = ValueFactory.eINSTANCE.createStringAttributeValue();
				result.setValue(val);
			}
				break;
			}
		}
		return result;
	}

	private void extractEventParameters() {
		event.getParams().forEach(p -> {
			String type = p.getType();
			switch (type) {
			case "java.lang.Boolean":
			case "boolean":
			case "java.lang.Float":
			case "float":
			case "java.lang.Integer":
			case "int":
			case "java.lang.String":
				break;
			default:
				ePackage.getEClassifiers().stream()
						.filter(c -> c instanceof EClass && ((EClass) c).getInstanceClassName().equals(type))
						.findFirst().map(c -> (EClass) c).ifPresent(c -> parameterToParameterClass.put(p, c));
			}
		});
	}

	private void gatherPotentialParameters() {
		final Set<EventParameter> eventParameters = parameterToParameterClass.keySet();
		executedModel.getAllContents().forEachRemaining(modelElement -> {
			final EClass elementClass = modelElement.eClass();
			eventParameters.stream().filter(r -> {
				final EClass parameterClass = parameterToParameterClass.get(r);
				return (parameterClass.getEPackage() == elementClass.getEPackage()
						&& elementClass.getClassifierID() == parameterClass.getClassifierID())
						|| elementClass.getEAllSuperTypes().contains(parameterClass);
			}).forEach(r -> {
				List<EObject> elements = parameterToMatchingModelElements.get(r);
				if (elements == null) {
					elements = new ArrayList<>();
					parameterToMatchingModelElements.put(r, elements);
				}
				elements.add(modelElement);
			});
		});
	}

	private List<Map<EventParameter, EObject>> computeAllPossibleEventOccurrences() {
		final List<Map<EventParameter, EObject>> result = new ArrayList<>();
		final int nbEventOccurrences = parameterToMatchingModelElements.values().stream().map(l -> l.size())
				.reduce((i1, i2) -> i1 * i2).orElse(0);
		final List<Map.Entry<EventParameter, List<EObject>>> entries = new ArrayList<>(
				parameterToMatchingModelElements.entrySet());
		for (int i = 0; i < nbEventOccurrences; i++) {
			int j = 1;
			final Map<EventParameter, EObject> parametersAssociation = new HashMap<>();
			for (Map.Entry<EventParameter, List<EObject>> entry : entries) {
				final List<EObject> modelElements = entry.getValue();
				parametersAssociation.put(entry.getKey(), modelElements.get((i / j) % modelElements.size()));
				j *= modelElements.size();
			}
			result.add(parametersAssociation);
		}
		return result;
	}

	class EventReferencePropertyValueFactory
			implements Callback<CellDataFeatures<EventOccurrence, Object>, ObservableValue<Object>> {

		private final EventParameter parameter;

		public EventReferencePropertyValueFactory(EventParameter parameter) {
			this.parameter = parameter;
		}

		@Override
		public ObservableValue<Object> call(CellDataFeatures<EventOccurrence, Object> p) {
			final List<EObject> modelElements = parameterToMatchingModelElements.get(parameter);
			SimpleObjectProperty<Object> result = null;
			if (modelElements == null || modelElements.isEmpty()) {
				result = eventParameterToProperty.computeIfAbsent(p.getValue(), v -> new HashMap<>())
						.computeIfAbsent(parameter, param -> new SimpleObjectProperty<Object>(null));
			} else {
				EventOccurrence eventOccurrence = p.getValue();
				result = eventOccurrence.getArgs().stream().filter(a -> a.getParameter() == parameter).findFirst()
						.map(a -> {
							final Value value = a.getValue();
							if (value == null) {
								return null;
							} else {
								return value.eGet(ValuePackage.Literals.SINGLE_REFERENCE_VALUE__REFERENCE_VALUE);
							}
						}).map(v -> new SimpleObjectProperty<Object>(v)).orElse(new SimpleObjectProperty<Object>(null));
			}
			return result;
		}
	}

	class EventAttributePropertyValueFactory<T>
			implements Callback<CellDataFeatures<EventOccurrence, T>, ObservableValue<T>> {

		private final EventParameter parameter;

		public EventAttributePropertyValueFactory(EventParameter parameter) {
			this.parameter = parameter;
		}

		@SuppressWarnings("unchecked")
		@Override
		public ObservableValue<T> call(CellDataFeatures<EventOccurrence, T> p) {
			EventOccurrence eventOccurrence = p.getValue();
			return eventOccurrence.getArgs().stream().filter(a -> a.getParameter() == parameter).findFirst().map(a -> {
				EStructuralFeature f = null;
				switch (a.getValue().eClass().getClassifierID()) {
				case ValuePackage.BOOLEAN_ATTRIBUTE_VALUE:
					f = ValuePackage.Literals.BOOLEAN_ATTRIBUTE_VALUE__ATTRIBUTE_VALUE;
					break;
				case ValuePackage.BOOLEAN_OBJECT_ATTRIBUTE_VALUE:
					f = ValuePackage.Literals.BOOLEAN_OBJECT_ATTRIBUTE_VALUE__ATTRIBUTE_VALUE;
					break;
				case ValuePackage.INTEGER_ATTRIBUTE_VALUE:
					f = ValuePackage.Literals.INTEGER_ATTRIBUTE_VALUE__ATTRIBUTE_VALUE;
					break;
				case ValuePackage.INTEGER_OBJECT_ATTRIBUTE_VALUE:
					f = ValuePackage.Literals.INTEGER_OBJECT_ATTRIBUTE_VALUE__ATTRIBUTE_VALUE;
					break;
				case ValuePackage.FLOAT_ATTRIBUTE_VALUE:
					f = ValuePackage.Literals.FLOAT_ATTRIBUTE_VALUE__ATTRIBUTE_VALUE;
					break;
				case ValuePackage.FLOAT_OBJECT_ATTRIBUTE_VALUE:
					f = ValuePackage.Literals.FLOAT_OBJECT_ATTRIBUTE_VALUE__ATTRIBUTE_VALUE;
					break;
				case ValuePackage.STRING_ATTRIBUTE_VALUE:
					f = ValuePackage.Literals.STRING_ATTRIBUTE_VALUE__ATTRIBUTE_VALUE;
					break;
				}
				return a.getValue().eGet(f);
			}).map(v -> new SimpleObjectProperty<T>((T) v)).orElse(new SimpleObjectProperty<T>(null));
		}
	}

	private final Function<Object, String> namingFunction = o -> {
		if (o == null) {
			return "";
		}
		if (o instanceof EObject) {
			final EObject eObject = (EObject) o;
			final QualifiedName fqn = nameprovider.apply(eObject);
			if (fqn == null || fqn.isEmpty()) {
				return eObject.eResource().getURIFragment(eObject);
			}
			return fqn.getLastSegment();
		}
		final String s = o.toString();
		return s.substring(s.lastIndexOf(".") + 1);
	};

	private Callback<TableColumn<EventOccurrence, Object>, TableCell<EventOccurrence, Object>> tableCellForTableColumn() {
		return col -> new ReferenceTableCell();
	}

	private Callback<TableColumn<EventOccurrence, Object>, TableCell<EventOccurrence, Object>> fileChooserTableCellForTableColumn(
			EventParameter parameter, Function<File, Object> fileHandler) {
		return col -> new FileChooserTableCell(parameter, fileHandler);
	}

	class ReferenceTableCell extends TableCell<EventOccurrence, Object> {

		public ReferenceTableCell() {
			super();
			setEditable(false);
		}

		@Override
		protected void updateItem(Object item, boolean empty) {
			super.updateItem(item, empty);
			setText(namingFunction.apply(item));
		}

	}

	class FileChooserTableCell extends TableCell<EventOccurrence, Object> {

		private EventParameter parameter;
		private FileChooser fileChooser;
		private Function<File, Object> fileHandler;
		private Label label;
		private Button button;

		private final EventHandler<ActionEvent> browseHandler = e -> {
			final File f = fileChooser.showOpenDialog(new Stage());
			final Object item = fileHandler.apply(f);
			final Map<EventParameter, SimpleObjectProperty<Object>> parameterToProperty = eventParameterToProperty
					.get(getTableRow().getItem());
			if (parameterToProperty != null) {
				final SimpleObjectProperty<Object> property = parameterToProperty.get(parameter);
				if (property != null) {
					property.set(item);
				}
			}
		};

		public FileChooserTableCell(EventParameter parameter, Function<File, Object> fileHandler) {
			super();
			setEditable(false);
			this.parameter = parameter;
			this.fileHandler = fileHandler;
			fileChooser = new FileChooser();
		}

		private void setupGraphic() {
			label = new Label("");
			label.setAlignment(Pos.CENTER_LEFT);
			button = new Button("...");
			button.setOnAction(browseHandler);
			final BorderPane pane = new BorderPane();
			pane.setLeft(label);
			pane.setRight(button);
			setGraphic(pane);
		}

		@Override
		protected void updateItem(Object item, boolean empty) {
			super.updateItem(item, empty);
			if (!empty) {
				if (button == null) {
					setupGraphic();
				}

				label.setText(namingFunction.apply(item));
			}
		}
	}
}
