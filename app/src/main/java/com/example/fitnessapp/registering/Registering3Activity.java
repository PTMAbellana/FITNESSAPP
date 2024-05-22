package com.example.fitnessapp.registering;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.fitnessapp.R;

public class Registering3Activity extends AppCompatActivity {

    public String username;

    private RadioButton selectedRadioButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registering3);

        Intent intent = getIntent();
        username = intent.getStringExtra("username");
    }

    public void onFrameClick(View view) {
        FrameLayout frameLayout = (FrameLayout) view;
        RadioButton radioButton = (RadioButton) frameLayout.getChildAt(3);
        radioButton.setChecked(true);

        clearOtherRadioButtons(radioButton.getId());

        selectedRadioButton = radioButton;
    }

    private void clearOtherRadioButtons(int selectedRadioButtonId) {
        int[] radioButtonIds = {
                R.id.radioButton1,
                R.id.radioButton2,
                R.id.radioButton3
        };

        for (int id : radioButtonIds) {
            if (id != selectedRadioButtonId) {
                RadioButton radioButton = findViewById(id);
                if (radioButton != null) {
                    radioButton.setChecked(false);
                }
            }
        }
    }

    public void onNextClicked(View view) {
        if (selectedRadioButton == null) {
            Toast.makeText(this, "Please select an option before proceeding.", Toast.LENGTH_SHORT).show();
        } else {
            Intent intent = new Intent(this, Registering4Activity.class);
            intent.putExtra("username", username);
            startActivity(intent);
//            finish();
        }
    }
}