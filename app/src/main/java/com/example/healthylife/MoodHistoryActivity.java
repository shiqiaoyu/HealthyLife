package com.example.healthylife;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Set;

public class MoodHistoryActivity extends AppCompatActivity {

    private ListView listView;
    private final String PREF_NAME = "MoodRecord";
    private final String MOOD_KEY = "mood_list";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mood_history);

        listView = findViewById(R.id.lv_mood_history);

        SharedPreferences prefs = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        Set<String> moodSet = prefs.getStringSet(MOOD_KEY, null);

        ArrayList<String> moodList = new ArrayList<>();
        if (moodSet != null) {
            moodList.addAll(moodSet);
        }

        moodList.sort((a, b) -> b.compareTo(a));

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this, android.R.layout.simple_list_item_1, moodList
        );
        listView.setAdapter(adapter);
    }
}

