package com.example.fitnessapp.onboarding;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.fitnessapp.R;
import com.example.fitnessapp.registering.Registering1Activity;

public class OnBoarding4Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_boarding4);
    }

    public void onCreateAccountClicked(View view) {
        Intent intent = new Intent(this, Registering1Activity.class);
        startActivity(intent);
    }

    public void onSkipClicked(View view) {
    }
}