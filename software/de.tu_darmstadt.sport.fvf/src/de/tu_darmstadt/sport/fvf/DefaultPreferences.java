package de.tu_darmstadt.sport.fvf;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.PreferenceConverter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;

public class DefaultPreferences extends AbstractPreferenceInitializer {

	@Override
	public void initializeDefaultPreferences() {
		IPreferenceStore store = FVF.getDefault().getPreferenceStore();
		store.setDefault(PreferenceConstants.DATABASE_REMEMBER, true);
		
		// measurement defaults
		store.setDefault(PreferenceConstants.TEST_LEDS, 4);
		store.setDefault(PreferenceConstants.TEST_FREQUENCY_START, 25.00);
		store.setDefault(PreferenceConstants.TEST_FREQUENCY_STEP, 5.00);
		store.setDefault(PreferenceConstants.TEST_CYCLES, 8);
		store.setDefault(PreferenceConstants.TEST_CYCLE_PAUSE, 1.50);
		store.setDefault(PreferenceConstants.TEST_LED_DURATION, 1.25);
		store.setDefault(PreferenceConstants.TEST_LED_PAUSE, 0.2);
		store.setDefault(PreferenceConstants.TEST_STOP_CRITERIA, 5);
		store.setDefault(PreferenceConstants.TEST_LIGHT, 1);
		store.setDefault(PreferenceConstants.TEST_DARK, 1);

		// visuals
		store.setDefault(PreferenceConstants.VISUAL_HIGHLIGHT_FLICKER_LED, false);
		store.setDefault(PreferenceConstants.VISUAL_ANIMATE_FLICKER_LED, false);
		PreferenceConverter.setDefault(store, PreferenceConstants.VISUAL_HIGHLIGHT_COLOR, Display.getDefault().getSystemColor(SWT.COLOR_RED).getRGB());
		
	}

}
