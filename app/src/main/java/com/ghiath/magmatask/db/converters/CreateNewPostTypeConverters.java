package com.ghiath.magmatask.db.converters;

import com.ghiath.magmatask.entities.AdImageEntity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

import androidx.room.TypeConverter;

public class CreateNewPostTypeConverters {

    @TypeConverter
    public static AdImageEntity stringToCategotryEntity(String json) {
        Gson gson = new Gson();
        Type type = new TypeToken<AdImageEntity>() {}.getType();
        return gson.fromJson(json, type);
    }

    @TypeConverter
    public static String categoryEntityToString(AdImageEntity entity) {
        Gson gson = new Gson();
        Type type = new TypeToken<AdImageEntity>() {}.getType();
        return gson.toJson(entity, type);
    }

}