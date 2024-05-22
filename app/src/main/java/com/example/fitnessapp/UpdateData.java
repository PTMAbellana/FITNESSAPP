package com.example.fitnessapp;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UpdateData {
    public static void updateNumPushups(String username, int numPushups) {
        try (Connection c = ConnectionClass.getConnection();
             PreparedStatement statement = c.prepareStatement(
                     "UPDATE tblusers SET num_pushups = ? WHERE username = ?"
             )) {
            c.setAutoCommit(false);

            statement.setInt(1, numPushups);
            statement.setString(2, username);

            statement.executeUpdate();
            System.out.println("Successfully updated num_pushups: " + username);

            c.commit();
        } catch (SQLException e) {
            e.printStackTrace();

            try (Connection c = ConnectionClass.getConnection()) {
                c.rollback();
            } catch (SQLException rollbackException) {
                rollbackException.printStackTrace();
            }
        }
    }
}
