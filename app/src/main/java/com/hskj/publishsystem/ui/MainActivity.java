package com.hskj.publishsystem.ui;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.RadioGroup;
import com.hskj.publishsystem.R;
import com.hskj.publishsystem.base.BaseActivity;
import com.hskj.publishsystem.control.AppManager;
import com.hskj.publishsystem.mylistener.FragmentCallBack;
import com.hskj.publishsystem.ui.check.CheckFragment;
import com.hskj.publishsystem.ui.home.HomeFragment;
import com.hskj.publishsystem.ui.me.MeFragment;
import com.hskj.publishsystem.ui.publish.PublishFragment;

public class MainActivity extends BaseActivity  implements FragmentCallBack {
    private HomeFragment homeFragment = null;
    private Fragment mContent = null;
    private CheckFragment checkFragment = null;
    private PublishFragment publishFragment = null;
    private MeFragment meFragment = null;
    private FragmentManager fragmentManager;
    private RadioGroup radioGroup;
    private static int position;
    private static boolean isClick = false;
    private Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_main);
        super.onCreate(savedInstanceState);
        initView();
        fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        mContent = homeFragment;
        fragmentTransaction.add(R.id.main_activity_con, homeFragment);
        fragmentTransaction.commit();
    }

    @Override
    protected void setLayout() {

    }

    @Override
    protected void initViews() {

    }

    private void initView() {
        homeFragment = new HomeFragment();
        checkFragment = new CheckFragment();
        publishFragment = new PublishFragment();
        meFragment = new MeFragment();

        radioGroup = findViewById(R.id.rGroup_activity_main);
        radioGroup.check(R.id.rBtn_activity_main_main);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rBtn_activity_main_main:
                        switchContent(homeFragment);
                        break;
                    case R.id.rb_activity_main_work_sheet:
                        switchContent(checkFragment);
                        break;
                    case R.id.rb_activity_main_news:
                        switchContent(publishFragment);
                        break;
                    case R.id.rb_activity_main_me:
                        switchContent(meFragment);
                        break;
                    default:
                        break;
                }
            }
        });

    }
    long waitTime = 2000;
    long touchTime = 0;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (KeyEvent.KEYCODE_BACK == keyCode) {
            long currentTime = System.currentTimeMillis();
            if ((currentTime - touchTime) >= waitTime) {
               showToast("再按一次，退出程序");
                touchTime = currentTime;
            } else {
//                finish();
                AppManager.getAppManager().finishAllActivity();
            }
            return true;
        }
        return false;
    }
    @Override
    protected boolean isNeedInitBack() {
        return false;
    }

    @Override
    protected String getTopbarTitle() {
        return null;
    }

    private void switchContent(Fragment to) {
        if (mContent != to) {
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            if (!to.isAdded()) { // 判断是否被add过
                // 隐藏当前的fragment，将 下一个fragment 添加进去
                transaction.hide(mContent).add(R.id.main_activity_con, to).commit();
            } else {
                // 隐藏当前的fragment，显示下一个fragment
                transaction.hide(mContent).show(to).commit();
            }
            mContent = to;
        }
    }

    @Override
    public void setPosition(int position) {
        switch (position) {
            case 1:
                radioGroup.check(R.id.rb_activity_main_work_sheet);
                break;
            case 2:
                radioGroup.check(R.id.rb_activity_main_news);
                break;
            default:
                break;
        }
    }
}