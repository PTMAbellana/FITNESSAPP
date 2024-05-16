package com.example.fitnessapp.onboarding;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.fitnessapp.R;

public class OnBoarding3Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_boarding3);
    }

    public void onNextClicked(View view) {
        Intent intent = new Intent(this, OnBoarding4Activity.class);
        startActivity(intent);
    }
}