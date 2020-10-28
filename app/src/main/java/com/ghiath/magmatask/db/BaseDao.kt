package com.ghiath.kelshimall.db

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Update

interface BaseDao<T> {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(obj: T)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg obj: T)
    @Delete
    fun delete(vararg obj: T)
    @Update
    fun update(vararg obj: T)

}