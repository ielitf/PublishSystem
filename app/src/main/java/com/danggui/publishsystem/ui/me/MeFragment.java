package com.danggui.publishsystem.ui.me;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.view.WindowManager.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

import com.danggui.publishsystem.R;
import com.danggui.publishsystem.adapter.MeItemAdapter3;
import com.danggui.publishsystem.base.BaseFragment;
import com.danggui.publishsystem.bean.MeItemBean;
import com.danggui.publishsystem.control.CodeConstants;
import com.danggui.publishsystem.utils.LogUtil;
import com.danggui.publishsystem.utils.Utils;
import com.danggui.publishsystem.widget.RoundImageView;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;

import pub.devrel.easypermissions.EasyPermissions;

import static android.app.Activity.RESULT_CANCELED;

/**
 * 我的
 */
public class MeFragment extends BaseFragment implements View.OnClickListener, AdapterView.OnItemClickListener {
    private static final int RC_READ_AND_WRITE_PERM = 122;
    private static final int RC_CAMERA_AND_WRITE_PERM = 121;
    private String photoPath = null;
    private String picturePath;
    private Uri outputUri;
    private static final int CODE_GALLERY_REQUEST = 0x0a;
    private static final int CODE_CAMERA_REQUEST = 0x0b;
    private static final int CODE_CROP_REQUEST = 0x0c;
    // 裁剪后图片的宽(X)和高(Y),120 X 120的正方形。
    private static int output_X = 120;
    private static int output_Y = 120;

    private LinearLayout linearLayout_main;
    private Button btnNormal, btnShowList, btnCostom;
    private RoundImageView headImg;
    private View contentView;
    private ListView listView;
    private ArrayList<MeItemBean> list = new ArrayList<>();
    private MeItemAdapter3 adapter;
    private PopupWindow popupWindow;
    private LayoutInflater inflate;
    private String custId;
    private String custName;
    private String avatarUrl;
    private String integralStr;
    private String signDateStr;
    private String account, password;
    private boolean isRefreshingUserInfo = false;
    private TextView login_name;
    private boolean isLogin;//是否已登录

    public MeFragment() {
        // Required empty public constructor
    }

    Handler handler = new Handler()
//    {
//        @Override
//        public void handleMessage(Message msg) {
//            handler.removeCallbacks(run_scroll_down);
//            handler.removeCallbacks(run_scroll_up);
//        }
//    }
            ;

    /**
     * 向上滚动
     */
    public void listScrollUp() {
        listScrollOff();
        handler.postDelayed(run_scroll_up, 0);
    }

    /**
     * 向下滚动
     */
    public void listScrollDown() {
        listScrollOff();
        handler.postDelayed(run_scroll_down, 0);
    }

    /**
     * 停止滚动
     */
    public void listScrollOff() {
        handler.removeCallbacks(run_scroll_down);
        handler.removeCallbacks(run_scroll_up);
    }

    Runnable run_scroll_up = new Runnable() {
        @Override
        public void run() {
            listView.smoothScrollBy(50, 1000);
            handler.postDelayed(run_scroll_up, 5000);
        }
    };

    Runnable run_scroll_down = new Runnable() {
        @Override
        public void run() {
            listView.smoothScrollBy(-80, 10);
            handler.postDelayed(run_scroll_up, 1000);
        }
    };

    @Override
    protected void onCreateView(Bundle savedInstanceState) {
        setContentView(R.layout.fragment_me);
        account = application.getCustAccountNumber();
        password = application.getCustAccountPassword();
        initViews();
        initData();

        File file = new File(Environment.getExternalStorageDirectory(), "/com.hskj.publishsystem/save/" + "touxiang" + ".jpg");
        if (file.getParentFile().exists()) {
            outputUri = Uri.fromFile(file);
            headImg.setImageURI(outputUri);
        }

        isLogin = application.isLogin();
        if (isLogin) {
            login_name.setText(application.getCustAccountNumber());
//            login_name.setClickable(false);
        } else {
            login_name.setText("点击登录");
//            login_name.setClickable(true);
        }
        adapter = new MeItemAdapter3(activity, list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
        listScrollUp();
        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (firstVisibleItem == 0) {
                    Log.e("ListView", "##### 滚动到顶部 #####");
                } else if ((firstVisibleItem + visibleItemCount) == totalItemCount) {
                    Log.e("ListView", "##### 滚动到底部 ######");
                    View lastVisibleItemView = listView.getChildAt(listView.getChildCount() - 1);
                    if (lastVisibleItemView != null && lastVisibleItemView.getBottom() == listView.getHeight()) {
                        Log.d("ListView", "##### 滚动到底部dsfdsfdsfds ######");
                        listView.smoothScrollToPosition(0);
                    }
                }
            }
        });
    }

    private void initViews() {
        listView = (ListView) findViewById(R.id.me_list);
        linearLayout_main = (LinearLayout) findViewById(R.id.linearLayout_main);
        headImg = (RoundImageView) findViewById(R.id.me_touxiang);
        login_name = (TextView) findViewById(R.id.cust_name_tv);
        headImg.setOnClickListener(this);
        login_name.setOnClickListener(this);
        listView.setOnItemClickListener(this);
    }

    private void initData() {
        list.add(new MeItemBean(R.mipmap.me_system_update, "系统更新"));
        list.add(new MeItemBean(R.mipmap.me_clear_cache, "清理缓存"));
        list.add(new MeItemBean(R.mipmap.me_system_exit, "退出登录"));
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

    @Override
    public void onResume() {
        super.onResume();
        isLogin = application.isLogin();
        if (isLogin) {
            login_name.setText(application.getCustAccountNumber());
//            login_name.setClickable(false);
        } else {
            login_name.setText("点击登录");
//            login_name.setClickable(true);
        }
    }

    private void ShowCostomPopupWindow() {
        inflate = LayoutInflater.from(activity);
        View contentView = inflate.inflate(R.layout.select_pic_dialog, null);
        popupWindow = new PopupWindow(contentView, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        popupWindow.setOutsideTouchable(true);//自己决定没用的代码
        popupWindow.setFocusable(true);//获取焦点，此时弹框外的可点击控件点击无效
        popupWindow.setBackgroundDrawable(new ColorDrawable());
        popupWindow.setAnimationStyle(R.style.pw_anim_style);
        // convView.setBackgroundColor(Color.TRANSPARENT);
        popupWindow.showAtLocation(linearLayout_main, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);

        Button takePhoto = (Button) contentView.findViewById(R.id.btn_take_photo);
        takePhoto.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                takePictureTask();
                popupWindow.dismiss();
            }
        });
        Button takePicture = (Button) contentView.findViewById(R.id.btn_pick_photo);
        takePicture.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                choseHeadImageFromGallery();
                popupWindow.dismiss();
            }
        });
        Button cancle = (Button) contentView.findViewById(R.id.btn_cancel);
        cancle.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });

    }

    private void takePictureTask() {
        LogUtil.w(TAG, "takePictureTask is called");
        String[] perms = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        if (EasyPermissions.hasPermissions(mContext, perms)) {
            choseHeadImageFromCameraCapture();
        } else {
            EasyPermissions.requestPermissions(this, "应用需要调取您的相机",
                    RC_CAMERA_AND_WRITE_PERM, perms);
        }
    }

    private void choseHeadImageFromCameraCapture() {
        // 判断存储卡是否可用，存储照片文件

        if (Utils.hasSdcard()) {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//            File imageFile = new File(Environment.getExternalStorageDirectory(), IMAGE_FILE_NAME);

            File imageFile = new File(Environment.getExternalStorageDirectory(), "/com.hskj.publishsystem/camera/" + System.currentTimeMillis() + ".jpg");
            LogUtil.i(TAG, "Environment.getExternalStorageDirectory()=" + Environment.getExternalStorageDirectory().getAbsolutePath());
            LogUtil.e(TAG, "imageFile.exist=" + imageFile.exists());
            if (!imageFile.getParentFile().exists())
                imageFile.getParentFile().mkdirs();
            photoPath = imageFile.getAbsolutePath();
            LogUtil.e(TAG, "photoPath=" + photoPath);
            Uri uri;
            if (Build.VERSION.SDK_INT >= 24) {
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                uri = FileProvider.getUriForFile(activity, "com.hskj.publishsystem.fileprovider", imageFile);

            } else {
                uri = Uri.fromFile(imageFile);
            }
            intent.putExtra(MediaStore.EXTRA_OUTPUT,
                    uri);
            LogUtil.i(TAG, "imageFile.exist=" + imageFile.exists());
            startActivityForResult(intent, CODE_CAMERA_REQUEST);

        } else {
            showToast("没有SD卡");
        }
    }

    /**
     * 从本地相册选取图片作为头像
     */

    private void choseHeadImageFromGallery() {

        Intent intent = new Intent(Intent.ACTION_PICK, null);
        // 此处调用了图片选择器
        // 如果直接写intent.setDataAndType("image/*");
        // 调用的是系统图库
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        startActivityForResult(intent, CODE_GALLERY_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        // 用户没有进行有效的设置操作，返回
        LogUtil.e(TAG, "requestCode=" + requestCode + "..resultCode=" + resultCode);
        if (resultCode == RESULT_CANCELED) {
            showToast("操作已取消");
            return;
        }
        switch (requestCode) {
            case CODE_GALLERY_REQUEST:
                cropRawPhoto(intent.getData());
                break;
            case CODE_CAMERA_REQUEST:
                if (Utils.hasSdcard()) {
                    File photoFile = new File(photoPath);
                    Uri uri;
                    if (Build.VERSION.SDK_INT >= 24) {
                        uri = FileProvider.getUriForFile(activity, "com.hskj.publishsystem.fileprovider", photoFile);
                    } else {
                        uri = Uri.fromFile(photoFile);
                    }
                    cropRawPhoto(uri);
                } else {
                    Toast.makeText(mContext, "没有Sd卡!", Toast.LENGTH_LONG).show();
                }
                break;
            case CODE_CROP_REQUEST:
                if (null != intent) {
                    headImg.setImageURI(outputUri);
//                    setImageToHeadView(intent);
                    // Todo 上传图片
//                    uploadAvatar();

//                    LogUtil.i(TAG,"photoPath="+photoPath);
//                    picturePath=photoPath;
//                    upload(photoPath);
                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, intent);
    }

    /**
     * 裁剪原始的图片
     */
    public void cropRawPhoto(Uri uri) {
        File file = new File(Environment.getExternalStorageDirectory(), "/com.hskj.publishsystem/save/" + "touxiang" + ".jpg");
        if (!file.getParentFile().exists())
            file.getParentFile().mkdirs();
        LogUtil.v(TAG, "cropRawPhoto中file.exists=" + file.exists());
        outputUri = Uri.fromFile(file);
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.setDataAndType(uri, "image/*");

        // 设置裁剪
        intent.putExtra("crop", "true");

        // aspectX , aspectY :宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);

        // outputX , outputY : 裁剪图片宽高
        intent.putExtra("outputX", output_X);
        intent.putExtra("outputY", output_Y);
        intent.putExtra("outputFormat",
                Bitmap.CompressFormat.JPEG.toString());
        intent.putExtra("return-data", true);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, outputUri);
        LogUtil.e(TAG, "cropRawPhoto IS CALLED");
        LogUtil.v(TAG, "outputUri=" + outputUri.getPath());
        startActivityForResult(intent, CODE_CROP_REQUEST);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cust_name_tv:
                startActivity(new Intent(activity, LoginActivity.class));
                break;
            case R.id.me_touxiang:
                ShowCostomPopupWindow();
                break;
            default:
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (position) {
            case 0://系统更新
                startActivity(new Intent(mContext,VersionActivity.class));
                break;
            case 1://清理缓存
                showToast("敬请期待");
                break;
            case 2://退出登录
                showDialog("确定退出登录？");
                break;
            default:
                break;
        }
    }

    private void showDialog(String message) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(activity);
        dialog.setTitle("提示");
        dialog.setMessage(message);
        dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                logOut();
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
     * 退出登录
     */
    private void logOut() {
        application.setCustId("");
        application.setCustToken("");
        login_name.setText("登陆");
        CodeConstants.HEADERS = "";
        showToast("退出成功");
//        showProgressDialog();
//        OkGo.<String>get(CodeConstants.URL_Query + "/logout?")
//                .params(CodeConstants.TOKEN, CodeConstants.HEADERS)
//                .execute(new StringCallback() {
//                    @Override
//                    public void onSuccess(Response<String> response) {
//                        LogUtil.i("===logOut", response.body());
//                        try {
//                            JSONObject jsonObject = new JSONObject(response.body());
//                            if (jsonObject.getInt("code") == 0) {
//                                showToast("退出成功");
//                            } else {
//                                showToast(jsonObject.getString("msg"));
//                            }
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                        dismissProgressDialog();
//                    }
//
//                    @Override
//                    public void onError(Response<String> response) {
//                        super.onError(response);
//                        dismissProgressDialog();
//                    }
//
//                    @Override
//                    public void onFinish() {
//                        dismissProgressDialog();
//                    }
//                });
    }
}
