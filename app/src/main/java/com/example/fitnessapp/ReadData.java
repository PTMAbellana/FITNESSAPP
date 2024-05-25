package com.example.fitnessapp;

import android.util.Log;

import org.mindrot.jbcrypt.BCrypt;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ReadData {
    public static boolean readDataLogin(String username, String password) {
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
//            Statement statement = c.createStatement();
//            String query = "SELECT name, email, height, weight, FROM tblusers WHERE username="+username;
            statement.setString(1, username);
//            ResultSet res = statement.executeQuery();
            return statement.executeQuery();
        }catch(SQLException e){
            throw new RuntimeException(e);
        }
    }

    // getting the user_id, naa nmn diay rawr
    public static int getSession(String username){
        try (Connection c = ConnectionClass.getConnection();
            PreparedStatement statement = c.prepareStatement("SELECT user_id FROM tblusers WHERE username=?");
        ){
            statement.setString(1, username);
            ResultSet res = statement.executeQuery();
            if (res.next()){
                int user_id = res.getInt("user_id");
                return user_id;
            }
        }catch (SQLException e){
            throw new RuntimeException();
        }
        return 0;
    }

    public static String getUsername(int uid){
        try (Connection c = ConnectionClass.getConnection();
             PreparedStatement statement = c.prepareStatement("SELECT username FROM tblusers WHERE user_id=?");
        ){
            statement.setInt(1, uid);
            ResultSet res = statement.executeQuery();
            if (res.next()) {
                return res.getString("username");
            } else {
                Log.e("ReadData", "No username found for user ID: " + uid);
            }
        }catch (SQLException e){
            throw new RuntimeException();
        }
        return null;
    }

    public static ResultSet getProfile(int uid){
        Connection c = null;
        try{
            c = ConnectionClass.getConnection();
            PreparedStatement statement = c.prepareStatement("SELECT name, email, username, height, weight FROM tblusers WHERE user_id=?");

            statement.setInt(1, uid);
            return statement.executeQuery();
        }catch(SQLException e){
            throw new RuntimeException(e);
        }
    }

    // Processing the Plan
    public static Double getBMI(int uid){
        try (Connection c = ConnectionClass.getConnection();
             PreparedStatement statement = c.prepareStatement("SELECT height, weight FROM tblusers WHERE user_id=?");
        ){
            statement.setInt(1, uid);
            ResultSet res = statement.executeQuery();
            if (res.next()) {
                // convert to meters
                double height = res.getDouble("height") / 100.0;

                // must be in kg
                double weight = res.getDouble("weight");

                // kg/m^2
                return weight / (height * height);
            } else {
                Log.e("ReadData", "No weight or height found for user ID: " + uid);
            }
        }catch (SQLException e){
            throw new RuntimeException();
        }
        return null;
    }

    public static String getPlan(int uid){
        try (Connection c = ConnectionClass.getConnection();
             PreparedStatement statement = c.prepareStatement("SELECT plan FROM tblusers WHERE user_id=?");
        ){
            statement.setInt(1, uid);
            ResultSet res = statement.executeQuery();
            if (res.next()) {
                return res.getString("plan");
            } else {
                Log.e("ReadData", "No plan found for user ID: " + uid);
            }
        }catch (SQLException e){
            throw new RuntimeException();
        }
        return null;
    }

    public static int[] getPlans(int uid) {
        try (Connection c = ConnectionClass.getConnection();
             PreparedStatement statement = c.prepareStatement("SELECT day, week FROM tblplans WHERE user_id=?");
        ){
            statement.setInt(1, uid);
            ResultSet res = statement.executeQuery();
            if (res.next()) {
                int[] result = new int[2];
                result[0] = res.getInt("day");
                result[1] = res.getInt("week");

                return result;
            } else {
                Log.e("ReadData", "No day and week found for user ID: " + uid);
            }
        }catch (SQLException e){
            throw new RuntimeException();
        }
        return null;
        // Perform database operations to fetch day and week for the given user ID
        // Return the values as an array [day, week]
        // This is a placeholder implementation. Replace with actual database fetching logic.
//        int day = 1; // Fetch this from the database
//        int week = 1; // Fetch this from the database
//        return new int[]{day, week};
    }


//    public static int readDataLogin(String username) {
//        try (Connection c = ConnectionClass.getConnection();) {
//            Statement statement = c.createStatement();
//            String query = "SELECT * FROM tblusers";
//            ResultSet res = statement.executeQuery(query);
//            while (res.next()) {
//                int id = res.getInt("id");
//                String name = res.getString("username");
//                if (name.equals(username)) {
//                    return id;
//                }
//            }
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//        return 0;
//    }
}
