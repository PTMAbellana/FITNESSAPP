package com.example.fitnessapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {
    private ConnectionClass connectionClass;
    private String name, str;

    @Override   
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        connectionClass = new ConnectionClass();
        connect();
    }   

    private void connect() {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() -> {
            try (Connection con = connectionClass.getConnection()) {
                if (con == null) {
                    str = "Error in connection with MySQL server";
                } else {
                    str = "Connected with MySQL server";
                    CreateTable.createTblUsers();
                }
            } catch (SQLException e) {
                str = "Error in connection: " + e.getMessage();
            }
            runOnUiThread(() -> {
                Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
            });
        });
    }

//    public void btnClick(View view) {
//        ExecutorService executorService = Executors.newSingleThreadExecutor();
//        executorService.execute(() -> {
//            StringBuilder bStr = new StringBuilder("Userlist! \n");
//            try (Connection con = connectionClass.getConnection()) {
//                if (con != null) {
//                    String query = "SELECT * FROM tblUsers"; // Ensure this table name is correct
//                    PreparedStatement stmt = con.prepareStatement(query);
//                    ResultSet rs = stmt.executeQuery();
//                    while (rs.next()) {
//                        bStr.append("Username: ").append(rs.getString("username")).append("\n");
//                    }
//                } else {
//                    bStr.append("Failed to connect to the database.");
//                }
//            } catch (SQLException e) {
//                bStr.append("Error: ").append(e.getMessage());
//            }
//
//            String userList = bStr.toString();
//            runOnUiThread(() -> {
//                TextView txtlist = findViewById(R.id.textview);
//                txtlist.setText(userList);
//            });
//        });
//    }

    public void onRegisterClicked(View view) {
        Intent intent = new Intent(this, RegisterView.class);
        startActivity(intent);
    }

    public void onLoginClicked(View view) {
        Intent intent = new Intent(this, LoginView.class);
        startActivity(intent);
    }
}
