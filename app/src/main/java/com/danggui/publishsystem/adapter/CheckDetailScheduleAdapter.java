package com.danggui.publishsystem.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.danggui.publishsystem.R;
import com.danggui.publishsystem.bean.CheckDetailListBean;

import java.util.List;

public class CheckDetailScheduleAdapter extends MyBaseAdapter<CheckDetailListBean> {
    private LayoutInflater inflater;
    public CheckDetailScheduleAdapter(Context context, List<CheckDetailListBean> data){
        super(data,context);
        inflater= LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return data.size();
    }

    @Override
    public CheckDetailListBean getItem(int position) {
        // TODO Auto-generated method stub
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        ViewHolder holderView;
        if (convertView == null) {
            convertView=inflater.inflate(R.layout.item_check_detail_schedule, null);
            holderView= new ViewHolder();
            holderView.title = convertView.findViewById(R.id.check_detail_schedule_title);
            holderView.com = convertView.findViewById(R.id.check_detail_schedule_com);
            convertView.setTag(holderView);
        } else {
            holderView=(ViewHolder) convertView.getTag();
        }
        CheckDetailListBean item=data.get(position);
        if(item != null){
            holderView.title.setText(item.getName()+"");
            holderView.com.setText(item.getDescription()+"");
        }
        return convertView;
    }

    class ViewHolder {
        TextView title;
        TextView com;
    }
}
