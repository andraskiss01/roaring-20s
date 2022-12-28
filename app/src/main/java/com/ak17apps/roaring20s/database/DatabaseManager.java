package com.ak17apps.roaring20s.database;

import android.content.Context;

import androidx.room.Room;

public class DatabaseManager {
    public static AppDatabase build(Context context){
        return Room.databaseBuilder(context, AppDatabase.class, "r20s").build();
    }
}
