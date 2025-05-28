package com.example.healthylife;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class HealthTipsActivity extends AppCompatActivity {

    TextView tvTips;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health_tips);

        tvTips = findViewById(R.id.tv_tips);

        String tips = "1. 每天保持充足的饮水量，成年人建议每天饮水 1500~2000ml。\n\n"
                + "2. 保证规律作息，尽量每天 23:00 前入睡，睡眠时间不少于 7 小时。\n\n"
                + "3. 每周至少进行 3 次有氧运动，每次不少于 30 分钟。\n\n"
                + "4. 减少高盐、高糖、高油饮食，多吃蔬果和粗粮。\n\n"
                + "5. 保持良好心态，避免长期处于紧张压力之中。\n\n"
                + "6. 定期体检，特别是关注血压、血糖、血脂、视力、体重等指标。\n\n"
                + "7. 居家要勤通风，每次通风不少于 15 分钟。\n\n"
                + "8. 每天远离电子设备一段时间，保护视力与注意力集中。";

        tvTips.setText(tips);
    }
}
