package org.eclipse.gemoc.executionframework.property.monitor.esper;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.gemoc.executionframework.property.model.property.EPLProperty;
import org.eclipse.viatra.query.patternlanguage.emf.specification.SpecificationBuilder;
import org.eclipse.viatra.query.runtime.api.IQuerySpecification;

import com.espertech.esper.client.EPAdministrator;
import com.espertech.esper.client.EPException;
import com.espertech.esper.client.EPStatement;
import com.espertech.esper.client.soda.EPStatementObjectModel;

@SuppressWarnings("restriction")
public class EsperTemporalProperty {

	private final EPStatementObjectModel statement;

	private final Map<String, IQuerySpecification<?>> queries = new HashMap<>();

	private final PropertyState propertyState;

	private final Resource propertyResource;
	
	private final String name;

	public EsperTemporalProperty(EPAdministrator admin, SpecificationBuilder builder, EPLProperty property) {
		propertyResource = property.eResource();
		name = propertyResource.getURI().trimFileExtension().lastSegment();
		propertyState = new PropertyState(property.getName(),
				property.isInitialTruthValue() ? TruthValue.TEMPORARILY_SATISFIED : TruthValue.TEMPORARILY_VIOLATED);
		try {
			statement = admin.compileEPL(property.getStatement());
		} catch (EPException e) {
			propertyResource.getResourceSet().getResources().remove(propertyResource);
			throw e;
		}
		property.getPatterns().forEach(p -> {
			final IQuerySpecification<?> query = builder.getOrCreateSpecification(p, true);
			queries.put(p.getName(), query);
		});
	}

	public EPStatement registerStatement(EPAdministrator admin) {
		final EPStatement compiledStatement = admin.create(this.statement);
		compiledStatement.addListener((n, o) -> {
			if (n != null && n.length > 0) {
				final HashMap<String, Object> properties = new HashMap<>();
				Arrays.stream(n[0].getEventType().getPropertyNames()).forEach(s -> properties.put(s, n[0].get(s)));
				properties.entrySet().forEach(e -> System.out.println("------[" + name + "] " + e.getKey() + ": " + e.getValue()));
				if (propertyState.getValue() == TruthValue.TEMPORARILY_VIOLATED) {
					propertyState.setValue(TruthValue.VIOLATED);
				} else if (propertyState.getValue() == TruthValue.TEMPORARILY_SATISFIED) {
					propertyState.setValue(TruthValue.SATISFIED);
				}
			}
		});
		return compiledStatement;
	}

	public void destroy(SpecificationBuilder builder) {
		queries.values().forEach(q -> builder.forgetSpecification(q));
		propertyResource.getResourceSet().getResources().remove(propertyResource);
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
			if (value == TruthValue.TEMPORARILY_VIOLATED) {
				value = TruthValue.VIOLATED;
			} else if (value == TruthValue.TEMPORARILY_SATISFIED) {
				value = TruthValue.SATISFIED;
			}
		}
	}
}
