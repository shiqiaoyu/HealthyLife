package com.example.healthylife;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.*;

public class MoodRecordActivity extends AppCompatActivity {

    RadioGroup rgMood;
    EditText etNote;
    Button btnSave, btnViewHistory;

    private final String PREF_NAME = "MoodRecord";
    private final String MOOD_KEY = "mood_list";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mood_record);

        rgMood = findViewById(R.id.rg_mood);
        etNote = findViewById(R.id.et_note);
        btnSave = findViewById(R.id.btn_save_mood);
        btnViewHistory = findViewById(R.id.btn_view_mood_history);

        btnSave.setOnClickListener(v -> saveMoodRecord());

        btnViewHistory.setOnClickListener(v -> {
            Intent intent = new Intent(MoodRecordActivity.this, MoodHistoryActivity.class);
            startActivity(intent);
        });
    }

    private void saveMoodRecord() {
        int checkedId = rgMood.getCheckedRadioButtonId();
        String mood = "";
        if (checkedId == R.id.rb_happy) {
            mood = "😊 开心";
        } else if (checkedId == R.id.rb_normal) {
            mood = "😐 一般";
        } else if (checkedId == R.id.rb_sad) {
            mood = "😢 难过";
        } else {
            Toast.makeText(this, "请选择一个心情", Toast.LENGTH_SHORT).show();
            return;
        }

        String note = etNote.getText().toString().trim();
        String time = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault()).format(new Date());
        String record = time + "  心情：" + mood + (note.isEmpty() ? "" : " 备注：" + note);

        SharedPreferences prefs = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        Set<String> moodSet = prefs.getStringSet(MOOD_KEY, new HashSet<>());
        Set<String> updatedSet = new HashSet<>(moodSet);
        updatedSet.add(record);

        prefs.edit().putStringSet(MOOD_KEY, updatedSet).apply();

        Toast.makeText(this, "记录已保存！", Toast.LENGTH_SHORT).show();
        etNote.setText("");
        rgMood.clearCheck();
    }
}