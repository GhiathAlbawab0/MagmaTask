package com.ghiath.magmatask;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.ghiath.magmatask.common.RetryCallback;
import com.ghiath.magmatask.databinding.ActivityMainBinding;
import com.ghiath.magmatask.entities.AdEntity;
import com.ghiath.magmatask.entities.AdImageEntity;
import com.ghiath.magmatask.utils.GeneralUtils;
import com.ghiath.magmatask.viewModel.AdViewModel;
import com.ghiath.magmatask.vo.Resource;
import com.ghiath.magmatask.vo.Status;
import com.google.api.client.http.HttpTransport;
import com.google.api.gax.paging.Page;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.Bucket;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import com.google.common.collect.Lists;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;
import dagger.Module;
import dagger.android.AndroidInjection;
import dagger.android.ContributesAndroidInjector;
import timber.log.Timber;

public class MainActivity extends AppCompatActivity {
    String firstBlobName = null;
public static final String INITIAL_AD_KEY="INITIAL_AD_KEY";
public static final String INITIAL_IMAGES_KEY="INITIAL_IMAGES_KEY";
    @Inject
    AppExecutors appExecutors;

    ActivityMainBinding binding;

    @Inject ViewModelProvider.Factory factory;
    AdViewModel adViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main);

        adViewModel = ViewModelProviders.of(this, factory).get(AdViewModel.class);

        pathes=new ArrayList<>();

        binding.loadingState.setCallback(new RetryCallback() {
            @Override
            public void retry() {

            }

            @Override
            public void cancel() {

                binding.loadingState.setResource(null);
            }
        });


        adViewModel.getAdsCount().observe(this,count->{
            binding.recordsCountTv.setText(String.valueOf(count));
        });
        adViewModel.getPendingImageCount().observe(this,count->{
            binding.imagesCountTv.setText(String.valueOf(count));
        });
        adViewModel.getAPPROVEDImageCount().observe(this,count->{
            binding.acceptedCountTv.setText(String.valueOf(count));
        });
        adViewModel.getRegejctedCount().observe(this,count->{
            binding.rejectedCountTv.setText(String.valueOf(count));
        });
//        whenPathesListReady= strings -> appExecutors.mainThread().execute(() -> {

        AdEntity initialAd= (AdEntity) getIntent().getSerializableExtra(INITIAL_AD_KEY);
        List<AdImageEntity> adImageEntities= (List<AdImageEntity>) getIntent().getSerializableExtra(INITIAL_IMAGES_KEY);



        if(initialAd !=null&& adImageEntities!=null) {
            Timber.d("ghiath initialAd=%s",initialAd);
            Timber.d("ghiath adImageEntities=%s",adImageEntities);

            setupPager(adImageEntities);


        }
        binding.acceptTv.setOnClickListener(s->{
            if(myAdsPagerAdapter.getPathes()!=null && !myAdsPagerAdapter.getPathes().isEmpty()) {
                adViewModel.updateAdImage(myAdsPagerAdapter.getPathes().get(binding.pager.getCurrentItem()).setImageStatusEnum(ImageStatusEnum.APPROVED));

                if (binding.pager.getCurrentItem() + 1 < myAdsPagerAdapter.getCount()) {
                    binding.pager.setCurrentItem(binding.pager.getCurrentItem() + 1);
                } else {
                    handleApproveReject();
                }
            }
        });
        binding.rejectTv.setOnClickListener(s->{
            if(myAdsPagerAdapter.getPathes()!=null && !myAdsPagerAdapter.getPathes().isEmpty()) {
                adViewModel.updateAdImage(myAdsPagerAdapter.getPathes().get(binding.pager.getCurrentItem()).setImageStatusEnum(ImageStatusEnum.REJECTED));

                if (binding.pager.getCurrentItem() + 1 < myAdsPagerAdapter.getCount()) {
                    binding.pager.setCurrentItem(binding.pager.getCurrentItem() + 1);
                } else {
                    handleApproveReject();
                }
            }
        });

//        binding.pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//            @Override
//            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//
//            }
//
//            @Override
//            public void onPageSelected(int position) {
//                if(position==binding.pager.getChildCount()-1)
//                {
//
//                }
//            }
//
//            @Override
//            public void onPageScrollStateChanged(int state) {
//
//            }
//        });




        adViewModel.getAdImageForRandom().observe(this,listResource -> {
            if(listResource.getStatus()!=Status.SUCCESS)
            binding.setResponseResource(listResource);
            Timber.d("ghiath for random listResource =%s",listResource);

            if(listResource.getData()!=null && listResource.getStatus()==Status.SUCCESS)
//                for(AdImageEntity ai:listResource.getData())
                {
                    adViewModel.set_getApprovedCount(new Object());
                    adViewModel.set_getPendingCount(new Object());
                    adViewModel.set_getRejectedCount(new Object());

                    Timber.d("ghiath for random listResource.getData()=%s",listResource.getData());
                    adViewModel.downloadImages(new ArrayList<>(listResource.getData()),this.getCacheDir())
                            .observe(this,adImageForRandom -> {
                                setupPager(adImageForRandom);
                                binding.setResponseResource(null);
                            });
                }
        });

        adViewModel.set_adEntities(new Object());
        adViewModel.getAdEntities().observe(this,adEntities->{
            binding.setResponseResource(adEntities);
            if(adEntities.getData()!=null && adEntities.getStatus()==Status.SUCCESS)
            {
                adViewModel.set_getAdCount(new Object());
//                for(AdEntity ae:adEntities.getData())
//                {
                    Timber.d("ghiath adEntities.getData()=%s",adEntities.getData());
//                    adViewModel.set_adImageEntities(ae);
                    //do nothing
//                }
            }
        });
//        adViewModel.getAdImageEntities().observe(this,adImages->{
//                    if(adImages.getData()!=null && adImages.getStatus()==Status.SUCCESS)
//                        for(AdImageEntity ai:adImages.getData())
//                        {
//                            Timber.d("ghiath ai=%s",ai);
//                        }
//        });
//        });

//        appExecutors.networkIO().execute(() -> {
//            try {
//                binding.loadingState.setResource(new Resource <String>(Status.LOADING,null,null));
//                binding.loadingStateLin.setVisibility(View.VISIBLE);
//
//
//                GoogleCredentials credentials = GoogleCredentials.fromStream((getResources().openRawResource(R.raw.m)))
//                        .createScoped(Lists.newArrayList("https://www.googleapis.com/auth/cloud-platform"));
//                Storage storage = StorageOptions.newBuilder().setCredentials(credentials)
//                        .setProjectId("ak-works-272219").build().getService();
//
//                Timber.d("ghiath buckets ");
//                Bucket bucket = storage.get("waseet-ads-images-bh");
//                Page<Blob> blobs = bucket.list();
//
//
//                for (Blob blob : blobs.iterateAll()) {
//                    Timber.d("ghiath blob.getName =%s", blob.getName());
//                    firstBlobName = blob.getName().substring(0, blob.getName().indexOf("-"));
//                    Timber.d("ghiath firstBlobName =%s", firstBlobName);
//                    break;
//                }
//                if (firstBlobName != null) {
//                    appExecutors.diskIO().execute(() -> {
//                    Page<Blob> blobsFiltered =
//                            bucket.list(
//                                    Storage.BlobListOption.prefix(firstBlobName),
//                                    Storage.BlobListOption.currentDirectory());
//
//                    for (Blob blob : blobsFiltered.iterateAll()) {
//                        Timber.d("ghiath blobsFiltered  blob.getName =%s", blob.getName());
//                        File outputDir = this.getCacheDir(); // context being the Activity pointer
//                        File outputFile = null;
//                        try {
//                            outputFile = File.createTempFile(blob.getName().substring(0, blob.getName().indexOf("."))
//                                    , blob.getName().substring(blob.getName().indexOf("."))
//                                    , outputDir);
//                            blob.downloadTo(new FileOutputStream(outputFile));
//                            String imagePath = outputFile.getAbsolutePath();
//                            Timber.d("ghiath imagePath =%s", imagePath);
//                            outputFile.deleteOnExit();
//                            pathes.add(imagePath);
//
//
//                        } catch (IOException e) {
//                            Timber.e(e, "ghiath");
//                            binding.loadingState.setResource(new Resource <String>(Status.ERROR,null,e.getMessage()));
//                        }
//
//
//                    }
//                    whenPathesListReady.onReady(pathes);
//                    });
//                }
//
//
//
//            } catch (Exception e) {
//                Timber.e(e, "ghiath");
//                binding.loadingState.setResource(new Resource <String>(Status.ERROR,null,e.getMessage()));
//            }
//        });



//        if(adViewModel.getAdEntitiesForInit()!=null && adViewModel.getAdEntitiesForInit().getValue()!=null
//                && adViewModel.getAdEntitiesForInit().getValue().getData()!=null)
//        {
//            for(AdEntity ae:adViewModel.getAdEntitiesForInit().getValue().getData())
//            {
//                adViewModel.set_adImageEntities(ae);
//            }
//        }
//
//        adViewModel.getAdImageEntities().observe(this,listResource -> {
//
//        });
//
//
//        adViewModel.set_adEntities(new Object());
//        adViewModel.getAdEntities().observe(this, listResource -> {
//            if (listResource != null && listResource.getData() != null && listResource.getStatus() == Status.SUCCESS) {
//
//                Timber.d("ghiath listResource =%s", listResource);
//                startActivity(new Intent(this,MainActivity.class));
//                finish();
//            }
//            else if(listResource!=null && listResource.getStatus()==Status.ERROR)
//                GeneralUtils.showDialog(this,"Sorry "+listResource.getMessage()
//                        ,null,null,null,null);
//        });



//...
    }

    void handleApproveReject()
    {
        ImageStatusEnum imageStatusEnum = ImageStatusEnum.APPROVED;
        for (AdImageEntity adi : myAdsPagerAdapter.getPathes())
            if (adi.getImageStatusEnum() != ImageStatusEnum.APPROVED)
                imageStatusEnum = ImageStatusEnum.REJECTED;

        ImageStatusEnum finalImageStatusEnum = imageStatusEnum;
        GeneralUtils.showDialog(this, String.format("All Images of this Ad has been intracted with," +
                        " you can save this ad as %s \n or you can edit it before.", imageStatusEnum),
                "Save & Fetch Another", (dialog, which) -> {
                    LiveData<AdEntity> dd2=adViewModel.getRandomAd();
                    dd2.observe(MainActivity.this,adEntity -> {
                        Timber.d("ghiath in getRandomAd= %s",adEntity);
                        dd2.removeObservers(this);
                        adViewModel.set__adImageForRandom(adEntity);///////

                    });
                  LiveData<AdEntity> dd= adViewModel.findAdEntityById(myAdsPagerAdapter.getPathes().get(0).getAdId());
                            dd.observe(this, ad -> {
                                Timber.d("ghiath in findAdEntityById= %s",ad);
                                adViewModel.updateAd(ad.setImageStatusEnum(finalImageStatusEnum));
                                dd.removeObservers(this);
                    });

                }, "Edit", null);
    }

    void setupPager(List<AdImageEntity> adImageEntities){
        myAdsPagerAdapter = new MyAdsPagerAdapter(getSupportFragmentManager(), adImageEntities);
        binding.pager.setAdapter(myAdsPagerAdapter);
        binding.loadingState.setResource(null);
        binding.loadingStateLin.setVisibility(View.GONE);
        binding.imagesCountTv.setText(String.valueOf(pathes.size()));
    }

    MyAdsPagerAdapter myAdsPagerAdapter;
    ArrayList<String> pathes;
    WhenPathesListReady whenPathesListReady;


    public class MyAdsPagerAdapter extends FragmentStatePagerAdapter{

        List<AdImageEntity> pathes;

        public List<AdImageEntity> getPathes() {
            return pathes;
        }

        public MyAdsPagerAdapter(@NonNull FragmentManager fm, List<AdImageEntity> pathes) {
            super(fm);
            this.pathes=pathes;
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            if(pathes!=null && pathes.get(position).getPath()!=null)
            return ImageFragment.newInstance(pathes.get(position).getPath());
            return null;
        }

        @Override
        public int getCount() {
            if(pathes!=null)
                return pathes.size();
            return 0;
        }
    }

    interface WhenPathesListReady{
        void onReady(ArrayList<String> strings);
    }

    @Module
    public abstract class ActivityModule{
        @ContributesAndroidInjector()
        abstract MainActivity contributeMainActivityInjector();
    }
}
