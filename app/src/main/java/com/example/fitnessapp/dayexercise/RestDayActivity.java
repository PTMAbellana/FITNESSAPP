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

public class RestDayActivity extends AppCompatActivity {
    TextView timer;
    Button playButton;
    Button pauseButton;

    protected String username;
    protected int user_id;
    protected int currentDay, currentWeek;
    protected int current_index;
    private final Handler handler = new Handler(Looper.getMainLooper());

    private int restElapsed = 0;
    private final int restDuration = 60;
    private boolean isPaused = false;
    private static final int BUTTON_DELAY = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rest_day);

        playButton = findViewById(R.id.btnPlay);
        pauseButton = findViewById(R.id.btnPause);

        timer = findViewById(R.id.tvTimer);

        username = Session.getUsername();
        user_id = Session.getUid();

        currentDay = getIntent().getIntExtra("currentDay", 0);
        currentWeek = getIntent().getIntExtra("currentWeek", 0);
        current_index = getIntent().getIntExtra("current_index", 0);

        startRestTime();
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
                    proceedEndExercise();
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

    public void onBackClicked(View view) {
        finish();
    }

    private void disableButtonsTemporarily() {
        playButton.setEnabled(false);
        pauseButton.setEnabled(false);

        handler.postDelayed(() -> {
            playButton.setEnabled(true);
            pauseButton.setEnabled(true);
        }, BUTTON_DELAY);
    }

    public void proceedEndExercise() {
        if (!isFinishing()) {
            Intent intent = new Intent(RestDayActivity.this, EndExerciseActivity.class);
            intent.putExtra("currentDay", currentDay);
            intent.putExtra("currentWeek", currentWeek);
            intent.putExtra("current_index", current_index);
            intent.putExtra("skipped_exercises", 0);
            startActivity(intent);
            finish();
        }
    }

    public void onSkipExerciseClicked(View view) {
        proceedEndExercise();
    }
}
