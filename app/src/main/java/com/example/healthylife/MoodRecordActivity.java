package com.example.healthylife;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

public class MoodRecordActivity extends AppCompatActivity {

    RadioGroup rgMood;
    EditText etNote;
    Button btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mood_record);

        rgMood = findViewById(R.id.rg_mood);
        etNote = findViewById(R.id.et_note);
        btnSave = findViewById(R.id.btn_save_mood);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int checkedId = rgMood.getCheckedRadioButtonId();
                String mood = "";
                if (checkedId == R.id.rb_happy) {
                    mood = "😊 开心";
                } else if (checkedId == R.id.rb_normal) {
                    mood = "😐 一般";
                } else if (checkedId == R.id.rb_sad) {
                    mood = "😢 难过";
                } else {
                    Toast.makeText(MoodRecordActivity.this, "请选择一个心情", Toast.LENGTH_SHORT).show();
                    return;
                }

                String note = etNote.getText().toString();
                long timestamp = System.currentTimeMillis();

                SharedPreferences prefs = getSharedPreferences("MoodRecord", MODE_PRIVATE);
                SharedPreferences.Editor editor = prefs.edit();
                editor.putString("mood", mood);
                editor.putString("note", note);
                editor.putLong("timestamp", timestamp);
                editor.apply();

                Toast.makeText(MoodRecordActivity.this, "记录已保存！", Toast.LENGTH_SHORT).show();
                finish(); // 结束返回
            }
        });
    }
}
