package com.danggui.publishsystem.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.danggui.publishsystem.R;
import com.danggui.publishsystem.mydata.NewsItemBean;

import java.util.ArrayList;

public class NewsItemAdapter3 extends MyBaseAdapter3<NewsItemBean> {
    private LayoutInflater inflater;
    public NewsItemAdapter3(Context context, ArrayList<NewsItemBean> mData) {
        super(context, mData);
        inflater = LayoutInflater.from(context);
    }

    @Override
    protected View newView(Context context, int position, ViewGroup parentView) {
        ViewHolder holderView = new ViewHolder();
        View convertView = inflater.inflate(R.layout.item_news, null, false);
        holderView.title = convertView.findViewById(R.id.news_item_title);
        holderView.control = convertView.findViewById(R.id.news_item_control);
        holderView.startTime = convertView.findViewById(R.id.news_item_startTime);
        holderView.endTime = convertView.findViewById(R.id.news_item_endTime);
        convertView.setTag(holderView);
        return convertView;
    }

    @Override
    protected void bindView(Context context, View view, int position, NewsItemBean model) {
        ViewHolder holderView = (ViewHolder) view.getTag();
        holderView.title.setText(model.getTitle());
        holderView.control.setText(model.getControl());
        holderView.startTime.setText(model.getStartTime());
        holderView.endTime.setText(model.getEndTime());
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    class ViewHolder {
        private TextView title,startTime,endTime,control;
    }
}
