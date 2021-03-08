package com.company.data;

import com.company.data.interfaces.IDBManager;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBManager implements IDBManager {
    public Connection getConnection() {
        try {
            //activating the drivers
            Class.forName("org.postgresql.Driver");

            //using the driver manager, take a connection to the database here
            Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/recipebook", "postgres", "postgres");

            return connection;
        } catch (Exception exception) {
            exception.printStackTrace();
        }

        return null;
    }
}
