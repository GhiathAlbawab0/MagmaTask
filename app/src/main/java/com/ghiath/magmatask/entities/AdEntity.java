package com.ghiath.magmatask.entities;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;

import static androidx.room.ForeignKey.CASCADE;

@Entity (primaryKeys = "ID",indices=@Index(value="ID"))
public class AdEntity implements Serializable {

    @NonNull
    private String ID;
    private String Name;

    public AdEntity(@NonNull String ID, String Name) {
        this.ID = ID;
        this.Name = Name;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AdEntity)) return false;
        AdEntity adEntity = (AdEntity) o;
        return getID().equals(adEntity.getID()) &&
                Objects.equals(getName(), adEntity.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getID(), getName());
    }
}
