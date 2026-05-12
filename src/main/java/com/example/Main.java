package com.example;
import java.util.List;
public class Main {

    public static void main(String[] args) {

        UserRepository repo =
            new UserRepository();

        List<User> users =
            repo.getAllUsers();

        for (User user : users) {

            System.out.println(user);
        }
    }
}