package com.danggui.publishsystem.ui.publish;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import com.alibaba.fastjson.JSON;
import com.danggui.publishsystem.R;
import com.danggui.publishsystem.adapter.PublishNotListAdapter;
import com.danggui.publishsystem.base.BaseFragment;
import com.danggui.publishsystem.bean.PublicListBean;
import com.danggui.publishsystem.control.CodeConstants;
import com.danggui.publishsystem.ui.me.LoginActivity;
import com.danggui.publishsystem.widget.PullToRefreshLayout;
import com.danggui.publishsystem.widget.pullableview.PullableListView;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;

public class PublishNotFragment extends BaseFragment {
    private PullableListView mListView;
    private PullToRefreshLayout pullToRefreshLayout;
    private PublishNotListAdapter publishNotListAdapter;
    private ArrayList<PublicListBean> list = new ArrayList<>();
    private int curPage = 1;
    private boolean isPullToRefresh = false;

    public PublishNotFragment() {
        // Required empty public constructor
    }

    @Override
    protected void onCreateView(Bundle savedInstanceState) {
        setContentView(R.layout.fragment_publish_not);
        initViews();
        loadData(curPage);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(mContext, PublishNewsDetailsActivity.class);
                intent.putExtra(CodeConstants.ID, list.get(position).getId());
                intent.putExtra(CodeConstants.NAME, list.get(position).getName());
                intent.putExtra(CodeConstants.START_DATA, list.get(position).getStartDate());
                intent.putExtra(CodeConstants.END_DATA, list.get(position).getEndDate());
                startActivity(intent);
            }
        });
        pullToRefreshLayout.setOnRefreshListener(new PullToRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {
                isPullToRefresh = true;
                curPage = 1;
                loadData(curPage);
            }

            @Override
            public void onLoadMore(PullToRefreshLayout pullToRefreshLayout) {
                isPullToRefresh = false;
                loadData(curPage + 1);
            }
        });
    }

    @Override
    protected boolean hasPopWindow() {
        return false;
    }

    @Override
    protected boolean isNeedInitBack() {
        return false;
    }

    @Override
    protected String getTopbarTitle() {
        return null;
    }

    private void loadData(final int page) {
        OkGo.<String>get(CodeConstants.URL_Query + "/publishs?")
                .headers("Token", CodeConstants.HEADERS)
                .params(CodeConstants.PAGE, page)
                .params(CodeConstants.PAGE_SIZE, 5)
                .params(CodeConstants.PUBLISH_STATUS, 4)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        Log.i("test", response.body());
                        try {
                            JSONObject jsonObject = new JSONObject(response.body());
                            if (jsonObject.getInt("code") == 0) {
                                String content = jsonObject.getString("data");
                                JSONObject jsonObject2 = new JSONObject(content);
                                String content2 = jsonObject2.getString("list");
                                if (!TextUtils.isEmpty(content2) && !content2 .equals("[]")) {
                                    if (page == 1) {
                                        list.clear();
                                    }
                                    curPage = curPage + 1;
                                    list.addAll(JSON.parseArray(content2, PublicListBean.class));
                                    if (publishNotListAdapter == null) {
                                        publishNotListAdapter = new PublishNotListAdapter(mContext, list);
                                        mListView.setAdapter(publishNotListAdapter);
                                    } else {
                                        publishNotListAdapter.notifyDataSetChanged();
                                    }
                                }
                            } else if (jsonObject.getInt("code") == 1005) {
                                showToast(jsonObject.getString("msg"));
                                startActivity(new Intent(activity, LoginActivity.class));
                            } else {
                                showToast(jsonObject.getString("msg"));
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        pullToRefreshLayout.refreshFinish(PullToRefreshLayout.FAIL);
                    }

                    @Override
                    public void onFinish() {
                        pullToRefreshLayout.refreshFinish(PullToRefreshLayout.SUCCEED);
                    }
                });
    }

    private void initViews() {
        mListView = (PullableListView) findViewById(R.id.publish_not_list);
        pullToRefreshLayout = (PullToRefreshLayout) findViewById(R.id.refresh_view);
    }
}
