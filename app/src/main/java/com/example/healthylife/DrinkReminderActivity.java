package com.example.healthylife;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Switch;
import android.app.TimePickerDialog;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class DrinkReminderActivity extends AppCompatActivity {

    private Button btnSetTime;
    private Switch switchReminder;

    private int hour = 9;   // 默认提醒时间
    private int minute = 0;

    private PendingIntent pendingIntent;
    private AlarmManager alarmManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drink_reminder);

        btnSetTime = findViewById(R.id.btn_set_time);
        switchReminder = findViewById(R.id.switch_reminder);
        alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        btnSetTime.setOnClickListener(v -> {
            TimePickerDialog timePickerDialog = new TimePickerDialog(
                    DrinkReminderActivity.this,
                    (TimePicker view, int hourOfDay, int minuteOfDay) -> {
                        hour = hourOfDay;
                        minute = minuteOfDay;
                        Toast.makeText(DrinkReminderActivity.this,
                                "提醒时间设置为 " + hour + ":" + String.format("%02d", minute),
                                Toast.LENGTH_SHORT).show();
                    },
                    hour, minute, true
            );
            timePickerDialog.show();
        });

        switchReminder.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                setReminder();
            } else {
                cancelReminder();
            }
        });
    }

    private void setReminder() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, 0);

        Intent intent = new Intent(this, DrinkReminderReceiver.class);
        pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_IMMUTABLE);

        alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
        Toast.makeText(this, "饮水提醒已设置", Toast.LENGTH_SHORT).show();
    }

    private void cancelReminder() {
        if (pendingIntent != null) {
            alarmManager.cancel(pendingIntent);
            Toast.makeText(this, "饮水提醒已取消", Toast.LENGTH_SHORT).show();
        }
    }
}
