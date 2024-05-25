package com.example.fitnessapp.registering;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fitnessapp.R;
import com.example.fitnessapp.UpdateData;

public class Registering5Activity extends AppCompatActivity {

    public String username;
    protected int user_id;

    EditText tfAge, tfWeight, tfHeight;
    TextView lblCheck;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registering5);

        username = getIntent().getStringExtra("username");
        user_id = getIntent().getIntExtra("user_id", 0);

        tfAge = findViewById(R.id.tfAge);
        tfWeight = findViewById(R.id.tfWeight);
        tfHeight = findViewById(R.id.tfHeight);
    }

    public void onNextClicked(View view) {
        String ageStr = tfAge.getText().toString();
        String weightStr = tfWeight.getText().toString();
        String heightStr = tfHeight.getText().toString();

        if (ageStr.isEmpty() || weightStr.isEmpty() || heightStr.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        int age = Integer.parseInt(ageStr);
        double weight = Double.parseDouble(weightStr);
        double height = Double.parseDouble(heightStr);

        new UpdateInfoTask().execute(username, String.valueOf(age), String.valueOf(weight), String.valueOf(height));

        Intent intent = new Intent(this, Registering6Activity.class);
        intent.putExtra("username", username);
        intent.putExtra("user_id", user_id);
        startActivity(intent);
        finish();
    }

    public void onBackClicked(View view) {
        Intent intent = new Intent(this, Registering4Activity.class);
        intent.putExtra("username", username);
        intent.putExtra("user_id", user_id);
        startActivity(intent);
        finish();
    }

    private class UpdateInfoTask extends AsyncTask<String, Void, Boolean> {

        @Override
        protected Boolean doInBackground(String... params) {
            String username = params[0];
            int age = Integer.parseInt(params[1]);
            double weight = Double.parseDouble(params[2]);
            double height = Double.parseDouble(params[3]);

            return UpdateData.updateForBMI(user_id, age, weight, height);
        }
    }
}
