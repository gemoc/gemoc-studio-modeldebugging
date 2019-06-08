package org.eclipse.gemoc.executionframework.property.monitor.manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.LinkedTransferQueue;
import java.util.concurrent.TransferQueue;
import java.util.stream.Collectors;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.gemoc.executionframework.property.model.property.TemporalProperty;
import org.eclipse.gemoc.executionframework.property.monitor.esper.TruthValue;
import org.eclipse.gemoc.executionframework.property.monitor.esper.properties.AbstractTemporalProperty;
import org.eclipse.gemoc.executionframework.property.monitor.esper.properties.AbstractTemporalProperty.PropertyState;
import org.eclipse.gemoc.trace.commons.model.trace.Step;
import org.eclipse.gemoc.xdsmlframework.api.core.IExecutionEngine;
import org.eclipse.gemoc.xdsmlframework.api.engine_addon.IEngineAddon;
import org.eclipse.viatra.query.patternlanguage.emf.specification.SpecificationBuilder;
import org.eclipse.viatra.query.runtime.api.AdvancedViatraQueryEngine;
import org.eclipse.viatra.query.runtime.api.IPatternMatch;
import org.eclipse.viatra.query.runtime.api.IQuerySpecification;
import org.eclipse.viatra.query.runtime.api.ViatraQueryMatcher;
import org.eclipse.viatra.query.runtime.emf.EMFScope;

import com.espertech.esper.common.client.configuration.Configuration;
import com.espertech.esper.runtime.client.EPRuntime;
import com.espertech.esper.runtime.client.EPRuntimeProvider;

public class PropertyManager implements IEngineAddon {

	private final ResourceSet patternResourceSet = new ResourceSetImpl();
	private AdvancedViatraQueryEngine queryEngine;
	private final Set<IQuerySpecification<?>> queries = new HashSet<>();
	private IExecutionEngine<?> executionEngine;

	private final SpecificationBuilder builder = new SpecificationBuilder();
	private final PropertyCompiler compiler = new PropertyCompiler(builder);

	private Configuration configuration;
	private EPRuntime runtime;

	private final PropertyState[] samplePropertyStateArray = new PropertyState[0];

	public PropertyState[] getPropertiesStates() {
		final List<PropertyState> result = new ArrayList<>(
				temporalProperties.stream().map(p -> p.getPropertyState()).collect(Collectors.toList()));
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
		temporalProperties.forEach(p -> p.destroy(builder));
		temporalProperties.clear();
	}

	public void setEngine(IExecutionEngine<?> executionEngine) {
		this.executionEngine = executionEngine;
		executionEngine.getExecutionContext().getExecutionPlatform().addEngineAddon(this);
	}

	private boolean initialized = false;
	
	private final Set<ViatraQueryMatcher<?>> touchedMatchers = new HashSet<>();

	public void initialize() {
		final ResourceSet rs = executionEngine.getExecutionContext().getResourceModel().getResourceSet();
		final Set<ResourceSet> scopeRoots = new HashSet<>();
		scopeRoots.add(rs);
		queryEngine = AdvancedViatraQueryEngine.createUnmanagedEngine(new EMFScope(scopeRoots));
		configuration = new Configuration();
		configuration.getRuntime().getThreading().setInternalTimerEnabled(false);
		activeTemporalProperties.addAll(temporalProperties);
		activeTemporalProperties.forEach(p -> {
			configuration.getCommon().addEventType(p.getName(), new HashMap<>());
			p.getQueries().entrySet().forEach(e -> {
				/*final ViatraQueryMatcher<?> matcher = */queryEngine.getMatcher(e.getValue());
//				queryEngine.addMatchUpdateListener(matcher, new IMatchUpdateListener<IPatternMatch>() {
//					@Override
//					public void notifyAppearance(IPatternMatch match) {
//						touchedMatchers.add(matcher);
//					}
//					@Override
//					public void notifyDisappearance(IPatternMatch match) {
//						touchedMatchers.add(matcher);
//					}
//				}, false);
//				p.addMatcher(matcher);
			});
		});
		runtime = EPRuntimeProvider.getRuntime(executionEngine.getName(), configuration);
		activeTemporalProperties.forEach(p -> p.deploy(runtime, prop -> temporalPropertiesToRemove.add(prop)));
		temporalProperties.forEach(p -> p.getPropertyState().setValue(TruthValue.UNKNOWN));
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
//		temporalProperties.forEach(p -> p.reset());
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
	}

//	private final boolean logging = true;

//	private final ThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(5);

	private void handleMatches() {
		if (queryEngine != null) {
			final Set<IPatternMatch> currentMatches = new PatternMatchSet();
			queryEngine.getCurrentMatchers().stream().flatMap(m -> m.getAllMatches().stream())
					.forEach(m -> currentMatches.add(m.toImmutable()));
//			executor.execute(() -> {
			activeTemporalProperties.forEach(p -> {
				final Map<String, Object> event = p.produceNewMatches(currentMatches);
//				final Map<String, Object> event = p.produceNewMatches2(touchedMatchers);
				if (event != null) {
//					if (logging) {
						if (event.isEmpty()) {
							System.out.println("----[Sending] Empty Event");
						} else {
							System.out.println("----[Sending] "
									+ event.entrySet().stream().map(e -> e.getKey() + ": " + e.getValue())
											.reduce((s1, s2) -> s1 + "; " + s2).orElse("ERROR: Empty Map"));
						}
//					}
					runtime.getEventService().sendEventMap(event, p.getName());
				}
			});
			AbstractTemporalProperty toRemove = temporalPropertiesToRemove.poll();
			while (toRemove != null) {
				activeTemporalProperties.remove(toRemove);
				toRemove = temporalPropertiesToRemove.poll();
			}
//			});
			touchedMatchers.clear();
		}
	}

	@Override
	public void aboutToExecuteStep(IExecutionEngine<?> engine, Step<?> stepToExecute) {
		if (initialized) {
			handleMatches();
		}
	}

	@Override
	public void stepExecuted(IExecutionEngine<?> engine, Step<?> stepExecuted) {
		if (initialized) {
			handleMatches();
		}
	}
}
