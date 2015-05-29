package de.tu_darmstadt.sport.fvf.adapter.stub;

import de.tu_darmstadt.sport.fvf.adapter.AbstractLedAdapter;
import de.tu_darmstadt.sport.fvf.testrunner.TestRunnerListenerCollection;

/**
 * Does some general collaboration.
 * 
 */
public class StubLedAdapter extends AbstractLedAdapter {
	
	// led on
	public void ledOn(int led) {
		listeners.fireEvent(TestRunnerListenerCollection.LED_ON, led, false);
	}
	
	public void ledOn(int led, int brightness) {
		listeners.fireEvent(TestRunnerListenerCollection.LED_ON, led, false);
	}
	
	// led flicker
	
	public void ledFlicker(int led, double frequency, int duration) {
		listeners.fireEvent(TestRunnerListenerCollection.LED_ON, led, true);
	}

	public void ledFlicker(int led, double frequency, int brightness, int light, int dark) {
		listeners.fireEvent(TestRunnerListenerCollection.LED_ON, led, true);
	}
	
	// led off
	
	public void ledOff(int led) {
		listeners.fireEvent(TestRunnerListenerCollection.LED_OFF, led);
	}

}
