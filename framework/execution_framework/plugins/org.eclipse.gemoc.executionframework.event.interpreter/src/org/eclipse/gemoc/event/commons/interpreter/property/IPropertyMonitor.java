package org.eclipse.gemoc.event.commons.interpreter.property;

import org.eclipse.gemoc.executionframework.event.model.property.Property;

public interface IPropertyMonitor {
	
	void monitorProperty(Property property, IPropertyListener listener);
	
	void unmonitorProperty(Property property, IPropertyListener listener);
}
