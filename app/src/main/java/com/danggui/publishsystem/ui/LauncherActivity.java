package com.danggui.publishsystem.ui;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.danggui.publishsystem.R;
import com.danggui.publishsystem.utils.SharedPreferenceManager;

public class LauncherActivity extends AppCompatActivity {
    private static final int DELAY_TIME = 1000;
    private Handler handler = new Handler();
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if (isFirstUse()){
                startActivity(new Intent(LauncherActivity.this,LauncherViewPagerActivity.class));
                finish();
            }
            else {
                startActivity(new Intent(LauncherActivity.this,MainActivity.class));
                finish();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);
        handler.postDelayed(runnable,DELAY_TIME);
    }

    private boolean isFirstUse() {
        boolean isFirstUse = SharedPreferenceManager.getIsFirstUse();
        if (isFirstUse) {
            SharedPreferenceManager.setIsFirstUse(false);
            return true;
        }
        return false;
    }
}
