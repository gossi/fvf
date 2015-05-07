package de.tu_darmstadt.sport.fvf.testrunner;

public interface ITestRunnerListener {
	public void ledOn(int number, boolean canFlicker);
	public void ledOff(int number);
	public void ledsOn(int mode);
	public void ledsOff(int mode);
	public void nextFrequency();
	public void measurementCycleStarted(int flickeringLed);
	public void measurementCycleFinished();
	public void measurementFinished();
	public void measurementReset();
}
