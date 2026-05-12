package com.example;

import java.sql.Connection;
import java.sql.DriverManager;

public class KetNoiDatabase {

    private static final String URL =
        "jdbc:mysql://localhost:3306/oj_crawling_db";

    private static final String USER = "root";

    private static final String PASSWORD = "123456";

    public static Connection getConnection() throws Exception {

        return DriverManager.getConnection(
            URL,
            USER,
            PASSWORD
        );
    }
}
