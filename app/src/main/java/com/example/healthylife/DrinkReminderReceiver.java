package com.example.healthylife;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class DrinkReminderReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "喝水时间到啦，记得补充水分哦！", Toast.LENGTH_LONG).show();
    }
}
