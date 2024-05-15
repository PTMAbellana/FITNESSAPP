package com.example.fitnessapp;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ReadData {
    public static boolean readData(String username, String password) {
        try (Connection c = ConnectionClass.getConnection();) {
            Statement statement = c.createStatement();
            String query = "SELECT * FROM tblUsers";
            ResultSet res = statement.executeQuery(query); //FOR READING
            while (res.next()) {
                String name = res.getString("username");
                String pass = res.getString("password");
                if (name.equals(username) && pass.equals(password)) {
                    return true;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return false;
    }

    public static int readData(String username) {
        try (Connection c = ConnectionClass.getConnection();) {
            Statement statement = c.createStatement();
            String query = "SELECT * FROM tblUsers";
            ResultSet res = statement.executeQuery(query);
            while (res.next()) {
                int id = res.getInt("id");
                String name = res.getString("username");
                if (name.equals(username)) {
                    return id;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return 0;
    }

}
