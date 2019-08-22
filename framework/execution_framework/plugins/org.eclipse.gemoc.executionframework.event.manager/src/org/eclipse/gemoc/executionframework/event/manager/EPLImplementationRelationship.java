package org.eclipse.gemoc.executionframework.event.manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import org.eclipse.gemoc.executionframework.event.model.event.EventOccurrence;
import org.eclipse.gemoc.executionframework.behavioralinterface.behavioralInterface.BehavioralInterface;
import org.eclipse.gemoc.executionframework.behavioralinterface.behavioralInterface.Event;

import com.espertech.esper.client.Configuration;
import com.espertech.esper.client.EPException;
import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.EPStatement;
import com.espertech.esper.client.util.FastClassClassLoaderProvider;

public class EPLImplementationRelationship implements IImplementationRelationship {

	private final BehavioralInterface behavioralInterface;

	private final EPServiceProvider epService;

	protected Consumer<ICallRequest> callRequestConsumer;

	protected Consumer<EventOccurrence> eventOccurrenceConsumer;

	private final Map<String, Event> behavioralUnitToEvent = new HashMap<>();

	private final List<ImplementationRuleSubscriber> rules;

	public EPLImplementationRelationship(BehavioralInterface behavioralInterface,
			List<ImplementationRuleSubscriber> rules) {
		this.behavioralInterface = behavioralInterface;
		Configuration config = new Configuration();
		config.addEventType(CallNotification.class);
		config.addEventType(ReturnNotification.class);
		config.addEventType(EPLEventOccurrence.class);
		config.getTransientConfiguration().put(FastClassClassLoaderProvider.NAME, new FastClassClassLoaderProvider() {
			@Override
			public ClassLoader classloader(@SuppressWarnings("rawtypes") Class clazz) {
				final ClassLoader loader = Thread.currentThread().getContextClassLoader();
				if (isSubscriber(clazz)) {
					return makeBridge(loader, clazz.getClassLoader());
				}
				return loader;
			}
		});
		epService = EPServiceProviderManager.getProvider("" + System.nanoTime(), config);
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
	public BehavioralInterface getBehavioralInterface() {
		return behavioralInterface;
	}

	@Override
	public void processEventOccurrence(EventOccurrence eventOccurrence) {
		epService.getEPRuntime().sendEvent(new EPLEventOccurrence(eventOccurrence));
	}

	@Override
	public void processCallNotification(BehavioralUnitNotification behavioralUnitNotification) {
		epService.getEPRuntime().sendEvent(behavioralUnitNotification);
	}

	private void configureSubscribers() {
		rules.forEach(r -> r.configure(behavioralInterface, callRequestConsumer, eventOccurrenceConsumer,
				behavioralUnitToEvent));
	}

	@Override
	public void configure(Consumer<EventOccurrence> eventOccurrenceconsumer,
			Consumer<ICallRequest> callRequestConsumer) {
		this.eventOccurrenceConsumer = eventOccurrenceconsumer;
		this.callRequestConsumer = callRequestConsumer;
		configureSubscribers();
	}

	private boolean isSubscriber(Class<?> clazz) {
		boolean result = false;
		Class<?> tmp = clazz.getSuperclass();
		while (!result && tmp != null && tmp != Object.class) {
			if (tmp == ImplementationRuleSubscriber.class) {
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
