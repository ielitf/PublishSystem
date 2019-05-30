package com.hskj.publishsystem.ui.me;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.view.WindowManager.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hskj.publishsystem.R;
import com.hskj.publishsystem.adapter.MeItemAdapter;
import com.hskj.publishsystem.base.BaseFragment;
import com.hskj.publishsystem.bean.MeItemBean;
import com.hskj.publishsystem.utils.LogUtil;
import com.hskj.publishsystem.widget.RoundImageView;

import java.util.ArrayList;

public class MeFragment extends BaseFragment implements View.OnClickListener {
    private LinearLayout linearLayout_main;
    private Button btnNormal, btnShowList, btnCostom;
    private RoundImageView headImg;
    private View contentView;
    private ListView listView;
    private ArrayList<MeItemBean> list = new ArrayList<>();
    private MeItemAdapter adapter;
    private PopupWindow popupWindow;
    private LayoutInflater inflate;
    private String custId;
    private String custName;
    private String avatarUrl;
    private String integralStr;
    private String signDateStr;
    private boolean isRefreshingUserInfo = false;
    private TextView login_name;

    public MeFragment() {
        // Required empty public constructor
    }

    @Override
    protected void onCreateView(Bundle savedInstanceState) {
        setContentView(R.layout.fragment_me);
        listView = (ListView) findViewById(R.id.me_list);
        linearLayout_main = (LinearLayout) findViewById(R.id.linearLayout_main);
        headImg = (RoundImageView) findViewById(R.id.me_touxiang);
        login_name = (TextView) findViewById(R.id.cust_name_tv);
        login_name.setOnClickListener(this);
        headImg.setOnClickListener(this);
        list.add(new MeItemBean(R.mipmap.me_system_update, "系统更新"));
        list.add(new MeItemBean(R.mipmap.me_clear_cache, "清理缓存"));
        list.add(new MeItemBean(R.mipmap.me_system_exit, "退出系统"));
        adapter = new MeItemAdapter(activity, list);
        listView.setAdapter(adapter);
    }

    @Override
    protected boolean hasPopWindow() {
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

    @Override
    public void onResume() {
        super.onResume();

    }

    private void ShowCostomPopupWindow() {
        inflate = LayoutInflater.from(activity);
        View contentView = inflate.inflate(R.layout.select_pic_dialog, null);
        popupWindow = new PopupWindow(contentView, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        popupWindow.setOutsideTouchable(true);//自己决定没用的代码
        popupWindow.setFocusable(true);//获取焦点，此时弹框外的可点击控件点击无效
        popupWindow.setBackgroundDrawable(new ColorDrawable());
        popupWindow.setAnimationStyle(R.style.pw_anim_style);
        // convView.setBackgroundColor(Color.TRANSPARENT);
        popupWindow.showAtLocation(linearLayout_main, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);

        Button takePhoto = (Button) contentView.findViewById(R.id.btn_take_photo);
        takePhoto.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Toast.makeText(activity, "不要点我", Toast.LENGTH_SHORT).show();
            }
        });
        Button cancle = (Button) contentView.findViewById(R.id.btn_cancel);
        cancle.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cust_name_tv:
                startActivity(new Intent(activity,LoginActivity.class));
                break;
            case R.id.me_touxiang:
                ShowCostomPopupWindow();
                break;
            default:
                break;
        }
    }
}
