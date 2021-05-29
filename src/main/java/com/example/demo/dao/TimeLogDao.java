package com.example.demo.dao;

import org.apache.log4j.Logger;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;

public class TimeLogDao {
    private static final Logger LOGGER = Logger.getLogger(TimeLogDao.class);
    private static final String QUERY1 = "SELECT SUM(Duration) AS SUM_DURATION FROM TIME_LOG WHERE user_id=? AND activity_id=? GROUP BY activity_id";
    private static final String QUERY2 = "INSERT INTO TIME_LOG VALUES(DEFAULT,?,?,?,?,?,0)";
    private static final String QUERY3 = "SELECT C.id ,COUNT(A.ID) AS TIME_LOG_NUMBER FROM TIME_LOG AS A\n" +
            "INNER JOIN ACTIVITY AS C ON A.activity_id=C.id WHERE C.id =? AND A.STATUS = ?  GROUP BY C.id";
    private static final String QUERY4 = "SELECT B.FIRST_NAME, B.LAST_NAME,B.USER_NAME,C.ACTIVITY_NAME,D.CATEGORY_NAME,A.id AS PERSONID,C.id ACTIVITYID FROM TIME_LOG A\n" +
            "INNER JOIN PERSON B ON  A.user_id=B.id\n" +
            "INNER JOIN ACTIVITY C ON A.activity_id=C.id \n" +
            "INNER JOIN CATEGORY D ON C.category_id = D.id WHERE C.id=? AND A.STATUS=?";
    private static final String QUERY5 = "UPDATE TIME_LOG SET STATUS=1 WHERE ID=?";
    private static final String QUERY6 = "SELECT C.ACTIVITY_NAME,B.CATEGORY_NAME,C.id ,COUNT(A.ID) AS TIME_LOG_NUMBER FROM TIME_LOG AS A \n" +
            "            RIGHT JOIN ACTIVITY AS C\n" +
            "           \tON A.activity_id=C.id\n" +
            "            INNER JOIN CATEGORY AS B\n" +
            "            ON C.CATEGORY_ID=B.ID\n" +
            "            GROUP BY C.id,C.ACTIVITY_NAME,B.CATEGORY_NAME ORDER BY TIME_LOG_NUMBER";
    private static final String QUERY7 = "SELECT A.FIRST_NAME,A.LAST_NAME,A.USER_NAME,C.name,COUNT(B.id),SUM(B.DURATION),A.ID\n" +
            "            FROM PERSON A LEFT JOIN TIME_LOG B ON A.id=B.USER_ID\n" +
            "\t\t\tINNER JOIN ROLE C ON A.role_id=C.id GROUP BY A.id,C.id";
    private static final String QUERY8 = "SELECT B.activity_name, C.category_name, A.start_at,A.end_at,A.duration,A.status,A.id FROM TIME_LOG AS A \n" +
            "INNER JOIN ACTIVITY AS B ON A.activity_id=B.id\n" +
            "INNER JOIN CATEGORY C ON B.category_id = C.id";
    private static final String QUERY9 = "UPDATE TIME_LOG SET ACTIVITY_ID = ?, START_AT = ?, END_AT=?,DURATION=? where id=?";
    private static DBManager dbm = DBManager.getInstance();

    private TimeLogDao() {
    }

    public static float countDifference(String time1, String time2) {
        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
        Date date1 = null;
        Date date2 = null;
        try {
            date1 = format.parse(time1);
            date2 = format.parse(time2);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return (float) ((date2.getTime() - date1.getTime()) / (60 * 1000 * 60.0));
    }

    public static float getSumDuration(Integer userId, Integer activityId) {
        ResultSet rs = null;
        try (Connection con = dbm.getConnection();
             PreparedStatement pstmt = con.prepareStatement(QUERY1)) {
            pstmt.setInt(1, userId);
            pstmt.setInt(2, activityId);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getFloat(Constants.SUM_DURATION);
            }
        } catch (SQLException throwables) {
            LOGGER.error(throwables.getMessage());
        } finally {
            DBManager.close(rs);
        }
        return 0.0F;
    }

    public static boolean addTimeLog(int userId, int activityId, Time start, Time end, float duration) {
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        boolean added = false;
        try {
            con = dbm.getConnection();
            pstmt = con.prepareStatement(QUERY2, Statement.RETURN_GENERATED_KEYS);
            pstmt.setInt(1, userId);
            pstmt.setInt(2, activityId);
            pstmt.setTime(3, start);
            pstmt.setTime(4, end);
            pstmt.setFloat(5, duration);
            if (pstmt.executeUpdate() > 0) {
                rs = pstmt.getGeneratedKeys();
                if (rs.next()) {
                    added = true;
                }
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        } finally {
            DBManager.close(rs);
            DBManager.close(pstmt);
            DBManager.close(con);
        }
        return added;
    }

    public static Integer getQuantityPerActivityAndStatus(int id, int status) {
        ResultSet rs = null;
        try (Connection con = dbm.getConnection();
             PreparedStatement pstmt = con.prepareStatement(QUERY3)) {
            pstmt.setInt(1, id);
            pstmt.setInt(2, status);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(Constants.TIME_LOG_NUMBER);
            }
        } catch (SQLException throwables) {
            LOGGER.error(throwables.getMessage());
        } finally {
            DBManager.close(rs);
        }
        return 0;
    }

    public static List<List<String>> getAllTimeLogsInfo() {
        List<List<String>> lst = new ArrayList<>();
        try (Connection con = dbm.getConnection();
             Statement pstmt = con.createStatement();
             ResultSet rs = pstmt.executeQuery(QUERY8)) {
            while (rs.next()) {
                List<String> values = new ArrayList<>(Arrays.asList(new String[]{rs.getString(Constants.ACTIVITY_NAME),
                        rs.getString(Constants.CATEGORY_NAME), String.valueOf(rs.getTime(Constants.START_AT)),
                        String.valueOf(rs.getTime(Constants.END_AT)), String.valueOf(rs.getFloat(Constants.DURATION))
                        , String.valueOf(rs.getInt(Constants.STATUS)), String.valueOf(rs.getInt(Constants.ENTITY_ID))}));
                lst.add(values);
            }
        } catch (SQLException throwables) {
            LOGGER.error(throwables.getMessage());
        }
        return lst;
    }

    public static List<List<String>> getInfoByStatus(int id, int status) {
        ResultSet rs = null;
        List<List<String>> lst = new ArrayList<>();
        try (Connection con = dbm.getConnection();
             PreparedStatement pstmt = con.prepareStatement(QUERY4)) {
            pstmt.setInt(1, id);
            pstmt.setInt(2, status);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                List<String> newList = new ArrayList<>(Arrays.asList(rs.getString(Constants.USER_FIRST_NAME),
                        rs.getString(Constants.USER_LAST_NAME), rs.getString(Constants.USER_LOGIN), rs.getString(Constants.ACTIVITY_NAME),
                        rs.getString(Constants.CATEGORY_NAME), String.valueOf(rs.getInt("PERSONID")), String.valueOf(rs.getInt("ACTIVITYID"))));
                lst.add(newList);
            }
        } catch (SQLException throwables) {
            LOGGER.error(throwables.getMessage());
        } finally {
            DBManager.close(rs);
        }
        return lst;
    }

    public static List<List<String>> getInfoAboutUser() {
        List<List<String>> lst = new ArrayList<>();
        try (Connection con = dbm.getConnection();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(QUERY7)) {
            while (rs.next()) {
                List<String> newList = new ArrayList<>(Arrays.asList(rs.getString(Constants.USER_FIRST_NAME),
                        rs.getString(Constants.USER_LAST_NAME), rs.getString(Constants.USER_LOGIN), rs.getString(Constants.NAME), String.valueOf(rs.getInt(Constants.COUNT)), String.valueOf(rs.getFloat(Constants.SUM)), String.valueOf(rs.getInt(Constants.ENTITY_ID))));
                lst.add(newList);
            }
        } catch (SQLException throwables) {
            LOGGER.error(throwables.getMessage());
        }
        return lst;
    }

    public static List<List<String>> getOrderedQuantityPerActivity() {
        List<List<String>> lst = new ArrayList<>();
        try (Connection con = dbm.getConnection();
             Statement pstmt = con.createStatement();
             ResultSet rs = pstmt.executeQuery(QUERY6)) {
            while (rs.next()) {
                List<String> values = new ArrayList<>(Arrays.asList(new String[]{rs.getString(Constants.ACTIVITY_NAME),
                        rs.getString(Constants.CATEGORY_NAME), rs.getString(Constants.ENTITY_ID)}));
                lst.add(values);
            }
        } catch (SQLException throwables) {
            LOGGER.error(throwables.getMessage());
        }
        return lst;
    }

    public static void updateTimeLog(int activityId, Time startAt, Time endAt, int timelogId) {
        ResultSet rs = null;
        try (Connection con = dbm.getConnection();
             PreparedStatement pstmt = con.prepareStatement(QUERY9)) {
            pstmt.setInt(1, activityId);
            pstmt.setTime(2, startAt);
            pstmt.setTime(3, endAt);
            pstmt.setFloat(4, countDifference(String.valueOf(startAt), String.valueOf(endAt)));
            pstmt.setInt(5, timelogId);
            rs = pstmt.executeQuery();
        } catch (SQLException throwables) {
            LOGGER.error(throwables.getMessage());
        } finally {
            DBManager.close(rs);
        }
    }


    public static void updateStatus(int time_log_id) {
        ResultSet rs = null;
        try (Connection con = dbm.getConnection();
             PreparedStatement pstmt = con.prepareStatement(QUERY5)) {
            pstmt.setInt(1, time_log_id);
            rs = pstmt.executeQuery();
        } catch (SQLException throwables) {
            LOGGER.error(throwables.getMessage());
        } finally {
            DBManager.close(rs);
        }
    }


}
