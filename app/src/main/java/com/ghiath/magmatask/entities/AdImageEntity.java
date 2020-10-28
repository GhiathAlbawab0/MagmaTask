package com.ghiath.magmatask.entities;

import com.ghiath.magmatask.ImageStatusEnum;
import com.ghiath.magmatask.db.converters.LocationTypeConverters;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.TypeConverters;

import static androidx.room.ForeignKey.CASCADE;

@Entity(primaryKeys = "AdImageId",
        foreignKeys=@ForeignKey(
                entity= AdImageEntity.class,
                parentColumns="AdImageId",
                childColumns="AdId",
                onDelete=CASCADE),
        indices=@Index(value="AdId")
)
@TypeConverters(LocationTypeConverters.class)

public class AdImageEntity implements Serializable {

    @NonNull
    private String AdImageId;

    private ImageStatusEnum imageStatusEnum;

    private String AdId;
    private String path;

    public AdImageEntity(@NonNull String AdImageId, ImageStatusEnum imageStatusEnum, String AdId, String path) {
       this. AdImageId = AdImageId;
        this.imageStatusEnum = imageStatusEnum;
        this.AdId = AdId;
        this.path= path;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @NonNull
    public String getAdImageId() {
        return AdImageId;
    }

    public void setAdImageId(@NonNull String adImageId) {
        AdImageId = adImageId;
    }

    public ImageStatusEnum getImageStatusEnum() {
        return imageStatusEnum;
    }

    public void setImageStatusEnum(ImageStatusEnum imageStatusEnum) {
        this.imageStatusEnum = imageStatusEnum;
    }

    public String getAdId() {
        return AdId;
    }

    public void setAdId(String adId) {
        AdId = adId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AdImageEntity)) return false;
        AdImageEntity that = (AdImageEntity) o;
        return AdImageId.equals(that.AdImageId) &&
                imageStatusEnum == that.imageStatusEnum &&
                Objects.equals(AdId, that.AdId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(AdImageId, imageStatusEnum, AdId);
    }
}
