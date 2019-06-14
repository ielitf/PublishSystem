package com.danggui.publishsystem.ui.me;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.danggui.publishsystem.R;
import com.danggui.publishsystem.base.BaseActivity;
import com.danggui.publishsystem.control.CodeConstants;
import com.danggui.publishsystem.control.MyApplication;
import com.danggui.publishsystem.utils.LogUtil;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 登录
 */
public class LoginActivity extends BaseActivity {
    private EditText accountEdit, passwordEdit;
    private CheckBox login_check;
    private Button login_button;
    private String account, password;
    private Boolean isChecked;

    @Override
    protected void setLayout() {
        setContentView(R.layout.activity_login);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        accountEdit.setText(application.getCustAccountNumber());
        passwordEdit.setText(application.getCustAccountPassword());
        login_check.setChecked(application.getIsSelfLogin());
        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginPreview();
            }
        });
    }


    @Override
    protected void initViews() {
        accountEdit = findViewById(R.id.login_account_edit);
        passwordEdit = findViewById(R.id.login_password_edit);
        login_check = findViewById(R.id.login_check);
        login_button = findViewById(R.id.login_button);
    }

    /**
     * 预登录
     */
    private void loginPreview() {
        account = accountEdit.getText() + "";
        password = passwordEdit.getText() + "";
        isChecked = login_check.isChecked();
        if (TextUtils.isEmpty(account)) {
            showToast("账号不能为空");
            return;
        }
        if (TextUtils.isEmpty(password)) {
            showToast("密码不能为空");
            return;
        }
        application.setIsSelfLogin(isChecked);
        application.setCustAccountNumber(account);
        if(isChecked){
            application.setCustAccountPassword(password);
        }else{
            application.setCustAccountPassword("");
        }
        login();
    }
    /**
     * 登录
     */
    private void login() {
        showProgressDialog();
        OkGo.<String>post(CodeConstants.URL_Query+"/login?")
                .params(CodeConstants.USER_NAME, account)
                .params(CodeConstants.PASSWORD, password)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        LogUtil.i("===login", response.body());
                        try {
                            JSONObject jsonObject = new JSONObject(response.body());
                            if (jsonObject.getInt("code")==0){
                                showToast("登录成功");
                                String data = jsonObject.getString("data");
                                application.setCustId(data);
                                application.setCustToken(data);
                                CodeConstants.HEADERS = data;
                                finish();
                            }
                            else {
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
    protected boolean isNeedInitBack() {
        return true;
    }

    @Override
    protected String getTopbarTitle() {
        return "登录";
    }
}
