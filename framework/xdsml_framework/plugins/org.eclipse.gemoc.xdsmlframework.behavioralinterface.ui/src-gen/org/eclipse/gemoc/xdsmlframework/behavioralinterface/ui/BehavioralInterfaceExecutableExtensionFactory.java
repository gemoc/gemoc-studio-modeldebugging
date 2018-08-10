/*
 * generated by Xtext 2.12.0
 */
package org.eclipse.gemoc.xdsmlframework.behavioralinterface.ui;

import com.google.inject.Injector;
import org.eclipse.gemoc.xdsmlframework.behavioralinterface.ui.internal.BehavioralinterfaceActivator;
import org.eclipse.xtext.ui.guice.AbstractGuiceAwareExecutableExtensionFactory;
import org.osgi.framework.Bundle;

/**
 * This class was generated. Customizations should only happen in a newly
 * introduced subclass. 
 */
public class BehavioralInterfaceExecutableExtensionFactory extends AbstractGuiceAwareExecutableExtensionFactory {

	@Override
	protected Bundle getBundle() {
		return BehavioralinterfaceActivator.getInstance().getBundle();
	}
	
	@Override
	protected Injector getInjector() {
		return BehavioralinterfaceActivator.getInstance().getInjector(BehavioralinterfaceActivator.ORG_ECLIPSE_GEMOC_XDSMLFRAMEWORK_BEHAVIORALINTERFACE_BEHAVIORALINTERFACE);
	}
	
}
