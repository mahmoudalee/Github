package com.example.github.Database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

@Database(entities = {ItemsEntity.class},version = 1,exportSchema = false)
public abstract class ItemsDataBase extends RoomDatabase {
    public abstract DAOAccess daoAccess();
}
