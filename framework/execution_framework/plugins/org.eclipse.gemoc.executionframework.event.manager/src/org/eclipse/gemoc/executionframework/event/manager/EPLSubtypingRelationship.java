package org.eclipse.gemoc.executionframework.event.manager;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.gemoc.executionframework.event.model.event.EventOccurrence;
import org.eclipse.gemoc.executionframework.behavioralinterface.behavioralInterface.BehavioralInterface;

import com.espertech.esper.client.Configuration;
import com.espertech.esper.client.EPException;
import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.EPStatement;
import com.espertech.esper.client.util.FastClassClassLoaderProvider;

public class EPLSubtypingRelationship implements ISubtypingRelationship {

	private final BehavioralInterface supertype;
	
	private final BehavioralInterface subtype;
	
	private final EPServiceProvider epService;
	
	private Consumer<EventOccurrence> eventOccurrenceConsumer;
	
	private Resource executedResource;

	private final List<SubtypingRuleSubscriber> rules;
	
	public EPLSubtypingRelationship(BehavioralInterface supertype,
			BehavioralInterface subtype,
			List<SubtypingRuleSubscriber> rules) {
		this.supertype = supertype;
		this.subtype = subtype;
		Configuration config = new Configuration();
		config.addEventType(EPLEventOccurrence.class);
		config.getTransientConfiguration().put(FastClassClassLoaderProvider.NAME, new FastClassClassLoaderProvider() {
			@Override
			public ClassLoader classloader(@SuppressWarnings("rawtypes") Class clazz) {
				final ClassLoader loader = Thread.currentThread().getContextClassLoader();
				if (isLocal(clazz) || isSubscriber(clazz)) {
					return makeBridge(loader, clazz.getClassLoader());
				}
				return loader;
			}
		});
		epService = EPServiceProviderManager.getProvider(""+System.nanoTime(), config);
		this.rules = new ArrayList<>(rules);
		this.rules.forEach(r -> {
			try {
				final EPStatement statement = epService.getEPAdministrator().createEPL(r.getStatement());
				statement.setSubscriber(r);
			} catch (EPException e) {
				e.printStackTrace();
			}
		});
	}

	@Override
	public BehavioralInterface getSupertype() {
		return supertype;
	}

	@Override
	public BehavioralInterface getSubtype() {
		return subtype;
	}
	
	@Override
	public void processEventOccurrence(EventOccurrence eventOccurrence) {
		epService.getEPRuntime().sendEvent(new EPLEventOccurrence(eventOccurrence));
	}
	
	private void configureSubscribers() {
		rules.forEach(r -> r.configure(supertype, subtype, eventOccurrenceConsumer, executedResource));
	}
	
	@Override
	public void setExecutedResource(Resource executedResource) {
		this.executedResource = executedResource;
		configureSubscribers();
	}
	
	@Override
	public void configure(Consumer<EventOccurrence> consumer) {
		this.eventOccurrenceConsumer = consumer;
		configureSubscribers();
	}
	
	protected boolean isLocal(Class<?> clazz) {
		return false;
	}
	
	private boolean isSubscriber(Class<?> clazz) {
		boolean result = false;
		Class<?> tmp = clazz.getSuperclass();
		while (!result && tmp != null && tmp != Object.class) {
			if (tmp == SubtypingRuleSubscriber.class) {
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
}
