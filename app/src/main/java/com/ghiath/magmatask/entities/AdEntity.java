package com.ghiath.magmatask.entities;

import com.ghiath.magmatask.ImageStatusEnum;
import com.ghiath.magmatask.db.converters.LocationTypeConverters;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.TypeConverters;

@Entity (primaryKeys = "ID",indices=@Index(value="ID"))
@TypeConverters(LocationTypeConverters.class)
public class AdEntity implements Serializable {

//    @Embedded(prefix = "ad_")
//    @NonNull
//    public final AdImageEntity.AdEntity ad;


    @NonNull
    private String ID;
    private String Name;
    private ImageStatusEnum imageStatusEnum;

    public AdEntity(@NonNull String ID, String Name,ImageStatusEnum imageStatusEnum) {
        this.ID = ID;
        this.Name = Name;
        this.imageStatusEnum=imageStatusEnum;
    }

    public ImageStatusEnum getImageStatusEnum() {
        return imageStatusEnum;
    }

    public AdEntity setImageStatusEnum(ImageStatusEnum imageStatusEnum) {
        this.imageStatusEnum = imageStatusEnum;
        return this;
    }

    @NonNull
    public String getID() {
        return ID;
    }

    public void setID(@NonNull String ID) {
        this.ID = ID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    @NotNull
    @Override
    public String toString() {
        return "AdEntity{" +
                "ID='" + ID + '\'' +
                ", Name='" + Name + '\'' +
                ", imageStatusEnum=" + imageStatusEnum +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AdEntity)) return false;
        AdEntity adEntity = (AdEntity) o;
        return getID().equals(adEntity.getID()) &&
                Objects.equals(getName(), adEntity.getName()) &&
                getImageStatusEnum() == adEntity.getImageStatusEnum();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getID(), getName(), getImageStatusEnum());
    }
}
