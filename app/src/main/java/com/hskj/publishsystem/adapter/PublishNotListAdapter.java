package com.hskj.publishsystem.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hskj.publishsystem.R;
import com.hskj.publishsystem.bean.CheckListBean;
import com.hskj.publishsystem.bean.PublicListBean;

import java.util.List;

public class PublishNotListAdapter extends MyBaseAdapter2<PublicListBean> {
    private LayoutInflater inflater;
    public PublishNotListAdapter(Context context, List<PublicListBean> data){
    	super(data,context);
    	inflater= LayoutInflater.from(context);
    }
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return data.size();
	}

	@Override
	public PublicListBean getItem(int position) {
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
           convertView=inflater.inflate(R.layout.item_news, null);
			holderView=new ViewHolder();
			holderView.title = convertView.findViewById(R.id.news_item_title);
			holderView.startTime = convertView.findViewById(R.id.news_item_startTime);
			holderView.endTime = convertView.findViewById(R.id.news_item_endTime);
			holderView.control = convertView.findViewById(R.id.news_item_control);
           convertView.setTag(holderView);
		} else {
			holderView=(ViewHolder) convertView.getTag();
		}
		PublicListBean item=data.get(position);
		holderView.title.setText("排期名称："+item.getName());
		holderView.startTime.setText(item.getStartDate());
		holderView.endTime.setText(item.getEndDate());
		holderView.control.setText("发布");
		return convertView;
	}

	class ViewHolder {
     TextView title;
     TextView startTime;
     TextView endTime;
     TextView control;
	}
}
