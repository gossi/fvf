package de.tu_darmstadt.sport.fvf.ui;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.jface.action.ContributionItem;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.ui.handlers.RadioState;
import org.eclipse.ui.menus.CommandContributionItem;
import org.eclipse.ui.menus.CommandContributionItemParameter;
import org.eclipse.ui.services.IServiceLocator;

import de.tu_darmstadt.sport.fvf.FVF;
import de.tu_darmstadt.sport.fvf.driver.ArduinoLedDriver;

public class ArduinoConnectMenu extends ContributionItem {

	private IServiceLocator locator;

	private IMenuListener menuListener = new IMenuListener() {
		public void menuAboutToShow(IMenuManager manager) {
			fillMenu(manager);
		}
	};

	public ArduinoConnectMenu() {
		this("de.tu_darmstadt.sport.fvf.commands.arduinoconnect");
	}

	public ArduinoConnectMenu(String id) {
		super(id);
		
		locator = (IServiceLocator)FVF.getDefault().getWorkbench();
	}
	
	@Override
	public void fill(Menu menu, int index) {
		if (getParent() instanceof MenuManager) {
			((MenuManager) getParent()).setRemoveAllWhenShown(true);
			((MenuManager) getParent()).addMenuListener(menuListener);
		}
	}
	
	private void fillMenu(IMenuManager mgr) {
		for (String port : getPorts()) {
			mgr.add(createCommand(port));
		}
		
		mgr.update();
	}
	
	@SuppressWarnings("serial")
	private CommandContributionItem createCommand(final String port) {
		CommandContributionItemParameter itemParam = new CommandContributionItemParameter(locator, null, "de.tu_darmstadt.sport.fvf.commands.arduinoconnect", SWT.RADIO);
		itemParam.label = port;
		itemParam.parameters = new HashMap<Object, Object>() {
			{
				put(RadioState.PARAMETER_ID, port);
			}
		};
		return new CommandContributionItem(itemParam);
	}
	
	private String[] getPorts() {
		List<String> ports = new LinkedList<String>();
		for (String port : ArduinoLedDriver.getPorts()) {
			if (!port.startsWith("/dev/cu")) {
				ports.add(port);
			}
		}
		return ports.toArray(new String[]{});
	}

}
