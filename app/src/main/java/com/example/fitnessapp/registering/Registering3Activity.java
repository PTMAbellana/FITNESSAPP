package com.example.fitnessapp.registering;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.fitnessapp.R;

public class Registering3Activity extends AppCompatActivity {

    public String username;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registering3);

        Intent intent = getIntent();
        username = intent.getStringExtra("username");
    }

    public void onNextClicked(View view) {
        Intent intent = new Intent(this, Registering4Activity.class);
        intent.putExtra("username", username);
        startActivity(intent);
        finish();
    }
}