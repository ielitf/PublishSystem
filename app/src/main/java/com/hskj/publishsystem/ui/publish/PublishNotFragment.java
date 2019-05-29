package com.hskj.publishsystem.ui.publish;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.hskj.publishsystem.R;
import com.hskj.publishsystem.adapter.PublishNotListAdapter;
import com.hskj.publishsystem.bean.PublicListBean;
import com.hskj.publishsystem.control.CodeConstants;
import com.hskj.publishsystem.widget.XListView;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class PublishNotFragment extends Fragment implements XListView.IXListViewListener{
    private View contentView;
    private Context context;
    private XListView mListView;
    private PublishNotListAdapter publishNotListAdapter;
    private ArrayList<PublicListBean> list = new ArrayList<>();
    private int curPage = 1;
    private boolean isPullToRefresh = false;
    public PublishNotFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        contentView = inflater.inflate(R.layout.fragment_publish_not, container, false);
        context = getActivity();
        initViews(contentView);
//        initdata();
        publishNotListAdapter = new PublishNotListAdapter(context, list);
        mListView.setAdapter(publishNotListAdapter);
        mListView.setXListViewListener(this);
        mListView.setPullRefreshEnable(true);
        mListView.setPullLoadEnable(true);
        loadData(curPage);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(context, position + "", Toast.LENGTH_SHORT).show();//position是从1开始的
                startActivity(new Intent(context, PublishNewsDetailsActivity.class));
            }
        });
        return contentView;
    }
    @Override
    public void onRefresh() {
        isPullToRefresh = true;
        curPage = 1;
        loadData(curPage);
    }

    @Override
    public void onLoadMore() {
        isPullToRefresh = false;
        loadData(curPage + 1);
    }
    private void loadData(final int page) {
        OkGo.<String>get("http://172.16.30.139:8085/cprs/publishs?")
                .headers("Token", CodeConstants.HEADERS)
                .params(CodeConstants.PAGE, page)
                .params(CodeConstants.PAGE_SIZE, 5)
                .params(CodeConstants.PUBLISH_STATUS, 4)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        Log.i("test", response.body().toString());
                        try {
                            JSONObject jsonObject = new JSONObject(response.body());
                            String content = jsonObject.getString("data");
                            JSONObject jsonObject2 = new JSONObject(content);
                            String content2 = jsonObject2.getString("list");

                            if(page == 1){
                                list.clear();
                            }
                            if (!TextUtils.isEmpty(content2) && content2 != "[]" ){
                                curPage = curPage +1;
                                list.addAll(JSON.parseArray(content2, PublicListBean.class ));
                                if(publishNotListAdapter == null){
                                    publishNotListAdapter = new PublishNotListAdapter(context,list);
                                    mListView.setAdapter(publishNotListAdapter);
                                }else{
                                    publishNotListAdapter.notifyDataSetChanged();
                                }
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                    }

                    @Override
                    public void onFinish() {
                        mListView.stopLoadMore();
                        mListView.stopRefresh();
                    }
                });
    }
    private void initViews(View contentView) {
        mListView = contentView.findViewById(R.id.publish_not_list);
    }


//    private void initdata() {
//        for (int i = 0; i < NewsData.news_title.length; i++) {
//            list.add(new NewsItemBean(NewsData.news_title[i], NewsData.news_start_time[i], NewsData.news_end_time[i], NewsData.news_control_publish[i]));
//        }
//    }

}
