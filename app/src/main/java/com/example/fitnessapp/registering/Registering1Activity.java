package com.example.fitnessapp.registering;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fitnessapp.HomeView;
import com.example.fitnessapp.InsertData;
import com.example.fitnessapp.LoginView;
import com.example.fitnessapp.R;
import com.example.fitnessapp.ReadData;

public class Registering1Activity extends AppCompatActivity {
    EditText tfName, tfEmail, tfUsername, pfPassword;
    RadioButton rbMale, rbFemale;
    TextView lblCheck;
    Button btnSignUp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registering1);

        tfName = findViewById(R.id.tfName);
        tfEmail = findViewById(R.id.tfEmail);
        tfUsername = findViewById(R.id.tfUsername);
        pfPassword = findViewById(R.id.pfPassword);
        rbMale = findViewById(R.id.g_Male);
        rbFemale = findViewById(R.id.g_Female);
        btnSignUp = findViewById(R.id.btnSignUp);
        lblCheck = findViewById(R.id.lblcheck);

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
        String gender;

        if (rbMale.isChecked()) gender = "Male";
        else if (rbFemale.isChecked()) gender = "Female";
        else gender = "Genderless";
        new SignUpTask().execute(name, username, email, password, gender);
    }

    private class SignUpTask extends AsyncTask<String, Void, Boolean>{
        @Override
        protected Boolean doInBackground(String... params) {
            String name = params[0];
            String username = params[1];
            String email = params[2];
            String password = params[3];
            String gender = params[4];
            if( ReadData.usernameExist(username)) return false;
            else {
                InsertData.insertData(Registering1Activity.this,name, email, username, password, gender);
                return true;
            }
        }

        @Override
        protected void onPostExecute(Boolean result) {
            if (result) {
                // Navigate to the next screen
                Intent intent = new Intent(Registering1Activity.this, HomeView.class);
                startActivity(intent);
                Toast.makeText(Registering1Activity.this, "Register Successfull", Toast.LENGTH_SHORT).show();
            } else {
                // Show error message
                lblCheck.setText("Username already exist");
            }
        }
    }

//    private void signUp() {
//        String name = tfName.getText().toString();
//        String username = tfUsername.getText().toString();
//        String email = tfEmail.getText().toString();
//        String password = pfPassword.getText().toString();
//        new RegisterView.RegisterTask().execute(name, username,email, password);
//    }
//
//    private class RegisterTask extends AsyncTask<String, Void, Boolean> {
//
//        @Override
//        protected Boolean doInBackground(String... params) {
//            String name = params[0];
//            String username = params[1];
//            String email = params[2];
//            String password = params[3];
//            return InsertData.insertData(name, email, username, password);
//        }
//
//        @Override
//        protected void onPostExecute(Boolean result) {
//            if (result) {
//                // Navigate to the next screen
//                Intent intent = new Intent(LoginView.this, HomeView.class);
//                startActivity(intent);
//                Toast.makeText(LoginView.this, "Login successful", Toast.LENGTH_SHORT).show();
//            } else {
//                // Show error message
//                lblcheck.setText("Invalid username/password");
//            }
//        }
//    }
    public void onBackClicked(View view) {
//        Intent intent = new Intent(this, MainActivity.class);
//        startActivity(intent);
        finish();
    }
    public void onLoginClicked(View view) {
        Intent intent = new Intent(this, LoginView.class);
        startActivity(intent);
        finish();
    }
}
