package com.ghiath.magmatask.db.converters;



import com.ghiath.magmatask.ImageStatusEnum;

import androidx.room.TypeConverter;

public class LocationTypeConverters {

    @TypeConverter
    public static ImageStatusEnum fromImageStatusEnum(String value) {
        return value == null ? null : ImageStatusEnum.valueOf(value);
    }
    @TypeConverter
    public static String dateToTimestamp(ImageStatusEnum locationType) {
        return locationType== null ? null : locationType.toString();
    }
}