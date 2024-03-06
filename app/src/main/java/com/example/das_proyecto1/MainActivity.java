package com.example.das_proyecto1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.os.Handler;
import android.content.Intent;

public class MainActivity extends AppCompatActivity {
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Simulate a 5-second delay (5000 milliseconds)
        new Handler(getMainLooper()).postDelayed(() -> {
            // Start your actual content/activity after the delay
            // For example, start HomeActivity
            Intent intent = new Intent(this, HomeActivity.class);
            startActivity(intent);
            finish(); // Close the splash screen activity
        }, 3000); // 5000 milliseconds = 5 seconds
    }
}