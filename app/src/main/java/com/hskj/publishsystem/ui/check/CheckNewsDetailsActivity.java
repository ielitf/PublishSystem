package com.hskj.publishsystem.ui.check;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.hskj.publishsystem.R;
import com.hskj.publishsystem.adapter.CheckListAdapter2;
import com.hskj.publishsystem.base.BaseActivity;
import com.hskj.publishsystem.bean.CheckListBean;
import com.hskj.publishsystem.control.CodeConstants;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

import org.json.JSONException;
import org.json.JSONObject;

public class CheckNewsDetailsActivity extends BaseActivity implements View.OnClickListener {
    private String name, startData, endData,opinion;
    private int id;
    private TextView check_detail_title, check_detail_time;
    private Button noPassBtn,passBtn;
    private ListView listView;
    private EditText editText;
    @Override
    protected void setLayout() {
        setContentView(R.layout.activity_check_news_details);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        name = activity.getIntent().getStringExtra(CodeConstants.NAME);
        startData = activity.getIntent().getStringExtra(CodeConstants.START_DATA);
        endData = activity.getIntent().getStringExtra(CodeConstants.END_DATA);
        id = getIntent().getIntExtra(CodeConstants.ID, 0);
        loadData();
    }


    @Override
    protected void initViews() {
        listView = findViewById(R.id.check_detail_list);
        noPassBtn = findViewById(R.id.check_detail_noPass);
        passBtn = findViewById(R.id.check_detail_pass);
        editText = findViewById(R.id.check_detail_edit);
        noPassBtn.setOnClickListener(this);
        passBtn.setOnClickListener(this);
        check_detail_title = findViewById(R.id.check_detail_title);
        check_detail_time = findViewById(R.id.check_detail_time);
        check_detail_title.setText(name);
        check_detail_time.setText(startData+"-"+endData);
    }

    @Override
    protected void initArgs() {
        super.initArgs();
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
        OkGo.<String>get("http://172.16.30.139:8085/cprs/material/audits?")
                .headers("Token", CodeConstants.HEADERS)
                .params(CodeConstants.ID, id)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        Log.i("====", response.body().toString());
                        try {
                            JSONObject jsonObject = new JSONObject(response.body());
//                            String content = jsonObject.getString("data");
//                            JSONObject jsonObject2 = new JSONObject(content);
//                            String content2 = jsonObject2.getString("list");

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
        switch (v.getId()){
            case R.id.check_detail_noPass :
                noPassBtn();
                break;
            case R.id.check_detail_pass :
                pass();
                break;
            default:
                break;
        }
    }

    private void noPassBtn() {

    }

    private void pass() {
    }
}

