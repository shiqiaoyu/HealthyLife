package com.example.healthylife;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class HealthCheckActivity extends AppCompatActivity {

    CheckBox cbSleep, cbExercise, cbBreakfast;
    Button btnSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health_check);

        cbSleep = findViewById(R.id.cb_sleep);
        cbExercise = findViewById(R.id.cb_exercise);
        cbBreakfast = findViewById(R.id.cb_breakfast);
        btnSubmit = findViewById(R.id.btn_submit);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean sleep = cbSleep.isChecked();
                boolean exercise = cbExercise.isChecked();
                boolean breakfast = cbBreakfast.isChecked();

                SharedPreferences prefs = getSharedPreferences("HealthCheck", MODE_PRIVATE);
                SharedPreferences.Editor editor = prefs.edit();

                editor.putBoolean("sleep", sleep);
                editor.putBoolean("exercise", exercise);
                editor.putBoolean("breakfast", breakfast);
                editor.putLong("timestamp", System.currentTimeMillis());
                editor.apply();

                Toast.makeText(HealthCheckActivity.this, "打卡成功！", Toast.LENGTH_SHORT).show();
                finish(); // 返回主页
            }
        });
    }
}
