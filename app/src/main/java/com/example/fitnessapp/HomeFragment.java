package com.example.fitnessapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fitnessapp.dayexercise.DayActivity;
import com.example.fitnessapp.videoexercise.VideoWorkoutActivity1;
import com.example.fitnessapp.videoexercise.VideoWorkoutActivity2;
import com.example.fitnessapp.videoexercise.VideoWorkoutActivity3;
import com.example.fitnessapp.videoexercise.VideoWorkoutActivity4;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    private static final String ARG_CURRENT_DAY = "currentDay";
    private static final String ARG_CURRENT_WEEK = "currentWeek";
    private static final String ARG_USERNAME = "username";

    private static final String ARG_USER_ID = "user_id";

    private int currentDay;
    private int currentWeek;
    private String username;
    private int user_id;
    private ImageButton btnWorkoutVideo1, btnWorkoutVideo2, btnWorkoutVideo3, btnWorkoutVideo4;

    private List<Exercise> exerciseList;

    public HomeFragment() {
        // Required empty public constructor
    }

    public static HomeFragment newInstance(int currentDay, int currentWeek, String username, int user_id) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_CURRENT_DAY, currentDay);
        args.putInt(ARG_CURRENT_WEEK, currentWeek);
        args.putString(ARG_USERNAME, username);
        args.putInt(ARG_USER_ID, user_id);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            currentDay = getArguments().getInt(ARG_CURRENT_DAY);
            currentWeek = getArguments().getInt(ARG_CURRENT_WEEK);
            username = getArguments().getString(ARG_USERNAME);
            user_id = getArguments().getInt(ARG_USER_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        btnWorkoutVideo1 = view.findViewById(R.id.btnWorkoutVideo1);
        btnWorkoutVideo2 = view.findViewById(R.id.btnWorkoutVideo2);
        btnWorkoutVideo3 = view.findViewById(R.id.btnWorkoutVideo3);
        btnWorkoutVideo4 = view.findViewById(R.id.btnWorkoutVideo4);
        btnWorkoutVideo1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                navigateToVideoWorkoutFragment();
                Intent intent = new Intent(requireContext(), VideoWorkoutActivity1.class);
                startActivity(intent);
            }
        });
        btnWorkoutVideo2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                navigateToVideoWorkoutFragment();
                Intent intent = new Intent(requireContext(), VideoWorkoutActivity2.class);
                startActivity(intent);
            }
        });

        btnWorkoutVideo3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                navigateToVideoWorkoutFragment();
                Intent intent = new Intent(requireContext(), VideoWorkoutActivity3.class);
                startActivity(intent);
            }
        });

        btnWorkoutVideo4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                navigateToVideoWorkoutFragment();
                Intent intent = new Intent(requireContext(), VideoWorkoutActivity4.class);
                startActivity(intent);
            }
        });
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Update greeting with username
        updateGreeting(username);

        // Add dynamic day FrameLayouts
        addDayFrameLayouts(view);
    }

    public void updateGreeting(String username) {
        Log.e("TAWAG", "username is " + username);
        View fragmentView = getView();
        if (fragmentView != null) {
            View headerFrame = fragmentView.findViewById(R.id.header_frame);
            if (headerFrame != null) {
                TextView tvGreeting = headerFrame.findViewById(R.id.tvGreeting);
                if (tvGreeting != null) {
                    tvGreeting.setText("Hello, " + username);
                } else {
                    Log.e("HomeFragment", "tvGreeting is null");
                }
            } else {
                Log.e("HomeFragment", "headerFrame is null");
            }
        } else {
            Log.e("HomeFragment", "Fragment view is null");
        }
    }

    private void addDayFrameLayouts(View view) {
        LinearLayout daysContainer = view.findViewById(R.id.days_container);
        if (daysContainer == null) {
            Log.e("HomeFragment", "daysContainer is null");
            return;
        }

        LayoutInflater inflater = LayoutInflater.from(getContext());

        int start = 0;
        if(currentDay < 5) {
            start = 1;
        } else {
            start = currentDay - 3;
        }

        for (int i = start ; i <= 30; i++) {
            FrameLayout dayFrameLayout = (FrameLayout) inflater.inflate(R.layout.day_frame_layout, daysContainer, false);
            TextView dayTextView = dayFrameLayout.findViewById(R.id.lblDay);
            Button startButton = dayFrameLayout.findViewById(R.id.btnStartDay);

            final int dayNumber = i;

            if ((dayNumber % 4) == 0) {
                dayTextView.setText("Day " + i + ": Rest Day");
                startButton.setText("Rest");
                startButton.setOnClickListener(v -> {
                    Toast.makeText(getContext(), "Rest Day", Toast.LENGTH_SHORT).show();
                });
            } else {
                dayTextView.setText("Day " + i);

                if (dayNumber == currentDay) {
                    dayFrameLayout.setBackgroundResource(R.drawable.dayslayout_radius);
                    startButton.setText("Start");
                    startButton.setOnClickListener(v -> {
                        Toast.makeText(getContext(), "Start Day " + dayNumber, Toast.LENGTH_SHORT).show();
                        new GenerateExerciseList().execute(user_id);
                    });
                } else if (dayNumber < currentDay) {
                    startButton.setText("Done");
                    startButton.setOnClickListener(v -> {
                        Toast.makeText(getContext(), "Day " + dayNumber + " is already done", Toast.LENGTH_SHORT).show();
                    });
                } else {
                    startButton.setText("Wait");
                    startButton.setOnClickListener(v -> {
                        Toast.makeText(getContext(), "Proceed to Day " + currentDay, Toast.LENGTH_SHORT).show();
                    });
                }
            }

            daysContainer.addView(dayFrameLayout);
        }
    }

    private class GenerateExerciseList extends AsyncTask<Integer, Void, List<Exercise>> {
        @Override
        protected List<Exercise> doInBackground(Integer... integers) {
            int userId = integers[0];
            return ReadData.getExerciseList(userId);
        }

        @Override
        protected void onPostExecute(List<Exercise> result) {
            exerciseList = result;
            Intent intent = new Intent(getActivity(), DayActivity.class);
            intent.putParcelableArrayListExtra("exercise_list", new ArrayList<>(exerciseList));
            intent.putExtra("username", username);
            intent.putExtra("user_id", user_id);
            intent.putExtra("currentDay", currentDay);

            Log.e("TAWAG", "Checking " + username + " " + user_id + " " + currentDay + " " + currentWeek);

            startActivity(intent);
        }
    }
}
