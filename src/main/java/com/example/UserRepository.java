package com.example;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import java.util.ArrayList;
import java.util.List;
public class UserRepository {

    public List<User> getAllUsers() {

        List<User> users = new ArrayList<>();

        String sql = "SELECT id, user_name FROM users";

        try (
            Connection conn = KetNoiDatabase.getConnection();

            Statement stmt = conn.createStatement();

            ResultSet rs = stmt.executeQuery(sql);
        ) {

            while (rs.next()) {

                int id = rs.getInt("id");

                String userName =
                    rs.getString("user_name");

                User user = new User(
                    id,
                    userName
                );

                users.add(user);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return users;
    }
}
