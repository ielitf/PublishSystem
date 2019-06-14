package com.danggui.publishsystem.ui.check;

import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.danggui.publishsystem.R;
import com.danggui.publishsystem.base.BaseActivity;
import com.danggui.publishsystem.utils.Utils;
import io.vov.vitamio.MediaPlayer;
import io.vov.vitamio.Vitamio;
import io.vov.vitamio.widget.MediaController;
import io.vov.vitamio.widget.VideoView;

/**
 * 审核 -->排期详情 -->排期内容预览详情
 */
public class CheckDetailsSchedulePreviewActivity extends BaseActivity implements MediaController.OnControllerListener, MediaPlayer.OnInfoListener, MediaPlayer.OnBufferingUpdateListener{
    private String path ="http://we.taagoo.com/vrplayer/VR.mp4";
//    private String path ="";
    private Uri uri;
    private VideoView mVideoView;
    private ProgressBar pb;
    private TextView downloadRateView, loadRateView;
    private FrameLayout fl_controller;
    boolean isPortrait = true;
    private int videoHeight;
    private long mPosition = 0;

    private static final String TAG = "MainActivity";
    @Override
    protected void setLayout() {
        setContentView(R.layout.activity_check_details_schedule_preview);
    }

    @Override
    protected void initViews() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        videoHeight = Utils.getWidthPixel(CheckDetailsSchedulePreviewActivity.this) * 9 / 16;
        Vitamio.isInitialized(getApplication());
        init();
    }
    MediaController mc;

    private void init() {
        mVideoView = (VideoView) findViewById(R.id.buffer);
        fl_controller = (FrameLayout) findViewById(R.id.fl_controller);
        pb = (ProgressBar) findViewById(R.id.probar);

        downloadRateView = (TextView) findViewById(R.id.download_rate);
        loadRateView = (TextView) findViewById(R.id.load_rate);
        if (path == "") {
            // Tell the user to provide a media file URL/path.
//            pb.setVisibility(View.GONE);
            return;
        } else {
            LinearLayout.LayoutParams fl_lp = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    videoHeight
            );
            fl_controller.setLayoutParams(fl_lp);
            /*
             * Alternatively,for streaming media you can use
             * mVideoView.setVideoURI(Uri.parse(URLstring));
             */
            uri = Uri.parse(path);

            mVideoView.setVideoURI(uri);
            mc = new MediaController(this, true, fl_controller);
            mVideoView.setMediaController(mc);
            mc.setVisibility(View.VISIBLE);
            mc.setGone(false);
            mc.setTitle("扑通扑通我的人生");
            mc.setOnControllerListener(this);
            mVideoView.requestFocus();
            mVideoView.setOnInfoListener(this);
            mVideoView.setOnBufferingUpdateListener(this);
            mVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mediaPlayer) {
                    // optional need Vitamio 4.0
                    mediaPlayer.setPlaybackSpeed(1.0f);
                }
            });
            mVideoView.setOnErrorListener(new MediaPlayer.OnErrorListener() {
                @Override
                public boolean onError(MediaPlayer mp, int what, int extra) {
                    // state==3播放出错
                    Log.e(TAG, "what = " + what + "  extra" + extra);
                    Toast.makeText(CheckDetailsSchedulePreviewActivity.this,"此播放地址无法播放",Toast.LENGTH_LONG).show();
                    return true;
                }
            });
        }

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (!isPortrait) {
                if (!isLock) {
                    halfScreen();
                }
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onBufferingUpdate(MediaPlayer mp, int percent) {
        loadRateView.setText(percent + "%");
    }

    @Override
    protected void onPause() {
        if (null != mVideoView && mVideoView.isPlaying()) {
            mPosition = mVideoView.getCurrentPosition();
            mVideoView.pause();
        }
        super.onPause();
    }

    @Override
    protected void onResume() {
//        mVideoView.resume();//直播用这个
        mVideoView.seekTo(mPosition);
        mVideoView.start();
        super.onResume();
    }


    @Override
    public boolean onInfo(MediaPlayer mp, int what, int extra) {

        switch (what) {
            case MediaPlayer.MEDIA_INFO_BUFFERING_START:
                if (mVideoView.isPlaying()) {
                    mc.show(4000);
                    mVideoView.pause();
                    pb.setVisibility(View.VISIBLE);
                    downloadRateView.setText("");
                    loadRateView.setText("");
                    downloadRateView.setVisibility(View.VISIBLE);
                    loadRateView.setVisibility(View.VISIBLE);

                }
                break;
            case MediaPlayer.MEDIA_INFO_BUFFERING_END:
                mVideoView.start();
                pb.setVisibility(View.GONE);
                downloadRateView.setVisibility(View.GONE);
                loadRateView.setVisibility(View.GONE);
                break;
            case MediaPlayer.MEDIA_INFO_DOWNLOAD_RATE_CHANGED:
                downloadRateView.setText("" + extra + "kb/s" + "  ");
                break;
        }
        return true;
    }

    //全屏控制
    @Override
    public void OnPortraitClick() {
        if (isPortrait) {
            mc.setLockShow(true);
            LinearLayout.LayoutParams fl_lp = new LinearLayout.LayoutParams(
                    Utils.getHeightPixel(CheckDetailsSchedulePreviewActivity.this),
                    Utils.getWidthPixel(CheckDetailsSchedulePreviewActivity.this) - Utils.getStatusBarHeight(CheckDetailsSchedulePreviewActivity.this)
            );

            fl_controller.setLayoutParams(fl_lp);
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

            mVideoView.setVideoLayout(VideoView.VIDEO_LAYOUT_SCALE, 0);
            isPortrait = false;
        } else {

            halfScreen();
        }
    }

    @Override
    public void OnBackClick() {
        if (isPortrait) {
            finish();
        } else {
            halfScreen();
        }
    }

    private boolean isLock = false;

    @Override
    public void OnLockClick() {
        mc.setLockVisible(isLock);
        isLock = !isLock;
    }

    private void halfScreen() {
        mc.setLockShow(false);
        LinearLayout.LayoutParams fl_lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                videoHeight
        );
        fl_controller.setLayoutParams(fl_lp);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        mVideoView.setVideoLayout(VideoView.VIDEO_LAYOUT_SCALE, 0);
        isPortrait = true;
    }
    @Override
    protected boolean isNeedInitBack() {
        return false;
    }

    @Override
    protected String getTopbarTitle() {
        return null;
    }
}
