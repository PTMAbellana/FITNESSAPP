package com.example.fitnessapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ProgressTrackingActivity extends AppCompatActivity {
    TextView tvName;
    TextView tvWeight;
    TextView tvHeight;
    TextView tvAge;
    private String username;
    private int uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress_tracking);
        username = Session.getUsername();
        uid = Session.getUid();

        tvName = (TextView) findViewById(R.id.tvName);
        tvWeight = (TextView) findViewById(R.id.tvWeight);
        tvHeight = (TextView) findViewById(R.id.tvHeight);
        tvAge = (TextView) findViewById(R.id.tvAge);

        new SetInfo().execute(uid);
    }
    public void onBackClicked(View view) {
//        Intent intent = new Intent(this, MainActivity.class);
//        startActivity(intent);
        finish();
    }

    private class SetInfo extends AsyncTask<Integer, Void, ResultSet>{

        @Override
        protected ResultSet doInBackground(Integer... ints) {
            return ReadData.getProfile(ints[0]);
        }

        @Override
        protected void onPostExecute(ResultSet res) {
            try {
                if (res.next()){
                    tvName.setText(res.getString("name"));
                    tvWeight.setText(String.valueOf(res.getString("weight")) + " kg");
                    tvHeight.setText(String.valueOf(res.getString("height") + " cm"));
                    tvAge.setText(res.getString("age"));
                } else {
                    Log.e("TAG", "NO PROFILE");
                }
            } catch (SQLException e){
                throw new RuntimeException(e);
            } finally {
                try{
                    res.close();
                } catch (SQLException r){
                    r.printStackTrace();
                }
            }
        }
    }
}