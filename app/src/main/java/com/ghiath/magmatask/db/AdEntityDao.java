package com.ghiath.magmatask.db;

import com.ghiath.kelshimall.db.BaseDao;
import com.ghiath.magmatask.entities.AdEntity;
import com.ghiath.magmatask.entities.AdImageEntity;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;

@Dao
public interface AdEntityDao extends BaseDao<AdEntity> {

    @Query("select * from AdEntity where ID=:id")
    LiveData<AdEntity> findAdEntityById(String id);

    @Query("select * from AdEntity order by id desc limit :count")
    LiveData<List<AdEntity>> getLatestOfCount(int count);


    @Query("select * from AdEntity order by id desc ")
    LiveData<List<AdEntity>> getLatest();

    @Query("select * from AdEntity where imageStatusEnum=='PENDING' order by RANDOM() desc limit 1 ")
    LiveData<AdEntity> getRandomAd();

    @Query("select count(*) from AdEntity  ")
    LiveData<Integer> getCount();


}
