package com.ghiath.magmatask.di;

import android.content.Context;

import com.ghiath.magmatask.MagmaApplication;
import com.ghiath.magmatask.utils.LocalPersistence;
import com.ghiath.magmatask.utils.LocaleManager;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class LocalManagerModule {

    @Singleton
    @Provides
    public LocaleManager provideLocalManager(MagmaApplication application)
    {
        return new LocaleManager(application.getApplicationContext());
    }

    @Provides @Singleton
    Context provideApplicationContext(MagmaApplication context) {
        return context;
    }
    @Singleton @Provides
    public LocalPersistence provideLocalPersistence (Context context)
    {
        return new LocalPersistence(context);
    }
}
