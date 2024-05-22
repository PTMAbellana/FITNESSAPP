package com.example.fitnessapp.registering;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.fitnessapp.LoginView;
import com.example.fitnessapp.R;
import com.example.fitnessapp.ReadData;

import org.w3c.dom.Text;

public class Registering2Activity extends AppCompatActivity {

    TextView greetings;
    public String username;
    protected int user_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registering2);

        Intent intent = getIntent();
        username = intent.getStringExtra("username");
//        int uid = intent.getIntExtra("user_id", 0);
        new GetSessionID().execute(username);
        greetings = (TextView) findViewById(R.id.tvStartJourneyGreetings);
        greetings.setText("Hello, " + username + "! \nWelcome to the journey \nto your dream body" );
//        uid = ReadData.getSession(username);
//        Log.e("Registering2Activity", "UID: " + uid);

    }

    public void onStartPersonalizationClicked(View view) {
        Intent intent = new Intent(this, Registering3Activity.class);
        intent.putExtra("username", username);
        intent.putExtra("user_id", user_id);
        startActivity(intent);
        finish();
    }

    private class GetSessionID extends AsyncTask<String, Void, Boolean>{

        @Override
        protected Boolean doInBackground(String... strings) {
            user_id = ReadData.getSession(strings[0]);
            return user_id != 0;
        }
    }

//    public void onRegisterClicked(View view) {
//        Intent intent = new Intent(this, Registering1Activity.class);
//        startActivity(intent);
//    }
}