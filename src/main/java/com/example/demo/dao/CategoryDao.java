package com.example.demo.dao;

import model.Category;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategoryDao {
    private static final Logger LOGGER = Logger.getLogger(CategoryDao.class);
    private static DBManager dbm = DBManager.getInstance();
    private static String QUERY1 = "SELECT * FROM CATEGORY ORDER BY category_name";
    private static String QUERY2 = "INSERT INTO CATEGORY VALUES(DEFAULT,?)";
    private static String QUERY3 = "DELETE FROM CATEGORY WHERE id=?";
    private static String QUERY4 = "UPDATE CATEGORY SET CATEGORY_NAME=? WHERE id=?";

    private CategoryDao() {
    }

    public static List<Category> getAllCategories() {
        List<Category> lst = new ArrayList<>();
        CategoryDao.CategoryMapper mapper = new CategoryDao.CategoryMapper();
        try (Connection con = dbm.getConnection();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(QUERY1)) {
            while (rs.next()) {
                lst.add(mapper.mapRow(rs));
            }
        } catch (SQLException throwables) {
            LOGGER.error(throwables.getMessage());
        }
        return lst;
    }

    public static boolean addCategory(String categoryName) {
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            con = dbm.getConnection();
            pstmt = con.prepareStatement(QUERY2, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, categoryName);
            if (pstmt.executeUpdate() > 0) {
                rs = pstmt.getGeneratedKeys();
                if (rs.next()) {
                    return true;
                }
            }
        } catch (SQLException throwables) {
            System.out.println(throwables.getMessage());
            LOGGER.error(throwables.getMessage());
            return false;
        } finally {
            DBManager.close(rs);
            DBManager.close(pstmt);
            DBManager.close(con);
        }
        return false;
    }

    public static void deleteCategory(int[] idList) {
        int n = idList.length;
        String QUERY7 = "DELETE FROM CATEGORY WHERE id IN (" + DBManager.repeat(n - 1, "?,") + "?)";
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


    private static class CategoryMapper implements EntityMapper<Category> {

        @Override
        public Category mapRow(ResultSet rs) {
            try {
                Category category = new Category();
                category.setId(rs.getInt(Constants.ENTITY_ID));
                category.setCategoryName(rs.getString(Constants.CATEGORY_NAME));
                return category;
            } catch (SQLException e) {
                throw new IllegalStateException(e);
            }
        }
    }

    public static void updateCategory(int categoryId, String categoryName) {
        ResultSet rs = null;
        try (Connection con = dbm.getConnection();
             PreparedStatement pstmt = con.prepareStatement(QUERY4)) {
            pstmt.setString(1, categoryName);
            pstmt.setInt(2, categoryId);
            rs = pstmt.executeQuery();
        } catch (SQLException throwables) {
            LOGGER.error(throwables.getMessage());
        } finally {
            DBManager.close(rs);
        }
    }


}
