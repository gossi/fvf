package de.tu_darmstadt.sport.fvf.handler;

import java.io.File;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.handlers.HandlerUtil;
import org.tmatesoft.sqljet.core.SqlJetException;
import org.tmatesoft.sqljet.core.SqlJetTransactionMode;
import org.tmatesoft.sqljet.core.table.SqlJetDb;

import de.tu_darmstadt.sport.fvf.FVF;
import de.tu_darmstadt.sport.fvf.PreferenceConstants;
import de.tu_darmstadt.sport.fvf.database.DatabaseLoader;

public class DatabaseCreateHandler extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		Shell shell = HandlerUtil.getActiveWorkbenchWindow(event).getShell();
		FileDialog fd = new FileDialog(shell, SWT.SAVE);
		fd.setText("Speichern");
//		fd.setFilterPath(System.getProperty("user.home"));
		fd.setFilterExtensions(new String[]{"*.db"});
		String path = fd.open();
		if (path != null) {
			File file = new File(path);
	        
	        if (file.exists() 
	        		&& !MessageDialog.openQuestion(shell, "Neue Datenbank anlegen", 
	        				"Soll die Datei " + file.getAbsolutePath() + " überschrieben werden?")) {
	        	return null;
			}
			createDatabase(file);
			DatabaseLoader.getInstance().setDatabase(file);
			DatabaseLoader.getInstance().initialize();
			
			IPreferenceStore store = FVF.getDefault().getPreferenceStore();
			store.setValue(PreferenceConstants.DATABASE_LASTDB, file.getAbsolutePath());
			
			DatabaseOpenHandler opener = new DatabaseOpenHandler();
			opener.open(file.getAbsolutePath());
		}
		return null;
	}
	
	private void createDatabase(File file) {
		file.delete();

		try {
			SqlJetDb db = SqlJetDb.open(file, true);	
			db.getOptions().setAutovacuum(true);
			db.beginTransaction(SqlJetTransactionMode.WRITE);
			try {
				db.getOptions().setUserVersion(1);
			} finally {
				db.commit();
			}
			
			db.beginTransaction(SqlJetTransactionMode.WRITE);
			try {
				db.createTable("CREATE TABLE person ("+
								"id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,"+
								"first_name VARCHAR(45) NULL,"+
								"last_name VARCHAR(45) NULL,"+
								"age INTEGER NULL"+
								")");
				db.createTable("CREATE TABLE test ("+
								"id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT ,"+
								"date TIMESTAMP NOT NULL ,"+
								"note TEXT NULL ,"+
								"leds INTEGER NULL ,"+
								"start_frequency DOUBLE NULL ,"+
								"frequency_step DOUBLE NULL ,"+
								"frequency_cycles INTEGER NULL ,"+
								"led_duration DOUBLE NULL ,"+
								"led_pause DOUBLE NULL ,"+
								"cycle_pause DOUBLE NULL ,"+
								"light INTEGER NULL ,"+
								"dark INTEGER NULL ,"+
								"stop_criteria INTEGER NULL ,"+
								"person_id INTEGER NOT NULL ,"+
								"CONSTRAINT fk_test_person "+
									"FOREIGN KEY (person_id) "+
									"REFERENCES person (id) "+
								")");
				db.createTable("CREATE TABLE frequency_cycle ("+
								"id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT ,"+
								"frequency DOUBLE NULL ,"+
								"test_id INTEGER NOT NULL ,"+
								"CONSTRAINT fk_test_frequency_cycle "+
									"FOREIGN KEY (test_id ) "+
									"REFERENCES test (id ) "+
								")");
				db.createTable("CREATE TABLE measurement ("+
								"id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT ,"+
								"run INT NOT NULL ,"+
								"led INTEGER NULL ,"+
								"person_led INTEGER NULL ,"+
								"error BOOLEAN NULL, "+
								"frequency_cycle_id INTEGER NOT NULL ,"+
								"CONSTRAINT fk_frequency_cycle_measurement_cycle "+
									"FOREIGN KEY (frequency_cycle_id ) "+
									"REFERENCES frequency_cycle (id ) "+
								")");
			} finally {
				db.commit();
			}
			
			db.close();
		} catch (SqlJetException e) {
			e.printStackTrace();
		}
	}

}