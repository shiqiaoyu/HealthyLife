package com.example.healthylife;

import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

public class BMICalculatorActivity extends AppCompatActivity {

    EditText etHeight, etWeight;
    Button btnCalculate;
    TextView tvResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bmicalculator);

        etHeight = findViewById(R.id.et_height);
        etWeight = findViewById(R.id.et_weight);
        btnCalculate = findViewById(R.id.btn_calculate_bmi);
        tvResult = findViewById(R.id.tv_result);

        btnCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String heightStr = etHeight.getText().toString();
                String weightStr = etWeight.getText().toString();

                if (heightStr.isEmpty() || weightStr.isEmpty()) {
                    Toast.makeText(BMICalculatorActivity.this, "请输入身高和体重", Toast.LENGTH_SHORT).show();
                    return;
                }

                float height = Float.parseFloat(heightStr) / 100; // 转为米
                float weight = Float.parseFloat(weightStr);
                float bmi = weight / (height * height);

                String suggestion;
                if (bmi < 18.5) {
                    suggestion = "偏瘦，注意营养";
                } else if (bmi < 24.9) {
                    suggestion = "正常，保持良好生活习惯";
                } else if (bmi < 29.9) {
                    suggestion = "超重，适量运动";
                } else {
                    suggestion = "肥胖，建议控制饮食+锻炼";
                }

                String result = String.format("您的BMI是：%.2f\n健康建议：%s", bmi, suggestion);
                tvResult.setText(result);
            }
        });
    }
}
