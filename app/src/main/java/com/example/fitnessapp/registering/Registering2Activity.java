package com.example.fitnessapp.registering;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.fitnessapp.LoginView;
import com.example.fitnessapp.R;

import org.w3c.dom.Text;

public class Registering2Activity extends AppCompatActivity {

    TextView greetings;
    public String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registering2);

        Intent intent = getIntent();
//        username = intent.getStringExtra("username");
        int uid = intent.getIntExtra("user_id", 0);

        greetings = (TextView) findViewById(R.id.tvStartJourneyGreetings);
        greetings.setText("Hello, " + username + "! \nWelcome to the journey \nto your dream body" );
        Log.e("Registering2Activity", "UID: " + uid);

    }

    public void onStartPersonalizationClicked(View view) {
        Intent intent = new Intent(this, Registering3Activity.class);
        intent.putExtra("username", username);
        startActivity(intent);
        finish();
    }
//    public void onRegisterClicked(View view) {
//        Intent intent = new Intent(this, Registering1Activity.class);
//        startActivity(intent);
//    }
}