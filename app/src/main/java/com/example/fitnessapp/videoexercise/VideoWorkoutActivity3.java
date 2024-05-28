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

public class VideoWorkoutActivity3 extends AppCompatActivity {
    private VideoView videoView3;

    private Button btnPlayPause, btnBack, btnForward;
    private SeekBar videoSeekBar;
    private Handler handler2 = new Handler();
    private boolean isPlaying2 = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_workout_activity3);

        videoView3 = findViewById(R.id.videoView);
        btnPlayPause = findViewById(R.id.btnPlayPause);
        btnBack = findViewById(R.id.btnWorkoutBack);
        btnForward = findViewById(R.id.btnForward);
        videoSeekBar = findViewById(R.id.videoSeekBar);

        String fileName = "videoworkout3";
        String filePlace = "android.resource://" + getPackageName() + "/raw/" + fileName;
        videoView3.setVideoURI(Uri.parse(filePlace));

        btnPlayPause.setOnClickListener(view -> {
            if (isPlaying2) {
                videoView3.pause();
                btnPlayPause.setText("Play");
            } else {
                videoView3.start();
                btnPlayPause.setText("Pause");
                updateSeekBar();
            }
            isPlaying2 = !isPlaying2;
        });

        btnBack.setOnClickListener(view -> {
            int currentPosition = videoView3.getCurrentPosition();
            videoView3.seekTo(Math.max(currentPosition - 5000, 0));
        });

        btnForward.setOnClickListener(view -> {
            int currentPosition = videoView3.getCurrentPosition();
            videoView3.seekTo(Math.min(currentPosition + 5000, videoView3.getDuration()));
        });

        videoSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    int newPosition = (int) ((progress / 100.0) * videoView3.getDuration());
                    videoView3.seekTo(newPosition);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // Pause video while seeking
                videoView3.pause();
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // Resume video after seeking
                videoView3.start();
            }
        });

        videoView3.setOnPreparedListener(mediaPlayer -> {
            videoSeekBar.setMax(100);
            updateSeekBar();
        });
    }

    private void updateSeekBar() {
        handler2.postDelayed(updateRunnable, 1000);
    }

    private Runnable updateRunnable = new Runnable() {
        @Override
        public void run() {
            if (videoView3 != null && videoView3.isPlaying()) {
                int currentPosition = videoView3.getCurrentPosition();
                int duration = videoView3.getDuration();
                if (duration > 0) {
                    int progress = (int) ((currentPosition / (float) duration) * 100);
                    videoSeekBar.setProgress(progress);
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