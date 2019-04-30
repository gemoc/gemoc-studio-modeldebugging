package org.eclipse.gemoc.executionframework.property.monitor.manager;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.gemoc.executionframework.property.model.property.EPLProperty;
import org.eclipse.gemoc.executionframework.property.monitor.esper.EsperTemporalProperty;
import org.eclipse.gemoc.executionframework.property.monitor.esper.EsperTemporalProperty.PropertyState;
import org.eclipse.gemoc.executionframework.property.monitor.esper.PatternMatchEvent;
import org.eclipse.gemoc.executionframework.property.monitor.esper.StepEvent;
import org.eclipse.gemoc.trace.commons.model.trace.Step;
import org.eclipse.gemoc.xdsmlframework.api.core.IExecutionEngine;
import org.eclipse.gemoc.xdsmlframework.api.engine_addon.IEngineAddon;
import org.eclipse.viatra.query.patternlanguage.emf.specification.SpecificationBuilder;
import org.eclipse.viatra.query.runtime.api.AdvancedViatraQueryEngine;
import org.eclipse.viatra.query.runtime.api.IMatchUpdateListener;
import org.eclipse.viatra.query.runtime.api.IPatternMatch;
import org.eclipse.viatra.query.runtime.api.IQuerySpecification;
import org.eclipse.viatra.query.runtime.api.ViatraQueryMatcher;
import org.eclipse.viatra.query.runtime.emf.EMFScope;

import com.espertech.esper.client.Configuration;
import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.time.CurrentTimeEvent;
import com.espertech.esper.client.util.FastClassClassLoaderProvider;

@SuppressWarnings("rawtypes")
public class PropertyManager implements IEngineAddon {

	private final ResourceSet patternResourceSet = new ResourceSetImpl();
	private AdvancedViatraQueryEngine queryEngine;
	private final Set<IQuerySpecification<?>> queries = new HashSet<>();
	private final Map<String, EsperTemporalProperty> esperTemporalProperties = new HashMap<>();
	private IExecutionEngine<?> executionEngine;

	private final SpecificationBuilder builder = new SpecificationBuilder();

	private EPServiceProvider epService;

	public PropertyManager() {
		Configuration config = new Configuration();
		config.addEventType(PatternMatchEvent.class);
		config.addEventType(StepEvent.class);
		config.getEngineDefaults().getThreading().setInternalTimerEnabled(false);
		config.getTransientConfiguration().put(FastClassClassLoaderProvider.NAME, new FastClassClassLoaderProvider() {
			@Override
			public ClassLoader classloader(Class clazz) {
				final ClassLoader loader = Thread.currentThread().getContextClassLoader();
				if (isSubscriber(clazz)) {
					return makeBridge(loader, clazz.getClassLoader());
				}
				return loader;
			}
		});
		ClassLoader contextClassloader = Thread.currentThread().getContextClassLoader();
		Thread.currentThread().setContextClassLoader(this.getClass().getClassLoader());
		epService = EPServiceProviderManager.getProvider("" + System.nanoTime(), config);
		Thread.currentThread().setContextClassLoader(contextClassloader);
	}

	private final PropertyState[] samplePropertyStateArray = new PropertyState[0];

	public PropertyState[] getPropertiesStates() {
		return esperTemporalProperties.values().stream().map(v -> v.getPropertyState()).collect(Collectors.toList())
				.toArray(samplePropertyStateArray);
	}

//	private final LinkedTransferQueue<Object> matchQueue = new LinkedTransferQueue<>();

	@Override
	public void engineInitialized(IExecutionEngine<?> executionEngine) {
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
				if (topElement instanceof EPLProperty) {
					final EPLProperty property = (EPLProperty) topElement;
					addProperty(property);
				}
			}
		}
	}

	public void addProperty(EPLProperty property) {
		final EsperTemporalProperty p = new EsperTemporalProperty(epService.getEPAdministrator(), builder,
				property);
		final String pName = property.getName();
		esperTemporalProperties.put(pName, p);
	}

	public void clearProperties() {
		esperTemporalProperties.values().forEach(p -> removeProperty(p));
		esperTemporalProperties.clear();
	}

	public void removeProperty(EsperTemporalProperty property) {
		property.destroy(builder);
	}

	public void setEngine(IExecutionEngine<?> executionEngine) {
		this.executionEngine = executionEngine;
		executionEngine.getExecutionContext().getExecutionPlatform().addEngineAddon(this);
	}

	public void initialize() {
		final ResourceSet rs = executionEngine.getExecutionContext().getResourceModel().getResourceSet();
		final Set<ResourceSet> scopeRoots = new HashSet<>();
		scopeRoots.add(rs);
		queryEngine = AdvancedViatraQueryEngine.createUnmanagedEngine(new EMFScope(scopeRoots));
		queries.forEach(q -> {
			queryEngine.getMatcher(q);
		});
		esperTemporalProperties.values().forEach(p -> {
//			p.registerStatement(epService.getEPAdministrator(), matchQueue);
			p.registerStatement(epService.getEPAdministrator(), null);
			p.getQueries().entrySet().forEach(e -> {
				final ViatraQueryMatcher<? extends IPatternMatch> m = queryEngine.getMatcher(e.getValue());
				final IMatchUpdateListener<IPatternMatch> listener = new IMatchUpdateListener<IPatternMatch>() {
					@Override
					public void notifyAppearance(IPatternMatch match) {
						epService.getEPRuntime().sendEvent(new PatternMatchEvent(match, e.getKey(), true));
					}

					@Override
					public void notifyDisappearance(IPatternMatch match) {
						epService.getEPRuntime().sendEvent(new PatternMatchEvent(match, e.getKey(), false));
					}
				};
				queryEngine.addMatchUpdateListener(m, listener, false);
			});
		});
//		epService.getEPAdministrator().createEPL("select * from StepEvent").addListener((n, o) -> {
//			Arrays.stream(n).forEach(e -> {
//				final String name = (String) e.get("name");
//				final boolean start = (boolean) e.get("start");
//				System.out.println((start ? "---[New] step started: " : "---[New] step ended: ") + name);
//			});
//			Arrays.stream(o).forEach(e -> {
//				final String name = (String) e.get("name");
//				final boolean start = (boolean) e.get("start");
//				System.out.println((start ? "---[Old] step started: " : "---[Old] step ended: ") + name);
//			});
//		});
	}

	public void reset() {
		if (queryEngine != null) {
			queryEngine.wipe();
			queryEngine.dispose();
			queryEngine = null;
		}
		if (epService != null) {
			epService.getEPAdministrator().destroyAllStatements();
		}
		stepNb = 0;
	}

	@Override
	public void engineAboutToStop(IExecutionEngine<?> engine) {
		finalizeProperties();
	}

	@Override
	public void engineStopped(IExecutionEngine<?> engine) {
		reset();
	}

	private void finalizeProperties() {
		esperTemporalProperties.values().forEach(p -> p.getPropertyState().finalize());
	}

//	private final LinkedTransferQueue<Object> stepQueue = new LinkedTransferQueue<>();
//	private final Object token = new Object();
//	private Thread delayingThread = null;

	private int stepNb = 0;

	@Override
	public void aboutToExecuteStep(IExecutionEngine<?> engine, Step<?> stepToExecute) {
		stepNb++;
		epService.getEPRuntime().sendEvent(new StepEvent(stepToExecute.getMseoccurrence().getMse().getName(), true));
		final CurrentTimeEvent timeEvent = new CurrentTimeEvent(stepNb * 1000);
		epService.getEPRuntime().sendEvent(timeEvent);
//		if (delayingThread == null) {
//			delayingThread = new Thread(() -> {
//				try {
//					queryEngine.delayUpdatePropagation(() -> stepQueue.take());
//				} catch (InvocationTargetException e) {
//					e.printStackTrace();
//				}
//			});
//			delayingThread.start();
//		}
//		update();
	}

	@Override
	public void stepExecuted(IExecutionEngine<?> engine, Step<?> stepExecuted) {
		stepNb++;
		epService.getEPRuntime().sendEvent(new StepEvent(stepExecuted.getMseoccurrence().getMse().getName(), false));
		final CurrentTimeEvent timeEvent = new CurrentTimeEvent(stepNb * 1000);
		epService.getEPRuntime().sendEvent(timeEvent);
//		update();
	}

	private boolean isSubscriber(Class<?> clazz) {
		boolean result = false;
		Class<?> tmp = clazz.getSuperclass();
		while (!result && tmp != null && tmp != Object.class) {
			if (tmp == EsperTemporalProperty.class) {
				result = true;
			} else {
				tmp = tmp.getSuperclass();
			}
		}
		return result;
	}

	private ClassLoader makeBridge(ClassLoader targetSpace, ClassLoader privateSpace) {
		return new ClassLoader(targetSpace) {
			@Override
			protected Class<?> findClass(String name) throws ClassNotFoundException {
				return privateSpace.loadClass(name);
			}
		};
	}

//	private void update() {
//		stepQueue.add(token);
//		try {
//			delayingThread.join();
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}

//		queryEngine.getCurrentMatchers().forEach(matcher -> {
//			queryEngine.addMatchUpdateListener(matcher, new IMatchUpdateListener<IPatternMatch>() {
//				@Override
//				public void notifyAppearance(IPatternMatch match) {
//					matchQueue.put(match);
//				}
//
//				@Override
//				public void notifyDisappearance(IPatternMatch match) {
//
//				}
//			}, false);
//			final IQuerySpecification<?> specification = matcher.getSpecification();
//			final int count = matcher.countMatches();
//			propertyToState.get(specification).value = "" + count;
//		});

//		delayingThread = new Thread(() -> {
//			try {
//				queryEngine.delayUpdatePropagation(() -> stepQueue.take());
//			} catch (InvocationTargetException e) {
//				e.printStackTrace();
//			}
//		});
//		delayingThread.start();
//	}
}
