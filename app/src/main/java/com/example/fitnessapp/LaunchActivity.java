package com.example.fitnessapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import com.example.fitnessapp.onboarding.OnBoarding1Activity;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class LaunchActivity extends AppCompatActivity {
    private ConnectionClass connectionClass;
    private String name, str;
    Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);

        connectionClass = new ConnectionClass();
        connect();

        handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                 Intent intent = new Intent(LaunchActivity.this, OnBoarding1Activity.class);
                 startActivity(intent);
                 finish();
            }
        }, 3000);
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
}