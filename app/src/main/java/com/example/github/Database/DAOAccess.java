package com.example.github.Database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface DAOAccess {
    @Insert
    Long insertTask(ItemsEntity itemsEntity);

    @Query("SELECT * FROM ItemsEntity ORDER BY name desc")
    LiveData<List<ItemsEntity>> fetchAllTasks();

    @Query("SELECT * FROM ItemsEntity WHERE id =:taskId")
    LiveData<ItemsEntity> getTask(int taskId);


    @Update
    void updateTask(ItemsEntity itemsEntity);


    @Delete
    void deleteTask(ItemsEntity itemsEntity);
}
