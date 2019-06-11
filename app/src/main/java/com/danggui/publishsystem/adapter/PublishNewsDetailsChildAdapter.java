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

import java.util.List;

public class PublishNewsDetailsChildAdapter extends MyBaseAdapter<ExpandListChildBean> {
    private LayoutInflater inflater;
    private static AdapterCallBack adapterCallBack;
    public PublishNewsDetailsChildAdapter(Context context, List<ExpandListChildBean> data) {
        super(data, context);
        inflater = LayoutInflater.from(context);
    }
    public static  void setAdapterCallBack(AdapterCallBack callBack){
        adapterCallBack = callBack;
    }
    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return data.size();
    }

    @Override
    public ExpandListChildBean getItem(int position) {
        // TODO Auto-generated method stub
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        ViewHolder holderView;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_child, null);
            holderView = new ViewHolder();
            holderView.me_item_title = convertView.findViewById(R.id.child_tv);
            holderView.me_item_pic = convertView.findViewById(R.id.child_checkBox);
            convertView.setTag(holderView);
        } else {
            holderView = (ViewHolder) convertView.getTag();
        }
        ExpandListChildBean item = data.get(position);
        holderView.me_item_title.setText(item.getTitle());
        holderView.me_item_pic.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                Toast.makeText(mContext,position+"",Toast.LENGTH_SHORT).show();
                adapterCallBack.setChildPosition(position, isChecked);
            }
        });
        return convertView;
    }

    class ViewHolder {
        private TextView me_item_title;
        private CheckBox me_item_pic;
    }
}
