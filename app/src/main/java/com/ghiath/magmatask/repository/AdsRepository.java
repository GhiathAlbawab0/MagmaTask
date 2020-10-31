package com.ghiath.magmatask.repository;

import com.ghiath.kelshimall.api.ApiResponse;
import com.ghiath.kelshimall.api.ApiSuccessResponse;
import com.ghiath.magmatask.AppExecutors;
import com.ghiath.magmatask.ImageStatusEnum;
import com.ghiath.magmatask.db.AdEntityDao;
import com.ghiath.magmatask.db.AdImageEntityDao;
import com.ghiath.magmatask.entities.AdEntity;
import com.ghiath.magmatask.entities.AdImageEntity;
import com.ghiath.magmatask.utils.RateLimiter;
import com.ghiath.magmatask.vo.Resource;
import com.google.api.gax.paging.Page;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.Bucket;
import com.google.cloud.storage.Storage;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import timber.log.Timber;

//@Singleton
public class AdsRepository {

    public static final String BUKET_NAME="waseet-ads-images-bh";
   private final AppExecutors appExecutors;
    private final AdEntityDao adEntityDao;
    private final AdImageEntityDao adImageEntityDao;


    private final Storage bucketStorage;
    private RateLimiter<String> repoListRateLimit = new RateLimiter<>(10, TimeUnit.MINUTES);
    private final String CATIGORIES_KEY="catigories_key";
    private final String BANNERS_KEY="BANNERS_KEY";
    @Inject
    public AdsRepository(AppExecutors appExecutors
,AdEntityDao adEntityDao
,Storage bucketStorage
 ,       AdImageEntityDao adImageEntityDao) {
        this.appExecutors = appExecutors;
        this.adEntityDao= adEntityDao;
        this.adImageEntityDao= adImageEntityDao;
        this.bucketStorage= bucketStorage;
    }


    public LiveData<Resource<List<AdEntity>>> getAdEntitiesForInit(int rowsLimit,
                                                                    boolean forceFetch){
        return new NetworkBoundResource<List<AdEntity>, Page<Blob>>(appExecutors) {
            MediatorLiveData<ApiResponse<Page<Blob>>> apiResponseMediatorLiveData;
            @NotNull
            @Override
            protected LiveData<ApiResponse<Page<Blob>>> createCall() {
                apiResponseMediatorLiveData= new MediatorLiveData<>();
                appExecutors.networkIO().execute( () -> {
                Bucket bucket = bucketStorage.get(BUKET_NAME);
//                LiveDataGoogleAdapter<Bucket> ss=ss.LiveDataGoogleAdapter(bucket);
                Page<Blob>blobPage   =bucket.list();


                ApiResponse<Page<Blob>> ss=ApiResponse.Companion.create(bucket,blobPage);

                        apiResponseMediatorLiveData.postValue(ss);
                });

                        return apiResponseMediatorLiveData;

            }

            @Override
            protected boolean shouldFetch(@Nullable List<AdEntity> data) {
                  return forceFetch ||data==null || data.isEmpty();
                          //|| repoListRateLimit.shouldFetch(CATIGORIES_KEY);;
            }

            @Override
            protected Page<Blob>  processResponse(@NotNull ApiSuccessResponse<Page<Blob>> response) {
                return super.processResponse(response);
            }


            @Override
            protected void saveCallResult(Page<Blob> item) {
                int rows=rowsLimit;

//                ArrayList<AdEntity> adEntities=new ArrayList<>();

                for (Blob blob : item.iterateAll()) {

                    String blobId = blob.getName().substring(0, blob.getName().indexOf("-"));

                    AdEntity adEntity=new AdEntity(blobId,blobId,ImageStatusEnum.PENDING);
//                    Timber.d("ghiath adEntity =%s", adEntity);
//                    adEntities.add(adEntity);
//                    AdImageEntity adImageEntity=new AdImageEntity(blob.getName().substring(0, blob.getName().indexOf("."))
//                            , ImageStatusEnum.PENDING
//                            ,blobId
//                            ,null );
//                    Timber.d("ghiath adImageEntity =%s", adImageEntity);

                    adEntityDao.insert(adEntity);
//                    adImageEntityDao.insert(adImageEntity);

                    if(rowsLimit!=0) {
                        rows--;
                        if (rows == 0)
                            break;
                    }
                }

//                adEntityDao.insertAll(adEntities.toArray(new AdEntity[0]));
            }






            @NotNull
            @Override
            protected LiveData<List<AdEntity>> loadFromDb() {
                if (rowsLimit != 0)
                    return adEntityDao.getLatestOfCount(rowsLimit);
                else
                    return adEntityDao.getLatest();
            }



//            @Override
//            protected void onFetchFailed() {
//                repoListRateLimit.reset(CATIGORIES_KEY);
//            }
        }.asLiveData();
    }

    public LiveData<List<AdImageEntity>> downloadImages(ArrayList<AdImageEntity> adImageEntities, File outputCacheDir )
    {
        MediatorLiveData<List<AdImageEntity>> apiResponseMediatorLiveData= new MediatorLiveData<>();
        ArrayList<AdImageEntity> listAdImages=new ArrayList<>();
        appExecutors.diskIO().execute( () -> {
            for (AdImageEntity ai: adImageEntities) {


                Blob blob = bucketStorage.get(BlobId.of(BUKET_NAME, ai.getImageName()));

//        File outputDir = context.getCacheDir(); // context being the Activity pointer
                File outputDir = outputCacheDir;
                File outputFile = null;
                try {
                    outputFile = File.createTempFile(blob.getName().substring(0, blob.getName().indexOf("."))
                            , blob.getName().substring(blob.getName().indexOf("."))
                            , outputDir);

                    outputFile.deleteOnExit();

                    blob.downloadTo(new FileOutputStream(outputFile));
                    String imagePath = outputFile.getAbsolutePath();
                    Timber.d("ghiath imagePath =%s", imagePath);
                    ai.setPath(imagePath);
                    listAdImages.add(ai);

                } catch (Exception e) {
                    Timber.e(e, "ghiath");
                }
            }
            apiResponseMediatorLiveData.postValue(listAdImages);
        });
        return apiResponseMediatorLiveData;

    }

    public LiveData<AdEntity> getRandomAd()
    {
        return adEntityDao.getRandomAd();
    }
    public LiveData<AdEntity> findAdEntityById(String id)
    {
        return adEntityDao.findAdEntityById(id);
    }
public LiveData<Integer> getAdsCount()
    {
        return adEntityDao.getCount();
    }
public LiveData<Integer> getAPPROVEDImageCount()
    {
        return adImageEntityDao.findAPPROVEDImageCount();
    }
public LiveData<Integer> getRegejctedCount()
    {
        return adImageEntityDao.findREJECTEDImageCount();
    }
public LiveData<Integer> getPendingImageCount() {
    return adImageEntityDao.findPendingImageCount();
}

    public void updateAd(AdEntity adEntity)
    {
        Timber.d("ghiath in adEntityDao.update");
        appExecutors.diskIO().execute(() -> adEntityDao.update(adEntity));
    }
    public void updateAdImage(AdImageEntity e)
    {
        Timber.d("ghiath in adImageEntityDao.update");
        appExecutors.diskIO().execute(() -> adImageEntityDao.update(e));
    }

    public LiveData<Resource<List<AdImageEntity>>> getAdImages(AdEntity adEntity,
                                                                    boolean forceFetch){
        return new NetworkBoundResource<List<AdImageEntity>, Page<Blob>>(appExecutors) {
            MediatorLiveData<ApiResponse<Page<Blob>>> apiResponseMediatorLiveData;
            @NotNull
            @Override
            protected LiveData<ApiResponse<Page<Blob>>> createCall() {
                apiResponseMediatorLiveData= new MediatorLiveData<>();
                appExecutors.networkIO().execute( () -> {

                Bucket bucket = bucketStorage.get(BUKET_NAME);
//                LiveDataGoogleAdapter<Bucket> ss=ss.LiveDataGoogleAdapter(bucket);
//                    Timber.d("ghiath createCall in ")
                    Page<Blob> blobsFiltered =
                            bucket.list(
                                    Storage.BlobListOption.prefix(adEntity.getID()),
                                    Storage.BlobListOption.currentDirectory());


                ApiResponse<Page<Blob>> ss=ApiResponse.Companion.create(bucket,blobsFiltered);

                        apiResponseMediatorLiveData.postValue(ss);
                });

                        return apiResponseMediatorLiveData;

            }

            @Override
            protected boolean shouldFetch(@Nullable List<AdImageEntity> data) {
                  return forceFetch ||data==null || data.isEmpty();
                          //|| repoListRateLimit.shouldFetch(CATIGORIES_KEY);;
            }

            @Override
            protected Page<Blob>  processResponse(@NotNull ApiSuccessResponse<Page<Blob>> response) {
                return super.processResponse(response);
            }


            @Override
            protected void saveCallResult(Page<Blob> item) {

                for (Blob blob : item.iterateAll()) {

                    String blobId = blob.getName().substring(0, blob.getName().indexOf("-"));

                    AdImageEntity adImageEntity=new AdImageEntity(blob.getName().substring(0, blob.getName().indexOf("."))
                            , ImageStatusEnum.PENDING
                            ,blobId
                            ,null ,blob.getName());
                    Timber.d("ghiath adImageEntity =%s", adImageEntity);

//                    adEntityDao.insert(adEntity);
                    adImageEntityDao.insert(adImageEntity);


                }

            }






            @NotNull
            @Override
            protected LiveData<List<AdImageEntity>> loadFromDb() {

                    return adImageEntityDao.findAdImageEntitiesByParentId(adEntity.getID());

            }



//            @Override
//            protected void onFetchFailed() {
//                repoListRateLimit.reset(CATIGORIES_KEY);
//            }
        }.asLiveData();
    }


}
