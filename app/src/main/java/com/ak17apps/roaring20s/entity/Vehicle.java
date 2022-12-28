package com.ak17apps.roaring20s.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;

import com.ak17apps.roaring20s.util.Type;

import java.util.Calendar;

@Entity(tableName = "vehicle")
public class Vehicle extends Property{

    @ColumnInfo(name = "speed")
    private int speed;  //mph

    @ColumnInfo(name = "consumption")
    private int consumption;    //gal/100m  lbs/100m

    @ColumnInfo(name = "arrival")
    private Calendar arrival;

    @ColumnInfo(name = "type")
    private String type;

    public Vehicle(){}

    public Vehicle(int id, String name, int speed, int capacity, int consumption){
        this(id, name, 0, 0, speed, capacity, consumption, true, Type.VEHICLE_CAR);
    }

    public Vehicle(int id, String name, int buyPrice, int sellPrice, int speed, int capacity, int consumption, String type) {
        this(id, name, buyPrice, sellPrice, speed, capacity, consumption, false, type);
    }

    public Vehicle(int id, String name, int buyPrice, int sellPrice, int speed, int capacity, int consumption, boolean owned, String type){
        this.id = id;
        this.name = name;
        this.buyPrice = buyPrice;
        this.sellPrice = sellPrice;
        this.speed = speed;
        this.capacity = capacity;
        this.consumption = consumption;
        this.owned = owned;
        this.type = type;
    }

    public Calendar getArrival() {
        return arrival;
    }

    public void setArrival(Calendar arrival) {
        this.arrival = arrival;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getConsumption() {
        return consumption;
    }

    public void setConsumption(int consumption) {
        this.consumption = consumption;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
