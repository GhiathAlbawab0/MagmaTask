//package com.ghiath.magmatask.viewModel;
//
//import com.ghiath.kelshimall.model.CreateNewPostRequestModel;
//import com.ghiath.kelshimall.model.LocationData;
//import com.ghiath.kelshimall.model.SpecificData;
//import com.ghiath.kelshimall.model.bases.BaseResponseModel;
//import com.ghiath.kelshimall.model.entities.LocationEntity;
//import com.ghiath.kelshimall.model.entities.SpecificDataEntity;
//import com.ghiath.magmatask.vo.Resource;
//import com.ghiath.kelshimall.repository.AddProductRepository;
//import com.ghiath.kelshimall.utils.AbsentLiveData;
//import com.ghiath.kelshimall.utils.LocalPersistence;
//import com.ghiath.kelshimall.utils.LocaleManager;
//
//import org.apache.commons.lang3.SerializationUtils;
//
//import java.util.List;
//import java.util.Objects;
//
//import javax.inject.Inject;
//import javax.inject.Singleton;
//
//import androidx.lifecycle.LiveData;
//import androidx.lifecycle.MediatorLiveData;
//import androidx.lifecycle.Transformations;
//import androidx.lifecycle.ViewModel;
//import timber.log.Timber;
//
//@Singleton
//public class AddProductViewModel extends ViewModel {
//
//
//    //    private final MediatorLiveData<LocationEntity> citiesInternalMediator;
////    private final MediatorLiveData<LocationEntity> areasInternalMediator;
//    @Inject
//    LocaleManager localeManager;
//    @Inject
//    LocalPersistence localPersistence;
//
////    public LatLng selectedLatLng;
//
//    private MediatorLiveData<SpecificData.RequestModel> _specificDataRequest = new MediatorLiveData<SpecificData.RequestModel>();
//    private MediatorLiveData<LocationData.RequestModel> _citiesRequestModel = new MediatorLiveData<LocationData.RequestModel>();
//    private MediatorLiveData<LocationData.RequestModel> _areaRequestModel = new MediatorLiveData<LocationData.RequestModel>();
//    private MediatorLiveData<LocationData.RequestModel> _neighbourRequestModel = new MediatorLiveData<LocationData.RequestModel>();
//    private MediatorLiveData<CreateNewPostRequestModel> _createNewPostRequestModel = new MediatorLiveData<CreateNewPostRequestModel>();
//    private MediatorLiveData<CreateNewPostRequestModel> _submitRequestModel = new MediatorLiveData<CreateNewPostRequestModel>();
//
//    private MediatorLiveData<CreateNewPostRequestModel> _mediatorcreateNewPostRequestModel = new MediatorLiveData<CreateNewPostRequestModel>();
//    private MediatorLiveData<CreateNewPostRequestModel> mediatorcreateNewPostRequestModel = new MediatorLiveData<CreateNewPostRequestModel>();
//
//    private LiveData<Resource<List<SpecificDataEntity>>> specificData;
//    private LiveData<Resource<List<LocationEntity>>> cities;
//    private LiveData<Resource<List<LocationEntity>>> areas;
//    private LiveData<Resource<List<LocationEntity>>> neighbours;
//    private MediatorLiveData<CreateNewPostRequestModel> createNewPostRequestModel = new MediatorLiveData<>();
//    //    private LiveData <Resource<CreateNewPostRequestModel>> createNewPostRequestModel;
//    private LiveData<Resource<BaseResponseModel>> submitRequestModel;
//
//
//    private AddProductRepository addProductRepository;
//
//    @Inject
//    public AddProductViewModel(AddProductRepository addProductRepository) {
//
//        this.addProductRepository = addProductRepository;
//
//        createNewPostRequestModel.addSource(
////                Transformations.distinctUntilChanged(
//                addProductRepository.getCurrentCreateNewPostRequestModel(false)
////                )
//                , createNewPostRequestModelResource -> {
//                    Timber.d("from viewmodel createNewPostRequestModelResource ()");
//                    Timber.d("createNewPostRequestModelResource = %s", createNewPostRequestModelResource);
//                    if (createNewPostRequestModelResource.getData() != null) {
//                        this.createNewPostRequestModel.
//                                setValue(createNewPostRequestModelResource.getData());
//                        if (!Objects.equals(createNewPostRequestModelResource.getData(), _createNewPostRequestModel.getValue()))
//                            this._createNewPostRequestModel.
//                                    setValue(createNewPostRequestModelResource.getData());
//
//                    }
//                });
//
////        createNewPostRequestModel.addSource(
////                Transformations.distinctUntilChanged(
////                _createNewPostRequestModel
////                )
////                ,createNewPostRequestModelResource -> {
////                    Timber.d("from viewmodel createNewPostRequestModelResource ()");
////                    Timber.d("createNewPostRequestModelResource = %s",createNewPostRequestModelResource );
////                    createNewPostRequestModel.setValue(
////                            addProductRepository.getCurrentCreateNewPostRequestModel()
////                                    .observe(()->
////                                            createNewPostRequestModelResource1 ->
////                                                    createNewPostRequestModel
////                                                            .setValue(createNewPostRequestModelResource1
////                                                                    )));
////
////                });
//
//
//
//
//        /*
//        //complete solution but needs to re-engineer the fragment
//        createNewPostRequestModel=Transformations.switchMap(
//                Transformations.distinctUntilChanged(_mediatorcreateNewPostRequestModel),input -> {
//                    Timber.d("from viewmodel createNewPostRequestModelResource ()");
//                    Timber.d("createNewPostRequestModelResource = %s",input );
//                    return addProductRepository.getCurrentCreateNewPostRequestModel();
//                });
//
//
//
//        mediatorcreateNewPostRequestModel.addSource(_createNewPostRequestModel,input -> {
//            Timber.d("from viewmodel _mediatorcreateNewPostRequestModel(_createNewPostRequestModel)");
//            Timber.d("input= %s",input);
//            if(input.getId()!=0)
//                addProductRepository.updateCreateNewPostRequestModel(input);
//            else
//                _mediatorcreateNewPostRequestModel.setValue(new CreateNewPostRequestModel());
//        });
//*/
//
//        specificData = Transformations.switchMap(_specificDataRequest, submittedMain -> {
//            Timber.d("from viewmodel Transformations.switchMap(_specificDataRequest)= %s ", submittedMain);
//            if (submittedMain != null) {
//                return addProductRepository.getSpecificDataValues(submittedMain);
//            } else
//                return AbsentLiveData.Companion.create();
//        });
//
//        cities = Transformations.switchMap(_citiesRequestModel, submittedMain -> {
//            Timber.d("from viewmodel Transformations.switchMap(_citiesRequestModel)");
//            if (submittedMain != null) {
//                return addProductRepository.getLocationList(submittedMain, LocationEntity.LocationType.CITY);
//            } else
//                return AbsentLiveData.Companion.create();
//        });
//
//        areas = Transformations.switchMap(_areaRequestModel, submittedMain -> {
//            Timber.d("from viewmodel Transformations.switchMap(_areaRequestModel)");
//            if (submittedMain != null) {
//                return addProductRepository.getLocationList(submittedMain, LocationEntity.LocationType.AREA);
//            } else
//                return AbsentLiveData.Companion.create();
//        });
//
//        neighbours = Transformations.switchMap(_neighbourRequestModel, submittedMain -> {
//            Timber.d("from viewmodel Transformations.switchMap(_neighbourRequestModel)");
//            if (submittedMain != null) {
//                return addProductRepository.getLocationList(submittedMain, LocationEntity.LocationType.NEIGHBOURHOOD);
//            } else
//                return AbsentLiveData.Companion.create();
//        });
////       citiesInternalMediator =new MediatorLiveData<>();
////        citiesInternalMediator .addSource(Transformations.distinctUntilChanged(getCities())
////                ,returned -> {
////                    Timber.d("in getcities  Observe in viewmodel");
////            if (returned.getData() != null
////                            && !returned.getData().isEmpty()
////                            &&returned.getData().size()>12
////
////            ) {
////                Timber.d("getcities observer, listResources = "
////                        +String.valueOf(returned.getData().size())
////                        +" Observe in viewmodel");
////
////                    set_areaRequestModel(new LocationData.RequestModel()
////                            .setCityID(returned.getData().get(12).getID()));
////            }
////
////        });
////        areasInternalMediator =new MediatorLiveData<>();
////        areasInternalMediator.addSource(Transformations.distinctUntilChanged(getAreas())
////                ,returned -> {
////            if (returned.getData() != null
////                            && !returned.getData().isEmpty()
////            )
////
////            {
////                Timber.d("getAreas observer, listResources = "
////                        +String.valueOf(returned.getData().size())
////                        +" Observe in viewmodel");
////
////                    set_neighbourRequestModel(new LocationData.RequestModel()
////                            .setCityID(_areaRequestModel.getValue().getCityID())
////                            .setAreaID(returned.getData().get(0).getID())
////                    );
////            }
////
////
////
////        });
//
//        submitRequestModel = Transformations.switchMap(_submitRequestModel, input -> {
//            Timber.d("from viewmodel Transformations.switchMap(_submitRequestModel)");
//            if (input != null) {
//                return addProductRepository.submitCreateNewPost(input);
//            } else
//                return AbsentLiveData.Companion.create();
////                return AbsentLiveData.Companion.create();
//        });
//
//    }
//
//    public LiveData<Resource<BaseResponseModel>> getSubmitRequestModel() {
//        return submitRequestModel;
//    }
//
//    public void set_submitRequestModel(CreateNewPostRequestModel _submitRequestModel) {
//        if (_submitRequestModel != this._submitRequestModel.getValue()) {
//            _submitRequestModel.fillUserData(localPersistence, localeManager);
//            this._submitRequestModel.setValue(_submitRequestModel);
//        }
//    }
//
//    public MediatorLiveData<CreateNewPostRequestModel> get_mediatorcreateNewPostRequestModel() {
//        return _mediatorcreateNewPostRequestModel;
//    }
//
//    private MediatorLiveData<CreateNewPostRequestModel> getCreateNewPostRequestModel() {
//        return _createNewPostRequestModel;
//    }
//
//    public MediatorLiveData<CreateNewPostRequestModel> getLiveDBCreateNewPostRequestModel() {
//        return createNewPostRequestModel;
//    }
//
//    public MediatorLiveData<LocationData.RequestModel> get_areaRequestModel() {
//        return _areaRequestModel;
//    }
//
////    public MediatorLiveData<LocationEntity> getCitiesInternalMediator() {
////        return citiesInternalMediator;
////    }
////
////    public MediatorLiveData<LocationEntity> getAreasInternalMediator() {
////        return areasInternalMediator;
////    }
//
//    public LiveData<Resource<List<LocationEntity>>> getCities() {
//        return cities;
//    }
//
//    public LiveData<Resource<List<LocationEntity>>> getAreas() {
//        return areas;
//    }
//
//    public LiveData<Resource<List<LocationEntity>>> getNeighbours() {
//        return neighbours;
//    }
//
//    public MediatorLiveData<CreateNewPostRequestModel> get_createNewPostRequestModel() {
//        return _createNewPostRequestModel;
//    }
//
//    public void clearCreatePostModel() {
//        if (createNewPostRequestModel != null && createNewPostRequestModel.getValue() != null) {
////            addProductRepository.deleteCreateNewPostRequestModel(createNewPostRequestModel.getValue().getData());
//
//            createNewPostRequestModel.removeSource(addProductRepository.getCurrentCreateNewPostRequestModel(false));
//            createNewPostRequestModel.addSource(
////                Transformations.distinctUntilChanged(
//                    addProductRepository.getCurrentCreateNewPostRequestModel(true)
////                )
//                    , createNewPostRequestModelResource -> {
//                        Timber.d("from viewmodel createNewPostRequestModelResource ()");
//                        Timber.d("createNewPostRequestModelResource = %s", createNewPostRequestModelResource);
//                        if (createNewPostRequestModelResource.getData() != null) {
//                            this.createNewPostRequestModel.
//                                    setValue(createNewPostRequestModelResource.getData());
//                            set_createNewPostRequestModel(createNewPostRequestModelResource.getData());
//                        }
//                    });
//
////            _createNewPostRequestModel.addSource(addProductRepository.getCurrentCreateNewPostRequestModel(),createNewPostRequestModelResource ->  {
////                _createNewPostRequestModel.setValue(createNewPostRequestModelResource .getData());
////                _createNewPostRequestModel.removeSource(addProductRepository.getCurrentCreateNewPostRequestModel());
////            });
//            set_createNewPostRequestModel(new CreateNewPostRequestModel());
//
//
//        }
//    }
//
//    public void set_createNewPostRequestModel(CreateNewPostRequestModel _createNewPostRequestModel) {
//        Timber.d("before (update) _createNewPostRequestModel = %s", _createNewPostRequestModel);
//        Timber.d("before (update) this._createNewPostRequestModel = %s", this._createNewPostRequestModel.getValue());
//        Timber.d("before (update) this.createNewPostRequestModel = %s", this.createNewPostRequestModel.getValue());
//        Timber.d("Objects.equals(createNewPostRequestModel.getValue(),this._createNewPostRequestModel.getValue()) = %s", Objects.equals(createNewPostRequestModel.getValue(), _createNewPostRequestModel));
//        if (!Objects.equals(createNewPostRequestModel.getValue(), _createNewPostRequestModel)) {
//            Timber.d("before (update) in equals if");
//            _createNewPostRequestModel.fillUserData(localPersistence, localeManager);
//            this._createNewPostRequestModel.setValue(_createNewPostRequestModel);
//            if (_createNewPostRequestModel.getId() != 0) {
//                Timber.d("(update) _createNewPostRequestModel = %s", _createNewPostRequestModel);
//                addProductRepository.updateCreateNewPostRequestModel(_createNewPostRequestModel);
//            }
//        }
//    }
//
////    public void set_specificDataRequest(MediatorLiveData<SpecificData.RequestModel> _specificDataRequest) {
////        this._specificDataRequest = _specificDataRequest;
////    }
//
//    public void set_citiesRequestModel(LocationData.RequestModel requestModel) {
//        if (requestModel != this._citiesRequestModel.getValue()) {
//            requestModel.fillUserData(localPersistence, localeManager);
//
//            this._citiesRequestModel.setValue(requestModel);
//        }
//    }
//
//    public void set_areaRequestModel(LocationData.RequestModel requestModel) {
//        if (requestModel != this._areaRequestModel.getValue()) {
//            requestModel.fillUserData(localPersistence, localeManager);
//
//            this._areaRequestModel.setValue(requestModel);
//        }
//    }
//
//    public void set_neighbourRequestModel(LocationData.RequestModel requestModel) {
//        if (requestModel != this._neighbourRequestModel.getValue()) {
//            requestModel.fillUserData(localPersistence, localeManager);
//
//            this._neighbourRequestModel.setValue(requestModel);
//        }
//    }
//
//    public void set_specificDataRequest(SpecificData.RequestModel requestModel) {
//        Timber.d("Objects.equals(requestModel,this._specificDataRequest.getValue())=%s", Objects.equals(requestModel, this._specificDataRequest.getValue()));
//        Timber.d("Objects.equals(requestModel,__requestModel=%s", requestModel);
//        if (requestModel != null && !Objects.equals(requestModel, this._specificDataRequest.getValue())) {
//            requestModel.fillUserData(localPersistence, localeManager);
//            this._specificDataRequest.setValue(requestModel);
//        } else if (requestModel == null)
//            this._specificDataRequest.setValue(null);
//    }
//
//
//    public LiveData<Resource<List<SpecificDataEntity>>> getSpecificData() {
//        return this.specificData;
//    }
//
//    public CreateNewPostRequestModel getCreateRequestModelForSubmission(boolean forSubmission) {
//
//        CreateNewPostRequestModel createNewPostRequestModel = get_createNewPostRequestModel().getValue();
//        CreateNewPostRequestModel tempCreateNewPostRequestModel = new CreateNewPostRequestModel();
//        ;
//        if (createNewPostRequestModel != null) {
////
////
////            tempCreateNewPostRequestModel.setCategoryID(createNewPostRequestModel.getCategoryID());
////            tempCreateNewPostRequestModel.setSubCategoryID(createNewPostRequestModel.getSubCategoryID());
////            tempCreateNewPostRequestModel.setPostDescription(createNewPostRequestModel.getPostDescription());
////            tempCreateNewPostRequestModel.setPostDescription(createNewPostRequestModel.getPostDescription());
////            tempCreateNewPostRequestModel.setPostTitle(createNewPostRequestModel.getPostTitle());
////            tempCreateNewPostRequestModel.setPrice(createNewPostRequestModel.getPrice());
////            tempCreateNewPostRequestModel.setCity(createNewPostRequestModel.getCity());
////            tempCreateNewPostRequestModel.setArea(createNewPostRequestModel.getArea());
////            tempCreateNewPostRequestModel.setNeighborhood(createNewPostRequestModel.getNeighborhood());
////            tempCreateNewPostRequestModel.setNegotiable(createNewPostRequestModel.getNegotiable());
////            tempCreateNewPostRequestModel.setHideEmail(createNewPostRequestModel.getHideEmail());
////            tempCreateNewPostRequestModel.setHideMobile(createNewPostRequestModel.getHideMobile());
////            tempCreateNewPostRequestModel.setHideWhatsApp(createNewPostRequestModel.getHideWhatsApp());
////            tempCreateNewPostRequestModel.setImages(createNewPostRequestModel.getImages());
////            tempCreateNewPostRequestModel.setCategorySpecificData(createNewPostRequestModel.getCategorySpecificData());
////
////            tempCreateNewPostRequestModel.setUserName(createNewPostRequestModel.getUserName());
////            tempCreateNewPostRequestModel.setLangID(createNewPostRequestModel.getLangID());
////            tempCreateNewPostRequestModel.setSessionToken(createNewPostRequestModel.getSessionToken());
////
////            if(!forSubmission)
////            {
////                tempCreateNewPostRequestModel.setMainCategory(createNewPostRequestModel.getMainCategory());
////                tempCreateNewPostRequestModel.setSubCategory(createNewPostRequestModel.getSubCategory());
////                tempCreateNewPostRequestModel.setSpecificDataEntitiesList(createNewPostRequestModel.getSpecificDataEntitiesList());
////                tempCreateNewPostRequestModel.setId(createNewPostRequestModel.getId());
////            }
//            tempCreateNewPostRequestModel = SerializationUtils.clone(createNewPostRequestModel);
//            if (forSubmission) {
//                tempCreateNewPostRequestModel.setMainCategory(null);
//                tempCreateNewPostRequestModel.setSubCategory(null);
//                tempCreateNewPostRequestModel.setSpecificDataEntitiesList(null);
//            }
//            if (tempCreateNewPostRequestModel != null && tempCreateNewPostRequestModel.getUserName() == null)
//                tempCreateNewPostRequestModel.fillUserData(localPersistence, localeManager);
//
//            return tempCreateNewPostRequestModel;
//        } else return null;
//
//    }
//
////    public void retry() {
////        switch (calledRequest) {
////            case getMainCategories: {
////                if (this._mainCat.getValue() != null)
////                    this._mainCat.setValue(this._mainCat.getValue());
////                break;
////            }
////            case getSubCategories:{
////                if(this._subCat.getValue()!=null)
////                    this._subCat.setValue(this._subCat.getValue());
////                break;
////            }
////            default:
////                break;
////        }
////    }
//
//
////    private HomeFragViewModel.CalledRequest calledRequest;
//
////    public HomeFragViewModel.CalledRequest getCalledRequest() {
////        return calledRequest;
////    }
//
////    public void setCalledRequest(HomeFragViewModel.CalledRequest calledRequest) {
////        this.calledRequest = calledRequest;
////    }
//
//    public void handleUploadImageClick( CreateNewPostRequestModel.Image item)
//    {
//        CreateNewPostRequestModel createNewPostRequestModel = getCreateRequestModelForSubmission(false);
//        if (createNewPostRequestModel != null
//                && createNewPostRequestModel.getImages() != null) {
//
//            for (CreateNewPostRequestModel.Image img : createNewPostRequestModel.getImages()) {
//                if (img.equals(item)) {
//                    if (img.getIsMainImage().getValue())
//                        return;
//
//                    else
//                        img.setIsMainImage(CreateNewPostRequestModel.NYMapper.Y);
//                } else
//                    img.setIsMainImage(CreateNewPostRequestModel.NYMapper.N);
//            }
//            set_createNewPostRequestModel(createNewPostRequestModel);
//        }
//
//    }
//
//    public LocalPersistence getLocalPersistence() {
//        return localPersistence;
//    }
//}
