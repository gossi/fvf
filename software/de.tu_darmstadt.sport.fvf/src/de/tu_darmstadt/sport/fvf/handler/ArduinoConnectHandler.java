package de.tu_darmstadt.sport.fvf.handler;

import gnu.io.PortInUseException;

import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.ui.commands.IElementUpdater;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.ui.handlers.RadioState;
import org.eclipse.ui.menus.UIElement;

import de.tu_darmstadt.sport.fvf.FVF;
import de.tu_darmstadt.sport.fvf.PreferenceConstants;
import de.tu_darmstadt.sport.fvf.driver.ArduinoLedDriver;

public class ArduinoConnectHandler extends AbstractHandler implements IElementUpdater {

	private String message;
	private CountDownLatch counter;
	private ArduinoLedDriver driver = FVF.getDefault().getArduinoDriver();

	@Override
	public Object execute(final ExecutionEvent event) throws ExecutionException {
		final ArduinoLedDriver driver = FVF.getDefault().getArduinoDriver();
		final IPreferenceStore store = FVF.getDefault().getPreferenceStore();
		
		try {
			counter = new CountDownLatch(1);
			new Thread(new Runnable() {
				public void run() {
					String port = event.getParameter(RadioState.PARAMETER_ID);
					if (port == null) {
						port = store.getString(PreferenceConstants.ARDUINO_LASTPORT);
					}
					
					try {
						driver.connect(port);
					} catch (PortInUseException e) {
						message = "Der USB-Port ist gerade in Verwendung. Bitte Kabel abziehen, wieder einstöpseln und erneut versuchen.";
					} catch (Exception ex) {
						message = "Fehler beim Verbinden aufgetreten: " + ex.getMessage();
					}

					counter.countDown();
				}
			}).start();
		
			counter.await(10, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		if (!driver.isConnected()) {
			MessageDialog.openWarning(HandlerUtil.getActiveWorkbenchWindow(event).getShell(), "Verbindung herstellen", message);
		}

		String port = driver.isConnected() ? driver.getPortName() : null;
		HandlerUtil.updateRadioState(event.getCommand(), port);
		
		// store last port
		store.setValue(PreferenceConstants.ARDUINO_LASTPORT, port);
		
		return null;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public void updateElement(UIElement element, Map parameters) {
		if (parameters.containsKey(RadioState.PARAMETER_ID) && driver.isConnected() && driver.getPortName() != null) {
			String port = (String)parameters.get(RadioState.PARAMETER_ID);
			
			if (driver.getPortName().equals(port)) {
				element.setChecked(true);
			}
		}
	}

}
