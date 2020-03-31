package com.example.logic.ann.jdbc.jdk;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MyConnection {
    private String url;

    public MyConnection(String url) {
        this.url = url;
    }

    public Connection getConnection() {
        try{
            return DriverManager.getConnection(url);
        } catch (SQLException ex){
            throw new RuntimeException(ex);
        }
    }
}
