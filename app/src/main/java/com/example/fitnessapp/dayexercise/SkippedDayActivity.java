package com.example.fitnessapp.dayexercise;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.fitnessapp.Exercise;
import com.example.fitnessapp.HomeViewActivity;
import com.example.fitnessapp.R;
import com.example.fitnessapp.Session;
import com.example.fitnessapp.crud.UpdateData;

import java.util.ArrayList;
import java.util.List;

public class SkippedDayActivity extends AppCompatActivity {

    public String username;
    protected int user_id;
    protected int currentDay, currentWeek;
    protected int skipped_exercises;
    TextView tvDay;
    TextView tvSkippedExercises;

    protected List<Exercise> exerciseList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_skipped_day);
        user_id = Session.getUid();
        username = Session.getUsername();

        // wala nmn guro ni nagamit
        currentDay = getIntent().getIntExtra("currentDay", 0);
        currentWeek = getIntent().getIntExtra("currentWeek", 0);
        skipped_exercises = getIntent().getIntExtra("skipped_exercises", 0);

        Log.e("EndExerciseActivity", "I was called in EndExerciseActivity! " + user_id + " " + username + " " + currentDay + " " + currentWeek + " " + skipped_exercises);

        tvDay = findViewById(R.id.tvDay);
        tvDay.setText("DAY " + currentDay);

        exerciseList = getIntent().getParcelableArrayListExtra("exercise_list");
        if (exerciseList != null) {
            Log.e("EndExerciseActivity", "Naay Sulod");
            for (Exercise exercise : exerciseList) {
                Log.e("EndExerciseActivity", exercise.getExerciseName());
            }
        }

        addExercisesInLayout();

        new ExecuteUpdateDayWeekPlan().execute(user_id);
    }

    public void onCheckDayClicked(View view) {
        Intent intent = new Intent(SkippedDayActivity.this, HomeViewActivity.class);
        intent.putParcelableArrayListExtra("exercise_list", new ArrayList<>(exerciseList));
        startActivity(intent);
        finish();
    }

    private void addExercisesInLayout() {
        LinearLayout exerciseContainer = findViewById(R.id.exerciseLayouyList);
        if (exerciseContainer == null) {
            Log.e("DayActivity", "Exercise container is null");
            return;
        }

        for (Exercise exercise : exerciseList) {
            ImageView imageView = new ImageView(this);

            String drawableName = exercise.getExerciseName().toLowerCase().replace(" ", "").replace("-", "");
            int drawableResourceId = getResources().getIdentifier(drawableName + "_p", "drawable", getPackageName());

            if (drawableResourceId != 0) {
                imageView.setImageResource(drawableResourceId);
                imageView.setLayoutParams(new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        (int) getResources().getDisplayMetrics().density * 110
                ));
                imageView.setPadding(5, 5, 5, 5);

                exerciseContainer.addView(imageView);
            } else {
                Log.e("DayActivity", "Walay png: " + exercise.getExerciseName());
            }
        }
    }

    private class ExecuteUpdateDayWeekPlan extends AsyncTask<Integer, Void, Boolean> {
        @Override
        protected Boolean doInBackground(Integer... integers) {
            return UpdateData.updateDayWeekPlan(integers[0]);
        }
    }
}