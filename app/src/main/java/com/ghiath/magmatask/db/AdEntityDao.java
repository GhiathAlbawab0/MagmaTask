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
    LiveData<AdImageEntity> findAdEntityById(String id);


}
