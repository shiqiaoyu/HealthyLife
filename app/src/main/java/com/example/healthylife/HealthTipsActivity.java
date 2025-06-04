package com.example.healthylife;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.Random;

public class HealthTipsActivity extends AppCompatActivity {

    private TextView tvYangsheng;
    private TextView tvJiankang;

    private static final String YANGSHENG_URL = "http://health.people.com.cn/GB/408572/index.html";
    private static final String JIANKANG_URL = "http://health.people.com.cn/GB/408571/index.html";
    private static final String ROOT_URL = "http://health.people.com.cn";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health_tips);

        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().permitAll().build());

        tvYangsheng = findViewById(R.id.tvYangsheng);
        tvJiankang = findViewById(R.id.tvJiankang);

        fetchRandomArticles();
    }

    private void fetchRandomArticles() {
        new Thread(() -> {
            try {
                // 从养生栏目随机抽取一篇
                Document docYang = Jsoup.connect(YANGSHENG_URL)
                        .timeout(10000)
                        .get();
                Elements yangItems = docYang.select("div.newsItems a");
                if (!yangItems.isEmpty()) {
                    Element yangArticle = yangItems.get(new Random().nextInt(yangItems.size()));
                    String yangTitle = yangArticle.text();
                    String yangLink = ROOT_URL + yangArticle.attr("href");

                    runOnUiThread(() -> {
                        tvYangsheng.setText("【养生推荐】 " + yangTitle);
                        tvYangsheng.setOnClickListener(v -> {
                            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(yangLink));
                            startActivity(intent);
                        });
                    });
                } else {
                    runOnUiThread(() -> tvYangsheng.setText("养生栏目暂无内容"));
                }

                // 从健身栏目随机抽取一篇
                Document docJian = Jsoup.connect(JIANKANG_URL)
                        .timeout(10000)
                        .get();
                Elements jianItems = docJian.select("div.newsItems a");
                if (!jianItems.isEmpty()) {
                    Element jianArticle = jianItems.get(new Random().nextInt(jianItems.size()));
                    String jianTitle = jianArticle.text();
                    String jianLink = ROOT_URL + jianArticle.attr("href");

                    runOnUiThread(() -> {
                        tvJiankang.setText("【科学健身】 " + jianTitle);
                        tvJiankang.setOnClickListener(v -> {
                            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(jianLink));
                            startActivity(intent);
                        });
                    });
                } else {
                    runOnUiThread(() -> tvJiankang.setText("健身栏目暂无内容"));
                }

            } catch (Exception e) {
                e.printStackTrace();
                runOnUiThread(() -> {
                    Toast.makeText(HealthTipsActivity.this, "加载健康小贴士失败，请检查网络", Toast.LENGTH_LONG).show();
                    tvYangsheng.setText("加载失败");
                    tvJiankang.setText("加载失败");
                });
            }
        }).start();
    }
}