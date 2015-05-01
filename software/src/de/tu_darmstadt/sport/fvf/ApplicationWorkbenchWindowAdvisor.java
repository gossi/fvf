package de.tu_darmstadt.sport.fvf;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.action.IStatusLineManager;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IPartListener;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.application.ActionBarAdvisor;
import org.eclipse.ui.application.IActionBarConfigurer;
import org.eclipse.ui.application.IWorkbenchWindowConfigurer;
import org.eclipse.ui.application.WorkbenchWindowAdvisor;
import org.eclipse.ui.commands.ICommandService;
import org.eclipse.ui.services.IServiceLocator;
import org.eclipse.ui.services.ISourceProviderService;

import de.tu_darmstadt.sport.fvf.driver.ArduinoLedDriver;
import de.tu_darmstadt.sport.fvf.driver.SerialAdapter;
import de.tu_darmstadt.sport.fvf.handler.ArduinoConnectionProvider;
import de.tu_darmstadt.sport.fvf.ui.PersonView;
import de.tu_darmstadt.sport.fvf.ui.ResultView;

public class ApplicationWorkbenchWindowAdvisor extends WorkbenchWindowAdvisor {

	private Image on;
	private Image off;
	private List<IStatusLineManager> statusManager = new ArrayList<IStatusLineManager>();
	private ArduinoLedDriver driver;
	private ArduinoConnectionProvider provider;
	private ICommandService commander;
	
	public ApplicationWorkbenchWindowAdvisor(IWorkbenchWindowConfigurer configurer) {
		super(configurer);
		
		driver = FVF.getDefault().getArduinoDriver();
		on = FVF.getImageDescriptor("icons/lightbulb/lightbulb-100.png").createImage();
		off = FVF.getImageDescriptor("icons/lightbulb/lightbulb_off.png").createImage();
	}

	public ActionBarAdvisor createActionBarAdvisor(
			IActionBarConfigurer configurer) {
		return new ApplicationActionBarAdvisor(configurer);
	}

	public void preWindowOpen() {
		IWorkbenchWindowConfigurer configurer = getWindowConfigurer();
		configurer.setInitialSize(new Point(1024, 576));
		configurer.setShowCoolBar(true);
		configurer.setShowStatusLine(true);
	}

	@Override
	public void postWindowCreate() {
		Shell shell = getWindowConfigurer().getWindow().getShell();
		shell.setLocation(125, 70);

		IServiceLocator locator = (IServiceLocator) getWindowConfigurer().getWindow().getWorkbench();
		ISourceProviderService service = (ISourceProviderService) locator.getService(ISourceProviderService.class);
		provider = (ArduinoConnectionProvider) service.getSourceProvider(ArduinoConnectionProvider.ARDUINO_CONNECTED);
		commander = (ICommandService) locator.getService(ICommandService.class);
		
		IWorkbenchPage page = getWindowConfigurer().getWindow().getActivePage();
		statusManager.add(page.findView(PersonView.ID).getViewSite().getActionBars().getStatusLineManager());
		
		getWindowConfigurer().getWindow().getPartService().addPartListener(new IPartListener() {
			public void partOpened(IWorkbenchPart part) {
				if (part instanceof ResultView) {
					statusManager.add(((ResultView)part).getViewSite().getActionBars().getStatusLineManager());
					updateConnectionStatus();
				}
			}
			
			public void partClosed(IWorkbenchPart part) {
				if (part instanceof ResultView) {
					statusManager.remove(((ResultView)part).getViewSite().getActionBars().getStatusLineManager());
					updateConnectionStatus();
				}
			}
			
			public void partBroughtToTop(IWorkbenchPart part) {}
			public void partActivated(IWorkbenchPart part) {}
			public void partDeactivated(IWorkbenchPart part) {}
		});
		
		// set initial connection status
		updateConnectionStatus();

		// attach listeners
		driver.addListener(new SerialAdapter() {
			public void connected() {
				updateConnectionStatus();
			}

			public void disconnected() {
				updateConnectionStatus();
			}
		});
	}
	
	private void updateConnectionStatus() {
		final boolean connected = driver.isConnected();
		
		getWindowConfigurer().getWindow().getShell().getDisplay().asyncExec(new Runnable() {
			public void run() {
				// status line
				for (IStatusLineManager status : statusManager) {
					if (connected) {
						status.setMessage(on, "Verbindung hergestellt: " + driver.getPortName());	
					} else {
						status.setMessage(off, "Keine Verbindung");
					}	
				}
				
				// provider
				provider.setConnected(connected);
				
				// refresh
				commander.refreshElements("de.tu_darmstadt.sport.fvf.commands.arduinotest", null);
				commander.refreshElements("de.tu_darmstadt.sport.fvf.commands.arduinoconnect", null);
			}
		});
	}
}
