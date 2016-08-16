package org.cplop.db;

import java.util.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
    private Connection connection;
    private String url;
    private String user;
    private String password;
    public Database(String url, String user, String password) {

        /* Register MySQL Connector/J */
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.err.println("Driver not found");
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        /* Connect to Server */
        try {
            this.connection = DriverManager.getConnection(url, user, password);
        } catch(SQLException e) {
            System.err.println("Failed to connect");
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        /* Instantiate */
        this.url        = url;
        this.user       = user;
        this.password   = password;

    }
    public Connection getConnection() {
        return this.connection;
    }
}
