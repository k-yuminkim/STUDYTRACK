package com.example.studytrack;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;

public class IntroActivity extends AppCompatActivity {

    public static Activity introActivity;

    private ArrayList<String[]> quotes = new ArrayList<>();

    private void add() {
        quotes.add(new String[]{ "지식보다 중요한 것은 상상력이다." , "Albert Einstein" });
        quotes.add(new String[]{ "나약한 태도는 성격도 나약하게 만든다." , "Albert Einstein" });
        quotes.add(new String[]{ "성공한 사람보다는 가치 있는 사람이 되라." , "Albert Einstein" });
    }

    private String get() {
        add();
        int i = new Random().nextInt(quotes.size());
        return "\"" + quotes.get(i)[0] + "\"\n— " + quotes.get(i)[1];
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        introActivity = IntroActivity.this;

        TextView tvQuote = findViewById(R.id.tvQuote);
        tvQuote.setText(get());

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(IntroActivity.this, MainActivity.class);
                startActivity(intent);
            }
        }, 2_500);
    }
}