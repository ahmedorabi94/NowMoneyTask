package com.example.nowmoneytask.repository.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {User.class}, version = 1, exportSchema = false)
public abstract class TaskDb extends RoomDatabase {

    public abstract TaskDao taskDao();
}