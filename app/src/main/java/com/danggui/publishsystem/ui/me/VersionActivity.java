package com.danggui.publishsystem.ui.me;

import android.Manifest;
import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.danggui.publishsystem.R;
import com.danggui.publishsystem.base.BaseActivity;
import com.danggui.publishsystem.bean.ResponseAppVersion;
import com.danggui.publishsystem.control.CodeConstants;
import com.danggui.publishsystem.utils.LogUtil;
import com.danggui.publishsystem.utils.Utils;

import java.io.File;

import pub.devrel.easypermissions.EasyPermissions;

public class VersionActivity extends BaseActivity {
    private static final int DOWNLOAD_STATUS_NEED_LOAD = 1;
    private static final int DOWNLOAD_STATUS_RUNNING = 2;
    private static final int DOWNLOAD_STATUS_LOADED = 3;
    private static final int RC_WRITE_EXTERNAL_PERM = 122;
    private static final int RC_SETTINGS = 123;
    private TextView currentVersion, newVersion;
    private Button checkBtn;
    private ResponseAppVersion checkAPPVersion = null;
    private CompleteReceiver completeReceiver;
    private int i = 1;
    @Override
    protected void setLayout() {
        setContentView(R.layout.activity_version);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        currentVersion.setText("当前版本：" + Utils.getAppVersionName(this));
        loadData();
        completeReceiver = new CompleteReceiver();
        registerReceiver(completeReceiver, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
        checkBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkVersion();
            }
        });
    }

    @Override
    protected void initViews() {
        currentVersion = findViewById(R.id.ver_current);
        newVersion = findViewById(R.id.ver_new);
        checkBtn = findViewById(R.id.ver_update);
    }

    private void loadData() {
        // todo 检查版本更新信息
    }

    public void checkVersion() {
        if (checkAPPVersion != null) {
            if (checkAPPVersion.getVersionCode() > Utils.getVersionCode(this)) {
            downloadTask();
            } else if (checkAPPVersion.getVersionCode() <= Utils.getVersionCode(activity)) {
                application.showToast("已是最新版本");
            }
        }else{
            application.showToast("没有最新版本信息");
        }
    }
    private void downloadTask() {
        if (EasyPermissions.hasPermissions(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            LogUtil.i(TAG, "downloadTask 下的EasyPermissions.hasPermissions is called and next startDownload is gonna be called");
            startDownload(this, checkAPPVersion);
        } else {
            EasyPermissions.requestPermissions(this, getString(R.string.rationale_write),
                    RC_WRITE_EXTERNAL_PERM, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }

    }
    private void startDownload(BaseActivity activity, ResponseAppVersion checkAPPVersion) {
        LogUtil.e(TAG, "i=" + i);
        i++;
        int status = getDownloadStatus(activity, checkAPPVersion);
        LogUtil.d(TAG, "status=" + status);
        if (status != DOWNLOAD_STATUS_NEED_LOAD) {
            return;
        }

        LogUtil.i(TAG, "startDownload is called");
        activity.application.showToast("开始下载安装包....");
        Uri mUri = Uri.parse(CodeConstants.URL_Query + checkAPPVersion.getVersionUrl());
        DownloadManager.Request request = new DownloadManager.Request(mUri);
        //set request property
        String apkName = getDownloadApkName(checkAPPVersion);
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, apkName);
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setMimeType("application/vnd.android.package-archive");
        DownloadManager dm = (DownloadManager) activity.getSystemService(Context.DOWNLOAD_SERVICE);
        dm.enqueue(request);
    }

    //判断当前版本下载状态
    private int getDownloadStatus(BaseActivity activity, ResponseAppVersion checkAPPVersion) {
        DownloadManager.Query query = new DownloadManager.Query();
        DownloadManager dm = (DownloadManager) activity.getSystemService(Context.DOWNLOAD_SERVICE);
        Cursor cursor = dm.query(query);
        if (!cursor.moveToFirst()) {
            //无下载内容
            return DOWNLOAD_STATUS_NEED_LOAD;
        }
        int j = 1;
        do {
            LogUtil.w(TAG, "j=" + j);
            j++;
            int status = cursor.getColumnIndex(DownloadManager.COLUMN_STATUS);
            String title = cursor.getString(cursor.getColumnIndex(DownloadManager.COLUMN_TITLE));
            String apkName = getDownloadApkName(checkAPPVersion);

            if (title.equals(apkName)) {
                //如果下载列表中文件是当前版本，则继续判断下载状态
                if (status == DownloadManager.STATUS_SUCCESSFUL) {
                    //如果已经下载，返回状态同时直接提示安装
                    LogUtil.e(TAG, "status=" + DownloadManager.STATUS_SUCCESSFUL);
                    String uri = cursor.getString(cursor.getColumnIndex(DownloadManager.COLUMN_LOCAL_URI));
                    installApk(activity, Uri.parse(uri));
                    return DOWNLOAD_STATUS_LOADED;
                } else if (status == DownloadManager.STATUS_RUNNING || status == DownloadManager.STATUS_PAUSED || status == DownloadManager.STATUS_PENDING) {
                    return DOWNLOAD_STATUS_RUNNING;
                } else {
                    return DOWNLOAD_STATUS_NEED_LOAD;
                }
            }
        } while (cursor.moveToNext());
        LogUtil.i(TAG, "跳出循环后 j=" + j);

        return DOWNLOAD_STATUS_NEED_LOAD;
    }
    private void installApk(Context context, Uri data) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(data,
                "application/vnd.android.package-archive");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    public String getDownloadApkName(ResponseAppVersion response) {
        return "publishSystem" + "_v" + response.getVersionName() + ".apk";
    }

    class CompleteReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            long completeDownloadId = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
            installApk(VersionActivity.this, Uri.fromFile(new File(Environment.getExternalStorageDirectory() + "/download/" + getDownloadApkName(checkAPPVersion))));
            LogUtil.i(TAG, "CompleteReceiver onReceive is called ");
        }
    }
    @Override
    protected void onDestroy() {
        LogUtil.e(TAG, "onDestroy is called");
        unregisterReceiver(completeReceiver);
        super.onDestroy();
    }
    @Override
    protected boolean isNeedInitBack() {
        return true;
    }

    @Override
    protected String getTopbarTitle() {
        return "版本信息";
    }
}
