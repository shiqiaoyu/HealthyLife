package com.example.healthylife;

import android.content.Intent;
import android.os.Bundle;
import android.widget.*;

import androidx.appcompat.app.AppCompatActivity;

public class BMICalculatorActivity extends AppCompatActivity {

    EditText etHeight, etWeight;
    Button btnCalculate, btnViewTips;
    TextView tvResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bmicalculator);

        etHeight = findViewById(R.id.et_height);
        etWeight = findViewById(R.id.et_weight);
        btnCalculate = findViewById(R.id.btn_calculate_bmi);
        btnViewTips = findViewById(R.id.btn_view_tips);
        tvResult = findViewById(R.id.tv_result);

        btnCalculate.setOnClickListener(v -> {
            String heightStr = etHeight.getText().toString();
            String weightStr = etWeight.getText().toString();

            if (heightStr.isEmpty() || weightStr.isEmpty()) {
                Toast.makeText(this, "请输入身高和体重", Toast.LENGTH_SHORT).show();
                return;
            }

            double height = Double.parseDouble(heightStr) / 100.0;
            double weight = Double.parseDouble(weightStr);
            double bmi = weight / (height * height);
            String suggestion;

            if (bmi < 18.5) {
                suggestion = "偏瘦，请注意营养均衡。";
            } else if (bmi < 24.9) {
                suggestion = "正常，继续保持！";
            } else if (bmi < 29.9) {
                suggestion = "超重，请适量锻炼。";
            } else {
                suggestion = "肥胖，建议改善饮食和加强锻炼。";
            }

            tvResult.setText(String.format("你的BMI是：%.2f\n%s", bmi, suggestion));
            btnViewTips.setVisibility(Button.VISIBLE);
        });

        btnViewTips.setOnClickListener(v -> {
            Intent intent = new Intent(this, HealthTipsActivity.class);
            startActivity(intent);
        });
    }
}