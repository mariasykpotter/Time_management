package com.example.demo.dao;

import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBManager {
    private static final Logger LOGGER = Logger.getLogger(DBManager.class);
    private static DBManager dbm;
    public static final String URL = "jdbc:postgresql://localhost:5432/time_management?user=postgres&password=Coala2111";


    private DBManager() {
    }

    public static synchronized DBManager getInstance() {
        if (dbm == null) {
            return new DBManager();
        }
        return dbm;
    }

    public Connection getConnection() {
        try {
            Class.forName("org.postgresql.Driver");
            return DriverManager.getConnection(URL, "postgres", "Coala2111");
        } catch (SQLException | ClassNotFoundException throwables) {
            LOGGER.error(throwables.getMessage());
            return null;
        }
    }

    public static String repeat(int count, String with) {
        return new String(new char[count]).replace("\0", with);
    }

    static void close(AutoCloseable ac) {
        if (ac != null) {
            try {
                ac.close();
            } catch (Exception e) {
                LOGGER.error(e.getMessage());
            }
        }
    }
}
