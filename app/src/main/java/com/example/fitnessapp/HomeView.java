package com.example.fitnessapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.fitnessapp.day.Day1;

public class HomeView extends AppCompatActivity {
    TextView greetings;
    ImageButton btnProfile;

    public String username;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_view);

        Intent intent = getIntent();
        username = intent.getStringExtra("username");

        greetings = (TextView) findViewById(R.id.tvGreeting);
        greetings.setText(greetings.getText() + " " + username);

//        btnProfile = findViewById(R.id.btnEditProfile);

    }

    public void onEditProfileActivityClicked(View view) {
        Intent intent = new Intent(HomeView.this, EditProfileActivity.class);
        intent.putExtra("username", username);
        startActivity(intent);

    }
    public void onWorkoutActivityClicked(View view) {
        Intent intent = new Intent(HomeView.this, WorkoutActivity.class);
        startActivity(intent);
    }
    public void onNutritionActivityClicked(View view) {
        Intent intent = new Intent(HomeView.this, NutritionActivity.class);
        startActivity(intent);
    }
    public void onProgressTrackingActivityClicked(View view) {
        Intent intent = new Intent(HomeView.this, ProgressTrackingActivity.class);
        startActivity(intent);
    }
    public void onStartDay1Clicked(View view) {
        Intent intent = new Intent(this, Day1.class);
        startActivity(intent);
    }
}