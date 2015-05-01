package de.tu_darmstadt.sport.fvf.testrunner;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.Random;

import de.tu_darmstadt.sport.fvf.FVF;
import de.tu_darmstadt.sport.fvf.adapter.arduino.ArduinoCycleAdapter;
import de.tu_darmstadt.sport.fvf.adapter.arduino.ArduinoLedAdapter;
import de.tu_darmstadt.sport.fvf.driver.ArduinoLedDriver;
import de.tu_darmstadt.sport.fvf.model.FrequencyCycle;
import de.tu_darmstadt.sport.fvf.model.Measurement;
import de.tu_darmstadt.sport.fvf.model.Test;

public class TestRunner implements Runnable {

	private Test test;
	private int run = 0;
	private double frequency = 0;
	private int errors = 0;
	private TestRunnerListenerCollection listeners;
	private IMeasurementCycleAdapter cycleAdapter;
	private PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);
	private FrequencyCycle currentFrequency = null;
	private Measurement currentMeasurement = null;
	private int[] frequencyFlickeringLeds;
	private int frequencies = 0;
	
	public TestRunner(Test test) {
		this.test = test;
		listeners = new TestRunnerListenerCollection();
		
		ArduinoLedDriver driver = FVF.getDefault().getArduinoDriver();
		ArduinoLedAdapter ledAdapter = new ArduinoLedAdapter(driver);
		cycleAdapter = new ArduinoCycleAdapter(ledAdapter, driver);
		cycleAdapter.setTestRunnerListenerCollection(listeners);
	}
	
	public void addPropertyChangeListener(String propertyName, PropertyChangeListener listener) {
		propertyChangeSupport.addPropertyChangeListener(propertyName, listener);
	}

	public void removePropertyChangeListener(PropertyChangeListener listener) {
		propertyChangeSupport.removePropertyChangeListener(listener);
	}
	
	public void addTestRunnerListener(ITestRunnerListener listener) {
		listeners.addTestRunnerListener(listener);
	}
	
	public void removeTestRunnerListener(ITestRunnerListener listener) {
		listeners.removeTestRunnerListener(listener);
	}

	@Override
	public void run() {
		try {
			// finished ?
			if (errors == test.getStopCriteria()) {
				listeners.fireEvent(TestRunnerListenerCollection.MEASUREMENT_FINISHED);
				return;
			}
			
			// new frequency?
			if (run == 0 || run == test.getFrequencyCycles() + 1) {
				currentFrequency = new FrequencyCycle();
				currentFrequency.addPropertyChangeListener("frequency", new PropertyChangeListener() {
					public void propertyChange(PropertyChangeEvent e) {
						propertyChangeSupport.firePropertyChange("frequency", frequency, frequency = (Double)e.getNewValue());
					}
				});
				test.addFrequencyRun(currentFrequency);
				
				// set frequency
				currentFrequency.setFrequency(test.getStartFrequency() + test.getFrequencyStep() * frequencies);
				frequencies++;
				
				// set flickering leds
				Random rand = new Random();
				frequencyFlickeringLeds = new int[test.getFrequencyCycles() + 1];
				
				for (int i = 0; i <= test.getFrequencyCycles(); i++) {
					frequencyFlickeringLeds[i] = rand.nextInt(test.getLeds()) + 1;
				}

				// set none flickering led
				frequencyFlickeringLeds[rand.nextInt(test.getFrequencyCycles()) + 1] = 0;
				
				// reset run
				run = 0;
				setErrors(0);
				
				// notify listeners
				listeners.fireEvent(TestRunnerListenerCollection.NEXT_FREQUENCY);
			}
			
			// new measurement
			currentMeasurement = new Measurement();
			currentFrequency.addMeasurement(currentMeasurement);
			
			// increment run
			run++;
			currentMeasurement.setRun(run);
			
			// get and set flicker led
			int flickerLed = frequencyFlickeringLeds[currentMeasurement.getRun() - 1];
			currentMeasurement.setLed(flickerLed);

			listeners.fireEvent(TestRunnerListenerCollection.MEASUREMENT_CYCLE_STARTED, flickerLed);

			cycleAdapter.run(test.getLeds(), flickerLed, getFrequency(), 
					(int)(test.getLedDuration() * 1000), (int)(test.getLedPause() * 1000),
					(int)(test.getCyclePause() * 1000), (int)(test.getBrightness()));
			
			listeners.fireEvent(TestRunnerListenerCollection.MEASUREMENT_CYCLE_FINISHED);

		} catch (InterruptedException e) {
			//e.printStackTrace();
		}
	}
	
	public void reset() {
		test.reset();
		run = 0;
		frequencies = 0;
		setErrors(0);
		listeners.fireEvent(TestRunnerListenerCollection.RESET);
	}
	
	public void resetFrequency() {
		currentFrequency.reset();
		run = 0;
		frequencies--;
		setErrors(0);
		listeners.fireEvent(TestRunnerListenerCollection.RESET);
	}
	
	public FrequencyCycle getCurrentFrequencyCycle() {
		return currentFrequency;
	}
	
	public Measurement getCurrentMeasurement() {
		return currentMeasurement;
	}

	/**
	 * @return the run
	 */
	public int getRun() {
		return run;
	}

	/**
	 * @return the frequency
	 */
	public double getFrequency() {
		return frequency;
	}

	/**
	 * @return the errors
	 */
	public int getErrors() {
		return errors;
	}
	
	public void setErrors(int errors) {
		propertyChangeSupport.firePropertyChange("errors", this.errors, this.errors = errors);
	}
}
