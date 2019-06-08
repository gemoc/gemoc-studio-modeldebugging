package org.eclipse.gemoc.executionframework.property.monitor.parts;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.IJobChangeEvent;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.core.runtime.jobs.JobChangeAdapter;
import org.eclipse.debug.internal.ui.SWTFactory;
import org.eclipse.gemoc.commons.eclipse.ui.ViewHelper;
import org.eclipse.gemoc.commons.eclipse.ui.dialogs.SelectAnyIFileDialog;
import org.eclipse.gemoc.executionframework.property.monitor.esper.properties.AbstractTemporalProperty.PropertyState;
import org.eclipse.gemoc.executionframework.property.monitor.manager.PropertyManager;
import org.eclipse.gemoc.executionframework.ui.Activator;
import org.eclipse.gemoc.executionframework.ui.views.engine.EnginesStatusView;
import org.eclipse.gemoc.executionframework.ui.views.engine.IEngineSelectionListener;
import org.eclipse.gemoc.trace.commons.model.trace.Step;
import org.eclipse.gemoc.xdsmlframework.api.core.IExecutionEngine;
import org.eclipse.gemoc.xdsmlframework.api.engine_addon.IEngineAddon;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.ColumnViewerToolTipSupport;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.TreeViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.ui.progress.UIJob;

@SuppressWarnings("restriction")
public class PropertyMonitor implements IEngineSelectionListener {
	private IExecutionEngine<?> executionEngine;
	private PropertyManager propertyManager = new PropertyManager();
	private TreeViewer propertyTreeViewer;

	@PostConstruct
	public void createPartControl(Composite parent) {
		
		final SashForm sashForm = new SashForm(parent, SWT.VERTICAL);
		
		final Group propertyGroup = createGroup(sashForm, "Properties");
		createPropertyTable(propertyGroup);
	
		final Composite buttonArea = new Composite(sashForm, SWT.NULL);
		final GridLayout gl = new GridLayout(2, true);
		buttonArea.setLayout(gl);
		
		Button loadButton = SWTFactory.createPushButton(buttonArea, "Load Property", null);
		loadButton.setSize(new Point(100, 25));
		loadButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent evt) {
				final SelectAnyIFileDialog dialog = new SelectAnyIFileDialog();
				if (dialog.open() == Dialog.OK) {
					final Job addPropertyJob = new Job("adding property") {
						@Override
						protected IStatus run(IProgressMonitor monitor) {
							propertyManager.addProperty(((IResource) dialog.getResult()[0]).getFullPath().toPortableString());
							return Status.OK_STATUS;
						}
					};
					Job.getJobManager().addJobChangeListener(new JobChangeAdapter() {
						@Override
						public void done(IJobChangeEvent event) {
							if (event.getJob() == addPropertyJob) {
								Job.getJobManager().removeJobChangeListener(this);
								UIJob job = new UIJob("adding property") {
									@Override
									public IStatus runInUIThread(IProgressMonitor monitor) {
										propertyTreeViewer.setInput(propertyManager);
										return Status.OK_STATUS;
									}
								};
								job.schedule();
							}
						}
					});
					addPropertyJob.schedule();
				}
			}
		});
		loadButton.pack();
		
		Button deleteButton = SWTFactory.createPushButton(buttonArea, "Remove Properties", null);
		deleteButton.setSize(new Point(100, 25));
		deleteButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent evt) {
				removeProperties();
			}
		});
		deleteButton.pack();
		
		sashForm.setWeights(new int[] {8, 1});

		startListeningToEngineSelectionChange();
		
	}

	private void createPropertyTable(Composite parent) {
		propertyTreeViewer = new TreeViewer(parent, SWT.FULL_SELECTION | SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL);
		final ITreeContentProvider contentProvider = new ITreeContentProvider() {
			@Override
			public Object[] getElements(Object inputElement) {
				if (inputElement instanceof PropertyManager) {
					return ((PropertyManager) inputElement).getPropertiesStates();
				}
				return new Object[0];
			}

			public Object[] getChildren(Object parentElement) {
				return new Object[0];
			}

			public Object getParent(Object element) {
				return null;
			}

			public boolean hasChildren(Object element) {
				return false;
			}
		};
		propertyTreeViewer.setContentProvider(contentProvider);
		ColumnViewerToolTipSupport.enableFor(propertyTreeViewer);

		TreeViewerColumn nameViewerColumn = new TreeViewerColumn(propertyTreeViewer, SWT.NONE);
		nameViewerColumn.getColumn().setText("Name");
		nameViewerColumn.getColumn().setAlignment(SWT.LEFT);
		nameViewerColumn.getColumn().setWidth(200);
		nameViewerColumn.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				return ((PropertyState) element).getName();
			}
		});
		
		TreeViewerColumn valueViewerColumn = new TreeViewerColumn(propertyTreeViewer, SWT.NONE);
		valueViewerColumn.getColumn().setText("Value");
		valueViewerColumn.getColumn().setAlignment(SWT.CENTER);
		valueViewerColumn.getColumn().setWidth(200);
		valueViewerColumn.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				return ((PropertyState) element).getValue().toString();
			}
		});
		
		final Font mono = JFaceResources.getFont(JFaceResources.TEXT_FONT);
		propertyTreeViewer.getTree().setFont(mono);
	}

	private Group createGroup(Composite parent, String text) {
		Group group = new Group(parent, SWT.NULL);
		group.setText(text);
		final FillLayout locationLayout = new FillLayout();
		group.setLayout(locationLayout);
		return group;
	}

	private void removeProperties() {
		propertyManager.clearProperties();
		propertyTreeViewer.setInput(propertyManager);
	}
	
	@PreDestroy
	public void preDestroy() {
		stopListeningToEngineSelectionChange();
	}

	@Override
	public void engineSelectionChanged(IExecutionEngine<?> engine) {
		if (engine != executionEngine) {
			propertyManager.reset();
			if (engine != null) {
				propertyManager.setEngine(engine);
				engine.getExecutionContext().getExecutionPlatform().addEngineAddon(new IEngineAddon() {
					public void aboutToExecuteStep(IExecutionEngine<?> engine, Step<?> stepToExecute) {
						updateViewers();
					}

					public void stepExecuted(IExecutionEngine<?> engine, Step<?> stepExecuted) {
						updateViewers();
					}
					
					public void engineStopped(IExecutionEngine<?> engine) {
						updateViewers();
					}

					private void updateViewers() {
						Display.getDefault().asyncExec(() -> {
							propertyTreeViewer.setInput(propertyManager);
						});
					}
				});
			}
		}
	}

	protected void startListeningToEngineSelectionChange() {
		ViewHelper.retrieveView(EnginesStatusView.ID);
		Activator.getDefault().getEngineSelectionManager().addEngineSelectionListener(this);
	}

	protected void stopListeningToEngineSelectionChange() {
		Activator.getDefault().getEngineSelectionManager().removeEngineSelectionListener(this);
	}
}
