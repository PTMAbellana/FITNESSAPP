package com.example.fitnessapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.sql.ResultSet;
import java.sql.SQLException;

public class EditProfileActivity extends AppCompatActivity {

    Button update;
    TextView tvName, tvEmail, tvWeight, tvHeight, tvAge;
    EditText tfName, tfEmail, tfUsername, tfWeight, tfHeight;
    String username;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        update = (Button) findViewById(R.id.btnEditProfile);

        tvName = (TextView) findViewById(R.id.tvName);
        tvEmail = (TextView) findViewById(R.id.tvEmail);
        tvWeight = (TextView) findViewById(R.id.tvWeight);
        tvHeight = (TextView) findViewById(R.id.tvHeight);
        tvAge = (TextView) findViewById(R.id.tvAge);

        tfName = (EditText) findViewById(R.id.tfName);
        tfEmail = (EditText) findViewById(R.id.tfEmail);
        tfUsername = (EditText) findViewById(R.id.tfUsername);
        tfWeight = (EditText) findViewById(R.id.tfWeight);
        tfHeight = (EditText) findViewById(R.id.tfHeight);


        Intent intent = getIntent();
        username = intent.getStringExtra("username");
        new GetProfileInfo().execute(username);
//        try ( ResultSet userProfile = ReadData.getProfile(username) ){
//            if (userProfile.next()){
//                tvName.setText(userProfile.getString("name"));
//                tvEmail.setText(userProfile.getString("email"));
//                tvHeight.setText(userProfile.getString("height"));
//                tvWeight.setText(userProfile.getString("weight"));
////                tvAge.setText(userProfile.getString("age"));
//            } else {
//                Log.e("TAG", "NO PROFILE");
//            }
//        } catch (SQLException e){
//            throw new RuntimeException(e);
//        }

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateProfile();
            }
        });
    }

    protected void updateProfile(){
        String name = tfName.getText().toString();
        String email = tfEmail.getText().toString();
        String username = tfUsername.getText().toString();
        String weight = tfWeight.getText().toString();
        String height = tfHeight.getText().toString();

        //if it doesnt require all fields
//        new UpdateName().execute(name);
//        new UpdateEmail().execute(email);
//        new UpdateUsername().execute(username);
//        new UpdateWeight().execute(weight);
//        new UpdateHeight().execute(height);

//        if it requires all fields
//        new UpdateProfile().execute(name, email, username, weight, height);
    }

    private class UpdateName extends AsyncTask<String, Void, Boolean>{

        @Override
        protected Boolean doInBackground(String... strings) {
            if (!strings[0].isEmpty()){
                //implement update here
                return true;
            }
            return false;
        }
    }
    private class UpdateEmail extends AsyncTask<String, Void, Boolean>{

        @Override
        protected Boolean doInBackground(String... strings) {
            if (!strings[0].isEmpty()){
                //implement update here
                return true;
            }
            return false;
        }
    }
    private class UpdateUsername extends AsyncTask<String, Void, Boolean>{

            @Override
        protected Boolean doInBackground(String... strings) {
            if (!strings[0].isEmpty()){
                //implement update here
                return true;
            }
            return false;
        }
    }
    private class UpdateWeight extends AsyncTask<String, Void, Boolean>{

        @Override
        protected Boolean doInBackground(String... strings) {
            if (!strings[0].isEmpty()){
                Float weight = Float.parseFloat(strings[0]);
                //implement update here
                return true;
            }
            return false;
        }
    }
    private class UpdateHeight extends AsyncTask<String, Void, Boolean>{

        @Override
        protected Boolean doInBackground(String... strings) {
            if (!strings[0].isEmpty()){
                Float height = Float.parseFloat(strings[0]);
                //implement update here
                return true;
            }
            return false;
        }
    }

    private class UpdateProfile extends AsyncTask<String, Void, Boolean> {

        @Override
        protected Boolean doInBackground(String... params) {
            String name = params[0];
            String email = params[1];
            String username = params[2];
            Float weight = Float.parseFloat(params[3]);
            Float height = Float.parseFloat(params[4]);
            return true; //return if update successfull
        }
    }

    private class GetProfileInfo extends AsyncTask<String, Void, ResultSet> {

        @Override
        protected ResultSet doInBackground(String... strings) {
            return ReadData.getProfile(strings[0]);
        }

        @Override
        protected void onPostExecute(ResultSet userProfile){
            try {
                if (userProfile.next()){
                    tvName.setText(userProfile.getString("name"));
                    tvEmail.setText(userProfile.getString("email"));
                    tvHeight.setText(String.valueOf(userProfile.getString("height")));
                    tvWeight.setText(String.valueOf(userProfile.getString("weight")));
//                tvAge.setText(userProfile.getString("age"));
                } else {
                    Log.e("TAG", "NO PROFILE");
                }
            } catch (SQLException e){
                throw new RuntimeException(e);
            } finally {
                try{
                    userProfile.close();
                } catch (SQLException r){
                    r.printStackTrace();
                }
            }
        }
    }

}
