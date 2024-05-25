package com.example.fitnessapp;

import android.util.Log;

import org.mindrot.jbcrypt.BCrypt;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

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
    }

    // Generating and Planning List of Exercise
    public static List<Exercise> getExerciseList(int uid) {
        List<Exercise> exerciseList = new ArrayList<>();
        try (Connection c = ConnectionClass.getConnection();
             PreparedStatement statement = c.prepareStatement(
                     "SELECT * FROM tblusers AS user JOIN tblplans AS plan " +
                             "ON user.user_id = plan.user_id WHERE user.user_id = ?");
        ){
            Log.e("ReadData", "Before getExerciseList");
            statement.setInt(1, uid);
            try (ResultSet res = statement.executeQuery()) {
                if (res.next()) {
                    int num_pushups = res.getInt("num_pushups");
                    int num_planks = res.getInt("num_planks");
                    String current_difficulty = res.getString("current_difficulty");
                    double bmi = res.getDouble("bmi");
                    int current_day = res.getInt("day");
                    int current_week = res.getInt("week");

                    Log.e("ReadData", "Success getExercise List: " + uid + " " + num_pushups + " " + num_planks
                            + " " + current_difficulty + " " + bmi + " " + current_week + " " + current_day);

                    String recommended_difficulty;
                    if (bmi >= 18.5 && bmi <= 24.9) {
                        recommended_difficulty = current_difficulty;
                    } else if (current_week < 3) {
                        recommended_difficulty = "Beginner";
                    } else if (current_week == 3) {
                        recommended_difficulty = "Intermediate";
                    } else {
                        recommended_difficulty = current_difficulty;
                    }

                    try (PreparedStatement exerciseStatement = c.prepareStatement(
                            "SELECT * FROM tblexercises WHERE difficulty = ?")) {
                        exerciseStatement.setString(1, recommended_difficulty);
                        try (ResultSet exerciseRes = exerciseStatement.executeQuery()) {
                            while (exerciseRes.next()) {
                                int exercise_sched = current_day % 4;

                                int exercise_id = exerciseRes.getInt("exercise_id");
                                if(exercise_id % 3 != exercise_sched % 3) {
                                    continue;
                                }

                                String exercise_name = exerciseRes.getString("exercise_name");
                                String target = exerciseRes.getString("target");
                                String difficulty = exerciseRes.getString("difficulty");
                                int no_of_sets = exerciseRes.getInt("no_of_sets");
                                int no_of_reps = exerciseRes.getInt("no_of_reps");
                                int no_of_seconds = exerciseRes.getInt("no_of_seconds");

                                if(exercise_name.contains("Push-ups")) {
                                    if(num_pushups == 0) {
                                        exercise_name = "Mountain Climbers";
                                        no_of_sets = 3;
                                        no_of_reps = Integer.parseInt(null);
                                        no_of_seconds = 30;
                                    } else if (num_pushups <= 5) {
                                        no_of_reps += 0;
                                    } else if (num_pushups <= 20) {
                                        no_of_reps += 5;
                                    } else {
                                        no_of_reps += 10;
                                    }
                                }

                                if(exercise_name.contains("Plank")) {
                                    if(num_planks == 0) {
                                        exercise_name = "Mountain Climbers";
                                        no_of_sets = 3;
                                        no_of_reps = Integer.parseInt(null);
                                        no_of_seconds = 30;
                                    } else if (num_planks <= 30) {
                                        no_of_seconds += 0;
                                    } else if (num_planks <= 120) {
                                        no_of_seconds += 30;
                                    } else {
                                        no_of_seconds += 60;
                                    }
                                }

                                Exercise exercise = new Exercise(exercise_id, exercise_name, target, difficulty, no_of_sets, no_of_reps, no_of_seconds);
                                exerciseList.add(exercise);
                            }
                        }
                    }
                } else {
                    Log.e("ReadData", "No data found for user: " + uid);
                }
            }

            Log.e("ReadData", "Successfully added List of Exercises");
            return exerciseList;
        } catch (SQLException e) {
            Log.e("ReadData", "Error getting exercise list", e);
            throw new RuntimeException(e);
        }
    }


//    public static int[] getExerciseList(int uid) {
//        int[] exerciseList = {1, 2, 3, 4, 5};
//        try (Connection c = ConnectionClass.getConnection();
//             PreparedStatement statement = c.prepareStatement(
//                     "SELECT * FROM tblusers AS user JOIN tblplans AS plan " +
//                             "ON user.user_id = plan.user_id WHERE user.user_id = ?");
//        ){
//            Log.e("ReadData", "Before getExerciseList");
//            statement.setInt(1, uid);
//            ResultSet res = statement.executeQuery();
//
//            if (res.next()) { // Ensure to move the cursor to the first row
//                int num_pushups = res.getInt("num_pushups");
//                int num_planks = res.getInt("num_planks");
//                String current_difficulty = res.getString("current_difficulty");
//                double bmi = res.getDouble("bmi");
//                int current_day = res.getInt("day");
//                int current_week = res.getInt("week");
//
//                Log.e("ReadData", "Success getExercise List: " + uid + " " + num_pushups + " " + num_planks
//                        + " " + current_difficulty + " " + bmi + " " + current_week + " " + current_day);
//
//                String recommended_difficulty;
//                if(bmi >= 18.5 && bmi <= 24.9) {
//                    // if normal weight ra
//                    recommended_difficulty = current_difficulty;
//                } else if (current_week < 3){
//                    recommended_difficulty = "Beginner";
//                } else if (current_week == 3){
//                    recommended_difficulty = "Intermediate";
//                } else {
//                    recommended_difficulty = current_difficulty;
//                }
//
//                // should i use query or statement?
//                String query = "SELECT * FROM tblexercises";
//
//
//            } else {
//                Log.e("ReadData", "No data found for user: " + uid);
//            }
//            return exerciseList;
//        }catch (SQLException e){
//            throw new RuntimeException();
//        }
//    }


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
