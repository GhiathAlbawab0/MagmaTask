package com.ghiath.magmatask.entities;

import com.ghiath.magmatask.ImageStatusEnum;
import com.ghiath.magmatask.db.converters.LocationTypeConverters;

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
                entity= AdEntity.class,
                parentColumns="ID",
                childColumns="AdId",
                onDelete=CASCADE),
        indices=@Index(value="AdId")
)
@TypeConverters(LocationTypeConverters.class)

public class AdImageEntity implements Serializable {

    @NonNull
    private String AdImageId;

    private ImageStatusEnum imageStatusEnum;

    private String imageName;
    private String AdId;
    private String path;

    public AdImageEntity(@NonNull String AdImageId, ImageStatusEnum imageStatusEnum, String AdId, String path, String imageName) {
       this. AdImageId = AdImageId;
        this.imageStatusEnum = imageStatusEnum;
        this.AdId = AdId;
        this.path= path;
        this.imageName= imageName;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
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

    public AdImageEntity setImageStatusEnum(ImageStatusEnum imageStatusEnum) {
        this.imageStatusEnum = imageStatusEnum;
        return this;
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
        return getAdImageId().equals(that.getAdImageId()) &&
                getImageStatusEnum() == that.getImageStatusEnum() &&
                Objects.equals(getImageName(), that.getImageName()) &&
                Objects.equals(getAdId(), that.getAdId()) &&
                Objects.equals(getPath(), that.getPath());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getAdImageId(), getImageStatusEnum(), getImageName(), getAdId(), getPath());
    }

}
