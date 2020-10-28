//package com.ghiath.magmatask.db;
//
//import com.ghiath.kelshimall.model.CreateNewPostRequestModel;
//
//import javax.inject.Singleton;
//
//import androidx.lifecycle.LiveData;
//import androidx.room.Dao;
//import androidx.room.Query;
//
//@Singleton
//@Dao
//public abstract class CreateNewPostRequestModelDao extends BaseTransactionDao<CreateNewPostRequestModel> {
//
//
//    @Query("select * from CreateNewPostRequestModel where id=:userId")
//    public abstract LiveData<CreateNewPostRequestModel> findPostByUserId(long userId);
//
//    @Query("select * from CreateNewPostRequestModel order by id desc limit 1")
//    public abstract LiveData<CreateNewPostRequestModel> getCurrentCreateNewPostRequestModel();
//
//    @Query("delete from CreateNewPostRequestModel where :rowsToKeep=:rowsToKeep")
//    @Override
//    public abstract void cleanDelete(int rowsToKeep);
//
////    @Query("delete from CreateNewPostRequestModel where id=:id")
////    public abstract  LiveData<Integer> deleteEntity(long id) ;
//}
