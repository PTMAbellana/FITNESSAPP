package com.example.fitnessapp;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UpdateData {

    public static boolean updatePlan(int user_id, int planSelected){
        try(Connection c = ConnectionClass.getConnection();
            PreparedStatement statement = c.prepareStatement("UPDATE tblusers SET plan = ? WHERE user_id=?");
        ){
            switch (planSelected){
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
    public static boolean updateNumPushups(int user_id, int numPushupsSelected) {
        try (Connection c = ConnectionClass.getConnection();
             PreparedStatement statement = c.prepareStatement(
                     "UPDATE tblusers SET num_pushups = ? WHERE user_id = ?"
             )) {
            switch (numPushupsSelected){
                case 0:
                    statement.setInt(1, 0);
                    break;
                case 1:
                    statement.setInt(1, 5);
                    break;
                case 2:
                    statement.setInt(1, 10);
                    break;
                case 3:
                    statement.setInt(1, 20);
                    break;
                case 4:
                    statement.setInt(1, 21);
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

    public static boolean updateForBMI(int user_id, int age, double weight, double height) {
        try (Connection c = ConnectionClass.getConnection();
             PreparedStatement statement = c.prepareStatement(
                     "UPDATE tblusers SET age = ?, weight = ?, height = ? WHERE user_id = ?"
             )) {
            statement.setInt(1, age);
            statement.setDouble(2, weight);
            statement.setDouble(3, height);
            statement.setInt(4, user_id);
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

    public static boolean updateNumPlanks(int user_id, int numPlanksSelected) {
        try (Connection c = ConnectionClass.getConnection();
             PreparedStatement statement = c.prepareStatement(
                     "UPDATE tblusers SET num_planks = ? WHERE user_id = ?"
             )) {
            switch (numPlanksSelected){
                case 0:
                    statement.setInt(1, 0);
                    break;
                case 1:
                    statement.setInt(1, 30);
                    break;
                case 2:
                    statement.setInt(1, 60);
                    break;
                case 3:
                    statement.setInt(1, 120);
                    break;
                case 4:
                    statement.setInt(1, 121);
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
