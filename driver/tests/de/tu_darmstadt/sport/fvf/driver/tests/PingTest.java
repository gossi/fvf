package de.tu_darmstadt.sport.fvf.driver.tests;

import de.tu_darmstadt.sport.fvf.driver.ArduinoLedDriver;

public class PingTest {

	private ArduinoLedDriver driver;
	
	public PingTest() {
		driver = new ArduinoLedDriver();
		ArduinoLedDriver.setDebugMode(true);
		try {
			driver.connect();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String args[]) {
		new PingTest();
		
		Thread t = new Thread() {
			public void run() {
				//the following line will keep this app alive for 1000 seconds,
				//waiting for events to occur and responding to them (printing incoming messages to console).
				try {Thread.sleep(1000000);} catch (Throwable ie) {
					ie.printStackTrace();
				}
			}
		};
		t.start();
		System.out.println("Started");
	}
}
