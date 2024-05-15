package com.example.fitnessapp;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class CreateTable {
    public static void createTables() {
        Connection conn = null;
        Statement stmt = null;
        String query = "CREATE TABLE IF NOT EXISTS tblUsers (" +
                "user_id INT(10) PRIMARY KEY AUTO_INCREMENT," +
                "name VARCHAR(100) NOT NULL," +
                "email VARCHAR(100) NOT NULL," +
                "username VARCHAR(50) NOT NULL," +
                "password VARCHAR(50) NOT NULL," +
                "gender VARCHAR(50) NOT NULL DEFAULT 'Male'," +
                "height FLOAT(10) NOT NULL DEFAULT 0," +
                "weight FLOAT(10) NOT NULL DEFAULT 0)";

        try {
            conn = ConnectionClass.getConnection();
            if (conn != null) {
                stmt = conn.createStatement();
                stmt.execute(query);
                System.out.println("Tables created successfully!");
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
