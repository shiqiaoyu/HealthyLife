package com.example.healthylife;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Switch;
import android.app.TimePickerDialog;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.Calendar;

public class DrinkReminderActivity extends AppCompatActivity {

    private Button btnSetTime;
    private Switch switchReminder;

    private int hour = 9;
    private int minute = 0;

    private PendingIntent pendingIntent;
    private AlarmManager alarmManager;

    private static final String CHANNEL_ID = "drink_reminder_channel";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drink_reminder);

        createNotificationChannel();

        btnSetTime = findViewById(R.id.btn_set_time);
        switchReminder = findViewById(R.id.switch_reminder);
        alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.POST_NOTIFICATIONS)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.POST_NOTIFICATIONS}, 1);
            }
        }//获取通知权限

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
                setDailyReminder();
            } else {
                cancelReminder();
            }
        });
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID,
                    "饮水提醒",
                    NotificationManager.IMPORTANCE_HIGH);
            channel.setDescription("饮水提醒通知渠道");

            NotificationManager manager = getSystemService(NotificationManager.class);
            if (manager != null) {
                manager.createNotificationChannel(channel);
            }
        }
    }

    private void setDailyReminder() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, 0);

        Intent intent = new Intent(this, DrinkReminderReceiver.class);
        pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_IMMUTABLE);

        if (calendar.getTimeInMillis() <= System.currentTimeMillis()) {
            calendar.add(Calendar.DAY_OF_YEAR, 1);
        }

        alarmManager.setRepeating(
                AlarmManager.RTC_WAKEUP,
                calendar.getTimeInMillis(),
                AlarmManager.INTERVAL_DAY,
                pendingIntent
        );

        Toast.makeText(this, "饮水提醒已开启", Toast.LENGTH_SHORT).show();
    }

    private void cancelReminder() {
        if (alarmManager != null && pendingIntent != null) {
            alarmManager.cancel(pendingIntent);
            Toast.makeText(this, "饮水提醒已取消", Toast.LENGTH_SHORT).show();
        }
    }
}

