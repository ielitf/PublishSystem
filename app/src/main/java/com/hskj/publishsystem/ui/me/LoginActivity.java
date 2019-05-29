package com.hskj.publishsystem.ui.me;

import android.os.Bundle;
import com.hskj.publishsystem.R;
import com.hskj.publishsystem.base.BaseActivity;

public class LoginActivity extends BaseActivity {

    @Override
    protected void setLayout() {
        setContentView(R.layout.activity_login);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initViews() {

    }

    @Override
    protected boolean isNeedInitBack() {
        return true;
    }

    @Override
    protected String getTopbarTitle() {
        return "登录";
    }
}
