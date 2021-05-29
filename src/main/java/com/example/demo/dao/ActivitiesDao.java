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
    private static final String QUERY5 = QUERY1 + " ORDER BY ";
    private static final String QUERY6 = "SELECT id from ACTIVITY WHERE ACTIVITY_NAME = ?";
    private static final String QUERY7 = "SELECT * from ACTIVITY";


    private ActivitiesDao() {
    }

    public static List<Activity> getAllActivities() {
        ActivityMapper activityMapper = new ActivitiesDao.ActivityMapper();
        List<Activity> lst = new ArrayList<>();
        try (Connection con = dbm.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(QUERY7)) {
            while (rs.next()) {
                lst.add(activityMapper.mapRow(rs));
            }
        } catch (SQLException throwables) {
            LOGGER.error(throwables.getMessage());
        }
        return lst;
    }

    public static List<List<String>> getAllActivitiesWithCategory() {
        List<List<String>> lst = new ArrayList<>();
        try (Connection con = dbm.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(QUERY1)) {
            while (rs.next()) {
                lst.add(new ArrayList<>(Arrays.asList(rs.getString(Constants.ACTIVITY_NAME), rs.getString(Constants.CATEGORY_NAME), String.valueOf(rs.getInt(Constants.ENTITY_ID)))));
            }
        } catch (SQLException throwables) {
            LOGGER.error(throwables.getMessage());
        }
        return lst;
    }

    public static List<List<String>> getOrderedActivitiesWithCategory(String orderField) {
        List<List<String>> lst = new ArrayList<>();
        try (Connection con = dbm.getConnection();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(QUERY5 + orderField)) {
            while (rs.next()) {
                lst.add(new ArrayList<>(Arrays.asList(rs.getString(Constants.ACTIVITY_NAME), rs.getString(Constants.CATEGORY_NAME), String.valueOf(rs.getInt(Constants.ENTITY_ID)))));
            }
        } catch (SQLException throwables) {
            LOGGER.error(throwables.getMessage());
        }
        return lst;
    }

    public static Activity getById(Integer id) {
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

    public static int getIdByName(String name) {
        ResultSet rs = null;
        try (Connection con = dbm.getConnection();
             PreparedStatement pstmt = con.prepareStatement(QUERY6)) {
            pstmt.setString(1, name);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(Constants.ENTITY_ID);
            }
        } catch (SQLException throwables) {
            LOGGER.error(throwables.getMessage());
        } finally {
            DBManager.close(rs);
        }
        return 0;
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

    public static void deleteActivity(int id) {
        ResultSet rs = null;
        try (Connection con = dbm.getConnection();
             PreparedStatement pstmt = con.prepareStatement(QUERY3)) {
            pstmt.setInt(1, id);
            rs = pstmt.executeQuery();
        } catch (SQLException throwables) {
            LOGGER.error(throwables.getMessage());
        } finally {
            DBManager.close(rs);
        }
    }

    private static class ActivityMapper implements EntityMapper<Activity> {

        @Override
        public Activity mapRow(ResultSet rs) {
            try {
                Activity activity = new Activity();
                activity.setId(rs.getInt(Constants.ENTITY_ID));
                activity.setActivityName(rs.getString(Constants.ACTIVITY_NAME));
                activity.setCategoryId(rs.getInt(Constants.CATEGORY_ID));
                return activity;
            } catch (SQLException e) {
                throw new IllegalStateException(e);
            }
        }
    }
}
