package com.danggui.publishsystem.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.danggui.publishsystem.R;
import com.danggui.publishsystem.bean.CheckListBean;

import java.util.List;

public class CheckListAdapter3 extends MyBaseAdapter3<CheckListBean> {
    private LayoutInflater inflater;
    public CheckListAdapter3(Context context, List<CheckListBean> mData) {
        super(context, mData);
        inflater = LayoutInflater.from(context);
    }

    @Override
    protected View newView(Context context, int position, ViewGroup parentView) {
        ViewHolder holderView = new ViewHolder();
        View convertView = inflater.inflate(R.layout.item_news, null, false);
        holderView.title = convertView.findViewById(R.id.news_item_title);
        holderView.startTime = convertView.findViewById(R.id.news_item_startTime);
        holderView.endTime = convertView.findViewById(R.id.news_item_endTime);
        convertView.setTag(holderView);
        return convertView;
    }

    @Override
    protected void bindView(Context context, View view, int position, CheckListBean model) {
        ViewHolder holderView = (ViewHolder) view.getTag();
        holderView.title.setText("排期名称："+model.getName());
        holderView.startTime.setText(model.getStartDate());
        holderView.endTime.setText(model.getEndDate());
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }

    class ViewHolder {
        private TextView title,startTime,endTime;
    }
}
