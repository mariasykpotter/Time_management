package com.example.demo.dao;


import com.example.demo.model.Person;
import org.apache.log4j.Logger;

import java.sql.*;


public class PersonDao {
    private static final DBManager dbm = DBManager.getInstance();
    private static final String INSERT_QUERY = "INSERT INTO PERSON VALUES (DEFAULT,?,?,?,?, DEFAULT, ?)";
    private static final String SELECT_QUERY = "SELECT * FROM PERSON WHERE user_name=?";
    private static final String DELETE_QUERY = "DELETE FROM PERSON WHERE ID=?";
    private static final Logger LOGGER = Logger.getLogger(PersonDao.class);

    private PersonDao() {
    }

    public static Person insertPerson(Person person) {
        try (Connection con = dbm.getConnection();
             PreparedStatement pstmt = con.prepareStatement(INSERT_QUERY, Statement.RETURN_GENERATED_KEYS);
        ) {
            pstmt.setString(1, person.getFirstName());
            pstmt.setString(2, person.getLastName());
            pstmt.setString(3, person.getUserName());
            pstmt.setInt(4, person.getRoleId());
            pstmt.setString(5, HashProcessor.generateStrongPasswordHash(person.getPassword()));
            if (pstmt.executeUpdate() > 0) {
                try (ResultSet rs = pstmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        int id = rs.getInt(1);
                        person.setId(id);
                    }
                }
            }
        } catch (SQLException throwables) {
            System.out.println(throwables);
            LOGGER.error(throwables.getMessage());
        }
        return person;
    }

    public static Person getUserByLogin(String userName) {
        Person person = null;
        try (Connection con = dbm.getConnection();
             PreparedStatement pstmt = con.prepareStatement(SELECT_QUERY);
        ) {
            pstmt.setString(1, userName);
            ResultSet rs = pstmt.executeQuery();
            UserMapper mapper = new UserMapper();
            if (rs.next()) {
                person = mapper.mapRow(rs);
            }
        } catch (SQLException throwables) {
            LOGGER.error(throwables.getMessage());
        }
        return person;
    }

    public static void deletePerson(int[] idList) {
        int n = idList.length;
        String QUERY7 = "DELETE FROM PERSON WHERE id IN (" + DBManager.repeat(n - 1, "?,") + "?)";
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

    private static class UserMapper implements EntityMapper<Person> {

        @Override
        public Person mapRow(ResultSet rs) {
            try {
                Person person = new Person();
                person.setId(rs.getInt(Constants.ENTITY_ID));
                person.setUserName(rs.getString(Constants.USER_LOGIN));
                person.setPassword(rs.getString(Constants.USER_PASSWORD));
                person.setFirstName(rs.getString(Constants.USER_FIRST_NAME));
                person.setLastName(rs.getString(Constants.USER_LAST_NAME));
                person.setLocaleName(rs.getString(Constants.USER_LOCALE_NAME));
                person.setRoleId(rs.getInt(Constants.USER_ROLE_ID));
                return person;
            } catch (SQLException e) {
                throw new IllegalStateException(e);
            }
        }
    }
}



