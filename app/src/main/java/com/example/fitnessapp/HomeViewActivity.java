package com.example.fitnessapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;
import java.util.List;

import com.example.fitnessapp.databinding.ActivityHomeViewBinding;

public class HomeViewActivity extends AppCompatActivity {
    TextView greetings;

    public String username;
    int uid;
//     binding;
    ActivityHomeViewBinding binding;
    private Integer[] id = {
            R.id.workout,
            R.id.progress,
            R.id.nutrition,
            R.id.home
    };

    List<Integer> menus = Arrays.asList(id);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeViewBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
//        setContentView(R.layout.activity_home_view);
        replaceFragment(new HomeFragment());
        Intent intent = getIntent();
        Log.e("TAWAG", "I was called! pero wa pay uid");
        uid = intent.getIntExtra("user_id", -1);
        Log.e("TAWAG", "I was called! uid is " + uid);

        if (uid != 0) {
            new GetUsername().execute(uid);
        } else {
            Log.e("HomeView", "Error: Invalid user ID received");
            Toast.makeText(this, "Error: Invalid user ID", Toast.LENGTH_SHORT).show();
        }

        // Set up bottom navigation view

        binding.bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            switch (menus.indexOf(item.getItemId())) {
                case 0:
                    replaceFragment(new WorkoutFragment());
                    break;
                case 1:
                    replaceFragment(new ProgressTrackingFragment());
                    break;
                case 2:
                    replaceFragment(new NutritionFragment());
                    break;
                case 3:
                    replaceFragment(new HomeFragment());
                    break;
            }
            return true;
        });
    }
    private void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout_workout, fragment);
        fragmentTransaction.commit();
    }
    public void onEditProfileActivityClicked(View view) {
        Intent intent = new Intent(HomeViewActivity.this, EditProfileActivity.class);
        intent.putExtra("user_id", uid);
        startActivity(intent);
    }

    public void onWorkoutActivityClicked(View view) {
        Intent intent = new Intent(HomeViewActivity.this, WorkoutActivity.class);
        startActivity(intent);
    }

    public void onNutritionActivityClicked(View view) {
        Intent intent = new Intent(HomeViewActivity.this, NutritionActivity.class);
        startActivity(intent);
    }

    public void onProgressTrackingActivityClicked(View view) {
        Intent intent = new Intent(HomeViewActivity.this, ProgressTrackingActivity.class);
        startActivity(intent);
    }

    private class GetUsername extends AsyncTask<Integer, Void, String> {
        private int uid;

        @Override
        protected String doInBackground(Integer... integers) {
            return ReadData.getUsername(integers[0]);
        }

        @Override
        protected void onPostExecute(String result) {
            if (result != null) {
                username = result;
                greetings = findViewById(R.id.tvGreeting);
                greetings.setText(greetings.getText() + " " + username);
            } else {
                Log.e("HomeView", "Error: Username not found for user ID: " + uid);
                Toast.makeText(HomeViewActivity.this, "Error: Username not found", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
