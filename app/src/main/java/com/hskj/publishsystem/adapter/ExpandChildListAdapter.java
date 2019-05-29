package com.hskj.publishsystem.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hskj.publishsystem.R;
import com.hskj.publishsystem.bean.ExpandListChildBean;

import java.util.ArrayList;

public class ExpandChildListAdapter extends MyBaseAdapter<ExpandListChildBean> {
    private LayoutInflater inflater;
    public ExpandChildListAdapter(Context context, ArrayList<ExpandListChildBean> mData) {
        super(context, mData);
        inflater = LayoutInflater.from(context);
    }

    @Override
    protected View newView(Context context, int position, ViewGroup parentView) {
        ViewHolder holderView = new ViewHolder();
        View convertView = inflater.inflate(R.layout.item_child, null, false);
        holderView.me_item_title = convertView.findViewById(R.id.child_tv);
        holderView.me_item_pic = convertView.findViewById(R.id.child_ico);
        convertView.setTag(holderView);
        return convertView;
    }

    @Override
    protected void bindView(Context context, View view, int position, ExpandListChildBean model) {
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
