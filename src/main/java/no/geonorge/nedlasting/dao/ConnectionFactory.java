package no.geonorge.nedlasting.dao;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionFactory {
	
	String dbDriverName = null;
	String dbUrl = null;
	String dbUsername = null;
	String dbPassword = null;
	
	private static ConnectionFactory connectionFactory = null;
	
	private ConnectionFactory() {
		/*
		 * Read from configuration file
		 */
		Properties prop = new Properties();
		InputStream reader = null;
		try {
			reader = new FileInputStream("/etc/geonorge/geonorge.conf");
			prop.load(reader);						
			this.dbDriverName = prop.getProperty("database.driver");
			this.dbUrl = prop.getProperty("database.url");
			this.dbUsername = prop.getProperty("database.username");
			this.dbPassword = prop.getProperty("database.password");
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			Class.forName(dbDriverName);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public Connection getConnection() throws SQLException {
		Connection connection = null;
		connection = DriverManager.getConnection(dbUrl,dbUsername,dbPassword);
		return connection;
	}
	
	public static ConnectionFactory getInstance() {
		if (connectionFactory == null) {
			connectionFactory = new ConnectionFactory();
		}
		return connectionFactory;
	}
}
