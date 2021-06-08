package com.example.demo.dao;

import com.example.demo.model.Person;

public class TestDBManager {

    public static void main(String[] args) {
//        PersonDao.insertPerson(new Person("Andriy", "Petov", "a_petov", 0, "123"));
        System.out.println(String.format("%s","new"));
        System.out.println(TimeLogDao.getInfoByStatus("102",0));
    }
}
