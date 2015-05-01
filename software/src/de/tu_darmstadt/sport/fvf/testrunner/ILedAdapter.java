package de.tu_darmstadt.sport.fvf.testrunner;

public interface ILedAdapter {
	
	public final static int BRIGHTNESS_HIGH = 100;
	
	/**
	 * Turns on a LED
	 * 
	 * @param led the number of the LED
	 */
	public void ledOn(int led);
	
	/**
	 * Turns on a LED
	 * 
	 * @param led the number of the LED
	 */
	public void ledOn(int led, int brightness);
	
	public void ledFlicker(int led, double frequency);
	
	public void ledFlicker(int led, double frequency, int brightness);
	
	/**
	 * Turns off a LED
	 * 
	 * @param led the number of the LED
	 */
	public void ledOff (int led);	

	public void setTestRunnerListenerCollection(TestRunnerListenerCollection collection);
}
