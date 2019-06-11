package com.danggui.publishsystem.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.danggui.publishsystem.R;
import com.danggui.publishsystem.mydata.ExpandListChildBean;
import com.danggui.publishsystem.mylistener.AdapterCallBack;

import java.util.ArrayList;

public class ExpandChildListAdapter3 extends MyBaseAdapter3<ExpandListChildBean> {
    private static AdapterCallBack adapterCallBack;
    private LayoutInflater inflater;
    public ExpandChildListAdapter3(Context context, ArrayList<ExpandListChildBean> mData) {
        super(context, mData);
        inflater = LayoutInflater.from(context);
    }
    public static  void setAdapterCallBack(AdapterCallBack callBack){
        adapterCallBack = callBack;
    }
    @Override
    protected View newView(Context context, int position, ViewGroup parentView) {
        ViewHolder holderView = new ViewHolder();
        View convertView = inflater.inflate(R.layout.item_child, null, false);
        holderView.me_item_title = convertView.findViewById(R.id.child_tv);
        holderView.me_item_pic = convertView.findViewById(R.id.child_checkBox);
        convertView.setTag(holderView);
        return convertView;
    }

    @Override
    protected void bindView(Context context, View view, final int position, ExpandListChildBean model) {
        ViewHolder holderView = (ViewHolder) view.getTag();
        holderView.me_item_title.setText(model.getTitle());
        holderView.me_item_pic.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                Toast.makeText(mContext,position+"",Toast.LENGTH_SHORT).show();
                adapterCallBack.setChildPosition(position,isChecked);
            }
        });
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    class ViewHolder {
        private TextView me_item_title;
        private CheckBox me_item_pic;
    }
}
