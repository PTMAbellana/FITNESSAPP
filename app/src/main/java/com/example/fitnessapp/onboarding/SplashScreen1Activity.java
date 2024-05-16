package com.example.fitnessapp.onboarding;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fitnessapp.LoginView;
import com.example.fitnessapp.R;
import com.example.fitnessapp.RegisterView;

public class SplashScreen1Activity extends AppCompatActivity {
    Button getStarted;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen1);
    }
    public void onLoginClicked(View view) {
        Intent intent = new Intent(this, LoginView.class);
        startActivity(intent);
    }
    public void onRegisterClicked(View view) {
        Intent intent = new Intent(this, RegisterView.class);
        startActivity(intent);
    }
}
