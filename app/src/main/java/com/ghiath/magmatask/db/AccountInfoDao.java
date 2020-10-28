//package com.ghiath.magmatask.db;
//
//import com.ghiath.kelshimall.model.AccountInfo;
//
//import javax.inject.Singleton;
//
//import androidx.lifecycle.LiveData;
//import androidx.room.Dao;
//import androidx.room.Query;
//
//@Singleton
//@Dao
//public abstract class AccountInfoDao extends BaseTransactionDao<AccountInfo> {
//
//
//
//    @Query("select * from AccountInfo where userName=:userId")
//    public abstract LiveData<AccountInfo> findProductDetailsById(String userId);
//
//
//
//    @Query("delete from AccountInfo where userName  not in " +
//            "(select userName from AccountInfo " +
//                    "order by insertedDate desc limit :rowsToKeep)")
//    @Override
//    public abstract void cleanDelete(int rowsToKeep);
//
//
//
//
//}
