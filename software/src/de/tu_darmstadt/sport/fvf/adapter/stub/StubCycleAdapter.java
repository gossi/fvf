package de.tu_darmstadt.sport.fvf.adapter.stub;

import de.tu_darmstadt.sport.fvf.testrunner.ILedAdapter;
import de.tu_darmstadt.sport.fvf.testrunner.IMeasurementCycleAdapter;
import de.tu_darmstadt.sport.fvf.testrunner.TestRunnerListenerCollection;

public class StubCycleAdapter implements IMeasurementCycleAdapter {

	private ILedAdapter ledAdapter;
	private TestRunnerListenerCollection listeners;
	
	public StubCycleAdapter(ILedAdapter ledAdapter) {
		this.ledAdapter = ledAdapter;
	}

	/**
	 * Runs one measurement
	 */
	public void run(int mode, int flickerLed, double frequency, int onDuration, int offDuration, int cyclePause, int brightness) throws InterruptedException {

		// glow all at the beginning wait a little
		ledsOff(mode);
		Thread.sleep(offDuration);
		ledsOn(mode, brightness);
		Thread.sleep(onDuration);
		ledsOff(mode);
		
		for (int i = 1; i <= mode; i++) {
			if (i == flickerLed) {
				ledAdapter.ledFlicker(i, frequency);
			} else {
				ledAdapter.ledOn(i);
			}
			Thread.sleep(onDuration);
			ledAdapter.ledOff(i);
			Thread.sleep(offDuration);
		}

		// all leds on, wait for input
		ledsOn(mode, 100);
	}
	
	private void ledsOn(int mode, int brightness) throws InterruptedException {
		for (int i = 1; i <= mode; i++) {
			ledAdapter.ledOn(i);
		}
	}
	
	private void ledsOff(int mode) {
		for (int i = 1; i <= mode; i++) {
			ledAdapter.ledOff(i);
		}
	}
	
	public void setTestRunnerListenerCollection(TestRunnerListenerCollection collection) {
		listeners = collection;
		ledAdapter.setTestRunnerListenerCollection(listeners);
	}

}
