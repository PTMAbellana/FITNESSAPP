package com.example.fitnessapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.example.fitnessapp.videoexercise.VideoWorkoutActivity1;
import com.example.fitnessapp.videoexercise.VideoWorkoutActivity2;
import com.example.fitnessapp.videoexercise.VideoWorkoutActivity3;
import com.example.fitnessapp.videoexercise.VideoWorkoutActivity4;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link WorkoutFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WorkoutFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private ImageButton btnVideo1, btnVideo2, btnVideo3, btnVideo4;


    public WorkoutFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment WorkoutFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static WorkoutFragment newInstance(String param1, String param2) {
        WorkoutFragment fragment = new WorkoutFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_workout, container, false);
        btnVideo1 = view.findViewById(R.id.btnVideo1);
        btnVideo2 = view.findViewById(R.id.btnVideo2);
        btnVideo3 = view.findViewById(R.id.btnVideo3);
        btnVideo4 = view.findViewById(R.id.btnVideo4);
        btnVideo1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                navigateToVideoWorkoutFragment();
                Intent intent = new Intent(requireContext(), VideoWorkoutActivity1.class);
                startActivity(intent);
            }
        });

        btnVideo2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                navigateToVideoWorkoutFragment();
                Intent intent = new Intent(requireContext(), VideoWorkoutActivity2.class);
                startActivity(intent);
            }
        });

        btnVideo3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                navigateToVideoWorkoutFragment();
                Intent intent = new Intent(requireContext(), VideoWorkoutActivity3.class);
                startActivity(intent);
            }
        });

        btnVideo4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                navigateToVideoWorkoutFragment();
                Intent intent = new Intent(requireContext(), VideoWorkoutActivity4.class);
                startActivity(intent);
            }
        });

        return view;
    }

    // Method to navigate to VideoWorkoutFragment
//    private void navigateToVideoWorkoutFragment() {
//        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
//        transaction.replace(R.id.frame_layout_1, new VideoWorkoutFragment());
//        transaction.addToBackStack(null);
//        transaction.commit();
//    }

}