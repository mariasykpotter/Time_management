package com.example.demo.dao;


import com.example.demo.model.Person;
import org.apache.log4j.Logger;

import java.sql.*;


/**
 * Data access object for Person entity
 */
public class PersonDao {
    private static DBManager dbm = DBManager.getInstance();
    private static final String INSERT_QUERY = "INSERT INTO PERSON VALUES (DEFAULT,?,?,?,?,?,?)";
    private static final String SELECT_QUERY = "SELECT * FROM PERSON WHERE user_name=?";
    private static final Logger LOGGER = Logger.getLogger(PersonDao.class);

    /**
     * Private constructor for PersonDao
     */
    private PersonDao() {
    }

    /**
     * Inserts given person into a table Person.
     *
     * @param person Person entity.
     * @return Person entity with updated id.
     */
    public static Person insertPerson(Person person) {
        try (Connection con = dbm.getConnection();
             PreparedStatement pstmt = con.prepareStatement(INSERT_QUERY, Statement.RETURN_GENERATED_KEYS)
        ) {
            pstmt.setString(1, person.getFirstName());
            pstmt.setString(2, person.getLastName());
            pstmt.setString(3, person.getUserName());
            pstmt.setInt(4, person.getRoleId());
            pstmt.setString(5, person.getLocaleName());
            pstmt.setString(6, HashProcessor.generateStrongPasswordHash(person.getPassword()));
            if (pstmt.executeUpdate() > 0) {
                try (ResultSet rs = pstmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        int id = rs.getInt(1);
                        person.setId(id);
                    }
                }
            }
        } catch (SQLException throwables) {
            LOGGER.error(throwables.getMessage());
        }
        return person;
    }

    /**
     * Returns Person entity by login.
     *
     * @param userName login.
     * @return Person entity.
     */
    public static Person getUserByLogin(String userName) {
        Person person = null;
        try (Connection con = dbm.getConnection();
             PreparedStatement pstmt = con.prepareStatement(SELECT_QUERY)
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

    /**
     * Extracts a user from the result set row.
     */
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



