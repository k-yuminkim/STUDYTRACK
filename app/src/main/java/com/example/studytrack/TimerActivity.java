package com.example.studytrack;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;

public class TimerActivity extends AppCompatActivity {

    Chronometer chrono;
    Button btnStop, btnFinish;

    NotificationManager manager;
    NotificationCompat.Builder builder;

    long pause = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);

        notify("스톱워치가 실행 중입니다.");

        chrono = findViewById(R.id.chrono);
        chrono.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
            @Override
            public void onChronometerTick(Chronometer chronometer) {
                long time = SystemClock.elapsedRealtime() - chronometer.getBase();

                int hour = (int) (time / 3_600_000);
                int min = (int) (time - hour * 3_600_000) / 60_000;
                int sec = (int) (time - hour * 3_600_000 - min * 60_000) / 1_000;

                String text = (hour < 10 ? "0" + hour : hour) + " : " + (min < 10 ? "0" + min : min) + " : " + (sec < 10 ? "0" + sec : sec);
                chronometer.setText(text);
            }
        });

        chrono.setBase(SystemClock.elapsedRealtime());
        chrono.start();

        btnStop = findViewById(R.id.btnStop);
        btnStop.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if (btnStop.getText().equals("정지")) {
                    chrono.stop();
                    pause = SystemClock.elapsedRealtime() - chrono.getBase();
                    btnStop.setText("계속");

                    TimerActivity.this.notify("스톱워치가 정지 상태입니다.");

                    ValueAnimator valueAnimator = ValueAnimator.ofObject(new ArgbEvaluator(), Color.WHITE, Color.RED);
                    valueAnimator.setDuration(150);
                    valueAnimator.addUpdateListener(animator -> {
                        chrono.setTextColor((int) animator.getAnimatedValue());
                    });
                    valueAnimator.start();
                } else {
                    chrono.setBase(SystemClock.elapsedRealtime() - pause);
                    chrono.start();
                    btnStop.setText("정지");

                    TimerActivity.this.notify("스톱워치가 실행 중입니다.");

                    ValueAnimator valueAnimator = ValueAnimator.ofObject(new ArgbEvaluator(), Color.RED, Color.WHITE);
                    valueAnimator.setDuration(150);
                    valueAnimator.addUpdateListener(animator -> {
                        chrono.setTextColor((int) animator.getAnimatedValue());
                    });
                    valueAnimator.start();
                }
            }
        });

        btnFinish = findViewById(R.id.btnFinish);
        btnFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO
                finish();
            }
        });

    }

    private void notify(String title) {
        manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        builder = new NotificationCompat.Builder(TimerActivity.this, "TimerActivity")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(title)
                .setOngoing(true);

        Intent intent = new Intent(TimerActivity.this, TimerActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(TimerActivity.this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pendingIntent);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("TimerActivity", "Timer", NotificationManager.IMPORTANCE_HIGH);
            manager.createNotificationChannel(channel);
        }

        manager.notify(2022, builder.build());
    }

    @Override
    protected void onDestroy() {
        manager.cancelAll();
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        btnFinish.callOnClick();
    }
}