package com.example.fitnessapp.dayexercise;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.fitnessapp.Exercise;
import com.example.fitnessapp.R;
import com.example.fitnessapp.Session;

import java.util.ArrayList;
import java.util.List;

public class RestActivity extends AppCompatActivity {
    TextView exercise;
    TextView level;
    TextView timer;

    Button playButton;
    Button pauseButton;
    Button skipButton;

    protected String username;
    protected int user_id;
    protected int currentDay, currentWeek;
    protected int current_index;
    protected int skipped_exercises;
    protected int num_reps, num_seconds;

    protected List<Exercise> exerciseList;

    private final Handler handler = new Handler(Looper.getMainLooper());

    private int restElapsed = 0;
    private final int restDuration = 20;
    private boolean isPaused = false;
    private static final int BUTTON_DELAY = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rest);

        exercise = findViewById(R.id.tvExercise);
        level = findViewById(R.id.tvDifficulty);
        timer = findViewById(R.id.tvTimer);

        playButton = findViewById(R.id.btnPlay);
        pauseButton = findViewById(R.id.btnPause);
        skipButton = findViewById(R.id.btnSkip);

        username = Session.getUsername();
        user_id = Session.getUid();

        currentDay = getIntent().getIntExtra("currentDay", 0);
        currentWeek = getIntent().getIntExtra("currentWeek", 0);
        current_index = getIntent().getIntExtra("current_index", 0);
        skipped_exercises = getIntent().getIntExtra("skipped_exercises", 0);

        Log.i("RestActivity", "Current Exercise Index: " + current_index);
        exerciseList = getIntent().getParcelableArrayListExtra("exercise_list");

        if (exerciseList != null) {
            Log.i("RestActivity", "Exercise list size: " + exerciseList.size());
            startRestTime();
        }

        Exercise currentExercise = null;
        if (exerciseList != null) {
            currentExercise = exerciseList.get(current_index);
        }

        if (currentExercise != null) {
            num_reps = currentExercise.getNoOfReps();
            Log.i("RestActivity", "no of reps: " + num_reps);
        }

        if (currentExercise != null) {
            num_seconds = currentExercise.getNoOfSeconds();
            Log.i("RestActivity", "no of seconds: " + num_seconds);
        }

        String exerciseName = null;
        if (currentExercise != null) {
            exerciseName = currentExercise.getExerciseName();
        }

        String nextExerciseString = "Next: " + exerciseName;
        exercise.setText(nextExerciseString);

        String level_difficulty = "Level: " + currentExercise.getDifficulty();
        level.setText(level_difficulty);

        String drawableName = null;
        if (exerciseName != null) {
            drawableName = exerciseName.toLowerCase().replace(" ", "").replace("-", "");
        }

        String gifName = drawableName + "_g";

        int gifResourceId = getResources().getIdentifier(gifName, "drawable", getPackageName());

        if (gifResourceId != 0) {
            ImageView imageViewGif = findViewById(R.id.imageViewGif);
            Glide.with(this).asGif().load(gifResourceId).into(imageViewGif);
        } else {
            Log.e("DayActivity", "Drawable resource not found for GIF: " + gifName);
        }
    }

    public void startRestTime() {
        handler.post(new Runnable() {
            @Override
            public void run() {
                if (!isPaused && restElapsed < restDuration) {
                    String time = String.format("%d seconds", restDuration - restElapsed);
                    timer.setText(time);
                    restElapsed++;
                    handler.postDelayed(this, 1000);
                } else if (restElapsed >= restDuration) {
                    if (num_reps == 0) {
                        proceedNextExerciseWithpg();
                    } else {
                        proceedNextExerciseWithtv();
                    }
                }
            }
        });
    }

    public void onPlayClicked(View view) {
        isPaused = false;
        startRestTime();
        disableButtonsTemporarily();
    }

    public void onPauseClicked(View view) {
        isPaused = true;
        handler.removeCallbacksAndMessages(null);
        disableButtonsTemporarily();
    }

    public void onSkipClicked(View view) {
        if (num_reps == 0) {
            proceedNextExerciseWithpg();
        } else {
            proceedNextExerciseWithtv();
        }
    }

    public void onBackClicked(View view) {
        finish();
    }

    private void disableButtonsTemporarily() {
        playButton.setEnabled(false);
        pauseButton.setEnabled(false);
        skipButton.setEnabled(false);

        handler.postDelayed(() -> {
            playButton.setEnabled(true);
            pauseButton.setEnabled(true);
            skipButton.setEnabled(true);
        }, BUTTON_DELAY);
    }

    public void proceedNextExerciseWithpg() {
        if (!isFinishing()) {
            Intent intent = new Intent(RestActivity.this, StartExerciseWithpgActivity.class);
            intent.putExtra("currentDay", currentDay);
            intent.putExtra("currentWeek", currentWeek);
            intent.putExtra("current_index", current_index);
            intent.putExtra("skipped_exercises", skipped_exercises);
            intent.putParcelableArrayListExtra("exercise_list", new ArrayList<>(exerciseList));
            startActivity(intent);
            finish();
        }
    }

    public void proceedNextExerciseWithtv() {
        if (!isFinishing()) {
            Intent intent = new Intent(RestActivity.this, StartExerciseWithtvActivity.class);
            intent.putExtra("currentDay", currentDay);
            intent.putExtra("currentWeek", currentWeek);
            intent.putExtra("current_index", current_index);
            intent.putExtra("skipped_exercises", skipped_exercises);
            intent.putParcelableArrayListExtra("exercise_list", new ArrayList<>(exerciseList));
            startActivity(intent);
            finish();
        }
    }
}
