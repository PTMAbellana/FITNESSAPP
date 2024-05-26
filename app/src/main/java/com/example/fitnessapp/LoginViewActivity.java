package com.example.fitnessapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.fitnessapp.registering.Registering1Activity;

public class LoginViewActivity extends AppCompatActivity {

    EditText tfUsername, pfPassword;
    TextView lblcheck;
    Button btnSignIn;

    String username, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_view);
        btnSignIn = findViewById(R.id.btnSignIn);
        tfUsername = findViewById(R.id.tfUsername);
        pfPassword = findViewById(R.id.pfPassword);
        lblcheck = findViewById(R.id.lblcheck);
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });
    }

    private void signIn() {
        username = tfUsername.getText().toString();
        password = pfPassword.getText().toString();
        new LoginTask().execute(username, password);
    }

    private class LoginTask extends AsyncTask<String, Void, Boolean> {
        private String username;
        private int uid;

        @Override
        protected Boolean doInBackground(String... params) {
            username = params[0];
            Log.e("TAWAG", "username is " + username);
            String password = params[1];
            if (ReadData.readDataLogin(username, password)) {
                uid = ReadData.getSession(username);
                Session s = new Session(uid, username);
                Log.e("Login SESSION", "uid : " + s.getUid());
                Log.e("Login SESSION", "username : " + s.getUsername());
//                s.setUid(uid);
//                s.setUsername(username);
                return true;
            }
            return false;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            if (result) {
                Log.e("TAWAG", "UID is " + uid);
                if (uid != 0) {
                    Intent intent = new Intent(LoginViewActivity.this, HomeViewActivity.class);
//                    intent.putExtra("username", username);
//                    intent.putExtra("user_id", uid);
                    Log.e("TAWAG", "Successful login: " + username + " " + uid);
                    startActivity(intent);
                    Toast.makeText(LoginViewActivity.this, "Login successful", Toast.LENGTH_SHORT).show();
                } else {
                    lblcheck.setText("Error retrieving user session.");
                }
            } else {
                lblcheck.setText("Invalid username/password");
            }
        }
    }

    public void onRegisterClicked(View view) {
        Intent intent = new Intent(this, Registering1Activity.class);
        startActivity(intent);
        finish();
    }
}
