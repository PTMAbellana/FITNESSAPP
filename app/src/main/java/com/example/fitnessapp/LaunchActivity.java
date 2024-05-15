package com.example.fitnessapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.fitnessapp.onboarding.OnBoarding1Activity;

public class LaunchActivity extends AppCompatActivity {
    Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);

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
}