package de.tu_darmstadt.sport.fvf.driver.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import de.tu_darmstadt.sport.fvf.driver.ArduinoLedDriver;
import de.tu_darmstadt.sport.fvf.driver.SerialAdapter;
import de.tu_darmstadt.sport.fvf.driver.SerialListener;

public class ArduinoLedDriverTest {
	
	private static ArduinoLedDriver driver;
	private final static int TIMEOUT = 3;
	
	private CountDownLatch counter;
	private String asyncInput;
	private int asyncErrorNumber;
	private boolean asyncCall;

	@Before
	public void setUp() {
		counter = new CountDownLatch(1);
		asyncInput = null;
		asyncErrorNumber = -1;
		asyncCall = false;
	}
	
	@BeforeClass
	public static void connect() {
		ArduinoLedDriver.setDebugMode(true);
		driver = new ArduinoLedDriver();
		try {
			driver.connect();
			
			CountDownLatch cnt = new CountDownLatch(1);
			cnt.await(TIMEOUT, TimeUnit.SECONDS);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@AfterClass
	public static void disconnect() {
		driver.disconnect();
	}
	
	private void send(ArduinoLedDriver driver, String message) {
		try {
			Method send = ArduinoLedDriver.class.getDeclaredMethod("send", String.class);
			send.setAccessible(true);
			send.invoke(driver, message);
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		
	}

	@Test
	public void testConnection() {
		assertTrue(driver.isConnected());
	}
	
	@Test
	public void testPing() {
		try {
			SerialListener listener = new SerialAdapter() {
				public void onInput(String input) {
					asyncInput = input;
					counter.countDown();
				}
			};
			driver.addListener(listener);
			send(driver, "ping");
		
			counter.await(TIMEOUT, TimeUnit.SECONDS);
			
			assertEquals("pong", asyncInput);
			
			driver.removeListener(listener);
			
		} catch (InterruptedException e) {
			e.printStackTrace();
			fail();
		}
	}

	@Test
	public void testLedOn() {
		try {
			SerialListener listener = new SerialAdapter() {
				public void onInput(String input) {
					asyncInput = input;
					counter.countDown();
				}
				
				public void ledOn(int led) {
					asyncCall = true;
				}
			};
			driver.addListener(listener);
			send(driver, "on 1");
		
			counter.await(TIMEOUT, TimeUnit.SECONDS);
			
			assertEquals("on 1", asyncInput);
			assertTrue(asyncCall);
			
			driver.removeListener(listener);
			
		} catch (InterruptedException e) {
			e.printStackTrace();
			fail();
		}
	}
	
	@Test
	public void testLedOff() {
		try {
			SerialListener listener = new SerialAdapter() {
				public void onInput(String input) {
					asyncInput = input;
					counter.countDown();
				}
				
				public void ledOff(int led) {
					asyncCall = true;
				}
			};
			driver.addListener(listener);
			send(driver, "off 1");
		
			counter.await(TIMEOUT, TimeUnit.SECONDS);
			
			assertEquals("off 1", asyncInput);
			assertTrue(asyncCall);
			
			driver.removeListener(listener);
			
		} catch (InterruptedException e) {
			e.printStackTrace();
			fail();
		}
	}

	@Test
	public void testUnknownCommand() {
		try {
			SerialListener listener = new SerialAdapter() {
				public void onError(int number) {
					asyncErrorNumber = number;
					counter.countDown();
				}
			};
			driver.addListener(listener);
			send(driver, "xyz");
		
			counter.await(TIMEOUT, TimeUnit.SECONDS);
			
			assertEquals(2, asyncErrorNumber);
			
			driver.removeListener(listener);
			
		} catch (InterruptedException e) {
			e.printStackTrace();
			fail();
		}
	}
	
	@Test
	public void testMalformedCommand() {
		try {
			SerialListener listener = new SerialAdapter() {
				public void onError(int number) {
					asyncErrorNumber = number;
					counter.countDown();
				}
			};
			driver.addListener(listener);
			send(driver, "on ");
		
			counter.await(TIMEOUT, TimeUnit.SECONDS);
			
			assertEquals(1, asyncErrorNumber);
			
			driver.removeListener(listener);
			
		} catch (InterruptedException e) {
			e.printStackTrace();
			fail();
		}
	}
	
	@Test
	public void testMalformedMeasurementCommand() {
		try {
			SerialListener listener = new SerialAdapter() {
				public void onError(int number) {
					asyncErrorNumber = number;
					counter.countDown();
				}
			};
			driver.addListener(listener);
			send(driver, "measurement 4 2 33 155");
		
			counter.await(TIMEOUT, TimeUnit.SECONDS);
			
			assertEquals(3, asyncErrorNumber);
			
			driver.removeListener(listener);
			
		} catch (InterruptedException e) {
			e.printStackTrace();
			fail();
		}
	}
	
	@Test
	public void testMalformedFlickerCommand() {
		try {
			SerialListener listener = new SerialAdapter() {
				public void onError(int number) {
					asyncErrorNumber = number;
					counter.countDown();
				}
			};
			driver.addListener(listener);
			send(driver, "flicker 4");
		
			counter.await(TIMEOUT, TimeUnit.SECONDS);
			
			assertEquals(4, asyncErrorNumber);
			
			driver.removeListener(listener);
			
		} catch (InterruptedException e) {
			e.printStackTrace();
			fail();
		}
	}

}
