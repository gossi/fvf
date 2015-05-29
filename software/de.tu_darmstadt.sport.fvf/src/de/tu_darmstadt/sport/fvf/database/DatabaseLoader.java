package de.tu_darmstadt.sport.fvf.database;

import java.io.File;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class DatabaseLoader {
	
	private static DatabaseLoader instance = null;
	private File database = null;
	private boolean initialized = false;
	private Map<String, String> config = new HashMap<String, String>();
	
	public static DatabaseLoader getInstance() {
		if (DatabaseLoader.instance == null) {
			DatabaseLoader.instance = new DatabaseLoader();
		}
		return DatabaseLoader.instance;
	}

	public void setDatabase(String file) {
		setDatabase(new File(file));
		reset();
	}

	public void setDatabase(File file) {
		database = file;
	}
	
	public boolean isInitialized() {
		return initialized;
	}
	
	private void reset() {
		initialized = false;
		config.clear();
	}

	public void initialize() {
		if (database != null) {
			
			// check existance
			if (!database.exists()) {
//				MessageDialog.openError(FVF.getDefault().getWorkbench().getActiveWorkbenchWindow().getShell(), 
//						"Database Error", "Database ("+database.getAbsolutePath()+") not found");
				return;
			}

			// load database
			try {
				Class.forName("org.sqlite.JDBC");
				DatabaseConnection conn = DatabaseConnection.getInstance();
				conn.connect(DriverManager.getConnection("jdbc:sqlite:" + database.getAbsolutePath()));
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			
			// get version
//			int version = 1;
//			try {
//				SqlJetDb db = SqlJetDb.open(database, true);
//				version = db.getOptions().getUserVersion();
//			} catch (SqlJetException e) {
//				e.printStackTrace();
//			}
			
			// check version
			// do something with a version and if version isn't new enough, update schema
			
			
			
			initialized = true;
		}
	}
}
