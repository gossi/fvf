package de.tu_darmstadt.sport.fvf.driver;

import gnu.io.CommPortIdentifier;
import gnu.io.RXTXHack;
import gnu.io.RXTXPort;
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ArduinoLedDriver {
	
	/** The port we're normally going to use. */
	private static final String PORT_NAMES[] = { 
		"/dev/tty.usbserial-A9007UX1", // Mac OS X
		"/dev/tty.usbmodem1431", 
		"/dev/ttyACM0", // Raspberry Pi
		"/dev/ttyUSB0", // Linux
		"COM6", // Windows
		"COM3"
	};
	
	private static boolean DEBUG = false;
	
	/**
	 * A BufferedReader which will be fed by a InputStreamReader converting the
	 * bytes into characters making the displayed results codepage independent
	 */
	private BufferedReader input;
	
	/** The output stream to the port */
	private OutputStream output;
	
	/** Milliseconds to block while waiting for port open */
	private static final int TIME_OUT = 2000;
	
	/** Default bits per second for COM port. */
	private static final int DATA_RATE = 9600;
	
	private boolean connected = false;
	
	private String portName;
	private RXTXPort serialPort;
	
	private List<SerialListener> listeners = new ArrayList<SerialListener>();

	private class TableTennisPlayer extends Thread {

		private static final int THRESHOLD = 5;
		private static final int TERMINATE_IO = 1;
		private static final int TERMINATE_THRESHOLD = 2;
		private static final int TERMINATE_INTERRUPT = 3;
		private static final int TERMINATE_DISCONNECT = 4;

		private ArduinoLedDriver driver;
		private String incoming = "";
		private boolean connected = true;
		private boolean pause = false;

		private SerialListener listener = null;
		
		public TableTennisPlayer(ArduinoLedDriver driver) {
			super("TableTennisPlayer");
			
			listener = new SerialAdapter() {
				public void measurementStarted() {
					pause = true;
					System.out.println("TTP: Measurement Started");
				}
				
				public void measurementFinished() {
					pause = false;
					System.out.println("TTP: Measurement Finished");
				}
				
				public void onInput(String input) {
					if (input.startsWith("pong")) {
						incoming = input;	
					}
				}
				
				public void disconnected() {
					connected = false;
				}
			};
			
			this.driver = driver;
			this.driver.addListener(listener);
		}

		public void run() {
			int counter = 0;
			int lost = 0;
			try {
				while (connected) {
					
					if (pause) {
						sleep(500);
						continue;
					}
					
					// legen ...
					try {
						driver.send("ping " + counter);
					} catch (IOException e) {
						terminate(TERMINATE_IO);
						return;
					}
					
					// ... wait for it...
					sleep(1000);
					
					// ... dary
					if (!incoming.equals("pong " + counter)) {
						lost++;
						if (lost > THRESHOLD) {
							terminate(TERMINATE_THRESHOLD);
							return;
						}
					} else {
						lost = 0;
					}
					
					counter++;
				}
				
				terminate(TERMINATE_DISCONNECT);
			} catch (InterruptedException e) {
				terminate(TERMINATE_INTERRUPT);
				return;
			}
			
		}
		
		private void terminate(int error) {
			@SuppressWarnings("serial")
			Map<Integer, String> messages = new HashMap<Integer, String>() {
				{
					put(TERMINATE_IO, "IO Error, could not write onto serial port");
					put(TERMINATE_THRESHOLD, "Lost too many pings");
					put(TERMINATE_INTERRUPT, "Got interrupt signal from somewhere else");
					put(TERMINATE_DISCONNECT, "Disconnected");
				}
			};
			
			debug("TTP, Terminated: " + messages.get(error));
			driver.removeListener(listener);
			driver = null;
			listener = null;
			
			if (error != TERMINATE_DISCONNECT) {
				doDisconnect();	
			}
			interrupt();
		}
	};
	
	private TableTennisPlayer tableTennisPlayer;

	@SuppressWarnings("unchecked")
	public static String[] getPorts() {
		ArrayList<String> ports = new ArrayList<String>();
		Enumeration<CommPortIdentifier> portEnum = CommPortIdentifier.getPortIdentifiers();

		// First, Find an instance of serial port as set in PORT_NAMES.
		while (portEnum.hasMoreElements()) {
			CommPortIdentifier currPortId = (CommPortIdentifier) portEnum.nextElement();
			ports.add(currPortId.getName());
		}

		return ports.toArray(new String[]{});
	}
	
	public static void setDebugMode(boolean debug) {
		DEBUG = debug;
	}
	
	private void debug(String message) {
		if (DEBUG) {
			if (message.endsWith("\n")) {
				message = message.substring(0, message.length() - 1);
			}
			System.out.println("[DEBUG: Arduino LED Driver] " + message);
		}
	}
	
	public void addListener(SerialListener listener) {
		if (!listeners.contains(listener)) {
			listeners.add(listener);
		}
	}
	
	public void removeListener(SerialListener listener) {
		listeners.remove(listener);
	}
	
	public void connect() throws Exception {
		connect(null);
	}
	
	public void connect(String port) throws Exception {
		// port id
		CommPortIdentifier portId = null;
		
		// get port identifiers
		@SuppressWarnings("unchecked")
		Enumeration<CommPortIdentifier> portEnum = CommPortIdentifier.getPortIdentifiers();
		
		// port names
		String[] portNames = PORT_NAMES;
		if (port != null) {
			String[] addPortNames = {port};
			String[] allPortNames = Arrays.copyOf(portNames, portNames.length + addPortNames.length);
			System.arraycopy(addPortNames, 0, allPortNames, portNames.length, addPortNames.length);
			portNames = allPortNames;
		}

		// First, Find an instance of serial port as set in PORT_NAMES.
		while (portEnum.hasMoreElements()) {
			CommPortIdentifier currPortId = (CommPortIdentifier) portEnum.nextElement();
			for (String portName : portNames) {
				if (currPortId.getName().equals(portName)) {
					portId = currPortId;
					this.portName = portName;
					break;
				}
			}
		}

		if (portId == null) {
			throw new Exception("Could not find Serial port.");
		}
		
		debug("Connected on port: " + portName);

		// open serial port, and use class name for the appName.
		serialPort = (RXTXPort) portId.open(this.getClass().getName(), TIME_OUT);

		// set port parameters
		serialPort.setSerialPortParams(DATA_RATE, SerialPort.DATABITS_8,
				SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);

		// open the streams
		input = new BufferedReader(new InputStreamReader(serialPort.getInputStream()));
		output = serialPort.getOutputStream();

		// add event listeners
		serialPort.addEventListener(new SerialPortEventListener() {
			public void serialEvent(SerialPortEvent event) {
				ArduinoLedDriver.this.serialEvent(event);
			}
		});
		serialPort.notifyOnDataAvailable(true);
		serialPort.disableReceiveTimeout();
		serialPort.enableReceiveThreshold(0);
		
		connected = true;
		
		tableTennisPlayer = new TableTennisPlayer(this);
		tableTennisPlayer.start();
		
		for (SerialListener listener : listeners) {
			listener.connected();
		}
	}
	
	public boolean isConnected() {
		return connected;
	}

	/**
	 * If connected returns the port name anyway null
	 * 
	 * @return the port name or null
	 */
	public String getPortName() {
		if (connected) {
			return portName;
		}

		return null;
	}

	/**
	 * This should be called when you stop using the port. This will prevent
	 * port locking on platforms like Linux.
	 */
	public void disconnect() {
		doDisconnect();
	}
	
	private void doDisconnect() {
		if (serialPort != null && connected) {
			debug("Disconnected on port " + portName);

			portName = "";
			connected = false;
			
			for (SerialListener listener : listeners) {
				listener.disconnected();
			}
			
			// close RXTX needs some hacks: 
			// http://archive.infiniteautomation.com/forum/posts/list/297.page
			try {
				input.close();
				output.close();
			} catch (IOException e) {}
//			serialPort.close();
			serialPort.removeEventListener();
			RXTXHack.closeRxtxPort(serialPort);
			serialPort = null;
			tableTennisPlayer = null;
		}
	}

	/**
	 * Handle an event on the serial port. Read the data and print it.
	 */
	private void serialEvent(SerialPortEvent event) {
		if (event.getEventType() == SerialPortEvent.DATA_AVAILABLE) {
			try {
				String inputLine = input.readLine();
				
				debug("Incoming: " + inputLine);
				
				// parse input
				String[] parts = inputLine.split(" ");
				
				if (parts.length > 1 && parts[0].equals("on")) {
					for (SerialListener listener : listeners) {
						listener.ledOn(Integer.valueOf(parts[1]));
					}
				} else if (parts.length > 1 && parts[0].equals("off")) {
					for (SerialListener listener : listeners) {
						listener.ledOff(Integer.valueOf(parts[1]));
					}
				} else if (parts.length > 1 && parts[0].equals("flicker")) {
					for (SerialListener listener : listeners) {
						listener.ledFlicker(Integer.valueOf(parts[1]));
					}
				} else if (parts.length > 1 && parts[0].equals("ons")) {
					for (SerialListener listener : listeners) {
						listener.ledsOn(Integer.valueOf(parts[1]));
					}
				} else if (parts.length > 1 && parts[0].equals("offs")) {
					for (SerialListener listener : listeners) {
						listener.ledsOff(Integer.valueOf(parts[1]));
					}
				} else if (parts.length > 1 && parts[0].equals("measurement") && parts[1].equals("on")) {
					for (SerialListener listener : listeners) {
						listener.measurementStarted();
					}
				} else if (parts.length > 1 && parts[0].equals("measurement") && parts[1].equals("off")) {
					for (SerialListener listener : listeners) {
						listener.measurementFinished();
					}
				}
				
				if (parts.length > 1 && parts[0].equals("error")) {
					for (SerialListener listener : listeners) {
						listener.onError(Integer.valueOf(parts[1]));
					}
				} else {
					for (SerialListener listener : listeners) {
						listener.onInput(inputLine);
					}
				}
			} catch (Exception e) {
				System.err.println(e.toString());
			}
		}
	}

	private void send(String text) throws IOException {
		if (!text.endsWith("\n")) {
			text += "\n";
		}
		debug("Outgoing: " + text);
		output.write(text.getBytes());
	}
	
	public void cmdLedOn(int led) {
		try {
			send("on " + led);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void cmdLedOff(int led) {
		try {
			send("off " + led);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void cmdMeasurement(int mode, int flickerLed, double frequency, int onDuration, int offDuration) {
		cmdMeasurement(mode, flickerLed, frequency, onDuration, offDuration, 1, 1);
	}
	
	public void cmdMeasurement(int mode, int flickerLed, double frequency, int onDuration, int offDuration, int light, int dark) {
		try {
			send("measurement " + mode + " " + flickerLed + " " + frequency + " " + onDuration + " " + offDuration + " " + light + " " + dark);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void cmdPing() {
		cmdPing("");
	}
	
	public void cmdPing(String seq) {
		try {
			if (!seq.isEmpty()) {
				send("ping " + seq);	
			} else {
				send("ping");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

}
