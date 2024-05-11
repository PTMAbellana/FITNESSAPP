package com.example.fitnessapp;

import android.util.Log;

import java.sql.Connection;

import java.sql.DriverManager;
import java.util.Objects;

public class ConnectionClass {
    protected static String db = "abellana_jdbc";
    protected static String ip = "192.168.43.238";
    protected static String port = "3306";
    protected static String username = "paul";
    protected static String password = "abellana";
    public Connection CONN(){
        Connection conn = null;
        try{
            Class.forName("com.mysql.jdbc.Driver");
            String connectionString = "jdbc:mysql://"+ip + ":" + port +"/" + db;
            conn = DriverManager.getConnection(connectionString,username,password);

        }catch(Exception e){
            Log.e("ERROR", Objects.requireNonNull(e.getMessage()));
        }
        return conn;
    }

}
