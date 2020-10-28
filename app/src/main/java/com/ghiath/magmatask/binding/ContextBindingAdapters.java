package com.ghiath.magmatask.binding;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.telephony.PhoneNumberUtils;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import javax.inject.Inject;

import androidx.core.app.ShareCompat;
import androidx.databinding.BindingAdapter;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.LifecycleOwner;
import timber.log.Timber;

public class ContextBindingAdapters {
    private Context context;

    @Inject
    public ContextBindingAdapters(Context context) {
        this.context = context;
    }

    @BindingAdapter("imageUrl4Act")
    public void bindImage4Act(ImageView imageView, String url) {
        if(url!=null && !url.isEmpty())
        {
            Glide.with(context).load(url).into(imageView);
//            Timber.d("string imageUrl4Act url=%s",url);
        }
    }

    @BindingAdapter("imageUrl4Act")
    public void bindImage4Act(ImageView imageView, int url) {
        Glide.with(context).load(url).into(imageView);
//        Timber.d("int imageUrl4Act url=%s",url);
    }



}
