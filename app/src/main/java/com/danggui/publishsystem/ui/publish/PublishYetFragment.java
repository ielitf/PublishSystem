package com.danggui.publishsystem.ui.publish;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import com.alibaba.fastjson.JSON;
import com.danggui.publishsystem.R;
import com.danggui.publishsystem.adapter.PublishYetListAdapter;
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

public class PublishYetFragment extends BaseFragment {
    private PullableListView mListView;
    private PullToRefreshLayout pullToRefreshLayout;
    private PublishYetListAdapter publishYetListAdapter;
    private ArrayList<PublicListBean> list = new ArrayList<>();
    private int curPage = 1;
    private boolean isPullToRefresh = false;

    public PublishYetFragment() {
        // Required empty public constructor
    }

    @Override
    protected void onCreateView(Bundle savedInstanceState) {
        setContentView(R.layout.fragment_publish_yet);
        initViews();
        loadData(curPage);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                showDialog("是否确定取消发布？",position);
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
    private void showDialog(String message, final int position) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(activity);
        dialog.setTitle("提示");
        dialog.setMessage(message);
        dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                canclePublish(position);
            }
        });
        dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        dialog.create().show();
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
                .params(CodeConstants.PUBLISH_STATUS, 5)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        Log.i("test", response.body().toString());
                        try {
                            JSONObject jsonObject = new JSONObject(response.body());
                            if (jsonObject.getInt("code") == 0) {
                                String content = jsonObject.getString("data");
                                JSONObject jsonObject2 = new JSONObject(content);
                                String content2 = jsonObject2.getString("list");
                                if (!TextUtils.isEmpty(content2) && content2 != "[]") {
                                    if (page == 1) {
                                        list.clear();
                                    }
                                    curPage = curPage + 1;
                                    list.addAll(JSON.parseArray(content2, PublicListBean.class));
                                    if (publishYetListAdapter == null) {
                                        publishYetListAdapter = new PublishYetListAdapter(mContext, list);
                                        mListView.setAdapter(publishYetListAdapter);
                                    } else {
                                        publishYetListAdapter.notifyDataSetChanged();
                                    }
                                } else if (jsonObject.getInt("code") == 1005) {
                                    showToast(jsonObject.getString("msg"));
                                    startActivity(new Intent(activity, LoginActivity.class));
                                } else {
                                    showToast(jsonObject.getString("msg"));
                                }
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

    private void canclePublish(int position) {
        showProgressDialog();
        OkGo.<String>delete(CodeConstants.URL_Query + "/publish/"+ list.get(position).getId())
                .headers("Token", CodeConstants.HEADERS)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        Log.i("====", response.body());
                        try {
                            JSONObject jsonObject = new JSONObject(response.body());
                            if (jsonObject.getInt("code") == 0) {
                                showToast(jsonObject.getString("msg"));
                            } else {
                                showToast(jsonObject.getString("msg"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        dismissProgressDialog();
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        dismissProgressDialog();
                    }

                    @Override
                    public void onFinish() {
                        dismissProgressDialog();
                    }
                });

    }
    private void initViews() {
        mListView = (PullableListView) findViewById(R.id.publish_yet_list);
        pullToRefreshLayout = (PullToRefreshLayout) findViewById(R.id.refresh_view);
    }
}
