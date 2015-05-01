package de.tu_darmstadt.sport.fvf.handler;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.ui.AbstractSourceProvider;
import org.eclipse.ui.ISources;

public class ArduinoConnectionProvider extends AbstractSourceProvider {
	
	public final static String ARDUINO_CONNECTED = "de.tu_darmstadt.sport.fvf.arduinoConnected";
	private final static String CONNECTED = "connected";
	private final static String DISCONNECTED = "disconnected";
	private boolean connected;

	@Override
	public void dispose() {
	}

	@Override
	public Map<String, String> getCurrentState() {
		Map<String, String> currentState = new HashMap<String, String>(1);
		String connectedState = connected ? CONNECTED : DISCONNECTED;
		currentState.put(ARDUINO_CONNECTED, connectedState);
		return currentState;
	}

	@Override
	public String[] getProvidedSourceNames() {
		return new String[] {ARDUINO_CONNECTED};
	}

	public void setConnected(boolean connected) {
		if (this.connected == connected) {
			return;
		}
		
		this.connected = connected;
		String currentState = connected ? CONNECTED : DISCONNECTED;
		fireSourceChanged(ISources.WORKBENCH, ARDUINO_CONNECTED, currentState); 
	}
	
	public boolean getConnected() {
		return connected;
	}
}
