package com.ak17apps.roaring20s.entity;

import androidx.room.ColumnInfo;
import androidx.room.PrimaryKey;

public class Entity {

    @PrimaryKey
    protected int id;

    @ColumnInfo(name = "name")
    protected String name;

    @ColumnInfo(name = "owned")
    protected boolean owned;

    public Entity(){
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isOwned() {
        return owned;
    }

    public void setOwned(boolean owned) {
        this.owned = owned;
    }
}
