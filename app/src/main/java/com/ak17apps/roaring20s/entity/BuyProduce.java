package com.ak17apps.roaring20s.entity;

import java.util.List;

public class BuyProduce {
    private List<Place> placeList;
    private List<Vehicle> vehicleList;
    private List<Person> personList;

    public BuyProduce(List<Place> placeList, List<Vehicle> vehicleList, List<Person> personList){
        this.placeList = placeList;
        this.vehicleList = vehicleList;
        this.personList = personList;
    }

    public List<Place> getPlaceList() {
        return placeList;
    }

    public void setPlaceList(List<Place> placeList) {
        this.placeList = placeList;
    }

    public List<Vehicle> getVehicleList() {
        return vehicleList;
    }

    public void setVehicleList(List<Vehicle> vehicleList) {
        this.vehicleList = vehicleList;
    }

    public List<Person> getPersonList() {
        return personList;
    }

    public void setPersonList(List<Person> personList) {
        this.personList = personList;
    }

}
