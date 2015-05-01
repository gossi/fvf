package de.tu_darmstadt.sport.fvf.driver;

public interface SerialListener {

	public void onInput(String input);
	
	public void onError(int errorNumber);
	
	public void ledOn(int led);
	
	public void ledOff(int led);
	
	public void ledFlicker(int led);
	
	public void ledsOn(int mode);
	
	public void ledsOff(int mode);
	
	public void connected();
	
	public void disconnected();
	
	public void measurementStarted();
	
	public void measurementFinished();
}
