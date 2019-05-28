package org.eclipse.gemoc.executionframework.property.monitor.manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.LinkedTransferQueue;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TransferQueue;
import java.util.stream.Collectors;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.gemoc.executionframework.property.model.property.TemporalProperty;
import org.eclipse.gemoc.executionframework.property.monitor.esper.AbstractTemporalProperty;
import org.eclipse.gemoc.executionframework.property.monitor.esper.AbstractTemporalProperty.PropertyState;
import org.eclipse.gemoc.executionframework.property.monitor.esper.EsperTemporalProperty;
import org.eclipse.gemoc.trace.commons.model.trace.Step;
import org.eclipse.gemoc.xdsmlframework.api.core.IExecutionEngine;
import org.eclipse.gemoc.xdsmlframework.api.engine_addon.IEngineAddon;
import org.eclipse.viatra.query.patternlanguage.emf.specification.SpecificationBuilder;
import org.eclipse.viatra.query.runtime.api.AdvancedViatraQueryEngine;
import org.eclipse.viatra.query.runtime.api.IPatternMatch;
import org.eclipse.viatra.query.runtime.api.IQuerySpecification;
import org.eclipse.viatra.query.runtime.emf.EMFScope;

import com.espertech.esper.common.client.configuration.Configuration;
import com.espertech.esper.runtime.client.EPRuntime;
import com.espertech.esper.runtime.client.EPRuntimeProvider;

public class PropertyManager implements IEngineAddon {

	private final ResourceSet patternResourceSet = new ResourceSetImpl();
	private AdvancedViatraQueryEngine queryEngine;
	private final Set<IQuerySpecification<?>> queries = new HashSet<>();
	private final Map<String, EsperTemporalProperty> esperTemporalProperties = new HashMap<>();
	private IExecutionEngine<?> executionEngine;

	private final SpecificationBuilder builder = new SpecificationBuilder();
	private final PropertyCompiler compiler = new PropertyCompiler(builder);

	private Configuration configuration;
	private EPRuntime runtime;

	private final PropertyState[] samplePropertyStateArray = new PropertyState[0];

	public PropertyState[] getPropertiesStates() {
		final List<PropertyState> result = new ArrayList<>(
				temporalProperties.stream().map(p -> p.getPropertyState()).collect(Collectors.toList()));
//		result.addAll(esperTemporalProperties.values().stream().map(v -> v.getPropertyState()).collect(Collectors.toList()));
		return result.toArray(samplePropertyStateArray);
	}

//	private final LinkedTransferQueue<Object> matchQueue = new LinkedTransferQueue<>();

	@Override
	public void engineInitialized(IExecutionEngine<?> executionEngine) {
		this.executionEngine = executionEngine;
		initialize();
//		final IGemocDebugger debugger = executionEngine.getAddon(OmniscientGenericSequentialModelDebugger.class);
//		if (debugger != null) {
//			debugger.addPredicateBreakpoint((e, s) -> {
//				final boolean shouldBreak = matchQueue.poll() != null;
//				if (shouldBreak) {
//					matchQueue.clear();
//				}
//				return shouldBreak;
//			});
//		}
	}

	public void addProperty(String filePath) {
		final Resource propertyResource = patternResourceSet.getResource(URI.createPlatformResourceURI(filePath, true),
				true);
		if (propertyResource != null) {
			if (propertyResource.getErrors().size() == 0 && !propertyResource.getContents().isEmpty()) {
				final EObject topElement = propertyResource.getContents().get(0);
				if (topElement instanceof TemporalProperty) {
					final TemporalProperty property = (TemporalProperty) topElement;
					addProperty(property);
				}
			}
		}
	}

	private final List<AbstractTemporalProperty> temporalProperties = new ArrayList<>();
	private final List<AbstractTemporalProperty> activeTemporalProperties = new ArrayList<>();
	private final TransferQueue<AbstractTemporalProperty> temporalPropertiesToRemove = new LinkedTransferQueue<>();

	public void addProperty(TemporalProperty property) {
		temporalProperties.add(compiler.compileProperty(property));
	}

	public void clearProperties() {
		esperTemporalProperties.values().forEach(p -> removeProperty(p));
		esperTemporalProperties.clear();
		temporalProperties.forEach(p -> p.destroy(builder));
		temporalProperties.clear();
	}

	public void removeProperty(EsperTemporalProperty property) {
//		property.destroy(builder);
	}

	public void setEngine(IExecutionEngine<?> executionEngine) {
		this.executionEngine = executionEngine;
		executionEngine.getExecutionContext().getExecutionPlatform().addEngineAddon(this);
	}

	private boolean initialized = false;

	public void initialize() {
		final ResourceSet rs = executionEngine.getExecutionContext().getResourceModel().getResourceSet();
		final Set<ResourceSet> scopeRoots = new HashSet<>();
		scopeRoots.add(rs);
		queryEngine = AdvancedViatraQueryEngine.createUnmanagedEngine(new EMFScope(scopeRoots));
		queries.forEach(q -> {
			queryEngine.getMatcher(q);
		});
		configuration = new Configuration();
		configuration.getRuntime().getThreading().setInternalTimerEnabled(false);
		activeTemporalProperties.addAll(temporalProperties);
		activeTemporalProperties.forEach(p -> {
			configuration.getCommon().addEventType(p.getName(), new HashMap<>());
			p.getQueries().entrySet().forEach(e -> {
				queryEngine.getMatcher(e.getValue());
			});
		});
		runtime = EPRuntimeProvider.getRuntime(executionEngine.getName(), configuration);
		activeTemporalProperties.forEach(p -> p.deploy(runtime, prop -> temporalPropertiesToRemove.add(prop)));
		initialized = true;
	}

	public void reset() {
		if (queryEngine != null) {
			queryEngine.wipe();
			queryEngine.dispose();
			queryEngine = null;
		}
		if (runtime != null) {
			runtime.destroy();
			runtime = null;
		}
		initialized = false;
		queries.clear();
		activeTemporalProperties.clear();
		temporalPropertiesToRemove.clear();
	}

	@Override
	public void engineAboutToStop(IExecutionEngine<?> engine) {
		finalizeProperties();
	}

	@Override
	public void engineStopped(IExecutionEngine<?> engine) {
		reset();
	}

	private final Object executionAboutToStopObject = new Object();
	
	private void finalizeProperties() {
		final Map<String, Object> event = new HashMap<>();
		event.put("executionAboutToStop", executionAboutToStopObject);
		activeTemporalProperties.forEach(p -> {
			runtime.getEventService().sendEventMap(event, p.getName());
		});
		
//		esperTemporalProperties.values().forEach(p -> p.getPropertyState().finalize());
	}

	private final boolean logging = true;

	private final ThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(5);
	
	private void handleMatches() {
		if (queryEngine != null) {
			final Set<IPatternMatch> currentMatches = new PatternMatchSet();
			queryEngine.getCurrentMatchers().stream().flatMap(m -> m.getAllMatches().stream())
					.forEach(m -> currentMatches.add(m.toImmutable()));
//			executor.execute(() -> {
				activeTemporalProperties.forEach(p -> {
					final Map<String, Object> event = p.produceNewMatches(currentMatches);
					if (event != null) {
						if (logging) {
							if (event.isEmpty()) {
								System.out.println("----[Sending] Empty Event");
							} else {
								System.out.println("----[Sending] "
										+ event.entrySet().stream().map(e -> e.getKey() + ": " + e.getValue())
												.reduce((s1, s2) -> s1 + "; " + s2).orElse("ERROR: Empty Map"));
							}
						}
						runtime.getEventService().sendEventMap(event, p.getName());
					}
				});
				AbstractTemporalProperty toRemove = temporalPropertiesToRemove.poll();
				while (toRemove != null) {
					activeTemporalProperties.remove(toRemove);
					toRemove = temporalPropertiesToRemove.poll();
				}
//			});
		}
	}

	@Override
	public void aboutToExecuteStep(IExecutionEngine<?> engine, Step<?> stepToExecute) {
		if (initialized) {
			handleMatches();
		}

//		Arrays.stream(runtime.getDeploymentService().getStatementNames()).map(n -> epAdmin.getStatement(n)).forEach(statement -> {
//			if (statement.iterator().hasNext()) {
//				final EventBean event = statement.iterator().next();
//				final HashMap<String, Object> properties = new HashMap<>();
//				Arrays.stream(event.getEventType().getPropertyNames()).forEach(s -> properties.put(s, event.get(s)));
//				properties.entrySet().forEach(e -> System.out
//						.println("------[" + statement.getName() + "] " + e.getKey() + ": " + e.getValue()));
//			}
//		});
	}

	@Override
	public void stepExecuted(IExecutionEngine<?> engine, Step<?> stepExecuted) {
		if (initialized) {
			handleMatches();
		}
//		Arrays.stream(epAdmin.getStatementNames()).map(n -> epAdmin.getStatement(n)).forEach(statement -> {
//			if (statement.iterator().hasNext()) {
//				final String name = statement.getName();
//				final EventBean event = statement.iterator().next();
//				final HashMap<String, Object> properties = new HashMap<>();
//				Arrays.stream(event.getEventType().getPropertyNames()).forEach(s -> properties.put(s, event.get(s)));
//				properties.entrySet()
//						.forEach(e -> System.out.println("------[" + name + "] " + e.getKey() + ": " + e.getValue()));
//			}
//		});
	}
}
