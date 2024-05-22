package com.example.fitnessapp;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UpdateData {

    public static boolean updatePlan(int user_id, int plan){
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
            statement.setInt(2, user_id);
            return statement.executeUpdate() != 0;
        } catch (SQLException e){
            throw new RuntimeException();
        }
    }
    public static boolean updateNumPushups(int user_id, int numPushups) {
        try (Connection c = ConnectionClass.getConnection();
             PreparedStatement statement = c.prepareStatement(
                     "UPDATE tblusers SET num_pushups = ? WHERE user_id = ?"
             )) {
            switch (numPushups){
                case 0:
                    statement.setString(1, "0");
                    break;
                case 1:
                    statement.setString(1, "5");
                    break;
                case 2:
                    statement.setString(1, "10");
                    break;
                case 3:
                    statement.setString(1, "20");
                    break;
                case 4:
                    statement.setString(1, "21");
                    break;

            }
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
