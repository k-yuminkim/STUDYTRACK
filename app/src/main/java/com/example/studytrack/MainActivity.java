package com.example.studytrack;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        IntroActivity introActivity = (IntroActivity) IntroActivity.introActivity;
        introActivity.finish();

        // TODO DatePicker, List, Button
    }
}