package com.danggui.publishsystem.ui.publish;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import com.danggui.publishsystem.R;
import com.danggui.publishsystem.adapter.ExpandChildListAdapter3;
import com.danggui.publishsystem.adapter.PublishNewsDetailsAdapter;
import com.danggui.publishsystem.base.BaseActivity;
import com.danggui.publishsystem.control.CodeConstants;
import com.danggui.publishsystem.mydata.ExpandListChildBean;
import com.danggui.publishsystem.mydata.data.ExpandChildData;
import com.danggui.publishsystem.mylistener.AdapterCallBack;
import com.danggui.publishsystem.utils.LogUtil;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

public class PublishNewsDetailsActivity extends BaseActivity implements View.OnClickListener , AdapterCallBack {
    private String name, startData, endData;
    private TextView publish_detail_title, publish_detail_time;
    private int id;
    private ExpandableListView listView;
    private PublishNewsDetailsAdapter adapter;
    private Button publishCancel, publishPublish;
    private ArrayList<String> grouplist;
    private List<List<ExpandListChildBean>> childlist = new ArrayList<>();
    private List<ExpandListChildBean> list;
    private int lastClick = -1;//上一次点击的group的position
    private boolean isFirst = true;

    @Override
    protected void setLayout() {
        setContentView(R.layout.activity_publish_news_details);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initDate();
        loadData();
        adapter = new PublishNewsDetailsAdapter(this, grouplist, childlist);
        listView.setAdapter(adapter);
//        listView.expandGroup(0);
        adapter.setAdapterCallBack(this);
        /**
         * 点击一个列表，其他的列表都关闭
         */
        listView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                if(isFirst){
                    for (int j = 0; j < grouplist.size(); j++) {
                        list = new ArrayList<ExpandListChildBean>();
                        for (int i = 0; i < ExpandChildData.child_check.length; i++) {
                            list.add(new ExpandListChildBean(ExpandChildData.child_title[i]));
                        }
                        childlist.add(list);
                    }
                    adapter.notifyDataSetChanged();
                    isFirst = false;
                }

                if (lastClick == -1) {
                    listView.expandGroup(groupPosition);//
//                    listView.collapseGroup(groupPosition);
                }
                if (lastClick != -1 && lastClick != groupPosition) {
                    listView.collapseGroup(lastClick);
                    listView.expandGroup(groupPosition);
                } else if (lastClick == groupPosition) {
                    if (listView.isGroupExpanded(groupPosition))
                        listView.collapseGroup(groupPosition);
                    else if (!listView.isGroupExpanded(groupPosition))
                        listView.expandGroup(groupPosition);
                }
                lastClick = groupPosition;
                return true;
            }
        });
    }

    @Override
    protected void initViews() {
        name = getIntent().getStringExtra(CodeConstants.NAME);
        startData = getIntent().getStringExtra(CodeConstants.START_DATA);
        endData = getIntent().getStringExtra(CodeConstants.END_DATA);
        id = getIntent().getIntExtra(CodeConstants.ID, 0);
        listView = findViewById(R.id.publish_detail_device);
        publishCancel = findViewById(R.id.publish_detail_cancel);
        publishPublish = findViewById(R.id.publish_detail_publish);
        publishCancel.setOnClickListener(this);
        publishPublish.setOnClickListener(this);
        publish_detail_title = findViewById(R.id.publish_detail_title);
        publish_detail_time = findViewById(R.id.publish_detail_time);
        publish_detail_title.setText(name);
        publish_detail_time.setText(startData + "-" + endData);
    }

    private void loadData() {
        OkGo.<String>get(CodeConstants.URL_Query + "/material/audits?")
                .headers("Token", CodeConstants.HEADERS)
                .params(CodeConstants.ID, id)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        Log.i("test", response.body().toString());
                        try {
                            JSONObject jsonObject = new JSONObject(response.body());
//                            if(jsonObject.getInt("code")==0){
//                                String content = jsonObject.getString("data");
//                                JSONObject jsonObject2 = new JSONObject(content);
//                                String content2 = jsonObject2.getString("list");
//                            }else{
//                                showToast(jsonObject.getString("msg"));
//                            }
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
                    }
                });
    }

    @Override
    protected boolean isNeedInitBack() {
        return true;
    }

    @Override
    protected String getTopbarTitle() {
        return "发布详情";
    }

    private void initDate() {
        grouplist = new ArrayList<>();
        grouplist.add("北京店面");
        grouplist.add("上海店面");
//        for (int j = 0; j < grouplist.size(); j++) {
//            list = new ArrayList<>();
//            for (int i = 0; i < ExpandChildData.child_check.length; i++) {
//                list.add(new ExpandListChildBean(ExpandChildData.child_check[i], ExpandChildData.child_title[i]));
//            }
//            childlist.add(list);
//        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.publish_detail_cancel:
                finish();
                break;
            case R.id.publish_detail_publish:
                showDialog("是否发布此节目排期？");
                break;
            default:
                break;
        }
    }

    private void showDialog(String message) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("提示");
        dialog.setMessage(message);
        dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                publishPublish();
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

    /**
     * 发布
     */
    private void publishPublish() {

    }
    /**
     *被选择的父组和子组
     */

    @Override
    public void setGroupPosition(int groupPosition, boolean isGroupChecked) {
        LogUtil.i(TAG,"父组");
        Toast.makeText(activity,"父组:"+groupPosition+","+isGroupChecked,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setChildPosition(int childPosition, boolean isChildChecked) {
        LogUtil.i(TAG,"子组");
        Toast.makeText(activity,"子组:"+childPosition+","+isChildChecked,Toast.LENGTH_SHORT).show();
    }
}
