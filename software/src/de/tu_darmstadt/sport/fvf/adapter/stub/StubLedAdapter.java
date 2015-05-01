package de.tu_darmstadt.sport.fvf.adapter.stub;

import de.tu_darmstadt.sport.fvf.adapter.AbstractLedAdapter;
import de.tu_darmstadt.sport.fvf.testrunner.ILedAdapter;
import de.tu_darmstadt.sport.fvf.testrunner.TestRunnerListenerCollection;

/**
 * Does some general collaboration.
 * 
 */
public class StubLedAdapter extends AbstractLedAdapter {
	
	// led on
	public void ledOn(int led) {
		ledFlicker(led, ILedAdapter.BRIGHTNESS_HIGH);
	}
	
	public void ledOn(int led, int brightness) {
		listeners.fireEvent(TestRunnerListenerCollection.LED_ON, led, brightness, false);
	}
	
	// led flicker
	
	public void ledFlicker(int led, double frequency) {
		ledFlicker(led, frequency, ILedAdapter.BRIGHTNESS_HIGH);
	}

	public void ledFlicker(int led, double frequency, int brightness) {
		listeners.fireEvent(TestRunnerListenerCollection.LED_ON, led, brightness, true);
	}
	
	// led off
	
	public void ledOff(int led) {
		listeners.fireEvent(TestRunnerListenerCollection.LED_OFF, led);
	}

}
