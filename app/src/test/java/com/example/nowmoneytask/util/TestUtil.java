package com.example.nowmoneytask.util;

import com.example.nowmoneytask.repository.db.User;
import com.example.nowmoneytask.repository.model.LoginResponse;
import com.example.nowmoneytask.repository.model.StatusResponse;

import java.util.ArrayList;
import java.util.List;

public class TestUtil {


    public static User createUser(String name, String number, String address) {
        return new User("12345", name, number, address);
    }


    public static List<User> createUsers(int count, String name, String number,
                                         String address) {
        List<User> repos = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            repos.add(createUser(name + " " + i, number + " " + i, address + " " + i));
        }
        return repos;
    }


    public static User createUserBody(String name, String number, String address) {
        return new User(name, number, address);
    }

    public static StatusResponse createStatusResponse(String status) {
        return new StatusResponse(status);
    }

    public static LoginResponse createLoginResponse(String token) {
        return new LoginResponse(token);
    }


}
