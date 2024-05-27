package com.example.fitnessapp;

import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.fitnessapp.crud.ReadData;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProgressTrackingFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProgressTrackingFragment extends Fragment {
    TextView tvName;
    TextView tvWeight;
    TextView tvHeight;
    TextView tvAge;
    TextView tvCurrentDifficulty;
    TextView tvTargetDifficulty;
    TextView tvBMI;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_USERNAME = "username";
    private static final String ARG_USERID = "user_id";

    // TODO: Rename and change types of parameters
    private String username;
    private int user_id;

    public ProgressTrackingFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param username Parameter 1.
     * @param user_id Parameter 2.
     * @return A new instance of fragment ProgressTrackingFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProgressTrackingFragment newInstance(String username, int user_id) {
        ProgressTrackingFragment fragment = new ProgressTrackingFragment();
        Bundle args = new Bundle();
        args.putString(ARG_USERNAME, username);
        args.putInt(ARG_USERID, user_id);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            username = getArguments().getString(ARG_USERNAME);
            user_id = getArguments().getInt(ARG_USERID);

            new SetInfo().execute(user_id);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_progress_tracking, container, false);
    }

    private class SetInfo extends AsyncTask<Integer, Void, ResultSet> {

        @Override
        protected ResultSet doInBackground(Integer... ints) {
            return ReadData.getProfile(ints[0]);
        }

        @Override
        protected void onPostExecute(ResultSet userProfile) {

            View fragmentView = getView();

            tvName = fragmentView.findViewById(R.id.tvName);
            tvWeight = fragmentView.findViewById(R.id.tvWeight);
            tvHeight = fragmentView.findViewById(R.id.tvHeight);
            tvAge = fragmentView.findViewById(R.id.tvAge);
            tvCurrentDifficulty = fragmentView.findViewById(R.id.tvCurrentDifficulty);
            tvTargetDifficulty = fragmentView.findViewById(R.id.tvTargetDifficulty);
            tvBMI = fragmentView.findViewById(R.id.tvBMI);

            try {
                if (userProfile.next()) {
                    tvName.setText(userProfile.getString("name"));
                    tvHeight.setText(String.format("Height: %.2f cm", userProfile.getFloat("height")));
                    tvWeight.setText(String.format("Weight: %.2f kg", userProfile.getFloat("weight")));
                    tvAge.setText(String.format("Age: %d", userProfile.getInt("age")));
                    tvCurrentDifficulty.setText(String.format("Current Difficulty: %s", userProfile.getString("current_difficulty")));
                    tvTargetDifficulty.setText(String.format("Target Difficulty: %s", userProfile.getString("target_difficulty")));
                    tvBMI.setText(String.format("BMI: %.2f", userProfile.getFloat("bmi")));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (userProfile != null) {
                        userProfile.close();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}