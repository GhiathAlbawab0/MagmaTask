package com.ghiath.magmatask;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Typeface;

import com.ghiath.magmatask.di.AppInjector;
import com.ghiath.magmatask.utils.LocaleManager;


import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;

import androidx.core.content.res.ResourcesCompat;

import androidx.multidex.MultiDex;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;
import timber.log.Timber;

//import com.ghiath.magmatask.di.AppInjector;

public class MagmaApplication extends Application implements HasActivityInjector {
    @Inject
    DispatchingAndroidInjector<Activity> dispatchingActivityInjector;



    static Typeface cairo_regular;

    @Override
    public void onCreate() {
        LocaleManager.setNewLocale(this,LocaleManager.getLanguage(this));

//        ///Todo: remove this for dev
//        User item=new User();
//        item.setUserID("591e1884-8e0c-4356-b06d-2c669d40c0c4");
//        item.setSessionToken("4fcc3cf1-2e8d-42ca-b2b8-961729197703");
////        userDao.insertUser(item);
//        new LocalPersistence(this).putUserObjectWrapper(item);
//
// User_ID : 591e1884-8e0c-4356-b06d-2c669d40c0c4
//SESSION_ID : 4fcc3cf1-2e8d-42ca-b2b8-961729197703/***//



        if(cairo_regular==null)
        cairo_regular= ResourcesCompat.getFont(this,
                R.font.cairo_regular);

        super.onCreate();

        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }
        AppInjector.INSTANCE.init(this);
    }
//
    @Override
    public void onConfigurationChanged(@NotNull Configuration newConfig) {
        LocaleManager.setNewLocale(this,LocaleManager.getLanguage(this));

        super.onConfigurationChanged(newConfig);
    }

    public Typeface getCairo_regular() {

        if(cairo_regular==null)
            cairo_regular= ResourcesCompat.getFont(this,
                    R.font.cairo_regular);

        return cairo_regular;
    }
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    public AndroidInjector<Activity> activityInjector() {
        return dispatchingActivityInjector;
    }


}
