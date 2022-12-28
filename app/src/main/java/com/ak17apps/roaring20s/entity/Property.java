package com.ak17apps.roaring20s.entity;

import androidx.room.ColumnInfo;

public class Property extends Entity {

    @ColumnInfo(name = "buyPrice")
    protected int buyPrice;

    @ColumnInfo(name = "sellPrice")
    protected int sellPrice;

    @ColumnInfo(name = "capacity")
    protected int capacity;   //gal

    @ColumnInfo(name = "load")
    protected int load; //gal

    public int getBuyPrice() {
        return buyPrice;
    }

    public void setBuyPrice(int buyPrice) {
        this.buyPrice = buyPrice;
    }

    public int getSellPrice() {
        return sellPrice;
    }

    public void setSellPrice(int sellPrice) {
        this.sellPrice = sellPrice;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getLoad() {
        return load;
    }

    public void setLoad(int load) {
        this.load = load;
    }
}
