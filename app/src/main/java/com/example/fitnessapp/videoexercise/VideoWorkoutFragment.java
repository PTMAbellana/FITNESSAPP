package com.example.fitnessapp.videoexercise;

import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.MediaController;
import android.widget.VideoView;

import com.example.fitnessapp.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link VideoWorkoutFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class VideoWorkoutFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    VideoView videoView;
    public VideoWorkoutFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment VideoWorkoutFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static VideoWorkoutFragment newInstance(String param1, String param2) {
        VideoWorkoutFragment fragment = new VideoWorkoutFragment();
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
        View view = inflater.inflate(R.layout.fragment_video_workout, container, false);
        videoView = view.findViewById(R.id.videoView);
        MediaController mediaController = new MediaController(requireContext());
        mediaController.setAnchorView(mediaController);
        String fileName = "videoworkout1";
        String filePlace = "android.resource://" + requireContext().getPackageName() + "/raw/" + fileName;
        videoView.setVideoURI(Uri.parse(filePlace));
        videoView.setMediaController(mediaController);

        return view;
    }
}