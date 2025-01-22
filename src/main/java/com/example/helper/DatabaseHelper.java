package com.example.helper;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseHelper {
    private static final String url = "jdbc:postgresql://localhost:5432/java_lession";
    private final static String username = "postgres";
    private final static String password = "123";

    public static Connection getConnection() throws SQLException {
        Connection conn = DriverManager.getConnection(url, username, password);
        conn.setAutoCommit(true); // Ensure autocommit is enabled
        return conn;
    }
}
