package com.example.fitnessapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.example.fitnessapp.nutritionalfood.NutritionFoodActivity1;
import com.example.fitnessapp.nutritionalfood.NutritionFoodActivity2;
import com.example.fitnessapp.nutritionalfood.NutritionFoodActivity3;
import com.example.fitnessapp.nutritionalfood.NutritionFoodActivity4;
import com.example.fitnessapp.nutritionalfood.NutritionFoodActivity5;
import com.example.fitnessapp.nutritionalfood.NutritionFoodActivity6;
import com.example.fitnessapp.nutritionalfood.NutritionFoodActivity7;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NutritionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NutritionFragment extends Fragment {

    private static final String ARG_USERNAME = "username";
    private static final String ARG_USERID = "user_id";

    private String username;
    private int user_id;
    private ImageButton btnViewFood1, btnViewFood2, btnViewFood3, btnViewFood4, btnViewFood5, btnViewFood6, btnViewFood7;

    public NutritionFragment() {
        // Required empty public constructor
    }

    public static NutritionFragment newInstance(String username, int user_id) {
        NutritionFragment fragment = new NutritionFragment();
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
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_nutrition, container, false);
        btnViewFood1 = view.findViewById(R.id.btnViewFood1);
        btnViewFood2 = view.findViewById(R.id.btnViewFood2);
        btnViewFood3 = view.findViewById(R.id.btnViewFood3);
        btnViewFood4 = view.findViewById(R.id.btnViewFood4);
        btnViewFood5 = view.findViewById(R.id.btnViewFood5);
        btnViewFood6 = view.findViewById(R.id.btnViewFood6);
        btnViewFood7 = view.findViewById(R.id.btnViewFood7);

        btnViewFood1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(requireContext(), NutritionFoodActivity1.class);
                startActivity(intent);
            }
        });

        btnViewFood2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(requireContext(), NutritionFoodActivity2.class);
                startActivity(intent);
            }
        });

        btnViewFood3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(requireContext(), NutritionFoodActivity3.class);
                startActivity(intent);
            }
        });

        btnViewFood4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(requireContext(), NutritionFoodActivity4.class);
                startActivity(intent);
            }
        });

        btnViewFood5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(requireContext(), NutritionFoodActivity5.class);
                startActivity(intent);
            }
        });

        btnViewFood6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(requireContext(), NutritionFoodActivity6.class);
                startActivity(intent);
            }
        });

        btnViewFood7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(requireContext(), NutritionFoodActivity7.class);
                startActivity(intent);
            }
        });

        return view;
    }
}