package com.example.fitnessapp;

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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    private static final String ARG_CURRENT_DAY = "currentDay";
    private static final String ARG_CURRENT_WEEK = "currentWeek";
    private static final String ARG_USERNAME = "username";

    private int currentDay;
    private int currentWeek;
    private String username;

    public HomeFragment() {
        // Required empty public constructor
    }

    public static HomeFragment newInstance(int currentDay, int currentWeek, String username) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_CURRENT_DAY, currentDay);
        args.putInt(ARG_CURRENT_WEEK, currentWeek);
        args.putString(ARG_USERNAME, username);
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
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
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

        for (int i = 1; i <= 30; i++) {
            FrameLayout dayFrameLayout = (FrameLayout) inflater.inflate(R.layout.day_frame_layout, daysContainer, false);
            TextView dayTextView = dayFrameLayout.findViewById(R.id.lblDay);
            Button startButton = dayFrameLayout.findViewById(R.id.btnStartDay);

            dayTextView.setText("Day " + i);

            final int dayNumber = i;

            if (dayNumber == currentDay) {
                dayFrameLayout.setBackgroundResource(R.drawable.dayslayout_radius);
                startButton.setOnClickListener(v -> {
                    Toast.makeText(getContext(), "Start Day " + dayNumber, Toast.LENGTH_SHORT).show();
                    // Implement click logic here
                });
            } else {
                startButton.setOnClickListener(v -> {
                    Toast.makeText(getContext(), "Proceed to Day " + currentDay, Toast.LENGTH_SHORT).show();
                });
            }

            daysContainer.addView(dayFrameLayout);
        }
    }
}
