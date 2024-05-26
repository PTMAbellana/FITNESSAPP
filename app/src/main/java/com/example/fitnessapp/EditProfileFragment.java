package com.example.fitnessapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.fitnessapp.registering.Registering1Activity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class EditProfileFragment extends Fragment {
    private static final String ARG_USERNAME = "username";
    private static final String ARG_USERID = "user_id";

    private Button update,logout;
    private TextView tvName, tvEmail, tvWeight, tvHeight, tvAge;
    private EditText tfName, tfEmail, tfUsername, tfWeight, tfHeight;
    private String username;
    private int user_id;
    private ExecutorService executorService;

    public EditProfileFragment() {
        // Required empty public constructor
    }

    public static EditProfileFragment newInstance(String username, int user_id) {
        EditProfileFragment fragment = new EditProfileFragment();
        Bundle args = new Bundle();
        args.putString(ARG_USERNAME, username);
        args.putInt(ARG_USERID, user_id);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            username = getArguments().getString(ARG_USERNAME);
            user_id = getArguments().getInt(ARG_USERID);
        }
        executorService = Executors.newFixedThreadPool(5);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_profile, container, false);

        update = view.findViewById(R.id.btnEditProfile);
        tvName = view.findViewById(R.id.tvName);
        tvEmail = view.findViewById(R.id.tvEmail);
        tvWeight = view.findViewById(R.id.tvWeight);
        tvHeight = view.findViewById(R.id.tvHeight);
        tvAge = view.findViewById(R.id.tvAge);

        tfName = view.findViewById(R.id.tfName);
        tfEmail = view.findViewById(R.id.tfEmail);
        tfUsername = view.findViewById(R.id.tfUsername);
        tfWeight = view.findViewById(R.id.tfWeight);
        tfHeight = view.findViewById(R.id.tfHeight);
        logout = view.findViewById(R.id.btnLogout);

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateProfile();
                Toast.makeText(getContext(), "Edit Profile Successful", Toast.LENGTH_SHORT).show();
            }
        });

        new GetProfileInfo().execute(user_id);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onLogoutClicked();
            }
        });
        return view;
    }

    protected void updateProfile() {
        String name = tfName.getText().toString();
        String email = tfEmail.getText().toString();
        String username = tfUsername.getText().toString();
        String weight = tfWeight.getText().toString();
        String height = tfHeight.getText().toString();

        final Object lock = new Object();
        final int numberOfUpdates = 5;
        final int[] remainingTasks = {numberOfUpdates};

        executorService.execute(new UpdateProfile("UPDATE tblusers SET name=? WHERE user_id=?", name, lock, remainingTasks));
        executorService.execute(new UpdateProfile("UPDATE tblusers SET email=? WHERE user_id=?", email, lock, remainingTasks));
        executorService.execute(new UpdateProfile("UPDATE tblusers SET username=? WHERE user_id=?", username, lock, remainingTasks));
        executorService.execute(new UpdateProfile("UPDATE tblusers SET weight=? WHERE user_id=?", weight, lock, remainingTasks));
        executorService.execute(new UpdateProfile("UPDATE tblusers SET height=? WHERE user_id=?", height, lock, remainingTasks));

        new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (lock) {
                    while (remainingTasks[0] > 0) {
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                    }
                    // UI updates need to be run on the main thread
                    if (getActivity() != null) {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getContext(), "Edit Profile Successful", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }
            }
        }).start();
    }

    private void refreshFragment() {
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.detach(this).attach(this).commit();
    }

    private class UpdateProfile implements Runnable {
        private final String query;
        private final String value;
        private final Object lock;
        private final int[] remainingTasks;

        public UpdateProfile(String query, String value, Object lock, int[] remainingTasks) {
            this.query = query;
            this.value = value;
            this.lock = lock;
            this.remainingTasks = remainingTasks;
        }

        @Override
        public void run() {
            try (Connection c = ConnectionClass.getConnection();
                 PreparedStatement statement = c.prepareStatement(query)) {
                if (!value.isEmpty()) {
                    if (query.contains("height") || query.contains("weight")) {
                        statement.setFloat(1, Float.parseFloat(value));
                    } else {
                        if (query.contains("username")) {
                            Session.setUsername(value);
                        }
                        statement.setString(1, value);
                    }
                    statement.setInt(2, user_id);
                    statement.executeUpdate();
                } else {
                    Log.e("EditProfileFragment", "Query " + query + " not executed because value is null or empty");
                }
            } catch (SQLException e) {
                Log.e("EditProfileFragment", "SQLException: " + e.getMessage(), e);
            } finally {
                synchronized (lock) {
                    remainingTasks[0]--;
                    Log.d("UpdateProfile", "Task completed, remaining tasks: " + remainingTasks[0]);
                    if (remainingTasks[0] == 0) {
                        lock.notifyAll();
                    }
                }
            }
        }

    }

    private class GetProfileInfo extends AsyncTask<Integer, Void, ResultSet> {

        @Override
        protected ResultSet doInBackground(Integer... integers) {
            return ReadData.getProfile(integers[0]);
        }

        @Override
        protected void onPostExecute(ResultSet userProfile) {
            try {
                if (userProfile.next()) {
                    tvName.setText(userProfile.getString("name"));
                    tvEmail.setText(userProfile.getString("email"));
                    tvHeight.setText(String.format("%s cm", userProfile.getString("height")));
                    tvWeight.setText(String.format("%s kg", userProfile.getString("weight")));
                    tvAge.setText(String.valueOf(userProfile.getInt("age")));
                } else {
                    Log.e("EditProfileFragment", "No profile found for user ID: " + user_id);
                }
            } catch (SQLException e) {
                Log.e("EditProfileFragment", "SQLException: " + e.getMessage(), e);
            } finally {
                try {
                    userProfile.close();
                } catch (SQLException e) {
                    Log.e("EditProfileFragment", "SQLException while closing ResultSet: " + e.getMessage(), e);
                }
            }
        }
    }
    public void onLogoutClicked() {
        Intent intent = new Intent(getActivity(), LaunchActivity.class);
        startActivity(intent);
        getActivity().finish();
    }
}
