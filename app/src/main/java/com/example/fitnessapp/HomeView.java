package com.example.fitnessapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class HomeView extends AppCompatActivity {
    TextView greetings;

    public String username;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_view);

        Intent intent = getIntent();
        username = intent.getStringExtra("username");

        greetings = (TextView) findViewById(R.id.tvGreeting);
        greetings.setText(greetings.getText() + " " + username);
    }

    public void onEditProfileActivityClicked(View view) {
        Intent intent = new Intent(this, EditProfileActivity.class);
        startActivity(intent);
    }
    public void onWorkoutActivityClicked(View view) {
        Intent intent = new Intent(this, WorkoutActivity.class);
        startActivity(intent);
    }
    public void onNutritionActivityClicked(View view) {
        Intent intent = new Intent(this, NutritionActivity.class);
        startActivity(intent);
    }
    public void onProgressTrackingActivityClicked(View view) {
        Intent intent = new Intent(this, ProgressTrackingActivity.class);
        startActivity(intent);
    }
}