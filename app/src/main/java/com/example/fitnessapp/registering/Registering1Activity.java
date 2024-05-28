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

import com.example.fitnessapp.crud.InsertData;
import com.example.fitnessapp.LoginViewActivity;
import com.example.fitnessapp.R;
import com.example.fitnessapp.crud.ReadData;

// Note: Ensure that the textfields are not NULL before clicking Sign Up
// Additional Note: Ensure that the inputted username is not existing; Read the data if username exist // this is done
public class Registering1Activity extends AppCompatActivity {
    EditText tfName, tfEmail, tfUsername, pfPassword;
    RadioButton rbMale, rbFemale;
    TextView lblCheck;
    Button btnSignUp;

    String name;
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
        lblCheck = (TextView) findViewById(R.id.lblcheck);

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUp();
            }
        });
    }

    public void signUp() {
        name = tfName.getText().toString();
        String username = tfUsername.getText().toString();
        String email = tfEmail.getText().toString();
        String password = pfPassword.getText().toString();
        String gender;

        if (rbMale.isChecked()) gender = "Male";
        else if (rbFemale.isChecked()) gender = "Female";
        else gender = "Genderless";
        new SignUpTask().execute(name, username, email, password, gender);
    }

    // Documentation during the creation of the project
    // Pwede paexplain ani na part
    // wa sd ko kasabot kay wa ko kasabot unsa ning AsyncTask, but this works...
    // Murag radaw ni shag thread pero dili pang long term... ayy basta wa ko kasabot ehe
    private class SignUpTask extends AsyncTask<String, Void, Integer>{
        String username;
        @Override
        protected Integer doInBackground(String... params) {
            String name = params[0];
            username = params[1];
            String email = params[2];
            String password = params[3];
            String gender = params[4];
            if (!name.isEmpty() && !username.isEmpty() && !email.isEmpty() && !password.isEmpty() && !gender.isEmpty() ){
                if (ReadData.usernameExist(username)) return 0;
                else {
                    InsertData.insertUserData(Registering1Activity.this, name, email, username, password, gender);
                    return 1;
                }
            } return 2;
        }

        @Override
        protected void onPostExecute(Integer result) {
            switch (result){
                case 0:
                    lblCheck.setText("Username already exist");
                    break;
                case 1:
                    Intent intent = new Intent(Registering1Activity.this,
                            Registering2Activity.class);
                    intent.putExtra("username", username);
                    intent.putExtra("name", name);
                    startActivity(intent);
                    Toast.makeText(Registering1Activity.this, "Register Successful", Toast.LENGTH_SHORT).show();
                    finish();
                    break;
                case 2:
                    lblCheck.setText("All fields are required");
                    break;
            }
        }
    }

    public void onLoginClicked(View view) {
        Intent intent = new Intent(this, LoginViewActivity.class);
        startActivity(intent);
        finish();
    }
}
