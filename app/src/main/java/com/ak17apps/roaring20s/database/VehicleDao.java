package com.ak17apps.roaring20s.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.ak17apps.roaring20s.entity.Person;
import com.ak17apps.roaring20s.entity.Vehicle;

import java.util.List;

@Dao
public interface VehicleDao {
    @Query("SELECT * FROM vehicle")
    List<Vehicle> getAll();

    @Query("DELETE FROM vehicle")
    void deleteAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(Vehicle... vehicle);

    @Query("SELECT * FROM vehicle WHERE id = :id")
    Vehicle getVehicleById(int id);

    @Query("SELECT * FROM vehicle")
    List<Vehicle> getVehicleList();
}
