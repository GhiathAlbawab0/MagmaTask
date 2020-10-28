package com.ghiath.magmatask.db

import androidx.room.*
import com.ghiath.kelshimall.db.BaseDao

abstract class BaseTransactionDao<T> : BaseDao<T> {

    abstract fun cleanDelete(rowsToKeep: Int)


    @Transaction
    open  fun insertWithCleanExceptCount(vararg array: T) {
        val rowsToKeep = 100
        cleanDelete(rowsToKeep)
        insertAll(*array)

    }
}