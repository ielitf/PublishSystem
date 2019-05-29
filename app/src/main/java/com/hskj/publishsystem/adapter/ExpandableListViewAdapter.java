package com.hskj.publishsystem.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.hskj.publishsystem.R;
import com.hskj.publishsystem.bean.ExpandListChildBean;
import com.hskj.publishsystem.bean.NewsItemBean;
import com.hskj.publishsystem.widget.NoScrollListView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class ExpandableListViewAdapter extends BaseExpandableListAdapter {

    private List<String> grouplist;
    private ArrayList<ArrayList<ExpandListChildBean>> childlist;
    private Context context;
    private final LayoutInflater inflater;

    public ExpandableListViewAdapter(Context context, List<String> grouplist, ArrayList<ArrayList<ExpandListChildBean>> childlist) {
        this.grouplist = grouplist;
        this.childlist = childlist;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getGroupCount() {
        return grouplist.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return 1;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return grouplist.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return childlist.get(groupPosition).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        GroupViewHolder groupViewHolder;
        if (convertView == null) {
            groupViewHolder = new GroupViewHolder();
            convertView = inflater.inflate(R.layout.item_group, null);
            groupViewHolder.tv_elv = convertView.findViewById(R.id.group_tv);
            groupViewHolder.img = convertView.findViewById(R.id.group_ico);

            groupViewHolder.img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, "我是图片", Toast.LENGTH_SHORT).show();
                }
            });
            convertView.setTag(groupViewHolder);
        } else {
            groupViewHolder = (GroupViewHolder) convertView.getTag();
        }
        groupViewHolder.tv_elv.setText(grouplist.get(groupPosition));
        return convertView;
    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ChildViewHolder childViewHolder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_child_listview, null);
            childViewHolder = new ChildViewHolder();
            childViewHolder.listView = convertView.findViewById(R.id.item_child_list);
            convertView.setTag(childViewHolder);
        } else {
            childViewHolder = (ChildViewHolder) convertView.getTag();
        }
        //得到数据
        ArrayList<ExpandListChildBean> listBeans = childlist.get(groupPosition);

        Log.i("========listBeans", listBeans.size() + "");
        //创建适配器
        ExpandChildListAdapter elvRvAdapter = new ExpandChildListAdapter(context, listBeans);
        childViewHolder.listView.setAdapter(elvRvAdapter);

        childViewHolder.listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(context, groupPosition + "," + position, Toast.LENGTH_SHORT).show();
            }
        });

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

    class GroupViewHolder {
        TextView tv_elv;
        ImageView img;
        ImageView group_arrow;

    }

    class ChildViewHolder {
        NoScrollListView listView;
    }
}



