package com.example.demo.dao;

import model.Activity;
import org.apache.log4j.Logger;

import java.sql.*;

import java.util.*;

public class ActivitiesDao {
    private static final Logger LOGGER = Logger.getLogger(ActivitiesDao.class);
    private static DBManager dbm = DBManager.getInstance();
    private static final String QUERY1 = "SELECT activity_name,category_name,Activity.id FROM ACTIVITY  INNER JOIN CATEGORY  ON ACTIVITY.category_id=CATEGORY.id";
    private static final String QUERY2 = "SELECT * FROM ACTIVITY WHERE id=?";
    private static final String QUERY3 = "DELETE FROM ACTIVITY WHERE id=?";
    private static final String QUERY4 = "INSERT INTO ACTIVITY VALUES(DEFAULT,?,?)";
    private static final String QUERY5 = "SELECT A.activity_name,B.category_name,A.id FROM ACTIVITY A INNER JOIN \n" +
            "CATEGORY B ON A.category_id=B.id WHERE B.category_name=?";
    private static final String QUERY6 = "SELECT * FROM ACTIVITY WHERE activity_name=?";
    private static final String QUERY8 = "SELECT COUNT(id) FROM ACTIVITY";

    private ActivitiesDao() {
    }



    public static List<List<String>> getAllActivitiesWithCategory(String orderParameter) {
        String query;
        if (orderParameter != null) {
            query = QUERY1 + " ORDER BY " + orderParameter;
        } else {
            query = QUERY1;
        }
        List<List<String>> lst = new ArrayList<>();
        try (Connection con = dbm.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(query)) {
            while (rs.next()) {
                lst.add(new ArrayList<>(Arrays.asList(rs.getString(Constants.ACTIVITY_NAME), rs.getString(Constants.CATEGORY_NAME), String.valueOf(rs.getInt(Constants.ENTITY_ID)))));
            }
        } catch (SQLException throwables) {
            LOGGER.error(throwables.getMessage());
        }
        return lst;
    }

    public static int getActivitiesCount() {
        List<List<String>> lst = new ArrayList<>();
        try (Connection con = dbm.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(QUERY8)) {
            while (rs.next()) {
                return rs.getInt(Constants.COUNT);
            }
        } catch (SQLException throwables) {
            LOGGER.error(throwables.getMessage());
        }
        return 0;
    }

    public static List<List<String>> getAllActivitiesByCategory(String categoryName) {
        List<List<String>> lst = new ArrayList<>();
        ResultSet rs = null;
        try (Connection con = dbm.getConnection();
             PreparedStatement pstmt = con.prepareStatement(QUERY5);) {
            pstmt.setString(1, categoryName);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                lst.add(new ArrayList<>(Arrays.asList(rs.getString(Constants.ACTIVITY_NAME), rs.getString(Constants.CATEGORY_NAME), String.valueOf(rs.getInt(Constants.ENTITY_ID)))));
            }
        } catch (SQLException throwables) {
            LOGGER.error(throwables.getMessage());
        }
        return lst;
    }

    public static Integer getIdByName(String name) {
        Activity activity = null;
        ResultSet rs = null;
        try (Connection con = dbm.getConnection();
             PreparedStatement pstmt = con.prepareStatement(QUERY6)) {
            pstmt.setString(1, name);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("id");
            }
        } catch (SQLException throwables) {
            LOGGER.error(throwables.getMessage());
        } finally {
            DBManager.close(rs);
        }
        return 0;
    }

    public static Activity getById(String str_id) {
        int id = Integer.valueOf(str_id);
        Activity activity = null;
        ResultSet rs = null;
        try (Connection con = dbm.getConnection();
             PreparedStatement pstmt = con.prepareStatement(QUERY2)) {
            pstmt.setInt(1, id);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                activity = new Activity(rs.getInt(Constants.ENTITY_ID), rs.getString(Constants.ACTIVITY_NAME), rs.getInt(Constants.CATEGORY_ID));
            }
        } catch (SQLException throwables) {
            LOGGER.error(throwables.getMessage());
        } finally {
            DBManager.close(rs);
        }
        return activity;
    }

    public static boolean addActivity(String activityName, int categoryId) {
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            con = dbm.getConnection();
            pstmt = con.prepareStatement(QUERY4, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, activityName);
            pstmt.setInt(2, categoryId);
            if (pstmt.executeUpdate() > 0) {
                rs = pstmt.getGeneratedKeys();
                if (rs.next()) {
                    return true;
                }
            }
        } catch (SQLException throwables) {
            LOGGER.error(throwables.getMessage());
            return false;
        } finally {
            DBManager.close(rs);
            DBManager.close(pstmt);
            DBManager.close(con);
        }
        return false;
    }

    public static void deleteActivity(int[] idList) {
        for (int e : idList) {
            System.out.println(e);
        }
        int n = idList.length;
        String QUERY7 = "DELETE FROM ACTIVITY WHERE id IN (" + DBManager.repeat(n - 1, "?,") + "?)";
        ResultSet rs = null;
        try (Connection con = dbm.getConnection();
             PreparedStatement pstmt = con.prepareStatement(QUERY7)) {
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
}
