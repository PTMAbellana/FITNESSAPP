package com.example.fitnessapp;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class CreateTable {
    public static boolean tableExists(Connection conn, String tableName) throws SQLException {
        DatabaseMetaData metaData = conn.getMetaData();
        ResultSet resultSet = metaData.getTables(null, null, tableName.toUpperCase(), new String[]{"TABLE"});
        return resultSet.next();
    }

    public static boolean usersTable(){
        try (Connection c = ConnectionClass.getConnection()) {
            DatabaseMetaData metaData = c.getMetaData();
            ResultSet resultSet = metaData.getTables(null, null, "tblUsers", new String[]{"TABLE"});
            return resultSet.next() && !resultSet.wasNull();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    public static void createTables() {
        Connection conn = null;
        Statement stmt = null;
        String query1 = "CREATE TABLE IF NOT EXISTS tblusers (" +
                "user_id INT(10) PRIMARY KEY AUTO_INCREMENT," +
                "name VARCHAR(100) NOT NULL," +
                "email VARCHAR(100) NOT NULL," +
                "username VARCHAR(50) NOT NULL," +
                "password TEXT NOT NULL," +
                "gender VARCHAR(50) NOT NULL DEFAULT 'Male'," +
                "plan VARCHAR(50) NOT NULL DEFAULT 'Beginner'," +
                "num_pushups INT(10) NOT NULL DEFAULT '0'," +
                "height FLOAT(10) NOT NULL DEFAULT 0," +
                "weight FLOAT(10) NOT NULL DEFAULT 0," +
                "age INT(10) NOT NULL DEFAULT 0," +
                "num_planks INT(10) NOT NULL DEFAULT '0'," +
                "reminder TIME DEFAULT '00:00:00')";

        String query2 = "CREATE TABLE IF NOT EXISTS tblexercises (" +
                "exercise_id INT(10) PRIMARY KEY AUTO_INCREMENT," +
                "exercise_name TEXT NOT NULL," +
                "target TEXT NOT NULL," +
                "difficulty TEXT NOT NULL," +
                "no_of_sets INT(10) NOT NULL," +
                "no_of_reps INT(10)," +
                "no_of_seconds INT(10))";

//        String quesry3 = "CREATE TABLE IF NOT EXISTS tblusersexercises (" +
//                "user_exercise_id INT(10) PRIMARY KEY AUTO_INCREMENT," +
//                " TEXT NOT NULL," +
//                "target TEXT NOT NULL," +
//                "difficulty TEXT NOT NULL," +
//                "no_of_sets INT(10) NOT NULL," +
//                "no_of_reps INT(10)," +
//                "no_of_seconds INT(10))";

        try {
            conn = ConnectionClass.getConnection();
            if (conn != null) {
                stmt = conn.createStatement();

                if (!tableExists(conn, "tblUsers")) {
                    stmt.execute(query1);
                    System.out.println("Table tblUsers created successfully!");
                } else {
                    System.out.println("Table tblUsers already exists.");
                }

                if (!tableExists(conn, "tblexercises")) {
                    stmt.execute(query2);
                    System.out.println("Table tblexercises created successfully!");
                } else {
                    System.out.println("Table tblexercises already exists.");
                }

                System.out.println("Tables checked/created successfully!");
            } else {
                System.out.println("Failed to make connection!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
