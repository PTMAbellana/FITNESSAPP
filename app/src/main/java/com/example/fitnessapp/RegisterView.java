package com.example.fitnessapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class RegisterView extends AppCompatActivity {
    EditText tfName, tfEmail, tfUsername, pfPassword;
    Button btnSignUp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_view);

        tfName = findViewById(R.id.tfName);
        tfEmail = findViewById(R.id.tfEmail);
        tfUsername = findViewById(R.id.tfUsername);
        pfPassword = findViewById(R.id.pfPassword);
        btnSignUp = findViewById(R.id.btnSignUp);

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUp();
            }
        });
    }

    public void signUp() {
        String name = tfName.getText().toString();
        String username = tfUsername.getText().toString();
        String email = tfEmail.getText().toString();
        String password = pfPassword.getText().toString();
        try {
            InsertData.insertData(RegisterView.this,name, email, username, password);
            Toast.makeText(RegisterView.this, "Sign Up Successful", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(RegisterView.this, "Database Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

}
