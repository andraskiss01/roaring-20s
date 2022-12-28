package com.ak17apps.roaring20s.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.ak17apps.roaring20s.entity.Place;
import com.ak17apps.roaring20s.entity.Vehicle;

import java.util.List;

@Dao
public interface PlaceDao {
    @Query("SELECT * FROM place")
    List<Place> getAll();

    @Query("DELETE FROM place")
    void deleteAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(Place... place);

    @Query("SELECT * FROM place WHERE id = :id")
    Place getPlaceById(int id);

    @Query("SELECT * FROM place WHERE type IN ('PLACE_AIRPORT', 'PLACE_RAILWAY_STATION', 'PLACE_PORT')")
    List<Place> getPortsStationsList();

    @Query("SELECT * FROM place WHERE type NOT IN ('PLACE_AIRPORT', 'PLACE_RAILWAY_STATION', 'PLACE_PORT')")
    List<Place> getManufactureStorageSaleList();

    @Query("SELECT * FROM place WHERE type IN ('PLACE_HOUSE', 'PLACE_SMALL_PUB', 'PLACE_MEDIUM_PUB', 'PLACE_BIG_PUB')")
    List<Place> getSaleList();
}
