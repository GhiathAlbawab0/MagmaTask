package com.ghiath.magmatask.db;





import com.ghiath.magmatask.entities.AdImageEntity;

import org.jetbrains.annotations.NotNull;

import javax.inject.Singleton;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(
        entities = {

                 AdImageEntity.class
        },
        version = 35,
        exportSchema = false)
//@TypeConverters({LocationTypeConverters.class})
@Singleton
public abstract class MagmaDb extends RoomDatabase {

    @NotNull
    public abstract AdImageEntityDao categoryDao();




}
