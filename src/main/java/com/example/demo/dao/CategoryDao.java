package com.example.demo.dao;

import com.example.demo.model.Category;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Data access object for categories related entities
 */
public class CategoryDao {
    private static final Logger LOGGER = Logger.getLogger(CategoryDao.class);
    private static DBManager dbm = DBManager.getInstance();
    private static final String QUERY1 = "SELECT * FROM CATEGORY ORDER BY category_name";
    private static final String QUERY2 = "INSERT INTO CATEGORY VALUES(DEFAULT,?)";
    private static final String QUERY4 = "UPDATE CATEGORY SET CATEGORY_NAME=? WHERE id=?";

    /**
     * Private constructor for CategoryDao
     */
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

    /**
     * Add a category with a particular category name
     *
     * @param categoryName category name
     * @return true or false depending on whether the category was added.
     */
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
     * Extracts a category from the result set row.
     */
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

    /**
     * Update category name.
     *
     * @param categoryId category id.
     * @param categoryName category name.
     */
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
