package com.example.fitnessapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;

public class StartDayActivity extends AppCompatActivity {
    //wait lng,, karun pa nako nakita ang StartDayActivity
    ProgressBar progress;
    TextView exercise;
    TextView timer;

    public String username;
    protected int user_id;
    protected int currentDay, currentWeek;
    protected List<Exercise> exerciseList;

    int valProg = 0; //for testing onleh
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_day);
        progress = (ProgressBar) findViewById(R.id.progressBar);
        exercise = (TextView) findViewById(R.id.tvExercise);
        timer = (TextView) findViewById(R.id.tvTimer);

        username = Session.getUsername();
        user_id = Session.getUid();

        currentDay = getIntent().getIntExtra("currentDay", 0);
        currentWeek = getIntent().getIntExtra("currentWeek", 0);
        exerciseList = getIntent().getParcelableArrayListExtra("exercise_list");

        Log.e("StartDayActivity", "I was called! Exercise list size : " + exerciseList.size());
        Log.e("StartDayActivity", "Part 2! List of all exercises received: ");
        if (exerciseList != null) {
            Log.e("TAWAG", "Naay Sulod");
            for (Exercise exercise : exerciseList) {
                Log.e("TAWAG", exercise.getExerciseName());
            }
        }
    }

    private void yourProgress(){
        progress.setProgress(valProg);
    }

    public void playGhorl(View view){
//        valProg+=10;
        yourProgress();
    }

    public void onBackClicked(View view) {
//        Intent intent = new Intent(this, MainActivity.class);
//        startActivity(intent);
        finish();
    }
}