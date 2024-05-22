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
    public static int uid;
    public static void insertUserData(Context context, String name, String email, String username, String password, String gender) {
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
        new InsertUserDataTask(context, name, email, username, hashedPassword, gender).execute();
//        Log.e("InsertData", "UID is " + uid);
//        return uid;
    }

    private static class InsertUserDataTask extends AsyncTask<Void, Void, Boolean> {
        private final Context context;
        private final String name;
        private final String email;
        private final String username;
        private final String password;
        private final String gender;
//        private final InsertUserDataCallback callback;

        public InsertUserDataTask(Context context, String name, String email, String username, String password, String gender) {
            this.context = context;
            this.name = name;
            this.email = email;
            this.username = username;
            this.password = password;
            this.gender = gender;
//            this.callback = callback;
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
                        "INSERT INTO tblusers (name, email, username, password, gender) VALUES (?,?,?,?,?)",
                        Statement.RETURN_GENERATED_KEYS
                );
                statement.setString(1, name);
                statement.setString(2, email);
                statement.setString(3, username);
                statement.setString(4, password);
                statement.setString(5, gender);
                int rowsInserted = statement.executeUpdate();
                System.out.println("Rows Inserted: " + rowsInserted);

                if (rowsInserted > 0) {

                ResultSet generatedKeys = statement.getGeneratedKeys();

//                if (generatedKeys.next()) {
////                    uid = generatedKeys.getInt(1);
//                    if (callback != null){
//                        callback.onUserInserted(generatedKeys.getInt(1));
//                    }
////                    Log.e("InsertData", "insert successful, uid is " + uid);
////                    return uid;
//                } else {
//                    if (callback != null){
//                        callback.onUserInserted(0);
//
//                    }
//                }

            }

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

    public static void insertAllExercisesData() {
        String insertQuery = "INSERT INTO tblexercises (exercise_name, difficulty, no_of_sets, no_of_reps, no_of_seconds) VALUES (?, ?, ?, ?, ?)";
        String checkQuery = "SELECT COUNT(*) FROM tblexercises WHERE exercise_name = ? AND difficulty = ?";

        try (Connection conn = ConnectionClass.getConnection();
             PreparedStatement insertStmt = conn.prepareStatement(insertQuery);
             PreparedStatement checkStmt = conn.prepareStatement(checkQuery)) {

            // Add all exercises data
            addExercise(checkStmt, insertStmt, "Crunches", "Beginner", 3, 15, null);
            addExercise(checkStmt, insertStmt, "Plank", "Beginner", 3, null, 20);
            addExercise(checkStmt, insertStmt, "Mountain Climbers", "Beginner", 3, null, 30);

            addExercise(checkStmt, insertStmt, "Crunches", "Intermediate", 3, 20, null);
            addExercise(checkStmt, insertStmt, "Plank", "Intermediate", 3, null, 30);
            addExercise(checkStmt, insertStmt, "Mountain Climbers", "Intermediate", 3, null, 45);

            addExercise(checkStmt, insertStmt, "Crunches", "Advanced", 4, 25, null);
            addExercise(checkStmt, insertStmt, "Plank", "Advanced", 3, null, 60);
            addExercise(checkStmt, insertStmt, "Mountain Climbers", "Advanced", 3, null, 60);

            addExercise(checkStmt, insertStmt, "Push-ups", "Beginner", 3, 10, null);
            addExercise(checkStmt, insertStmt, "Incline Push-ups", "Beginner", 3, 12, null);
            addExercise(checkStmt, insertStmt, "Mountain Climbers", "Beginner", 3, null, 30);

            addExercise(checkStmt, insertStmt, "Push-ups", "Intermediate", 3, 15, null);
            addExercise(checkStmt, insertStmt, "Incline Push-ups", "Intermediate", 3, 15, null);
            addExercise(checkStmt, insertStmt, "Mountain Climbers", "Intermediate", 3, null, 45);

            addExercise(checkStmt, insertStmt, "Push-ups", "Advanced", 4, 20, null);
            addExercise(checkStmt, insertStmt, "Incline Push-ups", "Advanced", 4, 20, null);
            addExercise(checkStmt, insertStmt, "Mountain Climbers", "Advanced", 3, null, 60);

            addExercise(checkStmt, insertStmt, "Arm Circles", "Beginner", 3, null, 30);
            addExercise(checkStmt, insertStmt, "Plank to Downward Dog", "Beginner", 3, 10, null);
            addExercise(checkStmt, insertStmt, "Mountain Climbers", "Beginner", 3, null, 30);

            addExercise(checkStmt, insertStmt, "Arm Circles", "Intermediate", 3, null, 45);
            addExercise(checkStmt, insertStmt, "Plank to Downward Dog", "Intermediate", 3, 15, null);
            addExercise(checkStmt, insertStmt, "Mountain Climbers", "Intermediate", 3, null, 45);

            addExercise(checkStmt, insertStmt, "Arm Circles", "Advanced", 3, null, 60);
            addExercise(checkStmt, insertStmt, "Plank to Downward Dog", "Advanced", 3, 20, null);
            addExercise(checkStmt, insertStmt, "Mountain Climbers", "Advanced", 3, null, 60);

            addExercise(checkStmt, insertStmt, "Superman Exercise", "Beginner", 3, 15, null);
            addExercise(checkStmt, insertStmt, "Plank Rows", "Beginner", 3, 10, null);
            addExercise(checkStmt, insertStmt, "Mountain Climbers", "Beginner", 3, null, 30);

            addExercise(checkStmt, insertStmt, "Superman Exercise", "Intermediate", 3, 20, null);
            addExercise(checkStmt, insertStmt, "Plank Rows", "Intermediate", 3, 15, null);
            addExercise(checkStmt, insertStmt, "Mountain Climbers", "Intermediate", 3, null, 45);

            addExercise(checkStmt, insertStmt, "Superman Exercise", "Advanced", 4, 25, null);
            addExercise(checkStmt, insertStmt, "Plank Rows", "Advanced", 3, 20, null);
            addExercise(checkStmt, insertStmt, "Mountain Climbers", "Advanced", 3, null, 60);

            addExercise(checkStmt, insertStmt, "Push-ups", "Beginner", 3, 10, null);
            addExercise(checkStmt, insertStmt, "Tricep Dips", "Beginner", 3, 12, null);
            addExercise(checkStmt, insertStmt, "Mountain Climbers", "Beginner", 3, null, 30);

            addExercise(checkStmt, insertStmt, "Push-ups", "Intermediate", 3, 15, null);
            addExercise(checkStmt, insertStmt, "Tricep Dips", "Intermediate", 3, 15, null);
            addExercise(checkStmt, insertStmt, "Mountain Climbers", "Intermediate", 3, null, 45);

            addExercise(checkStmt, insertStmt, "Push-ups", "Advanced", 4, 20, null);
            addExercise(checkStmt, insertStmt, "Tricep Dips", "Advanced", 4, 20, null);
            addExercise(checkStmt, insertStmt, "Mountain Climbers", "Advanced", 3, null, 60);

            addExercise(checkStmt, insertStmt, "Bodyweight Squats", "Beginner", 3, 15, null);
            addExercise(checkStmt, insertStmt, "Lunges", "Beginner", 3, 12, null);
            addExercise(checkStmt, insertStmt, "Mountain Climbers", "Beginner", 3, null, 30);

            addExercise(checkStmt, insertStmt, "Bodyweight Squats", "Intermediate", 3, 20, null);
            addExercise(checkStmt, insertStmt, "Lunges", "Intermediate", 3, 15, null);
            addExercise(checkStmt, insertStmt, "Mountain Climbers", "Intermediate", 3, null, 45);

            addExercise(checkStmt, insertStmt, "Bodyweight Squats", "Advanced", 4, 25, null);
            addExercise(checkStmt, insertStmt, "Lunges", "Advanced", 4, 20, null);
            addExercise(checkStmt, insertStmt, "Mountain Climbers", "Advanced", 3, null, 60);

            addExercise(checkStmt, insertStmt, "Glute Bridges", "Beginner", 3, 15, null);
            addExercise(checkStmt, insertStmt, "Donkey Kicks", "Beginner", 3, 12, null);
            addExercise(checkStmt, insertStmt, "Mountain Climbers", "Beginner", 3, null, 30);

            addExercise(checkStmt, insertStmt, "Glute Bridges", "Intermediate", 3, 20, null);
            addExercise(checkStmt, insertStmt, "Donkey Kicks", "Intermediate", 3, 15, null);
            addExercise(checkStmt, insertStmt, "Mountain Climbers", "Intermediate", 3, null, 45);

            addExercise(checkStmt, insertStmt, "Glute Bridges", "Advanced", 4, 25, null);
            addExercise(checkStmt, insertStmt, "Donkey Kicks", "Advanced", 4, 20, null);
            addExercise(checkStmt, insertStmt, "Mountain Climbers", "Advanced", 3, null, 60);

            insertStmt.executeBatch();

            System.out.println("Exercise data inserted successfully!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void addExercise(PreparedStatement checkStmt, PreparedStatement insertStmt, String exercise_name, String difficulty, int no_of_sets, Integer no_of_reps, Integer no_of_seconds) throws SQLException {
        checkStmt.setString(1, exercise_name);
        checkStmt.setString(2, difficulty);
        ResultSet rs = checkStmt.executeQuery();
        rs.next();
        int count = rs.getInt(1);
        if (count == 0) {
            insertStmt.setString(1, exercise_name);
            insertStmt.setString(2, difficulty);
            insertStmt.setInt(3, no_of_sets);
            if (no_of_reps != null) {
                insertStmt.setInt(4, no_of_reps);
            } else {
                insertStmt.setNull(4, java.sql.Types.INTEGER);
            }
            if (no_of_seconds != null) {
                insertStmt.setInt(5, no_of_seconds);
            } else {
                insertStmt.setNull(5, java.sql.Types.INTEGER);
            }
            insertStmt.addBatch();
        }
    }

}
