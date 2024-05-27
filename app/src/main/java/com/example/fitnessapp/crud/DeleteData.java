package com.example.fitnessapp.crud;

import com.example.fitnessapp.ConnectionClass;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DeleteData {
    public static boolean updateStatus(int user_id) {
        try (Connection c = ConnectionClass.getConnection();
             PreparedStatement statement = c.prepareStatement(
                     "UPDATE tblusers SET status = ? WHERE user_id = ?"
             )) {
            statement.setString(1, "deactivated");
            statement.setInt(2, user_id);
            return statement.executeUpdate() != 0;
        } catch (SQLException e) {
            e.printStackTrace();
            try (Connection c = ConnectionClass.getConnection()) {
                c.rollback();
            } catch (SQLException rollbackException) {
                rollbackException.printStackTrace();
            }
        }
        return false;
    }
}
