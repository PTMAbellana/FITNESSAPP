package com.example.fitnessapp;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.mindrot.jbcrypt.BCrypt;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class InsertData {

    public static void insertUserData(Context context, String name, String email, String username, String password, String gender) {
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
        new InsertUserDataTask(context, name, email, username, hashedPassword, gender).execute();
    }

    private static class InsertUserDataTask extends AsyncTask<Void, Void, Boolean> {
        private final Context context;
        private final String name;
        private final String email;
        private final String username;
        private final String password;
        private final String gender;

        public InsertUserDataTask(Context context, String name, String email, String username, String password, String gender) {
            this.context = context;
            this.name = name;
            this.email = email;
            this.username = username;
            this.password = password;
            this.gender = gender;
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            Connection c = ConnectionClass.getConnection();
            if (c == null) {
                return false; // Failed to connect to the database
            }

            if ( !CreateTable.usersTable() ){
                CreateTable.createTables();
            }

            try {
                PreparedStatement statement = c.prepareStatement(
                        "INSERT INTO tblusers (name, email, username, password, gender) VALUES (?,?,?,?,?)"
                );
                statement.setString(1, name);
                statement.setString(2, email);
                statement.setString(3, username);
                statement.setString(4, password);
                statement.setString(5, gender);
                int rowsInserted = statement.executeUpdate();
                System.out.println("Rows Inserted: " + rowsInserted);

                return true; // Insertion successful
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } finally {
                try {
                    c.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
//            return false;
        }

        @Override
        protected void onPostExecute(Boolean success) {
            if (success) {
//                InsertData.uid = id;
                showToast(context, "User inserted successfully!");
            } else {
//                InsertData.uid = 0;
                showToast(context, "Failed to insert user. Please check your network connection.");
            }
        }
    }

    private static void showToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    private static void addExercise(PreparedStatement checkStmt, PreparedStatement insertStmt, String exercise_name, String target, String difficulty, int no_of_sets, Integer no_of_reps, Integer no_of_seconds) throws SQLException {
        checkStmt.setString(1, exercise_name);
        checkStmt.setString(2, difficulty);
        ResultSet rs = checkStmt.executeQuery();
        rs.next();
        int count = rs.getInt(1);
        if (count == 0) {
            insertStmt.setString(1, exercise_name);
            insertStmt.setString(2, target);
            insertStmt.setString(3, difficulty);
            insertStmt.setInt(4, no_of_sets);
            insertStmt.setInt(5, no_of_reps);
            insertStmt.setInt(6, no_of_seconds);

            insertStmt.addBatch();
        }
    }

    public static void insertAllExercisesData() {
        String insertQuery = "INSERT INTO tblexercises (exercise_name, target, difficulty, no_of_sets, no_of_reps, no_of_seconds) VALUES (?, ?, ?, ?, ?, ?)";
        String checkQuery = "SELECT COUNT(*) FROM tblexercises WHERE exercise_name = ? AND difficulty = ?";

        try (Connection conn = ConnectionClass.getConnection();
             PreparedStatement insertStmt = conn.prepareStatement(insertQuery);
             PreparedStatement checkStmt = conn.prepareStatement(checkQuery)) {

            // Add all exercises data
            addExercise(checkStmt, insertStmt, "Mountain Climbers", "Abs","Beginner", 3, 0, 30);
            addExercise(checkStmt, insertStmt, "Crunches", "Abs","Beginner", 3, 15, 0);
            addExercise(checkStmt, insertStmt, "Plank", "Abs","Beginner", 3, 0, 20);

            addExercise(checkStmt, insertStmt, "Mountain Climbers", "Abs","Intermediate", 3, 0, 45);
            addExercise(checkStmt, insertStmt, "Crunches", "Abs","Intermediate", 3, 20, 0);
            addExercise(checkStmt, insertStmt, "Plank", "Abs","Intermediate", 3, 0, 30);

            addExercise(checkStmt, insertStmt, "Mountain Climbers", "Abs","Advanced", 3, 0, 60);
            addExercise(checkStmt, insertStmt, "Crunches", "Abs","Advanced", 4, 25, 0);
            addExercise(checkStmt, insertStmt, "Plank", "Abs","Advanced", 3, 0, 60);

            addExercise(checkStmt, insertStmt, "Push-ups", "Chest","Beginner", 3, 10, 0);
            addExercise(checkStmt, insertStmt, "Incline Push-ups", "Chest","Beginner", 3, 12, 0);
            addExercise(checkStmt, insertStmt, "Mountain Climbers", "Chest","Beginner", 3, 0, 30);

            addExercise(checkStmt, insertStmt, "Push-ups", "Chest","Intermediate", 3, 15, 0);
            addExercise(checkStmt, insertStmt, "Incline Push-ups", "Chest","Intermediate", 3, 15, 0);
            addExercise(checkStmt, insertStmt, "Mountain Climbers", "Chest","Intermediate", 3, 0, 45);

            addExercise(checkStmt, insertStmt, "Push-ups", "Chest","Advanced", 4, 20, 0);
            addExercise(checkStmt, insertStmt, "Incline Push-ups", "Chest","Advanced", 4, 20, 0);
            addExercise(checkStmt, insertStmt, "Mountain Climbers", "Chest","Advanced", 3, 0, 60);

            addExercise(checkStmt, insertStmt, "Arm Circles", "Shoulder","Beginner", 3, 0, 30);
            addExercise(checkStmt, insertStmt, "Mountain Climbers", "Shoulder","Beginner", 3, 0, 30);
            addExercise(checkStmt, insertStmt, "Plank to Downward Dog", "Shoulder","Beginner", 3, 10, 0);

            addExercise(checkStmt, insertStmt, "Arm Circles", "Shoulder","Intermediate", 3, 0, 45);
            addExercise(checkStmt, insertStmt, "Mountain Climbers", "Shoulder","Intermediate", 3, 0, 45);
            addExercise(checkStmt, insertStmt, "Plank to Downward Dog", "Shoulder","Intermediate", 3, 15, 0);

            addExercise(checkStmt, insertStmt, "Arm Circles", "Shoulder","Advanced", 3, 0, 60);
            addExercise(checkStmt, insertStmt, "Mountain Climbers", "Shoulder","Advanced", 3, 0, 60);
            addExercise(checkStmt, insertStmt, "Plank to Downward Dog", "Shoulder","Advanced", 3, 20, 0);

            addExercise(checkStmt, insertStmt, "Mountain Climbers", "Back","Beginner", 3, 0, 30);
            addExercise(checkStmt, insertStmt, "Superman Exercise", "Back","Beginner", 3, 15, 0);
            addExercise(checkStmt, insertStmt, "Plank Rows", "Back","Beginner", 3, 10, 0);

            addExercise(checkStmt, insertStmt, "Mountain Climbers", "Back","Intermediate", 3, 0, 45);
            addExercise(checkStmt, insertStmt, "Superman Exercise", "Back","Intermediate", 3, 20, 0);
            addExercise(checkStmt, insertStmt, "Plank Rows", "Back","Intermediate", 3, 15, 0);

            addExercise(checkStmt, insertStmt, "Mountain Climbers", "Back","Advanced", 3, 0, 60);
            addExercise(checkStmt, insertStmt, "Superman Exercise", "Back","Advanced", 4, 25, 0);
            addExercise(checkStmt, insertStmt, "Plank Rows", "Back","Advanced", 3, 20, 0);

            addExercise(checkStmt, insertStmt, "Tricep Dips", "Arm","Beginner", 3, 12, 0);
            addExercise(checkStmt, insertStmt, "Push-ups", "Arm","Beginner", 3, 10, 0);
            addExercise(checkStmt, insertStmt, "Mountain Climbers", "Arm","Beginner", 3, 0, 30);

            addExercise(checkStmt, insertStmt, "Tricep Dips", "Arm","Intermediate", 3, 15, 0);
            addExercise(checkStmt, insertStmt, "Push-ups", "Arm","Intermediate", 3, 15, 0);
            addExercise(checkStmt, insertStmt, "Mountain Climbers", "Arm","Intermediate", 3, 0, 45);

            addExercise(checkStmt, insertStmt, "Tricep Dips", "Arm","Advanced", 4, 20, 0);
            addExercise(checkStmt, insertStmt, "Push-ups", "Arm","Advanced", 4, 20, 0);
            addExercise(checkStmt, insertStmt, "Mountain Climbers", "Arm","Advanced", 3, 0, 60);

            addExercise(checkStmt, insertStmt, "Bodyweight Squats", "Leg","Beginner", 3, 15, 0);
            addExercise(checkStmt, insertStmt, "Mountain Climbers", "Leg","Beginner", 3, 0, 30);
            addExercise(checkStmt, insertStmt, "Lunges", "Leg","Beginner", 3, 12, 0);

            addExercise(checkStmt, insertStmt, "Bodyweight Squats", "Leg","Intermediate", 3, 20, 0);
            addExercise(checkStmt, insertStmt, "Mountain Climbers", "Leg","Intermediate", 3, 0, 45);
            addExercise(checkStmt, insertStmt, "Lunges", "Leg","Intermediate", 3, 15, 0);

            addExercise(checkStmt, insertStmt, "Bodyweight Squats", "Leg","Advanced", 4, 25, 0);
            addExercise(checkStmt, insertStmt, "Mountain Climbers", "Leg","Advanced", 3, 0, 60);
            addExercise(checkStmt, insertStmt, "Lunges", "Leg","Advanced", 4, 20, 0);

            addExercise(checkStmt, insertStmt, "Mountain Climbers", "Glutes","Beginner", 3, 0, 30);
            addExercise(checkStmt, insertStmt, "Glute Bridges", "Glutes","Beginner", 3, 15, 0);
            addExercise(checkStmt, insertStmt, "Donkey Kicks", "Glutes","Beginner", 3, 12, 0);

            addExercise(checkStmt, insertStmt, "Mountain Climbers", "Glutes","Intermediate", 3, 0, 45);
            addExercise(checkStmt, insertStmt, "Glute Bridges", "Glutes","Intermediate", 3, 20, 0);
            addExercise(checkStmt, insertStmt, "Donkey Kicks", "Glutes","Intermediate", 3, 15, 0);

            addExercise(checkStmt, insertStmt, "Mountain Climbers", "Glutes","Advanced", 3, 0, 60);
            addExercise(checkStmt, insertStmt, "Glute Bridges", "Glutes","Advanced", 4, 25, 0);
            addExercise(checkStmt, insertStmt, "Donkey Kicks", "Glutes","Advanced", 4, 20, 0);

            insertStmt.executeBatch();

            System.out.println("Exercise data inserted successfully!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void insertPlan(int user_id, String current_difficulty, String target_difficulty, int week, int day, double bmi) {
        String insertQuery = "INSERT INTO tblplans (user_id, current_difficulty, target_difficulty, week, day, bmi) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = ConnectionClass.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(insertQuery)) {

            pstmt.setInt(1, user_id);
            pstmt.setString(2, current_difficulty);
            pstmt.setString(3, target_difficulty);
            pstmt.setInt(4, week);
            pstmt.setInt(5, day);
            pstmt.setDouble(6, bmi);

            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                System.out.println("Plan inserted successfully!");
            } else {
                System.out.println("Failed to insert the plan.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
