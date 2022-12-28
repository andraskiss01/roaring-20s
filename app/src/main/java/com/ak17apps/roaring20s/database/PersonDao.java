package com.ak17apps.roaring20s.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.ak17apps.roaring20s.entity.Person;

import java.util.List;

@Dao
public interface PersonDao {
    @Query("SELECT * FROM person")
    List<Person> getAll();

    @Query("DELETE FROM person")
    void deleteAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(Person... person);

    @Query("SELECT * FROM person WHERE id = :id")
    Person getPersonById(int id);

    @Query("SELECT * FROM person")
    List<Person> getPersonList();
}
