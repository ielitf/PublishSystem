package com.danggui.publishsystem.ui.check;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.danggui.publishsystem.R;
import com.danggui.publishsystem.adapter.CheckDetailScheduleAdapter;
import com.danggui.publishsystem.base.BaseActivity;
import com.danggui.publishsystem.bean.CheckDetailListBean;
import com.danggui.publishsystem.control.CodeConstants;
import com.danggui.publishsystem.ui.me.LoginActivity;
import com.danggui.publishsystem.utils.LogUtil;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

/**
 * 审核 -->排期详情
 */
public class CheckNewsDetailsActivity extends BaseActivity implements View.OnClickListener, AdapterView.OnItemClickListener {
    private String name, startData, endData, opinionDescription;
    private int id;
    private TextView check_detail_title, check_detail_time;
    private Button noPassBtn, passBtn;
    private ListView listView;
    private CheckDetailScheduleAdapter adapter;
    private List<CheckDetailListBean> list = new ArrayList<>();
    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_check_news_details);
        super.onCreate(savedInstanceState);
        name = getIntent().getStringExtra(CodeConstants.NAME);
        startData = getIntent().getStringExtra(CodeConstants.START_DATA);
        endData = getIntent().getStringExtra(CodeConstants.END_DATA);
        id = getIntent().getIntExtra(CodeConstants.ID, 0);
        initView();
        loadData();
    }

    protected void initView() {
        listView = findViewById(R.id.check_detail_list);
        noPassBtn = findViewById(R.id.check_detail_noPass);
        passBtn = findViewById(R.id.check_detail_pass);
        editText = findViewById(R.id.check_detail_edit);
        noPassBtn.setOnClickListener(this);
        passBtn.setOnClickListener(this);
        listView.setOnItemClickListener(this);
        check_detail_title = findViewById(R.id.check_detail_title);
        check_detail_time = findViewById(R.id.check_detail_time);
        check_detail_title.setText(name);
        check_detail_time.setText(startData + "-" + endData);
    }

    @Override
    protected void initArgs() {
        super.initArgs();
    }

    @Override
    protected void setLayout() {

    }

    @Override
    protected void initViews() {

    }

    @Override
    protected boolean isNeedInitBack() {
        return true;
    }

    @Override
    protected String getTopbarTitle() {
        return "排期详情";
    }

    private void loadData() {
        OkGo.<String>get(CodeConstants.URL_Query + "/material/audits?")
                .headers("Token", CodeConstants.HEADERS)
                .params(CodeConstants.ID, 164)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        Log.i("test", response.body());
                        try {
                            JSONObject jsonObject = new JSONObject(response.body());
                            if(jsonObject.getInt("code")==0){
                                String content = jsonObject.getString("data");
                                if (!TextUtils.isEmpty(content) && !content .equals("[]")){
                                    list = JSON.parseArray(content,CheckDetailListBean.class);
                                    LogUtil.i(TAG,list.toString());
                                    adapter = new CheckDetailScheduleAdapter(activity, list);
                                    listView.setAdapter(adapter);
                                }
                            }else if(jsonObject.getInt("code")==1005){
                                showToast(jsonObject.getString("msg"));
                                startActivity(new Intent(activity, LoginActivity.class));
                            }else {
                                showToast(jsonObject.getString("msg"));
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
                    }
                });
    }

    @Override
    public void onClick(View v) {
        opinionDescription = editText.getText() + "";
        switch (v.getId()) {
            case R.id.check_detail_noPass:
                showDialog("是否审核“不通过”此排期？",false);
                break;
            case R.id.check_detail_pass:
                showDialog("是否审核“通过”此排期？",true);
                break;
            default:
                break;
        }
    }

    private void showDialog(String message,final boolean isPass ) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("提示");
        dialog.setMessage(message);
        dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                if(isPass){
                    pass();
                }else{
                    noPassBtn();
                }
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

    private void noPassBtn() {
        showProgressDialog();
        OkGo.<String>put(CodeConstants.URL_Query + "/schedule/" + id + "?")
                .headers("Token", CodeConstants.HEADERS)
                .params(CodeConstants.DESCRIPTION, opinionDescription)
                .params(CodeConstants.OPERATION, "auditFail")
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

    private void pass() {
        showProgressDialog();
        OkGo.<String>put(CodeConstants.URL_Query + "/schedule/" + id + "?")
                .headers("Token", CodeConstants.HEADERS)
                .params(CodeConstants.DESCRIPTION, opinionDescription)
                .params(CodeConstants.OPERATION, "auditSuccess")
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

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(activity, CheckDetailsSchedulePreviewActivity.class);
        startActivity(intent);
    }
}

