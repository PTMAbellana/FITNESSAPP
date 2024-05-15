package com.example.fitnessapp;

import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionClass {
    private static final String TAG = "ConnectionClass";
    private static final String DATABASE = "fitnessdb";
//    private static final String IP = "192.168.43.238";
//    private static final String IP = "192.168.1.3";
    private static final String IP = "10.0.2.2";
    private static final String PORT = "3306";
//    private static final String USERNAME = "paul";
    private static final String USERNAME = "francis";
//    private static final String PASSWORD = "abellana";

    private static final String PASSWORD = "chavez";

    public static Connection getConnection() {
        Connection conn = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            String connectionString = "jdbc:mysql://" + IP + ":" + PORT + "/" + DATABASE;
            conn = DriverManager.getConnection(connectionString, USERNAME, PASSWORD);
            Log.d(TAG, "Connection successful");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            Log.e(TAG, "MySQL JDBC Driver not found", e);
        } catch (SQLException e) {
            e.printStackTrace();
            Log.e(TAG, "Failed to connect to MySQL server", e);
        }
        return conn;
    }
}
