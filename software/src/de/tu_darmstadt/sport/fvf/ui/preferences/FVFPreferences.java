package de.tu_darmstadt.sport.fvf.ui.preferences;

import org.eclipse.jface.preference.BooleanFieldEditor;
import org.eclipse.jface.preference.ColorFieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

import de.tu_darmstadt.sport.fvf.FVF;
import de.tu_darmstadt.sport.fvf.PreferenceConstants;

public class FVFPreferences extends FieldEditorPreferencePage implements
		IWorkbenchPreferencePage {

	@Override
	public void init(IWorkbench workbench) {
		setPreferenceStore(FVF.getDefault().getPreferenceStore());
		setDescription("Eintellungen zum Programm");
	}

	@Override
	protected void createFieldEditors() {
		addField(new BooleanFieldEditor(PreferenceConstants.DATABASE_REMEMBER,
				"Letzte Datenbank merken und beim Start laden?", getFieldEditorParent()));
		
		addField(new BooleanFieldEditor(PreferenceConstants.VISUAL_HIGHLIGHT_FLICKER_LED,
				"Flimmernde LED im Test visuell hervorheben", getFieldEditorParent()));
		
		addField(new ColorFieldEditor(PreferenceConstants.VISUAL_HIGHLIGHT_COLOR, 
				"Farbe zum Hervorheben", getFieldEditorParent()));
		
		addField(new BooleanFieldEditor(PreferenceConstants.VISUAL_ANIMATE_FLICKER_LED,	
				"Animiere Flimmerende LED", getFieldEditorParent()));

	}

}
