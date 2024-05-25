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
import java.lang.Runnable;


// Checked
public class LaunchActivity extends AppCompatActivity {
    private ConnectionClass connectionClass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);

        connectionClass = new ConnectionClass();

        connect(new ConnectionCallback() {
            @Override
            public void onConnectionSuccess(String successMessage) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(LaunchActivity.this, successMessage, Toast.LENGTH_SHORT).show();
                    }
                });

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                Intent intent = new Intent(LaunchActivity.this, OnBoarding1Activity.class);
                                startActivity(intent);
                                finish();
                            }
                        }, 3000);
                    }
                });
            }



            @Override
            public void onConnectionFailure(String errorMessage) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(LaunchActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
                    }
                });
            }

        });
    }

    private void connect(ConnectionCallback callback) {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() -> {
            try (Connection con = connectionClass.getConnection()) {
                if (con == null) {
                    callback.onConnectionFailure("Error in connection with MySQL server");
                } else {
                    CreateTable.createTables();
                    InsertData.insertAllExercisesData();
                    callback.onConnectionSuccess("Connected with MySQL server");
                }
            } catch (SQLException e) {
                callback.onConnectionFailure("Error in connection: " + e.getMessage());
            }
        });
    }



//    private void connect() {
//        ExecutorService executorService = Executors.newSingleThreadExecutor();
//        executorService.execute(() -> {
//            try (Connection con = connectionClass.getConnection()) {
//                if (con == null) {
//                    str = "Error in connection with MySQL server";
//                } else {
//                    str = "Connected with MySQL server";
//                    CreateTable.createTables();
//                    InsertData.insertAllExercisesData();
//                }
//            } catch (SQLException e) {
//                str = "Error in connection: " + e.getMessage();
//            }
//            runOnUiThread(() -> {
//                Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
//            });
//        });
//    }

    //    private String name, str;
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_launch);
//
//        connectionClass = new ConnectionClass();
//        connect();
//
//        handler = new Handler();
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                 Intent intent = new Intent(LaunchActivity.this, OnBoarding1Activity.class);
//                 startActivity(intent);
//                 finish();
//            }
//        }, 3000);
//    }
}