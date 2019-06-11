package com.danggui.publishsystem.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.danggui.publishsystem.R;

import java.util.ArrayList;
import java.util.List;

public class LauncherViewPagerActivity extends AppCompatActivity {
    private ViewPager viewPager;
    private List<View> list = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher_view_pager);
        viewPager = findViewById(R.id.nav_viewPager);
        addViewToList();
        viewPager.setAdapter(new ViewPagerAdapter(list));

        View v  = list.get(1);
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(LauncherViewPagerActivity.this,"dianij",Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void addViewToList() {
        list = new ArrayList<View>();
        RelativeLayout tempView = (RelativeLayout) getLayoutInflater().inflate(
                R.layout.activity_launcher_view_pager, null);
        tempView.setBackgroundResource(R.drawable.boot_page1);
        list.add(tempView);

//        tempView = (RelativeLayout) getLayoutInflater().inflate(
//                R.layout.activity_launcher_view_pager, null);
//        tempView.setBackgroundResource(R.drawable.boot_page2);
//        list.add(tempView);

        tempView = (RelativeLayout) getLayoutInflater().inflate(
                R.layout.activity_launcher_view_pager, null);

        Button jumpButton = new Button(this);
        RelativeLayout.LayoutParams buttonParam = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                (int) getResources().getDimension(R.dimen.jump_btn_height));

        buttonParam.addRule(RelativeLayout.CENTER_HORIZONTAL);
        buttonParam.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        jumpButton.setLayoutParams(buttonParam);
        jumpButton.setBackgroundResource(R.mipmap.btn_jump);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
            jumpButton.setPaddingRelative(20, 20, 20, 20);
        }

        jumpButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                startActivity(new Intent(LauncherViewPagerActivity.this,MainActivity.class));
                finish();
            }
        });
        tempView.setBackgroundResource(R.drawable.boot_page2);
        tempView.addView(jumpButton);
        list.add(tempView);

    }
    class ViewPagerAdapter extends PagerAdapter {

        private List<View> list = null;

        public ViewPagerAdapter(List<View> list) {
            this.list = list;
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(list.get(position));
            return list.get(position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(list.get(position));
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

    }
}
