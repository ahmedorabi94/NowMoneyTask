package com.example.nowmoneytask.repository.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface TaskDao {


    @Insert
    void insert(User user);

    @Insert
    void insertAll(List<User> users);

    @Query("SELECT * from user")
    LiveData<List<User>> getAllUsers();


    @Query("DELETE FROM user WHERE task_id = :id")
    void deleteUser(String id);

    @Query("Delete FROM user")
    void deleteAllUsers();


}
