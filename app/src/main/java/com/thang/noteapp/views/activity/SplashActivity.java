package com.thang.noteapp.views.activity;

import androidx.annotation.Nullable;
import android.content.Intent;
import android.os.Bundle;
import com.thang.noteapp.R;
import java.util.Timer;
import java.util.TimerTask;

public class SplashActivity extends BaseActivity {

    @Override
    protected int getActivityLayoutId() {
        return R.layout.splash_main;
    }

    @Override
    protected void initialize(@Nullable Bundle savedInstanceState) {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                startActivity(new Intent(SplashActivity.this, MainActivity.class));
                finish();
            }
        }, 500);
    }
}