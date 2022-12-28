package com.ak17apps.roaring20s.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.ak17apps.roaring20s.entity.Person;
import com.ak17apps.roaring20s.entity.Place;
import com.ak17apps.roaring20s.entity.Vehicle;

@Database(entities = {Person.class, Place.class, Vehicle.class}, version = 1, exportSchema = false)
@TypeConverters({DataConverter.class})
public abstract class AppDatabase extends RoomDatabase {
    public abstract PersonDao personDao();
    public abstract VehicleDao vehicleDao();
    public abstract PlaceDao placeDao();
}