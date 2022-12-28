package com.ak17apps.roaring20s.database;

import androidx.room.TypeConverter;

import com.ak17apps.roaring20s.entity.Place;
import com.ak17apps.roaring20s.entity.Vehicle;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Calendar;

public class DataConverter {
    @TypeConverter
    public String fromVehicle(Vehicle vehicle) {
        if (vehicle == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<Vehicle>() {}.getType();
        return gson.toJson(vehicle, type);
    }

    @TypeConverter
    public Vehicle toVehicle(String vehicle) {
        if (vehicle == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<Vehicle>() {}.getType();
        return gson.fromJson(vehicle, type);
    }

    @TypeConverter
    public String fromPlace(Place place) {
        if (place == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<Place>() {}.getType();
        return gson.toJson(place, type);
    }

    @TypeConverter
    public Place toPlace(String place) {
        if (place == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<Place>() {}.getType();
        return gson.fromJson(place, type);
    }

    @TypeConverter
    public String fromCalendar(Calendar calendar) {
        if (calendar == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<Calendar>() {}.getType();
        return gson.toJson(calendar, type);
    }

    @TypeConverter
    public Calendar toCalendar(String calendar) {
        if (calendar == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<Calendar>() {}.getType();
        return gson.fromJson(calendar, type);
    }
}
