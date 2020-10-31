package com.ghiath.magmatask.viewModel;


import com.ghiath.kelshimall.utils.AbsentLiveData;
import com.ghiath.magmatask.entities.AdEntity;
import com.ghiath.magmatask.entities.AdImageEntity;
import com.ghiath.magmatask.repository.AdsRepository;
import com.ghiath.magmatask.utils.LocalPersistence;
import com.ghiath.magmatask.utils.LocaleManager;
import com.ghiath.magmatask.vo.Resource;


import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;
import timber.log.Timber;

@Singleton
public class AdViewModel extends ViewModel {


    //    private final MediatorLiveData<LocationEntity> citiesInternalMediator;
//    private final MediatorLiveData<LocationEntity> areasInternalMediator;
    @Inject
    LocaleManager localeManager;
    @Inject
    LocalPersistence localPersistence;

//    public LatLng selectedLatLng;

    private MediatorLiveData<Object> _adEntitiesForInit = new MediatorLiveData<Object>();
    private MediatorLiveData<Object> _adEntities = new MediatorLiveData<Object>();
    private MediatorLiveData<AdEntity> _adImageEntities = new MediatorLiveData<AdEntity>();
    private MediatorLiveData<AdEntity> _adImageForRandom = new MediatorLiveData<AdEntity>();

    private MediatorLiveData<Object> _getAdCount = new MediatorLiveData<Object>();
    private LiveData<Integer> getAdCount;
    private MediatorLiveData<Object> _getApprovedCount = new MediatorLiveData<Object>();
    private LiveData<Integer> getApprovedCount;
    private MediatorLiveData<Object> _getRejectedCount= new MediatorLiveData<Object>();
    private LiveData<Integer> getRejectedCount;
    private MediatorLiveData<Object> _getPendingCount= new MediatorLiveData<Object>();
    private LiveData<Integer> getPendingCount;

    private LiveData<Resource<List<AdEntity>>> adEntitiesForInit ;
    private LiveData<Resource<List<AdEntity>>> adEntities ;
    private LiveData<Resource<List<AdImageEntity>>> adImageEntities ;
    private LiveData<Resource<List<AdImageEntity>>> adImageForRandom;


    private AdsRepository addProductRepository;

    @Inject
    public AdViewModel(AdsRepository addProductRepository) {

        this.addProductRepository = addProductRepository;

        getPendingCount=Transformations.switchMap(_getPendingCount, submittedMain -> {

            Timber.d("from viewmodel Transformations.switchMap(_specificDataRequest)= %s ", submittedMain);
            if (submittedMain != null) {
                return addProductRepository.getPendingImageCount();
            } else
                return AbsentLiveData.Companion.create();
        });
        getApprovedCount=Transformations.switchMap(_getApprovedCount, submittedMain -> {

            Timber.d("from viewmodel Transformations.switchMap(_specificDataRequest)= %s ", submittedMain);
            if (submittedMain != null) {
                return addProductRepository.getAPPROVEDImageCount();
            } else
                return AbsentLiveData.Companion.create();
        });
        getRejectedCount=Transformations.switchMap(_getRejectedCount, submittedMain -> {

            Timber.d("from viewmodel Transformations.switchMap(_specificDataRequest)= %s ", submittedMain);
            if (submittedMain != null) {
                return addProductRepository.getRegejctedCount();
            } else
                return AbsentLiveData.Companion.create();
        });
        getAdCount=Transformations.switchMap(_getAdCount, submittedMain -> {

            Timber.d("from viewmodel Transformations.switchMap(_specificDataRequest)= %s ", submittedMain);
            if (submittedMain != null) {
                return addProductRepository.getAdsCount();
            } else
                return AbsentLiveData.Companion.create();
        });

        adImageForRandom=Transformations.switchMap(_adImageForRandom, submittedMain -> {

            Timber.d("ghiath from viewmodel Transformations.switchMap(_specificDataRequest)= %s ", submittedMain);
            if (submittedMain != null) {
                return  addProductRepository.getAdImages(submittedMain,true);
            } else
                return AbsentLiveData.Companion.create();
        });

        adEntitiesForInit= Transformations.switchMap(_adEntitiesForInit, submittedMain -> {
            Timber.d("from viewmodel Transformations.switchMap(_specificDataRequest)= %s ", submittedMain);
            if (submittedMain != null) {
                return addProductRepository.getAdEntitiesForInit(100,true);
            } else
                return AbsentLiveData.Companion.create();
        });
        adEntities= Transformations.switchMap(_adEntities, submittedMain -> {
            Timber.d("from viewmodel Transformations.switchMap(_specificDataRequest)= %s ", submittedMain);
            if (submittedMain != null) {
                return addProductRepository.getAdEntitiesForInit(0,true);
            } else
                return AbsentLiveData.Companion.create();
        });

        adImageEntities= Transformations.switchMap(_adImageEntities, submittedMain -> {
            Timber.d("from viewmodel Transformations.switchMap(_specificDataRequest)= %s ", submittedMain);
            if (submittedMain != null) {
                return addProductRepository.getAdImages(submittedMain,true);
            } else
                return AbsentLiveData.Companion.create();
        });



    }

    public void set_getAdCount(Object _getAdCount) {
        this._getAdCount .postValue(_getAdCount);
    }

    public void set_getApprovedCount(Object _getApprovedCount) {
        this._getApprovedCount .postValue( _getApprovedCount);
    }

    public void set_getRejectedCount(Object _getRejectedCount) {
        this._getRejectedCount .postValue( _getRejectedCount);
    }

    public void set_getPendingCount(Object _getPendingCount) {
        this._getPendingCount .postValue( _getPendingCount);
    }

    public LiveData<Integer> getGetAdCount() {
        return getAdCount;
    }

    public LiveData<Integer> getGetRejectedCount() {
        return getRejectedCount;
    }

    public LiveData<Integer> getGetPendingCount() {
        return getPendingCount;
    }

    public LiveData<Integer> getGetApprovedCount() {
        return getApprovedCount;
    }

    public LiveData<List<AdImageEntity>> downloadImages(ArrayList<AdImageEntity> adImageEntities, File outputCacheDir)
    {
       return addProductRepository.downloadImages(adImageEntities,outputCacheDir);
    }

    public LiveData<AdEntity> getRandomAd()
    {
        return addProductRepository.getRandomAd();
    }

    public LiveData<AdEntity> findAdEntityById(String id)
    {
        return Transformations.distinctUntilChanged( addProductRepository.findAdEntityById(id));
    }

    public void updateAd(AdEntity adEntity)
    {
         addProductRepository.updateAd(adEntity);
    }
    public void updateAdImage(AdImageEntity e)
    {
         addProductRepository.updateAdImage(e);
    }

    public LiveData<Integer> getAdsCount()
    {
        return addProductRepository.getAdsCount();
    }
    public LiveData<Integer> getRegejctedCount()
    {
        return addProductRepository.getRegejctedCount();
    }
    public LiveData<Integer> getAPPROVEDImageCount()
    {
        return addProductRepository.getAPPROVEDImageCount();
    }
    public LiveData<Integer> getPendingImageCount()
    {
        return addProductRepository.getPendingImageCount();
    }

    public void set_adEntities(Object _adEntities) {
        this._adEntities .postValue(_adEntities);
    }

    public LiveData<Resource<List<AdImageEntity>>> getAdImageForRandom() {
        return adImageForRandom;
    }

    public void set__adImageForRandom(AdEntity _adImageForRandom) {
        this._adImageForRandom .postValue(_adImageForRandom);
    }
    public void set_adImageEntities(AdEntity _adImageEntities) {
        this._adImageEntities .postValue(_adImageEntities);
    }

    public void set_adEntitiesForInit(Object _adEntitiesForInit) {
        this._adEntitiesForInit .postValue(_adEntitiesForInit);
    }

    public LiveData<Resource<List<AdEntity>>> getAdEntitiesForInit() {
        return adEntitiesForInit;
    }

    public LiveData<Resource<List<AdEntity>>> getAdEntities() {
        return adEntities;
    }

    public LiveData<Resource<List<AdImageEntity>>> getAdImageEntities() {
        return adImageEntities;
    }

    public LocalPersistence getLocalPersistence() {
        return localPersistence;
    }
}
