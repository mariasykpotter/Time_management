package com.example.demo.dao;

import com.example.demo.model.Person;

/**
 * Inserts the initial admin to the Person table
 */
public class TestDBManager {

    public static void main(String[] args) {
        PersonDao.insertPerson(new Person("Andriy", "Petov", "a_petov", 0, "en_US", "123"));
    }
}
