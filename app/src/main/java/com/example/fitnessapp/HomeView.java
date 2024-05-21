package com.example.fitnessapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Contacts;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class HomeView extends AppCompatActivity {
    TextView greetings;

    public String username;
    int uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_view);

//        uid = 1;
        Intent intent = getIntent();
        Log.e("TAWAG", "I was called! pero wa pay uid");
        uid = intent.getIntExtra("user_id", -1);
        Log.e("TAWAG", "I was called! uid is " + uid);
//        username = intent.getStringExtra("username");

        if (uid != 0) {
            new GetUsername().execute(uid);
        } else {
            Log.e("HomeView", "Error: Invalid user ID received");
            Toast.makeText(this, "Error: Invalid user ID", Toast.LENGTH_SHORT).show();
        }



//        btnProfile = findViewById(R.id.btnEditProfile);

    }

    public void onEditProfileActivityClicked(View view) {
        Intent intent = new Intent(HomeView.this, EditProfileActivity.class);
        intent.putExtra("user_id", uid);
//        intent.putExtra("username", username);
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
    private class GetUsername extends AsyncTask<Integer, Void, String> {
        private int uid;

        @Override
        protected String doInBackground(Integer... integers) {
            return ReadData.getUsername(integers[0]);
        }

        @Override
        protected void onPostExecute(String result){
            if (result != null) {
                username = result;
                greetings = findViewById(R.id.tvGreeting);
                greetings.setText(greetings.getText() + " " + username);
            } else {
                Log.e("HomeView", "Error: Username not found for user ID: " + uid);
                Toast.makeText(HomeView.this, "Error: Username not found", Toast.LENGTH_SHORT).show();
            }
        }
    }
}