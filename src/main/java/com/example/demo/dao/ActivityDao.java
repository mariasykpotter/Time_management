package com.example.demo.dao;

import com.example.demo.model.Activity;
import org.apache.log4j.Logger;

import java.sql.*;

import java.util.*;

/**
 * Data access object for activities related entities
 */
public class ActivityDao {

    private static final Logger LOGGER = Logger.getLogger(ActivityDao.class);
    private static DBManager dbm = DBManager.getInstance();
    private static final String QUERY1 = "SELECT activity_name,category_name,Activity.id FROM ACTIVITY  INNER JOIN CATEGORY  ON ACTIVITY.category_id=CATEGORY.id";
    private static final String QUERY2 = "SELECT * FROM ACTIVITY WHERE id=?";
    private static final String QUERY3 = "INSERT INTO ACTIVITY VALUES(DEFAULT,?,?)";
    private static final String QUERY4 = "SELECT * FROM ACTIVITY WHERE activity_name=?";

    /**
     * Private constructor for ActivityDao
     */
    private ActivityDao() {
    }


    /**
     *  Returns information about all activities
     *
     * @param orderParameter Parameters by which activities are ordered
     * @return List of List of String containing activity and category name and activity id
     */
    public static List<List<String>> getAllActivitiesWithCategory(String orderParameter) {
        String query;
        if (!orderParameter.equals("")) {
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

    /**
     *Returns an id of activity by a given name
     *
     * @param name activity name
     * @return activity id
     */
    public static Integer getIdByName(String name) {
        ResultSet rs = null;
        try (Connection con = dbm.getConnection();
             PreparedStatement pstmt = con.prepareStatement(QUERY4)) {
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

    /**
     * Returns Activity entity by id.
     *
     * @param strId activity id
     * @return Activity entity
     */
    public static Activity getById(String strId) {
        int id = Integer.parseInt(strId);
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

    /**
     * Returns boolean value whether of not activity has been added
     *
     * @param activityName activity name
     * @param categoryId category id
     * @return boolean value
     */
    public static boolean addActivity(String activityName, int categoryId) {
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            con = dbm.getConnection();
            pstmt = con.prepareStatement(QUERY3, Statement.RETURN_GENERATED_KEYS);
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

    /**
     * Deletes a list of activities by their ids.
     *
     * @param idList Identifies of activities.
     */
    public static void deleteActivity(int[] idList) {
        int n = idList.length;
        String query7 = "DELETE FROM ACTIVITY WHERE id IN (" + DBManager.repeat(n - 1, "?,") + "?)";
        ResultSet rs = null;
        try (Connection con = dbm.getConnection();
             PreparedStatement pstmt = con.prepareStatement(query7)) {
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
