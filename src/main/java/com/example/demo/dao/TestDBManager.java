package com.example.demo.dao;

import com.example.demo.model.Person;

import java.util.Locale;
import java.util.ResourceBundle;

public class TestDBManager {

    public static void main(String[] args) {
        PersonDao.insertPerson(new Person("Andriy", "Petov", "a_petov", 0, "en_US", "123"));
//        Locale l = new Locale("en","GB");
//        ResourceBundle rs = ResourceBundle.getBundle("resources",l);
//        System.out.println(rs.getString("login.title"));


    }
}
