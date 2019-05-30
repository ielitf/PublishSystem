package com.hskj.publishsystem.ui.publish;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ImageView;

import com.hskj.publishsystem.R;
import com.hskj.publishsystem.adapter.ExpandableListViewAdapter;
import com.hskj.publishsystem.base.BaseActivity;
import com.hskj.publishsystem.bean.ExpandListChildBean;
import com.hskj.publishsystem.bean.NewsItemBean;
import com.hskj.publishsystem.data.ExpandChildData;
import com.hskj.publishsystem.data.NewsData;

import java.util.ArrayList;

public class PublishNewsDetailsActivity extends BaseActivity {
    private ExpandableListView listView;
    private ExpandableListViewAdapter adapter;
    private ImageView arrowIma;

    private ArrayList<String> grouplist;
    private ArrayList<ArrayList<ExpandListChildBean>> childlist = new ArrayList<>();
    private ArrayList<ExpandListChildBean> list;
    private int lastClick = -1;//上一次点击的group的position
    @Override
    protected void setLayout() {
        setContentView(R.layout.activity_publish_news_details);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        listView = findViewById(R.id.publish_detail_device);
        initDate();
        adapter = new ExpandableListViewAdapter(this, grouplist, childlist);
        listView.setAdapter(adapter);
        listView.expandGroup(0);

        /**
         * 点击一个列表，其他的列表都关闭
         */
        listView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                if (lastClick == -1) {
//                    listView.expandGroup(groupPosition);
                    listView.collapseGroup(groupPosition);
                }
                if (lastClick != -1 && lastClick != groupPosition) {
                    listView.collapseGroup(lastClick);
                    listView.expandGroup(groupPosition);
                } else if (lastClick == groupPosition) {
                    if (listView.isGroupExpanded(groupPosition))
                        listView.collapseGroup(groupPosition);
                    else if (!listView.isGroupExpanded(groupPosition))
                        listView.expandGroup(groupPosition);
                }
                lastClick = groupPosition;
                return true;
            }
        });

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
        return "发布详情";
    }

    private void initDate() {
        grouplist = new ArrayList<>();
        grouplist.add("北京店面");
        grouplist.add("上海店面");
        for (int j = 0; j < grouplist.size(); j++) {
            list = new ArrayList<>();
            for (int i = 0; i < ExpandChildData.child_check.length; i++) {
                list.add(new ExpandListChildBean(ExpandChildData.child_check[i], ExpandChildData.child_title[i]));
            }
            childlist.add(list);
        }
    }
}
