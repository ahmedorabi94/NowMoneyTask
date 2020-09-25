package com.example.nowmoneytask.repository.db;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "user")
public class User {


    @PrimaryKey(autoGenerate = true)
    private Integer id;


    @ColumnInfo(name = "task_id")
    private String _id;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "number")
    private String number;

    @ColumnInfo(name = "address")
    private String address;


    public User() {
    }

    public User(String _id, String name, String number, String address) {
        this._id = _id;
        this.name = name;
        this.number = number;
        this.address = address;
    }

    public User(String name, String number, String address) {
        this.name = name;
        this.number = number;
        this.address = address;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
