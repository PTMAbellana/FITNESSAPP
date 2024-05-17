package com.example.fitnessapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.fitnessapp.registering.Registering1Activity;

public class MainActivity extends AppCompatActivity {
    private ConnectionClass connectionClass;
    private String name, str;

    @Override   
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
        Intent intent = new Intent(this, Registering1Activity.class);
        startActivity(intent);
    }

    public void onLoginClicked(View view) {
        Intent intent = new Intent(this, LoginView.class);
        startActivity(intent);
    }

}
