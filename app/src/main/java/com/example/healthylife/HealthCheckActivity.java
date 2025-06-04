package com.example.healthylife;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class HealthCheckActivity extends AppCompatActivity {

    private CheckBox checkboxWater, checkboxExercise, checkboxSleep, checkboxScreen;
    private SharedPreferences prefs;
    private String todayStr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health_check);

        checkboxWater = findViewById(R.id.checkbox_water);
        checkboxExercise = findViewById(R.id.checkbox_exercise);
        checkboxSleep = findViewById(R.id.checkbox_sleep);
        checkboxScreen = findViewById(R.id.checkbox_screen);
        Button btnSave = findViewById(R.id.btn_save_checkin);
        Button btnStats = findViewById(R.id.btn_stats);

        prefs = getSharedPreferences("HealthCheckPrefs", MODE_PRIVATE);
        todayStr = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());

        checkboxWater.setChecked(prefs.getBoolean(todayStr + "_water", false));
        checkboxExercise.setChecked(prefs.getBoolean(todayStr + "_exercise", false));
        checkboxSleep.setChecked(prefs.getBoolean(todayStr + "_sleep", false));
        checkboxScreen.setChecked(prefs.getBoolean(todayStr + "_screen", false));

        btnSave.setOnClickListener(v -> {
            SharedPreferences.Editor editor = prefs.edit();
            editor.putBoolean(todayStr + "_water", checkboxWater.isChecked());
            editor.putBoolean(todayStr + "_exercise", checkboxExercise.isChecked());
            editor.putBoolean(todayStr + "_sleep", checkboxSleep.isChecked());
            editor.putBoolean(todayStr + "_screen", checkboxScreen.isChecked());
            editor.apply();
            Toast.makeText(this, "今日打卡已保存！", Toast.LENGTH_SHORT).show();
        });

        btnStats.setOnClickListener(v -> {
            Intent intent = new Intent(HealthCheckActivity.this, CheckinStatsActivity.class);
            startActivity(intent);
        });
    }
}


