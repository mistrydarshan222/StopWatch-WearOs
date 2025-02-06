package com.example.darshanassignment1;

import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private Button startStopButton, resetButton;
    private TextView timerText;
    private Handler handler = new Handler();
    private long startTime, elapsedTime = 0;
    private boolean isRunning = false;

    private Runnable updateTimer = new Runnable() {
        @Override
        public void run() {
            if (isRunning) {
                elapsedTime = SystemClock.elapsedRealtime() - startTime;
                timerText.setText(formatTime(elapsedTime));
                handler.postDelayed(this, 100);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize UI components
        startStopButton = findViewById(R.id.start_stop_button);
        resetButton = findViewById(R.id.reset_button);
        timerText = findViewById(R.id.txt_timer);

        // Start/Stop Button logic
        startStopButton.setOnClickListener(v -> toggleStopwatch());

        // Reset Button logic
        resetButton.setOnClickListener(v -> resetStopwatch());
    }

    private void toggleStopwatch() {
        if (!isRunning) {
            // Start Stopwatch
            startTime = SystemClock.elapsedRealtime() - elapsedTime;
            handler.post(updateTimer);
            isRunning = true;
            startStopButton.setText(R.string.stop);  // Change button text to "Stop"
            resetButton.setEnabled(false);    // Disable Reset Button while running
        } else {
            // Stop Stopwatch
            handler.removeCallbacks(updateTimer);
            isRunning = false;
            startStopButton.setText(R.string.start);  // Change button text back to "Start"
            resetButton.setEnabled(true);      // Enable Reset Button when stopped
        }
    }

    private void resetStopwatch() {
        elapsedTime = 0;
        timerText.setText(formatTime(elapsedTime));
        resetButton.setEnabled(false);  // Disable Reset Button after resetting
    }

    private String formatTime(long milliseconds) {
        int seconds = (int) (milliseconds / 1000) % 60;
        int minutes = (int) ((milliseconds / (1000 * 60)) % 60);
        int hours = (int) ((milliseconds / (1000 * 60 * 60)) % 24);
        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }
}
