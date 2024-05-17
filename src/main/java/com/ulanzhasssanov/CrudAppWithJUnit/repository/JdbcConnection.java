package com.ulanzhasssanov.CrudAppWithJUnit.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JdbcConnection {
    static final String DATABASE_URL = "jdbc:mysql://localhost:3306/pubmentor_posts";
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";

    static final String USER = "root";
    static final String PASSWORD = "";

    public static Connection getConnection() throws SQLException {
        try {
            Class.forName(JDBC_DRIVER);
        } catch (ClassNotFoundException e) {
            throw new SQLException("MySQL Driver not found", e);
        }
        return DriverManager.getConnection(DATABASE_URL, USER, PASSWORD);
    }

}
