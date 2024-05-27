package com.example.fitnessapp.registering;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;

import com.example.fitnessapp.HomeViewActivity;
import com.example.fitnessapp.crud.InsertData;
import com.example.fitnessapp.R;
import com.example.fitnessapp.crud.ReadData;

public class Registering7Activity extends AppCompatActivity {
    public String username;
    protected int user_id;

    Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registering7);

        username = getIntent().getStringExtra("username");
        user_id = getIntent().getIntExtra("user_id", 0);

        new CreatePlanTask().execute();

        handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(Registering7Activity.this, HomeViewActivity.class);
                intent.putExtra("username", username);
                intent.putExtra("user_id", user_id);
                startActivity(intent);
                finish();
            }
        }, 3000);
    }

    // AsyncTask (thread) to create the plan
    private class CreatePlanTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            double bmi = calculateBMI();
            String currentDifficulty = getCurrentDifficulty(bmi);
            String targetDifficulty = getTargetDifficulty();

            InsertData.insertPlan(user_id, currentDifficulty, targetDifficulty, 1, 1, bmi);
            return null;
        }
    }

    private double calculateBMI() {
        double bmi = ReadData.getBMI(user_id);
        return bmi;
    }

    private String getCurrentDifficulty(double bmi) {
        if (bmi < 18.5 || bmi >= 25.0 ) {
            return "Beginner";
        } else {
            return ReadData.getPlan(user_id);
        }
    }

    private String getTargetDifficulty() {
        return ReadData.getPlan(user_id);
    }
}