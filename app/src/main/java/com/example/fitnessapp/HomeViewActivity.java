package com.example.fitnessapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.fitnessapp.databinding.ActivityHomeViewBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Arrays;
import java.util.List;

public class HomeViewActivity extends AppCompatActivity {

    public String username;
    protected int user_id;
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

        username = getIntent().getStringExtra("username");
        user_id = getIntent().getIntExtra("user_id", 0);

        Log.e("TAWAG", "I was called! user_id is " + user_id + username);

        if (user_id != 0) {
            new GetPlansTask().execute(user_id);
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

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit();
    }

    private class GetPlansTask extends AsyncTask<Integer, Void, int[]> {
        @Override
        protected int[] doInBackground(Integer... integers) {
            int userId = integers[0];
            // Fetch day and week from tblplans based on user_id
            // Assuming you have a method like ReadData.getPlans(userId) that returns an array [day, week]
            return ReadData.getPlans(userId);
        }

        @Override
        protected void onPostExecute(int[] result) {
            if (result != null && result.length == 2) {
                int currentDay = result[0];
                int currentWeek = result[1];
                Log.e("TAWAG", "Fetched day " + currentDay + " and week " + currentWeek + " for user " + user_id);
                HomeFragment homeFragment = HomeFragment.newInstance(currentDay, currentWeek, username, user_id);
                replaceFragment(homeFragment);
            } else {
                Log.e("HomeView", "Error: Unable to fetch plans for user ID: " + user_id);
                Toast.makeText(HomeViewActivity.this, "Error: Unable to fetch plans", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
