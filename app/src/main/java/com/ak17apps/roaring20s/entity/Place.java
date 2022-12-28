package com.ak17apps.roaring20s.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;

import com.ak17apps.roaring20s.util.Type;

import java.util.Calendar;

@Entity(tableName = "place")
public class Place extends Property{

    @ColumnInfo(name = "sell")
    private int sell;   //gal/day

    @ColumnInfo(name = "sale")
    private Calendar sale;

    @ColumnInfo(name = "completion")
    private Calendar completion;

    @ColumnInfo(name = "type")
    private String type;

    public Place(){}

    public Place(int id, String name, int capacity, int sell){
        this(id, name, 0, 0, capacity, sell, Type.PLACE_HOUSE, true);
    }

    public Place(int id, String name, int buyPrice, int sellPrice, int capacity, int sell, String type){
        this(id, name, buyPrice, sellPrice, capacity, sell, type, false);
    }

    public Place(int id, String name, int buyPrice, int sellPrice, int capacity, int sell, String type, boolean owned){
        this.id = id;
        this.name = name;
        this.buyPrice = buyPrice;
        this.sellPrice = sellPrice;
        this.capacity = capacity;
        this.sell = sell;
        this.type = type;
        this.owned = owned;
    }

    public int getSell() {
        return sell;
    }

    public void setSell(int sell) {
        this.sell = sell;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Calendar getSale() {
        return sale;
    }

    public void setSale(Calendar sale) {
        this.sale = sale;
    }

    public Calendar getCompletion() {
        return completion;
    }

    public void setCompletion(Calendar completion) {
        this.completion = completion;
    }
}
