package com.example.fitnessapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class LoginView extends AppCompatActivity {

    EditText tfUsername, pfPassword;
    TextView lblcheck;
    Button btnSignIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_view);
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
        String username = tfUsername.getText().toString();
        String password = pfPassword.getText().toString();
        new LoginTask().execute(username, password);
    }

    private class LoginTask extends AsyncTask<String, Void, Boolean> {

        @Override
        protected Boolean doInBackground(String... params) {
            String username = params[0];
            String password = params[1];
            return ReadData.readData(username, password);
        }

        @Override
        protected void onPostExecute(Boolean result) {
            if (result) {
                // Navigate to the next screen
                Intent intent = new Intent(LoginView.this, HomeView.class);
                startActivity(intent);
                Toast.makeText(LoginView.this, "Login successful", Toast.LENGTH_SHORT).show();
            } else {
                // Show error message
                lblcheck.setText("Invalid username/password");
            }
        }
    }
    public void onBackClicked(View view) {
//        Intent intent = new Intent(this, MainActivity.class);
//        startActivity(intent);
        finish();
    }
    public void onRegisterClicked(View view) {
        Intent intent = new Intent(this, RegisterView.class);
        startActivity(intent);
    }
}
