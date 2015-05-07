package de.tu_darmstadt.sport.fvf.testrunner;

public interface ILedAdapter {
	
	/**
	 * Turns on a LED
	 * 
	 * @param led the number of the LED
	 */
	public void ledOn(int led);
	
	public void ledFlicker(int led, double frequency, int duration);
	
	public void ledFlicker(int led, double frequency, int duration, int light, int dark);
	
	/**
	 * Turns off a LED
	 * 
	 * @param led the number of the LED
	 */
	public void ledOff (int led);	

	public void setTestRunnerListenerCollection(TestRunnerListenerCollection collection);
}
