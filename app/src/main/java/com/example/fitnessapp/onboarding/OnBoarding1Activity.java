package com.example.fitnessapp.onboarding;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fitnessapp.LoginViewActivity;
import com.example.fitnessapp.R;

// Checked
public class OnBoarding1Activity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_boarding1);
    }

    public void onGetStartedClicked(View view) {
        Intent intent = new Intent(this, OnBoarding2Activity.class);
        startActivity(intent);
        finish();
    }

    public void onLoginClicked(View view) {
        Intent intent = new Intent(this, LoginViewActivity.class);
        startActivity(intent);
        finish();
    }
}