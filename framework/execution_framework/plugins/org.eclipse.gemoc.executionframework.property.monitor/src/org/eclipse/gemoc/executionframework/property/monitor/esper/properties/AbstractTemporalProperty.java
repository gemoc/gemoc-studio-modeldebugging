package org.eclipse.gemoc.executionframework.property.monitor.esper.properties;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import org.eclipse.gemoc.executionframework.property.monitor.esper.TruthValue;
import org.eclipse.viatra.query.patternlanguage.emf.specification.SpecificationBuilder;
import org.eclipse.viatra.query.runtime.api.IPatternMatch;
import org.eclipse.viatra.query.runtime.api.IQuerySpecification;

import com.espertech.esper.common.client.EPCompiled;
import com.espertech.esper.common.client.configuration.Configuration;
import com.espertech.esper.common.internal.event.map.MapEventBean;
import com.espertech.esper.compiler.client.CompilerArguments;
import com.espertech.esper.compiler.client.EPCompileException;
import com.espertech.esper.compiler.client.EPCompilerProvider;
import com.espertech.esper.runtime.client.DeploymentOptions;
import com.espertech.esper.runtime.client.EPDeployException;
import com.espertech.esper.runtime.client.EPDeployment;
import com.espertech.esper.runtime.client.EPRuntime;
import com.espertech.esper.runtime.client.EPRuntimeDestroyedException;
import com.espertech.esper.runtime.client.EPUndeployException;

public abstract class AbstractTemporalProperty {

	protected final Map<String, IQuerySpecification<?>> queries = new HashMap<>();

	private final Map<String, IPatternMatch> lastMatches = new HashMap<>();

	private final PropertyState propertyState;

	private final String name;

	protected EPCompiled compiled;

	protected final SpecificationBuilder builder;
	
	private final Set<EPRuntime> activeRuntimes = new HashSet<>();

	public AbstractTemporalProperty(SpecificationBuilder builder, String name) {
		this.builder = builder;
		this.name = name;
		propertyState = new PropertyState(name, TruthValue.UNKNOWN);
	}

	public Map<String, Object> produceNewMatches(Set<IPatternMatch> matches) {
		final List<IPatternMatch> relevantMatches = matches.stream().filter(m -> queries.containsKey(m.patternName()))
				.collect(Collectors.toList());
		final boolean sameMatches = relevantMatches.isEmpty() ? lastMatches.isEmpty()
				: relevantMatches.stream()
						.allMatch(m -> lastMatches.containsKey(m.patternName())
								? lastMatches.get(m.patternName()).isCompatibleWith(m)
								: false);
		if (sameMatches) {
//			final Map<String, Object> result = new HashMap<>(lastMatches);
//			return result;
			return null;
		} else {
			lastMatches.clear();
			relevantMatches.forEach(m -> lastMatches.put(m.patternName(), m));
			final Map<String, Object> result = new HashMap<>(lastMatches);
			return result;
		}
	}

	public String getName() {
		return name;
	}

	protected abstract String getStatementString();
	
	protected abstract TruthValue getStatus(Map<String, List<Map<?, ?>>> events);

	protected String getFqn(IQuerySpecification<?> query) {
		return query.getFullyQualifiedName().replaceAll("\\.", "\\\\.");
	}

	public Configuration getConfiguration() {
		Configuration configuration = new Configuration();
		configuration.getCommon().addEventType(name, new HashMap<>());
		configuration.getCompiler().getExpression().setDuckTyping(true);
		return configuration;
	}

	public void compileEPL() {
		try {
			compiled = EPCompilerProvider.getCompiler().compile(getStatementString(),
					new CompilerArguments(getConfiguration()));
		} catch (EPCompileException e) {
			e.printStackTrace();
		}
	}
	
	public void deploy(EPRuntime runtime, Consumer<AbstractTemporalProperty> onTriggered) {
		try {
			activeRuntimes.add(runtime);
			final EPDeployment deployment = runtime.getDeploymentService().deploy(compiled,
					new DeploymentOptions().setDeploymentId(name + System.nanoTime()));
			Arrays.stream(deployment.getStatements()).forEach(statement -> statement.addListener((events, o, x, rt) -> {
				if (events != null) {
					triggered(Arrays.stream(events).map(e -> (MapEventBean) e).collect(Collectors.toList()));
					if (propertyState.getValue() == TruthValue.SATISFIED || propertyState.getValue() == TruthValue.VIOLATED) {
						try {
							rt.getDeploymentService().undeploy(deployment.getDeploymentId());
							onTriggered.accept(this);
						} catch (EPRuntimeDestroyedException e) {
							e.printStackTrace();
						} catch (EPUndeployException e) {
							e.printStackTrace();
						}
					}
				}
			}));
		} catch (EPDeployException e) {
			throw new RuntimeException(e);
		}
	}

	protected void triggered(List<MapEventBean> triggeringEvents) {
		final Map<String, List<Map<?, ?>>> result = new HashMap<>();
		triggeringEvents.forEach(eventBean -> {
			eventBean.getProperties().entrySet().forEach(e -> {
				if (e.getValue() != null) {
					final List<Map<?, ?>> list = result.computeIfAbsent(e.getKey(), k -> new ArrayList<>());
					if (e.getValue() instanceof Map<?, ?>[]) {
						Arrays.stream((Map<?, ?>[]) e.getValue()).forEach(m -> list.add(m));
					} else {
						list.add((Map<?, ?>) e.getValue());
					}
				}
			});
		});
		final TruthValue status = getStatus(result);
		if (status == TruthValue.SATISFIED || status == TruthValue.VIOLATED) {
			System.out.println("----[" + name + "] " + status);
			result.entrySet().forEach(entry -> {
				final List<Map<?, ?>> list = entry.getValue();
				if (!list.isEmpty()) {
					System.out.println("--------[" + entry.getKey() + "]");
					list.forEach(m -> {
						m.entrySet().stream()
								.forEach(e -> System.out.println("------------ " + e.getValue()));
					});
				}
			});
		}
		propertyState.setValue(status);
	}
	
	public void destroy(SpecificationBuilder builder) {
		queries.values().forEach(q -> builder.forgetSpecification(q));
	}

	public Map<String, IQuerySpecification<?>> getQueries() {
		return queries;
	}

	public PropertyState getPropertyState() {
		return propertyState;
	}

	public static class PropertyState {
		private final String name;
		private TruthValue value;

		public PropertyState(String name, TruthValue value) {
			this.name = name;
			this.value = value;
		}

		public String getName() {
			return name;
		}

		public TruthValue getValue() {
			return value;
		}

		public void setValue(TruthValue value) {
			this.value = value;
		}

		public void finalize() {
//			if (value == TruthValue.TEMPORARILY_VIOLATED) {
//				value = TruthValue.VIOLATED;
//			} else if (value == TruthValue.TEMPORARILY_SATISFIED) {
//				value = TruthValue.SATISFIED;
//			}
		}
	}
}
