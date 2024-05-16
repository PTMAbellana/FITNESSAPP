package com.example.fitnessapp.registering;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.fitnessapp.LoginView;
import com.example.fitnessapp.R;
import com.example.fitnessapp.RegisterView;

public class Registering1Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registering1);
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