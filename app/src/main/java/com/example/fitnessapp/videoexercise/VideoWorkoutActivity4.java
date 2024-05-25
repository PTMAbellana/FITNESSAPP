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

public class VideoWorkoutActivity4 extends AppCompatActivity {
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
    private VideoView videoView4;

    private Button btnPlayPause4, btnBack4, btnForward4;
    private SeekBar videoSeekBar4;
    private Handler handler4 = new Handler();
    private boolean isPlaying4 = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_workout_activity4);

        videoView4 = findViewById(R.id.videoView4);
        btnPlayPause4 = findViewById(R.id.btnPlayPause4);
        btnBack4 = findViewById(R.id.btnWorkoutBack4);
        btnForward4 = findViewById(R.id.btnForward4);
        videoSeekBar4 = findViewById(R.id.videoSeekBar4);

        String fileName = "videoworkout4";
        String filePlace = "android.resource://" + getPackageName() + "/raw/" + fileName;
        videoView4.setVideoURI(Uri.parse(filePlace));

        btnPlayPause4.setOnClickListener(view -> {
            if (isPlaying4) {
                videoView4.pause();
                btnPlayPause4.setText("Play");
            } else {
                videoView4.start();
                btnPlayPause4.setText("Pause");
                updateSeekBar();
            }
            isPlaying4 = !isPlaying4;
        });

        btnBack4.setOnClickListener(view -> {
            int currentPosition = videoView4.getCurrentPosition();
            videoView4.seekTo(Math.max(currentPosition - 5000, 0));
        });

        btnForward4.setOnClickListener(view -> {
            int currentPosition = videoView4.getCurrentPosition();
            videoView4.seekTo(Math.min(currentPosition + 5000, videoView4.getDuration()));
        });

        videoSeekBar4.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    int newPosition = (int) ((progress / 100.0) * videoView4.getDuration());
                    videoView4.seekTo(newPosition);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // Pause video while seeking
                videoView4.pause();
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // Resume video after seeking
                videoView4.start();
            }
        });

        videoView4.setOnPreparedListener(mediaPlayer -> {
            videoSeekBar4.setMax(100);
            updateSeekBar();
        });
    }

    private void updateSeekBar() {
        handler4.postDelayed(updateRunnable, 1000);
    }

    private Runnable updateRunnable = new Runnable() {
        @Override
        public void run() {
            if (videoView4 != null && videoView4.isPlaying()) {
                int currentPosition = videoView4.getCurrentPosition();
                int duration = videoView4.getDuration();
                if (duration > 0) {
                    int progress = (int) ((currentPosition / (float) duration) * 100);
                    videoSeekBar4.setProgress(progress);
                }
                updateSeekBar();
            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler4.removeCallbacks(updateRunnable);
    }
    public void onBackClicked(View view) {
//        Intent intent = new Intent(this, HomeView.class);
//        startActivity(intent);
        finish();
    }
}