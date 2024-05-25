    package com.example.fitnessapp;

    import android.util.Log;

    import java.sql.Connection;
    import java.sql.DriverManager;
    import java.sql.SQLException;

    // DatabaseHelper
    public class ConnectionClass {
        private static final String TAG = "ConnectionClass";

        // For the local database
//        private static final String DATABASE = "fitnessdb"; // local
//        private static final String IP = "10.0.2.2"; // locally hosted in machine
//        private static final String PORT = "3306";
//        private static final String USERNAME = "paul"; // local
////        private static final String USERNAME = "francis"; // local
//        private static final String PASSWORD = "abellana"; // local
//        private static final String PASSWORD = "chavez"; // local
//        private static final String IP = "192.168.1.4";
//        private static final String IP = "127.0.0.1";
//        private static final String USERNAME = "root";
//        private static final String PASSWORD = "123456";

        // For the online database
        private static final String DATABASE = "sql12709080";
        private static final String IP = "sql12.freesqldatabase.com";
        private static final String PORT = "3306";
        private static final String USERNAME = "sql12709080";
        private static final String PASSWORD = "cUFTy1vWpp";

        public static Connection getConnection() {
            Connection conn = null;
            try {
                Class.forName("com.mysql.jdbc.Driver");
                String connectionString = "jdbc:mysql://" + IP + ":" + PORT + "/" + DATABASE;
//                Class.forName("org.mariadb.jdbc.Driver");
//                String connectionString = "jdbc:mariadb://" + IP + ":" + PORT + "/" + DATABASE;
                conn = DriverManager.getConnection(connectionString, USERNAME, PASSWORD);

            } catch (ClassNotFoundException e) {
                e.printStackTrace();
                Log.e(TAG, "MySQL JDBC Driver not found", e);
            }
            catch (SQLException e) {
                e.printStackTrace();
                Log.e(TAG, "Failed to connect to MySQL server", e);
            }
            return conn;
        }
    }
