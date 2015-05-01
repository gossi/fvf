package de.tu_darmstadt.sport.fvf.ui.preferences;

import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.IntegerFieldEditor;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

import de.tu_darmstadt.sport.fvf.FVF;
import de.tu_darmstadt.sport.fvf.PreferenceConstants;

public class TestPreferences extends FieldEditorPreferencePage implements
		IWorkbenchPreferencePage {

	@Override
	public void init(IWorkbench workbench) {
		setPreferenceStore(FVF.getDefault().getPreferenceStore());
		setDescription("Standardparameter für einen neuen Test");
	}

	@Override
	protected void createFieldEditors() {
		
		// create fields
		IntegerFieldEditor leds = new IntegerFieldEditor(PreferenceConstants.TEST_LEDS, "LEDs", getFieldEditorParent());
		DoubleFieldEditor startFrequency = new DoubleFieldEditor(PreferenceConstants.TEST_FREQUENCY_START, 
				"Startfrequenz [Hz]", getFieldEditorParent());
		DoubleFieldEditor frequencyStep = new DoubleFieldEditor(PreferenceConstants.TEST_FREQUENCY_STEP, 
				"Steigerungsfrequenz [Hz]", getFieldEditorParent()); 
		IntegerFieldEditor cycles = new IntegerFieldEditor(PreferenceConstants.TEST_CYCLES, 
				"Messzyklen pro Frequenz", getFieldEditorParent());
		DoubleFieldEditor cyclePause = new DoubleFieldEditor(PreferenceConstants.TEST_CYCLE_PAUSE, 
				"Pausendauer pro Messzyklus [s]", getFieldEditorParent());
		DoubleFieldEditor ledDuration = new DoubleFieldEditor(PreferenceConstants.TEST_LED_DURATION, 
				"Anzeigedauer pro LED [s]", getFieldEditorParent());
		DoubleFieldEditor ledPause = new DoubleFieldEditor(PreferenceConstants.TEST_LED_PAUSE, 
				"Pausendauer pro LED [s]", getFieldEditorParent());
		IntegerFieldEditor stopCriteria = new IntegerFieldEditor(PreferenceConstants.TEST_STOP_CRITERIA, 
				"Abbruchkriterium (Falschnennungen)", getFieldEditorParent());
//		IntegerFieldEditor brightness = new IntegerFieldEditor(PreferenceConstants.TEST_BRIGHTNESS, 
//				"Helligkeit der LEDs", getFieldEditorParent());
		
		// costumize them
//		FieldEditor[] fields = new FieldEditor[]{leds, startFrequency, frequencyStep, cycles, cyclePause, ledDuration,
//				ledPause, stopCriteria, light, dark};
		
		// add them
		addField(leds);
		addField(startFrequency);
		addField(frequencyStep);
		addField(cycles);
		addField(cyclePause);
		addField(ledDuration);
		addField(ledPause);
		addField(stopCriteria);
//		addField(brightness);
	}

}
