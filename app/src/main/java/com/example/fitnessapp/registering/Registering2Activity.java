package com.example.fitnessapp.registering;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.fitnessapp.R;
import com.example.fitnessapp.crud.ReadData;
import com.example.fitnessapp.Session;

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
        new GetSessionID().execute(username); // Start AsyncTask to get user_id
        greetings = findViewById(R.id.tvStartJourneyGreetings);
    }

    public void onStartPersonalizationClicked(View view) {
        Intent intent = new Intent(this, Registering3Activity.class);
        intent.putExtra("username", username);
        intent.putExtra("user_id", user_id);
        Session s = new Session(user_id, username);
        s.setUid(user_id);
        s.setUsername(username);
        startActivity(intent);
        finish();
    }

    private class GetSessionID extends AsyncTask<String, Void, Integer>{
        @Override
        protected Integer doInBackground(String... strings) {
            return ReadData.getSession(strings[0]);
        }

        @Override
        protected void onPostExecute(Integer result) {
            super.onPostExecute(result);
            user_id = result;
            greetings.setText("Hello, " + username + "! \nWelcome to the journey \nto your dream body" );
        }
    }
}