package org.eclipse.gemoc.executionframework.event.interpreter.scenario;

import org.eclipse.gemoc.executionframework.event.model.scenario.Arbiter;
import org.eclipse.gemoc.executionframework.event.model.scenario.TruthValue;

public interface IArbiterManager {

	TruthValue getTruthValue();

	void loadArbiter(Arbiter<?, ?, ?> arbiter);

}