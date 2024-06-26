package com.example.fitnessapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.fitnessapp.crud.ReadData;
import com.example.fitnessapp.databinding.ActivityHomeViewBinding;

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
            R.id.profile,
            R.id.home
    };

    List<Integer> menus = Arrays.asList(id);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeViewBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Before we created the Session Class, we had to pass it through intents
//        username = getIntent().getStringExtra("username");
//        user_id = getIntent().getIntExtra("user_id", 0);
        username = Session.getUsername();
        user_id = Session.getUid();

        Log.e("TAWAG", "I was called! user_id is " + user_id + username);

        if (user_id != 0) {
            new GetPlansTask().execute(user_id);
        } else {
            Log.e("HomeView", "Error: Invalid user ID received");
            Toast.makeText(this, "Error: Invalid user ID", Toast.LENGTH_SHORT).show();
        }

        binding.bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            switch (menus.indexOf(item.getItemId())) {
                case 0:
                    WorkoutFragment workoutFragment = WorkoutFragment.newInstance(username, user_id);
                    replaceFragment(workoutFragment);
                    break;
                case 1:
                    ProgressTrackingFragment progressTrackingFragment = ProgressTrackingFragment.newInstance(username, user_id);
                    replaceFragment(progressTrackingFragment);
                    break;
                case 2:
                    NutritionFragment nutritionFragment = NutritionFragment.newInstance(username, user_id);
                    replaceFragment(nutritionFragment);
                    break;
                case 3:
                    EditProfileFragment editProfileFragment = EditProfileFragment.newInstance(username, user_id);
                    replaceFragment(editProfileFragment);
                    break;
                case 4:
                    new GetPlansTask().execute(user_id);
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
            return ReadData.getPlans(userId);
        }

        @Override
        protected void onPostExecute(int[] result) {
            if (result != null && result.length == 2) {
                int currentDay = result[0];
                int currentWeek = result[1];
                Log.i("HomeViewActivity", "Fetched day " + currentDay + " and week " + currentWeek + " for user " + user_id);
                HomeFragment homeFragment = HomeFragment.newInstance(currentDay, currentWeek, username, user_id);
                replaceFragment(homeFragment);
            } else {
                Log.e("HomeViewActivity", "Error: Unable to fetch plans for user ID: " + user_id);
                Toast.makeText(HomeViewActivity.this, "Error: Unable to fetch plans", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
