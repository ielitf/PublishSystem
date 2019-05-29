package com.hskj.publishsystem.ui.me;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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
import android.widget.Toast;

import com.hskj.publishsystem.R;
import com.hskj.publishsystem.adapter.MeItemAdapter;
import com.hskj.publishsystem.bean.MeItemBean;
import com.hskj.publishsystem.widget.RoundImageView;

import java.util.ArrayList;

public class MeFragment extends Fragment {
    private LinearLayout linearLayout_main;
    private Button btnNormal, btnShowList,btnCostom;
    private RoundImageView headImg;
    private View contentView;
    private Context context;
    private ListView listView;
    private ArrayList <MeItemBean> list = new ArrayList<>();
    private MeItemAdapter adapter ;
    private PopupWindow popupWindow;
    private LayoutInflater inflate;

    public MeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        contentView = inflater.inflate(R.layout.fragment_me,container,false);
        headImg = contentView.findViewById(R.id.headImg);
        listView = contentView.findViewById(R.id.me_list);
        linearLayout_main = contentView.findViewById(R.id.linearLayout_main);

        context = getActivity();
        list.add(new MeItemBean(R.mipmap.me_system_update,"系统更新"));
        list.add(new MeItemBean(R.mipmap.me_clear_cache,"清理缓存"));
        list.add(new MeItemBean(R.mipmap.me_system_exit,"退出系统"));
        adapter = new MeItemAdapter(context,list);
        listView.setAdapter(adapter);
        headImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                startActivity(new Intent(getActivity(),LoginActivity.class));
                ShowCostomPopupWindow();
            }
        });
        return contentView;
    }
    private void ShowCostomPopupWindow() {
        inflate = LayoutInflater.from(context);
        View contentView = inflate.inflate(R.layout.select_pic_dialog, null);
        popupWindow  = new PopupWindow(contentView, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
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
                Toast.makeText(context, "不要点我", Toast.LENGTH_SHORT).show();
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

}
