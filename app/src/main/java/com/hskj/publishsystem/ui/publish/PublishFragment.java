package com.hskj.publishsystem.ui.publish;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hskj.publishsystem.R;

public class PublishFragment extends Fragment implements View.OnClickListener{
    private Fragment mContent = null;
    private PublishNotFragment publishNotFragment = null;
    private PublishYetFragment publishYetFragment = null;
    private FragmentManager fragmentManager;
    private LinearLayout publish_yet_ll, publish_not_ll;
    private TextView publish_not_tv, publish_not_line, publish_yet_tv, publish_yet_line;
    private View contentView;
    private Context context;
    public PublishFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        contentView = inflater.inflate(R.layout.fragment_publish, container, false);
        context = getActivity();
        initViews(contentView);
        publish_yet_ll.setOnClickListener(this);
        publish_not_ll.setOnClickListener(this);


        fragmentManager = getChildFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        mContent = publishNotFragment;
        fragmentTransaction.add(R.id.publish_fragment_con, publishNotFragment);
        fragmentTransaction.commit();

        return contentView;
    }

    private void initViews(View contentView) {
        publish_yet_ll = contentView.findViewById(R.id.publish_yet_ll);
        publish_not_ll = contentView.findViewById(R.id.publish_not_ll);
        publish_not_tv = contentView.findViewById(R.id.publish_not_tv);
        publish_not_line = contentView.findViewById(R.id.publish_not_line);
        publish_yet_tv = contentView.findViewById(R.id.publish_yet_tv);
        publish_yet_line = contentView.findViewById(R.id.publish_yet_line);
        publishNotFragment = new PublishNotFragment();
        publishYetFragment = new PublishYetFragment();
    }
    private void switchContent(Fragment to) {
        if (mContent != to) {
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            if (!to.isAdded()) { // 判断是否被add过
                // 隐藏当前的fragment，将 下一个fragment 添加进去
                transaction.hide(mContent).add(R.id.publish_fragment_con, to).commit();
            } else {
                // 隐藏当前的fragment，显示下一个fragment
                transaction.hide(mContent).show(to).commit();
            }
            mContent = to;
        }
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.publish_not_ll://未发布
                publish_not_tv.setTextColor(getResources().getColor(R.color.text_black));
                publish_not_line.setBackgroundColor(getResources().getColor(R.color.text_nav));
                publish_yet_tv.setTextColor(getResources().getColor(R.color.text_gray));
                publish_yet_line.setBackgroundColor(getResources().getColor(R.color.transparent));
                switchContent(publishNotFragment);
                break;
            case R.id.publish_yet_ll:
                publish_not_tv.setTextColor(getResources().getColor(R.color.text_gray));
                publish_not_line.setBackgroundColor(getResources().getColor(R.color.transparent));
                publish_yet_tv.setTextColor(getResources().getColor(R.color.text_black));
                publish_yet_line.setBackgroundColor(getResources().getColor(R.color.text_nav));
                switchContent(publishYetFragment);
                break;
            default:
                break;
        }
    }
}
