package org.eclipse.gemoc.trace.gemoc.api;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.gemoc.executionframework.debugger.IMutableFieldExtractor;

public interface IModelAccessor {

	public void setIMutableFieldExtractor(IMutableFieldExtractor extractor);
	
	public Object getValue(EObject object, String featureName);
	
	public void setValue(EObject object, String featureName, Object value);
	
}
