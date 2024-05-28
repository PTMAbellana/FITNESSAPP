package com.example.fitnessapp.videoexercise;

import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;


import com.example.fitnessapp.R;

public class VideoWorkoutActivity2 extends AppCompatActivity {
    // In case we use this
//    VideoView videoView;
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        EdgeToEdge.enable(this);
//        setContentView(R.layout.activity_video_workout_activity2);
//        videoView = findViewById(R.id.videoView2);
//        MediaController mediaController = new MediaController(this);
//        mediaController.setAnchorView(mediaController);
//        String fileName = "videoworkout1";
//        String filePlace = "android.resource://" + getPackageName() + "/raw/" + fileName;
//        videoView.setVideoURI(Uri.parse(filePlace));
//        videoView.setMediaController(mediaController);
//    }
    private VideoView videoView2;

    private Button btnPlayPause2, btnBack2, btnForward2;
    private SeekBar videoSeekBar2;
    private Handler handler2 = new Handler();
    private boolean isPlaying2 = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_workout_activity2);

        videoView2 = findViewById(R.id.videoView);
        btnPlayPause2 = findViewById(R.id.btnPlayPause);
        btnBack2 = findViewById(R.id.btnWorkoutBack);
        btnForward2 = findViewById(R.id.btnForward);
        videoSeekBar2 = findViewById(R.id.videoSeekBar);

        String fileName = "videoworkout2";
        String filePlace = "android.resource://" + getPackageName() + "/raw/" + fileName;
        videoView2.setVideoURI(Uri.parse(filePlace));

        btnPlayPause2.setOnClickListener(view -> {
            if (isPlaying2) {
                videoView2.pause();
                btnPlayPause2.setText("Play");
            } else {
                videoView2.start();
                btnPlayPause2.setText("Pause");
                updateSeekBar();
            }
            isPlaying2 = !isPlaying2;
        });

        btnBack2.setOnClickListener(view -> {
            int currentPosition = videoView2.getCurrentPosition();
            videoView2.seekTo(Math.max(currentPosition - 5000, 0));
        });

        btnForward2.setOnClickListener(view -> {
            int currentPosition = videoView2.getCurrentPosition();
            videoView2.seekTo(Math.min(currentPosition + 5000, videoView2.getDuration()));
        });

        videoSeekBar2.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    int newPosition = (int) ((progress / 100.0) * videoView2.getDuration());
                    videoView2.seekTo(newPosition);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // Pause video while seeking
                videoView2.pause();
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // Resume video after seeking
                videoView2.start();
            }
        });

        videoView2.setOnPreparedListener(mediaPlayer -> {
            videoSeekBar2.setMax(100);
            updateSeekBar();
        });
    }

    private void updateSeekBar() {
        handler2.postDelayed(updateRunnable, 1000);
    }

    private Runnable updateRunnable = new Runnable() {
        @Override
        public void run() {
            if (videoView2 != null && videoView2.isPlaying()) {
                int currentPosition = videoView2.getCurrentPosition();
                int duration = videoView2.getDuration();
                if (duration > 0) {
                    int progress = (int) ((currentPosition / (float) duration) * 100);
                    videoSeekBar2.setProgress(progress);
                }
                updateSeekBar();
            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler2.removeCallbacks(updateRunnable);
    }
    public void onBackClicked(View view) {
//        Intent intent = new Intent(this, HomeView.class);
//        startActivity(intent);
        finish();
    }
}