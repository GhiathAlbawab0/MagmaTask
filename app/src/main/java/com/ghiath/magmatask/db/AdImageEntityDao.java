package com.ghiath.magmatask.db;

import com.ghiath.kelshimall.db.BaseDao;

import com.ghiath.magmatask.entities.AdImageEntity;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;

@Dao
public interface AdImageEntityDao extends BaseDao<AdImageEntity> {



    @Query("select * from AdImageEntity where AdImageId=:id")
    LiveData<AdImageEntity> findAdImageEntityById(String id);

    @Query("select * from AdImageEntity where AdId=:parentCategoryID")
    LiveData<List<AdImageEntity>> findAdImageEntitiesByParentId(String parentCategoryID);

    @Query("select * from AdImageEntity where AdId=:parentCategoryID and imageStatusEnum=='PENDING'")
    LiveData<List<AdImageEntity>> findPendingImageEntitiesByParentId(String parentCategoryID);



}
