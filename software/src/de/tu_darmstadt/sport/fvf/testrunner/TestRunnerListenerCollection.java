package de.tu_darmstadt.sport.fvf.testrunner;

import java.util.ArrayList;
import java.util.List;

public class TestRunnerListenerCollection {
	
	final static public int LED_ON = 1;
	final static public int LED_OFF = 2;
	final static public int NEXT_FREQUENCY = 3;
	final static public int MEASUREMENT_CYCLE_STARTED = 4;
	final static public int MEASUREMENT_CYCLE_FINISHED = 5;
	final static public int MEASUREMENT_FINISHED = 6;
	final static public int RESET = 7;
	final static public int LEDS_ON = 8;
	final static public int LEDS_OFF = 9;

	private List<ITestRunnerListener> listeners; 
	
	public TestRunnerListenerCollection() {
		listeners = new ArrayList<ITestRunnerListener>();
	}
	
	public void addTestRunnerListener(ITestRunnerListener listener) {
		listeners.add(listener);
	}
	
	public void removeTestRunnerListener(ITestRunnerListener listener) {
		listeners.remove(listener);
	}
	
	public void fireEvent(int eventType) {
		fireEvent(eventType, null, null, null);
	}
	
	public void fireEvent(int eventType, Object param) {
		fireEvent(eventType, param, null, null);
	}
	
	public void fireEvent(int eventType, Object param, Object param2) {
		fireEvent(eventType, param, param2, null);
	}
	
	public void fireEvent(int eventType, Object param, Object param2, Object param3) {
		for (ITestRunnerListener listener : listeners) {
			switch (eventType) {
			case LED_ON:
				listener.ledOn((Integer)param, (Boolean) param2);
				break;
				
			case LED_OFF:
				listener.ledOff((Integer)param);
				break;
				
			case LEDS_ON:
				listener.ledsOn((Integer)param);
				break;
				
			case LEDS_OFF:
				listener.ledsOff((Integer)param);
				break;
				
			case NEXT_FREQUENCY:
				listener.nextFrequency();
				break;
				
			case MEASUREMENT_CYCLE_STARTED:
				listener.measurementCycleStarted((Integer)param);
				break;
				
			case MEASUREMENT_CYCLE_FINISHED:
				listener.measurementCycleFinished();
				break;
				
			case MEASUREMENT_FINISHED:
				listener.measurementFinished();
				break;
				
			case RESET:
				listener.measurementReset();
				break;
			}
		}
	}
}
