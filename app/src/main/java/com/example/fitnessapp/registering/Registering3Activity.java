package com.example.fitnessapp.registering;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.fitnessapp.ConnectionClass;
import com.example.fitnessapp.R;
import com.example.fitnessapp.UpdateData;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Arrays;
import java.util.List;

public class Registering3Activity extends AppCompatActivity {

    public String username;
    protected int uid;
    private Integer[] radioButtonIds = {
            R.id.radioButton1,
            R.id.radioButton2,
            R.id.radioButton3
    };

    List<Integer> rbID = Arrays.asList(radioButtonIds);

    private RadioButton selectedRadioButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registering3);

        Intent intent = getIntent();
        username = intent.getStringExtra("username");
        uid = intent.getIntExtra("user_id", 0);
    }

    public void onFrameClick(View view) {
        FrameLayout frameLayout = (FrameLayout) view;
        RadioButton radioButton = (RadioButton) frameLayout.getChildAt(3);
        radioButton.setChecked(true);

        clearOtherRadioButtons(radioButton.getId());

        selectedRadioButton = radioButton;
    }

    private void clearOtherRadioButtons(int selectedRadioButtonId) {
//        int[] radioButtonIds = {
//                R.id.radioButton1,
//                R.id.radioButton2,
//                R.id.radioButton3
//        };

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
//            Log.e("THE PLAN", "clicked radio button: btn no." + rbID.indexOf(selectedRadioButton.getId()));
            new UpdatePlan().execute(uid, rbID.indexOf(selectedRadioButton.getId()));

//            UpdateData.updatePlan(uid, rbID.indexOf(selectedRadioButton.getId()));

            Intent intent = new Intent(this, Registering4Activity.class);
            intent.putExtra("username", username);
            intent.putExtra("user_id", uid);
            startActivity(intent);
//            finish();
        }
    }

    private class UpdatePlan extends AsyncTask<Integer, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Integer... integers) {
            return UpdateData.updatePlan(integers[0], integers[1]);
        }
    }
}