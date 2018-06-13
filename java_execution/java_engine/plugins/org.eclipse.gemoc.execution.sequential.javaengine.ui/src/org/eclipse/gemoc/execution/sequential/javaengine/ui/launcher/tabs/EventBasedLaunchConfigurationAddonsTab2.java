package org.eclipse.gemoc.execution.sequential.javaengine.ui.launcher.tabs;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.gemoc.xdsmlframework.api.engine_addon.IEngineAddon;
import org.eclipse.gemoc.xdsmlframework.api.extensions.engine_addon.EngineAddonSpecificationExtension;
import org.eclipse.gemoc.xdsmlframework.api.extensions.engine_addon.EngineAddonSpecificationExtensionPoint;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.CheckboxCellEditor;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.ComboBoxCellEditor;
import org.eclipse.jface.viewers.EditingSupport;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerComparator;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;

public class EventBasedLaunchConfigurationAddonsTab2 extends LaunchConfigurationTab {

	private static class AddonConfiguration {

		public final EngineAddonSpecificationExtension addon;
		public final String name;
		public boolean activated = false;
		public int priority = 0;

		public AddonConfiguration(EngineAddonSpecificationExtension addon) {
			this.addon = addon;
			this.name = addon.getName();
		}
	}

	private static class ActivatedEditingSupport extends EditingSupport {

		private final TableViewer viewer;
		private final EventBasedLaunchConfigurationAddonsTab2 tab;

		public ActivatedEditingSupport(TableViewer viewer, EventBasedLaunchConfigurationAddonsTab2 tab) {
			super(viewer);
			this.viewer = viewer;
			this.tab = tab;
		}

		@Override
		protected CellEditor getCellEditor(Object element) {
			return new CheckboxCellEditor(viewer.getTable(), SWT.CHECK);
		}

		@Override
		protected boolean canEdit(Object element) {
			return true;
		}

		@Override
		protected Object getValue(Object element) {
			return ((AddonConfiguration) element).activated;
		}

		@Override
		protected void setValue(Object element, Object value) {
			((AddonConfiguration) element).activated = (Boolean) value;
			tab.updateLaunchConfigurationDialog();
			viewer.update(element, null);
		}
	}

	private static class PriorityEditingSupport extends EditingSupport {

		private final TableViewer viewer;
		private final EventBasedLaunchConfigurationAddonsTab2 tab;

		public PriorityEditingSupport(TableViewer viewer, EventBasedLaunchConfigurationAddonsTab2 tab) {
			super(viewer);
			this.viewer = viewer;
			this.tab = tab;
		}

		@Override
		protected CellEditor getCellEditor(Object element) {
			return new ComboBoxCellEditor(viewer.getTable(),
					new String[] { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9" });
		}

		@Override
		protected boolean canEdit(Object element) {
			return true;
		}

		@Override
		protected Object getValue(Object element) {
			return ((AddonConfiguration) element).priority;
		}

		@Override
		protected void setValue(Object element, Object value) {
			((AddonConfiguration) element).priority = (Integer) value;
			tab.updateLaunchConfigurationDialog();
			viewer.update(element, null);
		}
	}

	private static class AddonViewerComparator extends ViewerComparator {
		private int propertyIndex;
		private static final int DESCENDING = 1;
		private int direction = DESCENDING;

		public AddonViewerComparator() {
			this.propertyIndex = 0;
			direction = DESCENDING;
		}

		public int getDirection() {
			return direction == 1 ? SWT.DOWN : SWT.UP;
		}

		public void setColumn(int column) {
			if (column == this.propertyIndex) {
				// Same column as last sort; toggle the direction
				direction = 1 - direction;
			} else {
				// New column; do an ascending sort
				this.propertyIndex = column;
				direction = DESCENDING;
			}
		}

		@Override
		public int compare(Viewer viewer, Object e1, Object e2) {
			AddonConfiguration c1 = (AddonConfiguration) e1;
			AddonConfiguration c2 = (AddonConfiguration) e2;
			int rc = 0;
			switch (propertyIndex) {
			case 0:
				if (c1.activated == c2.activated) {
					rc = 0;
				} else
					rc = (c1.activated ? 1 : -1);
				break;
			case 1:
				rc = c1.name.compareTo(c2.name);
				break;
			case 3:
				rc = c1.priority - c2.priority;
				break;
			default:
				rc = 0;
			}
			if (direction == DESCENDING) {
				rc = -rc;
			}
			return rc;
		}

	}

	private TableViewer viewer;
	private AddonViewerComparator comparator = new AddonViewerComparator();
	private List<AddonConfiguration> addonConfigurations = new ArrayList<>();

	public EventBasedLaunchConfigurationAddonsTab2() {
		super();
		EngineAddonSpecificationExtensionPoint.getSpecifications()
				.forEach(addon -> addonConfigurations.add(new AddonConfiguration(addon)));
	}

	@Override
	public void createControl(Composite parent) {
		Composite content = new Composite(parent, SWT.NULL);
		GridLayout gl = new GridLayout(1, false);
		content.setLayout(gl);
		content.layout();
		setControl(content);
		setupViewer(content);
	}

	private void setupViewer(Composite parent) {
		viewer = new TableViewer(parent, SWT.MULTI | SWT.V_SCROLL | SWT.FULL_SELECTION | SWT.BORDER);
		setupColumns();

		final Table table = viewer.getTable();
		table.setHeaderVisible(true);
		table.setLinesVisible(true);

		viewer.setContentProvider(ArrayContentProvider.getInstance());
		viewer.setInput(addonConfigurations);

		GridData gridData = new GridData();
		gridData.grabExcessHorizontalSpace = true;
		viewer.getControl().setLayoutData(gridData);
	}

	private void setupColumns() {
		final TableViewerColumn firstColumn = new TableViewerColumn(viewer, SWT.CENTER);
		final TableColumn firstCol = firstColumn.getColumn();
		firstColumn.getColumn().setText("");
		firstColumn.setEditingSupport(new ActivatedEditingSupport(viewer, this));
		firstColumn.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				return null;
			}
		});
		firstCol.addSelectionListener(getSelectionAdapter(firstCol, 0));

		final TableViewerColumn secondColumn = new TableViewerColumn(viewer, SWT.LEFT);
		final TableColumn secondCol = secondColumn.getColumn();
		secondColumn.getColumn().setText("Addon");
		secondColumn.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				return ((AddonConfiguration) element).name;
			}
		});
		secondCol.addSelectionListener(getSelectionAdapter(secondCol, 1));

		final TableViewerColumn thirdColumn = new TableViewerColumn(viewer, SWT.LEFT);
		final TableColumn thirdCol = thirdColumn.getColumn();
		thirdColumn.getColumn().setText("Priority");
		thirdColumn.setEditingSupport(new PriorityEditingSupport(viewer, this));
		thirdColumn.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				return null;
			}
		});
		thirdCol.addSelectionListener(getSelectionAdapter(thirdCol, 2));
	}

	private SelectionAdapter getSelectionAdapter(final TableColumn column, final int index) {
		SelectionAdapter selectionAdapter = new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				comparator.setColumn(index);
				int dir = comparator.getDirection();
				viewer.getTable().setSortDirection(dir);
				viewer.getTable().setSortColumn(column);
				viewer.refresh();
			}
		};
		return selectionAdapter;
	}

	@Override
	public void setDefaults(ILaunchConfigurationWorkingCopy configuration) {
		addonConfigurations.forEach(c -> configuration.setAttribute(c.name, c.addon.getDefaultActivationValue()));
	}

	@Override
	public void initializeFrom(ILaunchConfiguration configuration) {
		for (AddonConfiguration addonConfiguration : addonConfigurations) {
			try {
				addonConfiguration.activated = configuration.getAttribute(addonConfiguration.name, false);
			} catch (CoreException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void performApply(ILaunchConfigurationWorkingCopy configuration) {
		addonConfigurations.forEach(c -> configuration.setAttribute(c.name, c.activated));
	}

	@Override
	public String getName() {
		return "Engine Addons";
	}

	@Override
	public boolean isValid(ILaunchConfiguration config) {
		final List<IEngineAddon> addons = addonConfigurations.stream().filter(c -> c.activated).map(c -> {
			try {
				return c.addon.instanciateComponent();
			} catch (CoreException e) {
				e.printStackTrace();
				return null;
			}
		}).collect(Collectors.toList());
		List<String> errors = addons.stream().flatMap(a -> a.validate(addons).stream()).collect(Collectors.toList());
		errors.forEach(msg -> setErrorMessage(msg));
		setErrorMessage(null);
		return true;
	}
}
