package de.tu_darmstadt.sport.fvf;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

import de.tu_darmstadt.sport.fvf.database.DatabaseLoader;
import de.tu_darmstadt.sport.fvf.driver.ArduinoLedDriver;


/**
 * The activator class controls the plug-in life cycle
 */
public class FVF extends AbstractUIPlugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "de.tu_darmstadt.sport.fvf"; //$NON-NLS-1$

	// The shared instance
	private static FVF plugin;
	
	private ArduinoLedDriver driver = new ArduinoLedDriver();
	
	/**
	 * The constructor
	 */
	public FVF() {
		
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
		
		IPreferenceStore store = getPreferenceStore();
		
		// database
		if (store.getBoolean(PreferenceConstants.DATABASE_REMEMBER)) {
			DatabaseLoader loader = DatabaseLoader.getInstance();
			loader.setDatabase(store.getString(PreferenceConstants.DATABASE_LASTDB));
			loader.initialize();
		}
		
		// start connections
		ArduinoLedDriver.setDebugMode(true);
		try {
			driver.connect();
		} catch (Exception e) {
		}
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext context) throws Exception {
		driver.disconnect();
		
		plugin = null;
		super.stop(context);
	}
	
	public ArduinoLedDriver getArduinoDriver() {
		return driver;
	}


	/**
	 * Returns the shared instance
	 *
	 * @return the shared instance
	 */
	public static FVF getDefault() {
		return plugin;
	}

	/**
	 * Returns an image descriptor for the image file at the given
	 * plug-in relative path
	 *
	 * @param path the path
	 * @return the image descriptor
	 */
	public static ImageDescriptor getImageDescriptor(String path) {
		return imageDescriptorFromPlugin(PLUGIN_ID, path);
	}
}
