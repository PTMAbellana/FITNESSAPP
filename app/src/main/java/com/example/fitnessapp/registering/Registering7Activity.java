package com.example.fitnessapp.registering;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import com.example.fitnessapp.HomeViewActivity;
import com.example.fitnessapp.R;

public class Registering7Activity extends AppCompatActivity {
    public String username;
    protected int user_id;

    Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registering7);

        Intent intent = getIntent();
        username = getIntent().getStringExtra("username");
        user_id = getIntent().getIntExtra("user_id", 0);

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
}