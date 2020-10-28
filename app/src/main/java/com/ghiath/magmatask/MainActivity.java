package com.ghiath.magmatask;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import dagger.Module;
import dagger.android.AndroidInjection;
import dagger.android.ContributesAndroidInjector;
import timber.log.Timber;

import android.os.Bundle;
import android.view.View;

import com.ghiath.magmatask.common.RetryCallback;
import com.ghiath.magmatask.databinding.ActivityMainBinding;
import com.ghiath.magmatask.utils.CloudStorage;
import com.ghiath.magmatask.vo.Resource ;
import com.ghiath.magmatask.vo.Status;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.gax.paging.Page;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.Bucket;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import com.google.common.collect.Lists;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

public class MainActivity extends AppCompatActivity {
    String firstBlobName = null;

    @Inject
    AppExecutors appExecutors;

    ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main);

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
        whenPathesListReady= strings -> appExecutors.mainThread().execute(() -> {
            myAdsPagerAdapter = new MyAdsPagerAdapter(getSupportFragmentManager(), strings);
            binding.pager.setAdapter(myAdsPagerAdapter);
            binding.loadingState.setResource(null);
            binding.loadingStateLin.setVisibility(View.GONE);
            binding.imagesCountTv.setText(String.valueOf(pathes.size()));
        });

        appExecutors.networkIO().execute(() -> {
            HttpTransport httpTransport = null;
            try {
                binding.loadingState.setResource(new Resource <String>(Status.LOADING,null,null));
                binding.loadingStateLin.setVisibility(View.VISIBLE);


                GoogleCredentials credentials = GoogleCredentials.fromStream((getResources().openRawResource(R.raw.m)))
                        .createScoped(Lists.newArrayList("https://www.googleapis.com/auth/cloud-platform"));
                Storage storage = StorageOptions.newBuilder().setCredentials(credentials)
                        .setProjectId("ak-works-272219").build().getService();

                Timber.d("ghiath buckets ");
                Bucket bucket = storage.get("waseet-ads-images-bh");
                Page<Blob> blobs = bucket.list();


                for (Blob blob : blobs.iterateAll()) {
                    Timber.d("ghiath blob.getName =%s", blob.getName());
                    firstBlobName = blob.getName().substring(0, blob.getName().indexOf("-"));
                    Timber.d("ghiath firstBlobName =%s", firstBlobName);
                    break;
                }
                if (firstBlobName != null) {
                    appExecutors.diskIO().execute(() -> {
                    Page<Blob> blobsFiltered =
                            bucket.list(
                                    Storage.BlobListOption.prefix(firstBlobName),
                                    Storage.BlobListOption.currentDirectory());

                    for (Blob blob : blobsFiltered.iterateAll()) {
                        Timber.d("ghiath blobsFiltered  blob.getName =%s", blob.getName());
                        File outputDir = this.getCacheDir(); // context being the Activity pointer
                        File outputFile = null;
                        try {
                            outputFile = File.createTempFile(blob.getName().substring(0, blob.getName().indexOf("."))
                                    , blob.getName().substring(blob.getName().indexOf("."))
                                    , outputDir);
                            blob.downloadTo(new FileOutputStream(outputFile));
                            String imagePath = outputFile.getAbsolutePath();
                            Timber.d("ghiath imagePath =%s", imagePath);
                            outputFile.deleteOnExit();
                            pathes.add(imagePath);


                        } catch (IOException e) {
                            Timber.e(e, "ghiath");
                            binding.loadingState.setResource(new Resource <String>(Status.ERROR,null,e.getMessage()));
                        }


                    }
                    whenPathesListReady.onReady(pathes);
                    });
                }



            } catch (Exception e) {
                Timber.e(e, "ghiath");
                binding.loadingState.setResource(new Resource <String>(Status.ERROR,null,e.getMessage()));
            }
        });



//...
    }
    MyAdsPagerAdapter myAdsPagerAdapter;
    ArrayList<String> pathes;
    WhenPathesListReady whenPathesListReady;


    public class MyAdsPagerAdapter extends FragmentStatePagerAdapter{

        ArrayList<String> pathes;
        public MyAdsPagerAdapter(@NonNull FragmentManager fm, ArrayList<String> pathes) {
            super(fm);
            this.pathes=pathes;
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            if(pathes!=null)
            return ImageFragment.newInstance(pathes.get(position));
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
