package com.example.healthylife;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;

import java.text.SimpleDateFormat;
import java.util.*;

public class CheckinStatsActivity extends AppCompatActivity {

    private MaterialCalendarView calendarView;
    private TextView tvTotalDays, tvStreak, tvCongrats;
    private SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkin_stats);

        calendarView = findViewById(R.id.calendarView);
        tvTotalDays = findViewById(R.id.tv_total_days);
        tvStreak = findViewById(R.id.tv_streak);
        tvCongrats = findViewById(R.id.tv_congrats);
        prefs = getSharedPreferences("HealthCheckPrefs", MODE_PRIVATE);

        List<CalendarDay> checkedDates = new ArrayList<>();
        int totalDays = 0;
        int currentStreak = 0;

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

        // 当月
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        Date firstDate = calendar.getTime();
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        Date lastDate = calendar.getTime();
        calendar.setTime(firstDate);

        while (!calendar.getTime().after(lastDate)) {
            String dateKey = sdf.format(calendar.getTime());
            if (prefs.getBoolean(dateKey + "_water", false) ||
                    prefs.getBoolean(dateKey + "_exercise", false) ||
                    prefs.getBoolean(dateKey + "_sleep", false) ||
                    prefs.getBoolean(dateKey + "_screen", false)) {
                totalDays++;
                checkedDates.add(CalendarDay.from(calendar));
            }
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }

        // 连续打卡
        calendar.setTime(new Date());
        while (true) {
            String dateKey = sdf.format(calendar.getTime());
            boolean any = prefs.getBoolean(dateKey + "_water", false) ||
                    prefs.getBoolean(dateKey + "_exercise", false) ||
                    prefs.getBoolean(dateKey + "_sleep", false) ||
                    prefs.getBoolean(dateKey + "_screen", false);
            if (any) {
                currentStreak++;
                calendar.add(Calendar.DAY_OF_MONTH, -1);
            } else {
                break;
            }
        }

        calendarView.addDecorator(new DayViewDecorator() {
            @Override
            public boolean shouldDecorate(CalendarDay day) {
                return checkedDates.contains(day);
            }

            @Override
            public void decorate(DayViewFacade view) {
                view.setBackgroundDrawable(getResources().getDrawable(android.R.drawable.presence_online));
            }
        });

        tvTotalDays.setText("本月打卡天数：" + totalDays);
        tvStreak.setText("连续打卡天数：" + currentStreak);

        if (currentStreak >= 7) {
            tvCongrats.setText("🎉 太棒了！你已连续打卡 " + currentStreak + " 天！");
        } else if (currentStreak >= 3) {
            tvCongrats.setText("👍 已连续打卡 " + currentStreak + " 天，加油！");
        } else {
            tvCongrats.setText("开始打卡，养成健康好习惯！");
        }
    }
}
