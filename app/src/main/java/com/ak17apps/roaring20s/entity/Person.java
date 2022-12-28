package com.ak17apps.roaring20s.entity;

import androidx.room.ColumnInfo;

@androidx.room.Entity(tableName = "person")
public class Person extends Entity{

    @ColumnInfo(name = "salary")
    private int salary;

    @ColumnInfo(name = "type")
    private String type;

    @ColumnInfo(name = "vehicle")
    private Vehicle vehicle;

    @ColumnInfo(name = "place")
    private Place place;

    public Person(){}

    public Person(int id, String name, int salary, String type){
        this.id = id;
        this.name = name;
        this.salary = salary;
        this.type = type;
        this.owned = false;
    }

    public Place getPlace() {
        return place;
    }

    public void setPlace(Place place) {
        this.place = place;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
