package com.example.github;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.os.AsyncTask;

import com.example.github.Database.ItemsDataBase;
import com.example.github.Database.ItemsEntity;
import com.example.github.Models.Item;

import java.util.List;

public class ItemRepository {
    private String DB_NAME = "db_items";
    private ItemsDataBase itemsDataBase;
    public ItemRepository(Context context){
        itemsDataBase = Room.databaseBuilder(context,ItemsDataBase.class,DB_NAME).build();
    }
    public void insertItem (String name , String avatar){
        ItemsEntity item =new ItemsEntity();
        item.setName(name);
        item.setAvatar(avatar);
        insertItem(item);
    }

    public void insertItem(final ItemsEntity item){
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                itemsDataBase.daoAccess().insertTask(item);
                return null;
            }
        }.execute();
    }

    public void updateTask(final ItemsEntity item) {

        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                itemsDataBase.daoAccess().updateTask(item);
                return null;
            }
        }.execute();
    }

    public void deleteTask(final int id) {
        final LiveData<ItemsEntity> task = getTask(id);
        if(task != null) {
            new AsyncTask<Void, Void, Void>() {
                @Override
                protected Void doInBackground(Void... voids) {
                    itemsDataBase.daoAccess().deleteTask(task.getValue());
                    return null;
                }
            }.execute();
        }
    }

    public void deleteTask(final ItemsEntity item) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                itemsDataBase.daoAccess().deleteTask(item);
                return null;
            }
        }.execute();
    }

    public LiveData<ItemsEntity> getTask(int id) {
        return itemsDataBase.daoAccess().getTask(id);
    }

    public LiveData<List<ItemsEntity>> getTasks() {
        return itemsDataBase.daoAccess().fetchAllTasks();
    }
}