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
import android.widget.Toast;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class EditProfileActivity extends AppCompatActivity {

    Button update;
    TextView tvName, tvEmail, tvWeight, tvHeight, tvAge;
    EditText tfName, tfEmail, tfUsername, tfWeight, tfHeight;
    String username;
    int uid;
    ExecutorService executorService;
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
//        username = intent.getStringExtra("username");
        uid = intent.getIntExtra("user_id", -1);
        new GetProfileInfo().execute(uid);

        executorService = Executors.newFixedThreadPool(5);

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateProfile();
//                Toast.makeText(EditProfileActivity.this, "Edit Profile Successful", Toast.LENGTH_SHORT).show();
            }
        });
    }

    protected void updateProfile(){
        String name = tfName.getText().toString();
        String email = tfEmail.getText().toString();
        String username = tfUsername.getText().toString();
        String weight = tfWeight.getText().toString();
        String height = tfHeight.getText().toString();

//        new UpdateProfile().execute(name, email, username, weight, height);

        final Object lock = new Object();
        final int numberOfUpdates = 5;
        final int[] remainingTasks = {numberOfUpdates};

        executorService.execute(new UpdateProfile("UPDATE tblusers SET name=? WHERE user_id=?", name, lock, remainingTasks));
        executorService.execute(new UpdateProfile("UPDATE tblusers SET email=? WHERE user_id=?", email, lock, remainingTasks));
        executorService.execute(new UpdateProfile("UPDATE tblusers SET username=? WHERE user_id=?", username, lock, remainingTasks));
        executorService.execute(new UpdateProfile("UPDATE tblusers SET weight=? WHERE user_id=?", weight, lock, remainingTasks));
        executorService.execute(new UpdateProfile("UPDATE tblusers SET height=? WHERE user_id=?", height, lock, remainingTasks));

        new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (lock) {
                    while (remainingTasks[0] > 0) {
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                    }
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        finish();
                        overridePendingTransition(0, 0);
                        startActivity(getIntent());
                        overridePendingTransition(0, 0);
                        Toast.makeText(EditProfileActivity.this, "Edit Profile Successful", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }).start();
    }

    public void onBackClicked(View view) {
//        Intent intent = new Intent(this, HomeView.class);
//        startActivity(intent);
        finish();
    }

    private class UpdateProfile implements Runnable {
        String query;
        String value;
        final Object lock;
        final int[] remainingtasks;

        public UpdateProfile(String query, String value, Object lock, int[] remainingtasks) {
            this.query = query;
            this.value = value;
            this.lock = lock;
            this.remainingtasks = remainingtasks;
        }

        @Override
        public void run() {
            try (
                    Connection c = ConnectionClass.getConnection();
                    PreparedStatement statement = c.prepareStatement(query);
                    ){
                if (!value.isEmpty()){
                    if (query.contains("height") || query.contains("weight")) {
                        statement.setFloat(1, Float.parseFloat(value));
                    } else {
                        statement.setString(1, value);
                    }
                    statement.setInt(2, uid);
                    statement.executeUpdate();
                } else {
                    Log.e("EditProfileActivity", "Query "+query+" not kuan kay value is null or empty");
                }
            } catch (SQLException e){
                throw new RuntimeException();
            } finally {
                synchronized (lock){
                    remainingtasks[0]--;
                    Log.d("UpdateProfile", "Task completed, remaining tasks: " + remainingtasks[0]);
                    if (remainingtasks[0] == 0){
                        lock.notifyAll();
                    }
                }
            }
        }
    }

    private class GetProfileInfo extends AsyncTask<Integer, Void, ResultSet> {

        @Override
        protected ResultSet doInBackground(Integer... integers) {
            return ReadData.getProfile(integers[0]);
        }

        @Override
        protected void onPostExecute(ResultSet userProfile){
            try {
                if (userProfile.next()){
                    tvName.setText(userProfile.getString("name"));
                    tvEmail.setText(userProfile.getString("email"));
                    tvHeight.setText(String.valueOf(userProfile.getString("height") + " cm"));
                    tvWeight.setText(String.valueOf(userProfile.getString("weight")) + " kg");
                    username = userProfile.getString("username");
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
