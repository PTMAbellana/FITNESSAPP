package com.example.fitnessapp.registering;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.fitnessapp.R;

public class Registering4Activity extends AppCompatActivity {

    public String username;
    private int uid;

    private RadioButton selectedRadioButton;
    private FrameLayout selectedFrameLayout;

    private int numPushups;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registering4);

        username = getIntent().getStringExtra("username");
        uid = getIntent().getIntExtra("user_id", 0);
    }

    public void onFrameClick(View view) {
        FrameLayout frameLayout = (FrameLayout) view;
        RadioButton radioButton = (RadioButton) frameLayout.getChildAt(1);
        radioButton.setChecked(true);

        if (selectedFrameLayout != null) {
            selectedFrameLayout.setBackgroundTintList(null);
        }
        frameLayout.setBackgroundTintList(getColorStateList(R.color.colorWhite));
        selectedFrameLayout = frameLayout;

        clearOtherRadioButtons(radioButton.getId());
        selectedRadioButton = radioButton;

        numPushups = getNumPushups();
    }

    private void clearOtherRadioButtons(int selectedRadioButtonId) {
        int[] radioButtonIds = {
                R.id.radioButton1,
                R.id.radioButton2,
                R.id.radioButton3,
                R.id.radioButton4,
                R.id.radioButton5
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
//            Toast.makeText(this, numPushups, Toast.LENGTH_SHORT).show();
//            InsertData.insertNumPushups(numPushups);

            Intent intent = new Intent(this, Registering5Activity.class);
            intent.putExtra("username", username);
            intent.putExtra("user_id", uid);
            startActivity(intent);
            finish();
        }
    }

    public void onBackClicked(View view) {
        Intent intent = new Intent(this, Registering3Activity.class);
        intent.putExtra("username", username);
        intent.putExtra("user_id", uid);
        startActivity(intent);
//        finish();
    }

    private int getNumPushups() {
//        if (selectedRadioButton.getId() == R.id.radioButton1) {
//            return 0;
//        }
//
//        if (selectedRadioButton.getId() == R.id.radioButton2) {
//            return 5;
//        }
//
//        if (selectedRadioButton.getId() == R.id.radioButton3) {
//            return 10;
//        }
//
//        if (selectedRadioButton.getId() == R.id.radioButton4) {
//            return 20;
//        }
//
//        if (selectedRadioButton.getId() == R.id.radioButton5) {
//            return 21;
//        }
//        return 0;


        // some reason, switch case not working
//        switch (selectedRadioButton.getId()) {
//            case R.id.radioButton1:
//                return 0;
//            case R.id.radioButton2:
//                return 5;
//            case R.id.radioButton3:
//                return 10;
//            case R.id.radioButton4:
//                return 20;
//            case R.id.radioButton5:
//                return 21;
//            default:
//                return 0;
//        }
        return 0;
    }
}

