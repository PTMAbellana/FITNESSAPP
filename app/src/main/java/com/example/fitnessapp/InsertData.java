package com.example.fitnessapp;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import org.mindrot.jbcrypt.BCrypt;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class InsertData {

    public static void insertData(Context context, String name, String email, String username, String password, String gender) {
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
        new InsertDataTask(context, name, email, username, hashedPassword, gender).execute();
    }

    private static class InsertDataTask extends AsyncTask<Void, Void, Boolean> {
        private final Context context;
        private final String name;
        private final String email;
        private final String username;
        private final String password;
        private final String gender;

        public InsertDataTask(Context context, String name, String email, String username, String password, String gender) {
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

            if (!CreateTable.usersTable()){
                CreateTable.createTblUsers();
            }

            try {
                PreparedStatement statement = c.prepareStatement(
                        "INSERT INTO tblUsers (name, email, username, password, gender) VALUES (?,?,?,?,?)"
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
        }

        @Override
        protected void onPostExecute(Boolean success) {
            if (success) {
                showToast(context, "User inserted successfully!");
            } else {
                showToast(context, "Failed to insert user. Please check your network connection.");
            }
        }
    }

    private static void showToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }
}
