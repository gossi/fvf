package de.tu_darmstadt.sport.fvf.adapter;

import de.tu_darmstadt.sport.fvf.testrunner.ILedAdapter;
import de.tu_darmstadt.sport.fvf.testrunner.TestRunnerListenerCollection;

public abstract class AbstractLedAdapter implements ILedAdapter {
	
	protected TestRunnerListenerCollection listeners;

	@Override
	public void setTestRunnerListenerCollection(TestRunnerListenerCollection collection) {
		listeners = collection;
	}
}
