package com.danggui.publishsystem.utils;

import android.content.Context;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.danggui.publishsystem.R;
import com.danggui.publishsystem.control.CodeConstants;

public class GlideHelper {

    public static void showAvatarWithUrl(@Nullable Context context, String url, @Nullable ImageView imageView) {
        Glide.with(context)
                .load(CodeConstants.URL_Query + url)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.default_head_icon)
                .error(R.drawable.default_head_icon)
                .transform(new GlideCircleTransform(context))
                .into(imageView);
    }

    public static void showImageWithUrl(@Nullable Context context, String url, @Nullable ImageView imageView) {
        Glide.with(context)
                .load(CodeConstants.URL_Query + url)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.default_title)
                .error(R.drawable.default_title)
                .dontAnimate()
                .into(imageView);
        LogUtil.i("GlideHelper", "imageurl=" + CodeConstants.URL_Query + url);
    }

    public static void showImageWithFullUrl(@Nullable Context context, String url, @Nullable ImageView imageView) {
        Glide.with(context)
                .load(url)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.default_title)
                .error(R.drawable.default_title)
                .dontAnimate()
                .into(imageView);
    }

    public static void showBannerWithUrl(int tag, @Nullable Context context, String url, @Nullable ImageView imageView) {
        if (tag == 1) {
            Glide.with(context)
                    .load(CodeConstants.URL_Query + url)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .placeholder(R.drawable.default_title)
                    .error(R.drawable.default_title)
                    .dontAnimate()
                    .into(imageView);
        } else {
            Glide.with(context)
                    .load(CodeConstants.URL_Query + url)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .placeholder(R.drawable.default_title)
                    .error(R.drawable.default_title)
                    .dontAnimate()
                    .into(imageView);
        }
    }
}
