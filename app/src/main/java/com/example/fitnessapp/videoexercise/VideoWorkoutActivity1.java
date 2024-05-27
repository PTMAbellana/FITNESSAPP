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

public class VideoWorkoutActivity1 extends AppCompatActivity {
    private VideoView videoView;

    private Button btnPlayPause, btnBack, btnForward;
    private SeekBar videoSeekBar;
    private Handler handler = new Handler();
    private boolean isPlaying = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_workout_activity1);

        videoView = findViewById(R.id.videoView);
        btnPlayPause = findViewById(R.id.btnPlayPause);
        btnBack = findViewById(R.id.btnWorkoutBack);
        btnForward = findViewById(R.id.btnForward);
        videoSeekBar = findViewById(R.id.videoSeekBar);

        String fileName = "videoworkout1";
        String filePlace = "android.resource://" + getPackageName() + "/raw/" + fileName;
        videoView.setVideoURI(Uri.parse(filePlace));

        btnPlayPause.setOnClickListener(view -> {
            if (isPlaying) {
                videoView.pause();
                btnPlayPause.setText("Play");
            } else {
                videoView.start();
                btnPlayPause.setText("Pause");
                updateSeekBar();
            }
            isPlaying = !isPlaying;
        });

        btnBack.setOnClickListener(view -> {
            int currentPosition = videoView.getCurrentPosition();
            videoView.seekTo(Math.max(currentPosition - 5000, 0));
        });

        btnForward.setOnClickListener(view -> {
            int currentPosition = videoView.getCurrentPosition();
            videoView.seekTo(Math.min(currentPosition + 5000, videoView.getDuration()));
        });


        videoSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    int newPosition = (int) ((progress / 100.0) * videoView.getDuration());
                    videoView.seekTo(newPosition);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // Pause video while seeking
                videoView.pause();
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // Resume video after seeking
                videoView.start();
            }
        });

        videoView.setOnPreparedListener(mediaPlayer -> {
            videoSeekBar.setMax(100);
            updateSeekBar();
        });
    }

    private void updateSeekBar() {
        handler.postDelayed(updateRunnable, 1000);
    }

    private Runnable updateRunnable = new Runnable() {
        @Override
        public void run() {
            if (videoView != null && videoView.isPlaying()) {
                int currentPosition = videoView.getCurrentPosition();
                int duration = videoView.getDuration();
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
        handler.removeCallbacks(updateRunnable);
    }
    public void onBackClicked(View view) {
//        Intent intent = new Intent(this, HomeView.class);
//        startActivity(intent);
        finish();
    }

}