package de.tu_darmstadt.sport.fvf.handler;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;

import de.tu_darmstadt.sport.fvf.FVF;
import de.tu_darmstadt.sport.fvf.driver.ArduinoLedDriver;

public class ArduinoDisconnectHandler extends AbstractHandler {
	
	private CountDownLatch counter;

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		
		try {
			counter = new CountDownLatch(1);
			new Thread(new Runnable() {
				@Override
				public void run() {
					ArduinoLedDriver driver = FVF.getDefault().getArduinoDriver();
					driver.disconnect();
					
					counter.countDown();
				}
			}).start();
		
			counter.await(10, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return null;
	}

}
