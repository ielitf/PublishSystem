package com.hskj.publishsystem.ui.check;

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
import com.hskj.publishsystem.adapter.CheckListAdapter;
import com.hskj.publishsystem.adapter.CheckListAdapter2;
import com.hskj.publishsystem.adapter.NewsItemAdapter;
import com.hskj.publishsystem.bean.CheckListBean;
import com.hskj.publishsystem.bean.NewsItemBean;
import com.hskj.publishsystem.control.CodeConstants;
import com.hskj.publishsystem.data.NewsData;
import com.hskj.publishsystem.widget.XListView;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class CheckFragment extends Fragment implements XListView.IXListViewListener {
    private View contentView;
    private Context context;
    private XListView mListView;
    private CheckListAdapter2 checkListAdapter = null;
    private List<CheckListBean> list = new ArrayList<>();
    private int curPage = 1;
    private boolean isPullToRefresh = false;
    public CheckFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        contentView = inflater.inflate(R.layout.fragment_check,container,false);
        context = getActivity();
        mListView = contentView.findViewById(R.id.check_list);
        mListView.setXListViewListener(this);
        mListView.setPullRefreshEnable(true);
        mListView.setPullLoadEnable(true);
        loadData(curPage);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(context,position+"",Toast.LENGTH_SHORT).show();//position是从1开始的
                startActivity(new Intent(context, CheckNewsDetailsActivity.class));
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
        OkGo.<String>get("http://172.16.30.139:8085/cprs/audits?")
                .headers("Token", CodeConstants.HEADERS)
                .params(CodeConstants.PAGE, page)
                .params(CodeConstants.PAGE_SIZE, 5)
                .params(CodeConstants.STATUS, 2)
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
                                list.addAll(JSON.parseArray(content2,CheckListBean.class ));
                                if(checkListAdapter == null){
                                    checkListAdapter = new CheckListAdapter2(context,list);
                                    mListView.setAdapter(checkListAdapter);
                                }else{
                                    checkListAdapter.notifyDataSetChanged();
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

}
