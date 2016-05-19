package com.example.eduardoluttinger.reaxiumforparents.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.WindowManager;

import com.example.eduardoluttinger.reaxiumforparents.GGMainActivity;
import com.example.eduardoluttinger.reaxiumforparents.GGMainFragment;
import com.example.eduardoluttinger.reaxiumforparents.R;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Eduardo Luttinger on 10/05/2016.
 */
public class SplashActivity extends GGMainActivity {

    private static final long SPLASH_SCREEN_DELAY = 2000;

    @Override
    protected Integer getMainLayout() {
        return R.layout.splash_activity;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!isTaskRoot()) {
            finish();
            return;
        }
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    @Override
    public void runMyFragment(GGMainFragment fragment, Bundle params, int drawerId) {

    }

    @Override
    protected void setViews() {
        finishTheSplash();
    }

    @Override
    protected void setViewsEvents() {

    }

    private void finishTheSplash() {
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                Intent goToLoginPage = new Intent(SplashActivity.this, LoginActivity.class);
                startActivity(goToLoginPage);
            }
        };
        Timer timer = new Timer();
        timer.schedule(task, SPLASH_SCREEN_DELAY);
    }

    @Override
    protected GGMainFragment getMainFragment() {
        return null;
    }

    @Override
    protected Integer getMainToolbarId() {
        return null;
    }
}
