package com.example.demo.dao;

import org.apache.log4j.Logger;

import java.sql.*;

/**
 * DB manager. Works with Postgresql.
 * Only the required DAO methods are defined!
 */
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

    /**
     * Returns a DB connection.
     *
     * @return A DB connectione
     */
    public Connection getConnection() {
        try {
            Class.forName("org.postgresql.Driver");
            return DriverManager.getConnection(URL, "postgres", "Coala2111");
        } catch (SQLException | ClassNotFoundException throwables) {
            LOGGER.error(throwables.getMessage());
            return null;
        }
    }

    /**
     * Static method that returns String with repeating pattern
     *
     * @param count number of times to repeat
     * @param with  string which repeats
     * @return
     */
    public static String repeat(int count, String with) {
        return new String(new char[count]).replace("\0", with);
    }

    public static void deleteEntity(String entity, int[] idList) {
        int n = idList.length;
        String query = "DELETE FROM " + entity + " WHERE id IN (" + DBManager.repeat(n - 1, "?,") + "?)";
        ResultSet rs = null;
        try (Connection con = dbm.getInstance().getConnection();
             PreparedStatement pstmt = con.prepareStatement(query)) {
            for (int i = 1; i <= n; i++) {
                pstmt.setInt(i, idList[i - 1]);
            }
            rs = pstmt.executeQuery();
        } catch (SQLException throwables) {
            LOGGER.error(throwables.getMessage());
        } finally {
            DBManager.close(rs);
        }
    }

    /**
     * Closes the given connection.
     *
     * @param ac implements AutoCloseable
     */
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
