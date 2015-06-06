package de.tu_darmstadt.sport.fvf.handler;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.ui.AbstractSourceProvider;

import de.tu_darmstadt.sport.fvf.validators.OSValidator;

public class OSProvider extends AbstractSourceProvider {

	public final static String OS = "de.tu_darmstadt.sport.fvf.os";
	public final static String MAC = "mac";
	public final static String WINDOWS = "windows";
	public final static String SOLARIS = "solaris";
	public final static String UNIX = "unix";
	public final static String UNKNOWN = "unknown";
	
	public OSProvider() {
	}

	@Override
	public void dispose() {
	}

	@Override
	public Map<String, String> getCurrentState() {
		Map<String, String> currentState = new HashMap<String, String>(1);

		if (OSValidator.isMac()) {
			currentState.put(OS, MAC);	
		} else if (OSValidator.isSolaris()) {
			currentState.put(OS, SOLARIS);
		} else if (OSValidator.isWindows()) {
			currentState.put(OS, WINDOWS);
		} else if (OSValidator.isUnix()) {
			currentState.put(OS, UNIX);
		} else {
			currentState.put(OS, UNKNOWN);
		}
		
		return currentState;
	}

	@Override
	public String[] getProvidedSourceNames() {
		return new String[] {OS};
	}

}
