package com.example.fitnessapp;

import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionClass {
    protected static String db = "fitnessdb";
    protected static String ip = "192.168.43.238";
    protected static String port = "3306";
    protected static String username = "paul";
    protected static String password = "abellana";

    public static Connection CONN() {
        Connection conn = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            String connectionString = "jdbc:mysql://" + ip + ":" + port + "/" + db;
            conn = DriverManager.getConnection(connectionString, username, password);
        } catch (ClassNotFoundException e) {
            Log.e("ERROR", "MySQL JDBC Driver not found", e);
        } catch (SQLException e) {
            Log.e("ERROR", "Failed to connect to MySQL server", e);
        }
        return conn;
    }

    //GRANT ALL PRIVILEGES ON *.* TO 'paul'@'DESKTOP-VSREQPO' IDENTIFIED BY 'abellana';
}
