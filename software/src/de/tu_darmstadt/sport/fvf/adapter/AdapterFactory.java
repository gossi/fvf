package de.tu_darmstadt.sport.fvf.adapter;

import de.tu_darmstadt.sport.fvf.FVF;
import de.tu_darmstadt.sport.fvf.adapter.arduino.ArduinoCycleAdapter;
import de.tu_darmstadt.sport.fvf.adapter.arduino.ArduinoLedAdapter;
import de.tu_darmstadt.sport.fvf.adapter.stub.StubCycleAdapter;
import de.tu_darmstadt.sport.fvf.adapter.stub.StubLedAdapter;
import de.tu_darmstadt.sport.fvf.driver.ArduinoLedDriver;
import de.tu_darmstadt.sport.fvf.testrunner.ILedAdapter;
import de.tu_darmstadt.sport.fvf.testrunner.IMeasurementCycleAdapter;

public class AdapterFactory {

	public static IMeasurementCycleAdapter createMeasurementAdapter() {
		ArduinoLedDriver driver = FVF.getDefault().getArduinoDriver();
		
		// adapter for arduino
		if (driver.isConnected()) {
			ArduinoLedAdapter ledAdapter = new ArduinoLedAdapter(driver);
			return new ArduinoCycleAdapter(ledAdapter, driver);
		}

		// dry-run
		ILedAdapter ledAdapter = new StubLedAdapter();
		return new StubCycleAdapter(ledAdapter);
	}
}
