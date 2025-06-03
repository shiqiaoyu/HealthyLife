package com.example.healthylife;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    Button btnHealthCheck, btnDrinkReminder, btnMoodRecord, btnBMICalculator, btnHealthTips;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnHealthCheck = findViewById(R.id.btn_health_check);
        btnMoodRecord = findViewById(R.id.btn_mood_record);
        btnBMICalculator = findViewById(R.id.btn_bmi_calculator);
        btnHealthTips = findViewById(R.id.btn_health_tips);

        btnHealthCheck.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, HealthCheckActivity.class));
        });

        btnMoodRecord.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, MoodRecordActivity.class));
        });

        btnBMICalculator.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, BMICalculatorActivity.class));
        });

        btnHealthTips.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, HealthTipsActivity.class));
        });
    }
}


