package com.example.fitnessapp.onboarding;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.fitnessapp.R;
import com.example.fitnessapp.registering.Registering1Activity;

// Checked
public class OnBoarding2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_boarding2);
    }

    public void onNextClicked(View view) {
        Intent intent = new Intent(this, OnBoarding3Activity.class);
        startActivity(intent);
        finish();
    }

    public void onSkipClicked(View view) {
        Intent intent = new Intent(this, Registering1Activity.class);
        startActivity(intent);
        finish();
    }
}