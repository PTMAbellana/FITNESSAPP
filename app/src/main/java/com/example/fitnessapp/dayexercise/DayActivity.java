package com.example.fitnessapp.dayexercise;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fitnessapp.Exercise;
import com.example.fitnessapp.HomeViewActivity;
import com.example.fitnessapp.R;
import com.example.fitnessapp.Session;

import java.util.ArrayList;
import java.util.List;

public class DayActivity extends AppCompatActivity {
    public String username;
    protected int user_id;
    protected int currentDay, currentWeek;
    TextView tvDay;

    protected List<Exercise> exerciseList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_day);
//        user_id = getIntent().getIntExtra("user_id", 0);
//        username = getIntent().getStringExtra("username");
        user_id = Session.getUid();
        username = Session.getUsername();
        currentDay = getIntent().getIntExtra("currentDay", 0);
        currentWeek = getIntent().getIntExtra("currentWeek", 0);

        Log.e("TAWAG", "I was called in DayActivity! " + user_id + " " + username + " " + currentDay + " " + currentWeek);

        tvDay = findViewById(R.id.tvDay);
        tvDay.setText("DAY " + currentDay);

        exerciseList = getIntent().getParcelableArrayListExtra("exercise_list");
        if (exerciseList != null) {
            Log.i("DayActivity", "Not Empty");
            for (Exercise exercise : exerciseList) {
                Log.e("DayActivity", exercise.getExerciseName());
            }
        }

        addExercisesInLayout();
    }
    public void onBackClicked(View view) {
        Intent intent = new Intent(DayActivity.this, HomeViewActivity.class);
        startActivity(intent);
        finish();
    }

    public void onStartDayClicked(View view) {
        Intent intent = new Intent(DayActivity.this, RestActivity.class);
        intent.putExtra("currentDay", currentDay);
        intent.putExtra("currentWeek", currentWeek);
        intent.putExtra("current_index", 0);
        intent.putExtra("skipped_exercises", 0);
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
                Log.e("DayActivity", "No png: " + exercise.getExerciseName());
            }
        }
    }
}