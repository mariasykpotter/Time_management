package com.example.demo.dao;

import model.Person;

public class TestDBManager {

    public static void main(String[] args) {
        PersonDao.insertPerson(new Person("Andriy", "Petov", "a_petov", 0, "123"));
    }
}
