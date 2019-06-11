package com.danggui.publishsystem.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.danggui.publishsystem.R;
import com.danggui.publishsystem.bean.MeItemBean;

import java.util.ArrayList;

public class MeItemAdapter3 extends MyBaseAdapter3<MeItemBean> {
    private LayoutInflater inflater;
    public MeItemAdapter3(Context context, ArrayList<MeItemBean> mData) {
        super(context, mData);
        inflater = LayoutInflater.from(context);
    }

    @Override
    protected View newView(Context context, int position, ViewGroup parentView) {
        ViewHolder holderView = new ViewHolder();
        View convertView = inflater.inflate(R.layout.item_me, null, false);
        holderView.me_item_title = convertView.findViewById(R.id.me_item_title);
        holderView.me_item_pic = convertView.findViewById(R.id.me_item_pic);
        convertView.setTag(holderView);
        return convertView;
    }

    @Override
    protected void bindView(Context context, View view, int position, MeItemBean model) {
        ViewHolder holderView = (ViewHolder) view.getTag();
        holderView.me_item_title.setText(model.getTitle());
        holderView.me_item_pic.setImageResource(model.getIcon());
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    class ViewHolder {
        private TextView me_item_title;
        private ImageView me_item_pic;
    }
}
