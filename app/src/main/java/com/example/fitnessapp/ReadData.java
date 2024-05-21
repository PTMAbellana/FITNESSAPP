package com.example.fitnessapp;

import org.mindrot.jbcrypt.BCrypt;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ReadData {
    public static boolean readData(String username, String password) {
        try (Connection c = ConnectionClass.getConnection();
             PreparedStatement statement = c.prepareStatement("SELECT password FROM tblusers WHERE username = ?")) {

            statement.setString(1, username);
            try (ResultSet res = statement.executeQuery()) {
                if (res.next()) {
                    String hashedPassword = res.getString("password");
                    return BCrypt.checkpw(password, hashedPassword);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    public static boolean usernameExist(String username) {
        try (Connection c = ConnectionClass.getConnection();
             PreparedStatement statement = c.prepareStatement("SELECT username FROM tblusers WHERE username = ?")) {

            statement.setString(1, username);
            try (ResultSet res = statement.executeQuery()) {
                return res.next();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static ResultSet getProfile(String username){
        Connection c = null;
        try{
            c = ConnectionClass.getConnection();
            PreparedStatement statement = c.prepareStatement("SELECT name, email, height, weight FROM tblusers WHERE username=?");

            statement.setString(1, username);
            return statement.executeQuery();
        }catch(SQLException e){
            throw new RuntimeException(e);
        }
    }

    public static ResultSet getProfile(int uid){
        Connection c = null;
        try{
            c = ConnectionClass.getConnection();
            PreparedStatement statement = c.prepareStatement("SELECT name, email, height, weight FROM tblusers WHERE user_id=?");

            statement.setInt(1, uid);
            return statement.executeQuery();
        }catch(SQLException e){
            throw new RuntimeException(e);
        }
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
