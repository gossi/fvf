package de.tu_darmstadt.sport.fvf.database;

import java.sql.Connection;
import java.sql.SQLException;

import org.sormula.Database;

public class DatabaseConnection {

	private static DatabaseConnection instance = null;
	private Connection connection;
	private Database database;

	public void connect(Connection connection) {
		this.connection = connection;
		database = new Database(this.connection);
	}
	
	public static DatabaseConnection getInstance() {
		if (DatabaseConnection.instance == null) {
			DatabaseConnection.instance = new DatabaseConnection();
		}
		return DatabaseConnection.instance;
	}
	
	public boolean isConnected() {
		try {
			return !(connection == null || connection.isClosed());
		} catch (SQLException e) {
			return false;
		}
	}
	
	public Connection getConnection() {
		return connection;
	}
	
	public Database getDatabase() {
		return database;
	}
	
	public void close() throws SQLException {
		connection.close();
	}
}
