package com.example.fitnessapp.dayexercise;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
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

public class StartExerciseWithtvActivity extends AppCompatActivity {

    TextView exercise;
    TextView level;
    TextView reps;
    Button doneButton;
    Button skipButton;

    public String username;
    protected int user_id;
    protected int currentDay, currentWeek;
    protected int current_index;
    protected int skipped_exercises;
    protected List<Exercise> exerciseList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_exercise_withtv);

        exercise = findViewById(R.id.tvExercise);
        level = findViewById(R.id.tvDifficulty);
        reps = findViewById(R.id.tvReps);

        doneButton = findViewById(R.id.btnDone);
        skipButton = findViewById(R.id.btnSkip);

        username = Session.getUsername();
        user_id = Session.getUid();

        currentDay = getIntent().getIntExtra("currentDay", 0);
        currentWeek = getIntent().getIntExtra("currentWeek", 0);
        current_index = getIntent().getIntExtra("current_index", 0);
        skipped_exercises = getIntent().getIntExtra("skipped_exercises", 0);


        Log.i("StartExerciseWithtvActivity", "Current Exercise Index: " + current_index);
        exerciseList = getIntent().getParcelableArrayListExtra("exercise_list");
        if (exerciseList != null) {
            Log.i("StartExerciseWithtvActivity", "Exercise list size: " + exerciseList.size());
        }

        Exercise currentExercise = null;
        if (exerciseList != null) {
            currentExercise = exerciseList.get(current_index);
        }

        if (currentExercise != null) {
            reps.setText(currentExercise.getNoOfReps() + " reps");
        }

        String exerciseName = null;
        if (currentExercise != null) {
            exerciseName = currentExercise.getExerciseName();
        }

        String nextExerciseString = "Next: " + exerciseName;
        exercise.setText(nextExerciseString);

        String level_difficulty = null;
        if (currentExercise != null) {
            level_difficulty = "Level: " + currentExercise.getDifficulty();
        }
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
            Log.e("StartExerciseWithtvActivity", "Drawable resource not found for GIF: " + gifName);
        }

    }

    public void onDoneClicked(View view) {
        proceedRest();
    }

    public void onSkipClicked(View view) {
        skipped_exercises++;
        proceedRest();
    }

    public void onBackClicked(View view) {
        finish();
    }

    public void onSkipExerciseClicked(View view) {
        if (!isFinishing()) {
            Intent intent = new Intent(StartExerciseWithtvActivity.this, SkippedDayActivity.class);
            intent.putExtra("currentDay", currentDay);
            intent.putExtra("currentWeek", currentWeek);
            intent.putExtra("current_index", current_index);
            intent.putExtra("skipped_exercises", skipped_exercises);
            intent.putParcelableArrayListExtra("exercise_list", new ArrayList<>(exerciseList));
            startActivity(intent);
            finish();
        }
    }

    public void proceedRest() {
        Exercise currentExercise = exerciseList.get(current_index);
        currentExercise.setNoOfSets(currentExercise.getNoOfSets() - 1);

        boolean allExercisesCompleted = true;

        for (int i = 0; i < exerciseList.size(); i++) {
            int index = (current_index + 1 + i) % exerciseList.size();
            if (exerciseList.get(index).getNoOfSets() > 0) {
                current_index = index;
                allExercisesCompleted = false;
                break;
            }
        }

        if (allExercisesCompleted) {
            proceedEnd();
        } else {
            if (!isFinishing()) {
                Intent intent = new Intent(StartExerciseWithtvActivity.this, RestActivity.class);
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

    public void proceedEnd() {
        if (!isFinishing()) {
            Intent intent = new Intent(StartExerciseWithtvActivity.this, EndExerciseActivity.class);
            intent.putExtra("currentDay", currentDay);
            intent.putExtra("currentWeek", currentWeek);
            intent.putExtra("skipped_exercises", skipped_exercises);
            intent.putParcelableArrayListExtra("exercise_list", new ArrayList<>(exerciseList));
            startActivity(intent);
            finish();
        }
    }
}