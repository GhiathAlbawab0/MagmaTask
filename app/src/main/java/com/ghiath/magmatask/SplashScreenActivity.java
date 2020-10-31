package com.ghiath.magmatask;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.ghiath.magmatask.utils.GeneralUtils;
import com.ghiath.magmatask.utils.LocalPersistence;
import com.ghiath.magmatask.utils.LocaleManager;
import com.ghiath.magmatask.viewModel.AdViewModel;
import com.ghiath.magmatask.vo.Status;

import java.io.Serializable;
import java.util.ArrayList;

import javax.inject.Inject;

import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import dagger.Module;
import dagger.android.AndroidInjection;
import dagger.android.ContributesAndroidInjector;
import timber.log.Timber;

public class SplashScreenActivity extends FragmentActivity {
//
    LocalPersistence localPersistence;
    @Inject ViewModelProvider.Factory factory;
    @Inject AppExecutors appExecutors;
    AdViewModel adViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        setContentView(R.layout.activity_splash_screen);
        super.onCreate(savedInstanceState);

        adViewModel = ViewModelProviders.of(this, factory).get(AdViewModel.class);

        LocaleManager.setNewLocale(this, LocaleManager.getLanguage(this));


        Handler handler = new Handler();
        localPersistence = new LocalPersistence(this);

//        handler.postDelayed(() -> {
//            if(localPersistence.getUserObjectWrapper()!=null
//                    && (
//                            localPersistence.getUserObjectWrapper().getUserID()!=null
//                            &&!localPersistence.getUserObjectWrapper().getUserID().isEmpty()
//                            &&localPersistence.getUserObjectWrapper().getSessionToken()!=null
//                            &&!localPersistence.getUserObjectWrapper().getSessionToken().isEmpty()
//                             ))
//                startActivity(new Intent(SplashScreenActivity.this, HomeActivity.class));
//            else
//            {
//
//                startActivity(new Intent(SplashScreenActivity.this, LoginActivity.class));
//
//            }
//
//            finish();
//        },5000);


//        ((TextView)findViewById(R.id.version_tv)).setText(BuildConfig.VERSION_NAME);

        adViewModel.set_adEntitiesForInit(new Object());
        adViewModel.getAdEntitiesForInit().observe(this, listResource -> {
            if (listResource != null && listResource.getData() != null && listResource.getStatus() == Status.SUCCESS) {

                Timber.d("ghiath listResource =%s", listResource);

                adViewModel.getRandomAd().observe(this,adEntity -> {
                  adViewModel.set_adImageEntities(adEntity);

                  adViewModel.getAdImageEntities().observe(this, imagesList ->{
                      if(imagesList.getStatus()==Status.SUCCESS &&imagesList.getData()!=null)

                          adViewModel.downloadImages(new ArrayList<>(imagesList.getData()),this.getCacheDir())
                             .observe(this,adImageEntities -> {
                                 startActivity(new Intent(this,MainActivity.class)
                                         .putExtra(MainActivity.INITIAL_AD_KEY,adEntity)
                                         .putExtra(MainActivity.INITIAL_IMAGES_KEY, (Serializable) adImageEntities));
                                 finish();
                             });



                  });
                });



            }
            else if(listResource!=null && listResource.getStatus()==Status.ERROR)
                GeneralUtils.showDialog(this,"Sorry "+listResource.getMessage()
                        ,null,null,null,null);
        });
    }

    @Module
    public abstract class ActivityModule{
        @ContributesAndroidInjector()
        abstract SplashScreenActivity contributeSplashScreenActivityInjector();
    }
}
