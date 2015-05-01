package de.tu_darmstadt.sport.fvf.adapter.arduino;

import de.tu_darmstadt.sport.fvf.driver.ArduinoLedDriver;
import de.tu_darmstadt.sport.fvf.testrunner.ILedAdapter;
import de.tu_darmstadt.sport.fvf.testrunner.IMeasurementCycleAdapter;
import de.tu_darmstadt.sport.fvf.testrunner.TestRunnerListenerCollection;

public class ArduinoCycleAdapter implements IMeasurementCycleAdapter {

	private ILedAdapter ledAdapter;
	private ArduinoLedDriver driver;
	private TestRunnerListenerCollection listeners;
	
	public ArduinoCycleAdapter(ArduinoLedAdapter ledAdapter, ArduinoLedDriver driver) {
		this.ledAdapter = ledAdapter;
		this.driver = driver;
	}

	/**
	 * Runs one measurement
	 */
	public void run(int mode, int flickerLed, double frequency, int onDuration, int offDuration, int cyclePause, int brightness) throws InterruptedException {
		int sleep = offDuration + onDuration + mode * offDuration + mode * onDuration;
		for (int i = 1; i <= 4; i++) {
			driver.cmdLedOff(i);
		}
		
		driver.cmdMeasurement(mode, flickerLed, frequency, onDuration, offDuration);
		
		Thread.sleep(sleep);
	}

	public void setTestRunnerListenerCollection(TestRunnerListenerCollection collection) {
		listeners = collection;
		ledAdapter.setTestRunnerListenerCollection(listeners);
	}

}
