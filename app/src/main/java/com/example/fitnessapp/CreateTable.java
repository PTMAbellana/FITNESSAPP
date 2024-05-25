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
            ResultSet resultSet = metaData.getTables(null, null, "tblusers", new String[]{"TABLE"});
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
                "user_id INT PRIMARY KEY AUTO_INCREMENT," +
                "name VARCHAR(100) NOT NULL," +
                "email VARCHAR(100) NOT NULL," +
                "username VARCHAR(50) NOT NULL," +
                "password TEXT NOT NULL," +
                "gender VARCHAR(50) NOT NULL DEFAULT 'Male'," +
                "plan VARCHAR(50) NOT NULL DEFAULT 'Beginner'," +
                "num_pushups INT NOT NULL DEFAULT 0," +
                "height FLOAT NOT NULL DEFAULT 0," +
                "weight FLOAT NOT NULL DEFAULT 0," +
                "age INT NOT NULL DEFAULT 0," +
                "num_planks INT NOT NULL DEFAULT 0," +
                "status VARCHAR(50) NOT NULL DEFAULT 'active'" +
//                "UNIQUE (user_id)," +
//                "UNIQUE (plan)" +
                ")";

        String query2 = "CREATE TABLE IF NOT EXISTS tblexercises (" +
                "exercise_id INT PRIMARY KEY AUTO_INCREMENT," +
                "exercise_name TEXT NOT NULL," +
                "target TEXT NOT NULL," +
                "difficulty TEXT NOT NULL," +
                "no_of_sets INT NOT NULL," +
                "no_of_reps INT," +
                "no_of_seconds INT" +
                ")";

        // Underweight, Normal, Overweight, Obese
        String query3 = "CREATE TABLE IF NOT EXISTS tblplans (" +
                "plan_id INT PRIMARY KEY AUTO_INCREMENT," +
                "user_id INT NOT NULL," +
                "current_difficulty VARCHAR(50) NOT NULL DEFAULT 'Beginner'," +
                "target_difficulty VARCHAR(50) NOT NULL," +
                "week INT NOT NULL DEFAULT 1," +
                "day INT NOT NULL DEFAULT 1," +
                "bmi FLOAT NOT NULL DEFAULT 1," +
                "FOREIGN KEY (user_id) REFERENCES tblusers(user_id)" +
                ")";

        String query4 = "CREATE TABLE IF NOT EXISTS tbldietplans (" +
                "plan_id INT PRIMARY KEY AUTO_INCREMENT," +
                "bmi_category VARCHAR(50) NOT NULL DEFAULT 'Normal'," +
                "plan_name TEXT NOT NULL" +
                ")";

        try {
            conn = ConnectionClass.getConnection();
            if (conn != null) {
                stmt = conn.createStatement();

                if (!tableExists(conn, "tblusers")) {
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

                if (!tableExists(conn, "tblplans")) {
                    stmt.execute(query3);
                    System.out.println("Table tblplans created successfully!");
                } else {
                    System.out.println("Table tblplans already exists.");
                }

                if (!tableExists(conn, "tbldietplans")) {
                    stmt.execute(query4);
                    System.out.println("Table tbldietplans created successfully!");
                } else {
                    System.out.println("Table tbldietplans already exists.");
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
