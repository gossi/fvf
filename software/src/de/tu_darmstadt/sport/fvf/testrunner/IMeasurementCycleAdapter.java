package de.tu_darmstadt.sport.fvf.testrunner;


public interface IMeasurementCycleAdapter {

	public final static int MODE2 = 2;
	public final static int MODE4 = 4;

	/**
	 * Setup one single Testrun
	 * 
	 * @param mode either 2 oder 4 LED use one of the MODE* constants
	 * @param flickerLed the led which flickers: 1, 2, 3, 4 or 0 if none  
	 * @param frequency the frequency at which the flickerLed is flickering
	 * @param onDuration the duration which each individual led is on [ms]
	 * @param offDuration the duration between the flickering [ms]
	 * @param cyclePause the duration between each measurement cycle [ms]
	 * @param light the light part for the light:dark ratio
	 * @param dark the dark part for the light:dark ratio
	 * @throws InterruptedException
	 */
	void run(int mode, int flickerLed, double frequency, int onDuration,
			int offDuration, int cyclePause, int light, int dark)
			throws InterruptedException;
	
	public void setTestRunnerListenerCollection(TestRunnerListenerCollection collection);
}
