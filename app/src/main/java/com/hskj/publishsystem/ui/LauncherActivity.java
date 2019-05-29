package com.hskj.publishsystem.ui;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.hskj.publishsystem.R;
import com.hskj.publishsystem.utils.SharedPreferenceManager;

public class LauncherActivity extends AppCompatActivity {
    private static final int DELAY_TIME = 2000;
    private Handler handler = new Handler();
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if (isFirstUse()){
                finish();
                startActivity(new Intent(LauncherActivity.this,LauncherViewPagerActivity.class));
            }
            else {
                startActivity(new Intent(LauncherActivity.this,MainActivity.class));
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
