package com.example.fitnessapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;

public class StartDayActivity extends AppCompatActivity {
    ProgressBar progress;
    TextView exercise;
    TextView timer;

    public String username;
    protected int user_id;
    protected int currentDay, currentWeek;
    protected List<Exercise> exerciseList;

    int valProg = 0;
    int exerciseIndex = 0;
    Handler handler = new Handler(Looper.getMainLooper());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_day);
        progress = findViewById(R.id.progressBar);
        exercise = findViewById(R.id.tvExercise);
        timer = findViewById(R.id.tvTimer);

        username = Session.getUsername();
        user_id = Session.getUid();

        currentDay = getIntent().getIntExtra("currentDay", 0);
        currentWeek = getIntent().getIntExtra("currentWeek", 0);
        exerciseList = getIntent().getParcelableArrayListExtra("exercise_list");

        if (exerciseList != null) {
            Log.e("StartDayActivity", "Exercise list size: " + exerciseList.size());
            startExercise();
        }
    }

    private void startExercise() {
        if (exerciseIndex < exerciseList.size()) {
            Exercise currentExercise = exerciseList.get(exerciseIndex);
            exercise.setText(currentExercise.getExerciseName());
            int duration = currentExercise.getNoOfSeconds(); // Assuming duration is in seconds
            updateProgress(duration);
        } else {
            Log.e("StartDayActivity", "All exercises completed!");
            exercise.setText("All exercises completed!");
            progress.setProgress(100);
        }
    }

    private void updateProgress(int duration) {
        valProg = 0;
        progress.setMax(duration);

        handler.post(new Runnable() {
            int elapsedTime = 0;

            @Override
            public void run() {
                if (elapsedTime < duration) {
                    valProg++;
                    progress.setProgress(valProg);
                    timer.setText(String.format("%d", duration - elapsedTime));
                    elapsedTime++;
                    handler.postDelayed(this, 1000);
                } else {
                    restTime();
                }
            }
        });
    }

    private void restTime() {
        exercise.setText("Rest time!");
        int restDuration = 20; // 20 seconds rest

        handler.post(new Runnable() {
            int restElapsed = 0;

            @Override
            public void run() {
                if (restElapsed < restDuration) {
                    timer.setText(String.format("%d", restDuration - restElapsed));
                    restElapsed++;
                    handler.postDelayed(this, 1000);
                } else {
                    exerciseIndex++;
                    startExercise();
                }
            }
        });
    }

    public void playGhorl(View view) {
        valProg += 10;
        progress.setProgress(valProg);
    }

    public void onBackClicked(View view) {
        finish();
    }
}
