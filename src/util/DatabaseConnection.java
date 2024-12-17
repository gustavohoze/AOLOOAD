package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static DatabaseConnection instance;
    private Connection connection;

    private static final String DB_URL = "jdbc:mysql://localhost:3307/stellarfest";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "root";

    private DatabaseConnection() throws SQLException {
    	try {
    	    Class.forName("com.mysql.cj.jdbc.Driver"); // Explicitly load the driver
    	    this.connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    	} catch (ClassNotFoundException e) {
    	    throw new SQLException("MySQL Driver not found. Ensure it's in the classpath.", e);
    	}

    }

    public static DatabaseConnection getInstance() throws SQLException {
        if (instance == null) {
            instance = new DatabaseConnection();
        }
        return instance;
    }

    public Connection getConnection() {
        return connection;
    }
}
