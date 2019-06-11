package com.danggui.publishsystem.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.danggui.publishsystem.R;
import com.danggui.publishsystem.mydata.ExpandListChildBean;
import com.danggui.publishsystem.mylistener.AdapterCallBack;
import com.danggui.publishsystem.widget.NoScrollListView;

import java.util.ArrayList;
import java.util.List;

public class PublishNewsDetailsAdapter extends BaseExpandableListAdapter {
    private AdapterCallBack adapterCallBack;
    private List<String> grouplist;
    private List<List<ExpandListChildBean>> childlist;
    private Context context;
    private final LayoutInflater inflater;

    public PublishNewsDetailsAdapter(Context context, List<String> grouplist, List<List<ExpandListChildBean>> childlist) {
        this.grouplist = grouplist;
        this.childlist = childlist;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }
    public  void setAdapterCallBack(AdapterCallBack callBack){
        adapterCallBack = callBack;
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
    public View getGroupView(final int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        GroupViewHolder groupViewHolder;
        if (convertView == null) {
            groupViewHolder = new GroupViewHolder();
            convertView = inflater.inflate(R.layout.item_group, null);
            groupViewHolder.tv_elv = convertView.findViewById(R.id.group_tv);
            groupViewHolder.checkBox = convertView.findViewById(R.id.group_checkBox);

            convertView.setTag(groupViewHolder);
        } else {
            groupViewHolder = (GroupViewHolder) convertView.getTag();
        }
        groupViewHolder.tv_elv.setText(grouplist.get(groupPosition));
        groupViewHolder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                    Toast.makeText(context,groupPosition+","+isChecked,Toast.LENGTH_SHORT).show();
                    adapterCallBack.setGroupPosition(groupPosition,isChecked);
            }
        });
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
        if(childlist.size()>0){
            //得到数据
            List<ExpandListChildBean> listBeans = childlist.get(groupPosition);
            Log.i("========listBeans", listBeans.size() + "");
            //创建适配器
//            ExpandChildListAdapter3 elvRvAdapter = new ExpandChildListAdapter3(context, listBeans);
            PublishNewsDetailsChildAdapter elvRvAdapter = new PublishNewsDetailsChildAdapter(context, listBeans);
            childViewHolder.listView.setAdapter(elvRvAdapter);
            childViewHolder.listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Toast.makeText(context, groupPosition + "," + position, Toast.LENGTH_SHORT).show();
                }
            });
        }

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

    class GroupViewHolder {
        TextView tv_elv;
        CheckBox checkBox;

    }

    class ChildViewHolder {
        NoScrollListView listView;
    }
}



