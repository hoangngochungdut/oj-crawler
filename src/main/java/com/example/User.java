package com.example;

public class User {

    private int id;
    private String userName;

    public User(int id, String userName) {
        this.id = id;
        this.userName = userName;
    }

    public int getId() {
        return id;
    }

    public String getUserName() {
        return userName;
    }

    @Override
    public String toString() {
        return id + " - " + userName;
    }
}