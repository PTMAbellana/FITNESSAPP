package com.example.fitnessapp;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UpdateData {

    public static boolean updatePlan(int uid, int plan){
        try(Connection c = ConnectionClass.getConnection();
            PreparedStatement statement = c.prepareStatement("UPDATE tblusers SET plan = ? WHERE user_id=?");
        ){
            switch (plan){
                case 0:
                    statement.setString(1, "Beginner");
                    break;
                case 1:
                    statement.setString(1, "Intermediate");
                    break;
                case 2:
                    statement.setString(1, "Advanced");
                    break;
            }
            statement.setInt(2, uid);
            return statement.executeUpdate() != 0;
        } catch (SQLException e){
            throw new RuntimeException();
        }
    }
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
