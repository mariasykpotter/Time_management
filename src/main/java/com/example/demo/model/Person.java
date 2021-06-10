package com.example.demo.model;

import java.io.Serializable;

public class Person implements Serializable {
    private static final long serialVersionUID = -1795748331141069634L;
    private int id;
    private String firstName;
    private String lastName;
    private String userName;
    private int roleId;
    private String localeName;
    private String password;

    public Person() {
        super();
        id = 0;
    }

    public Person(String firstNam, String lastNam, String userNam, int rolId, String localeNam, String pass) {
        firstName = firstNam;
        lastName = lastNam;
        userName = userNam;
        roleId = rolId;
        localeName = localeNam;
        password = pass;
    }

    public int getId() {
        return id;
    }


    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getUserName() {
        return userName;
    }

    public int getRoleId() {
        return roleId;
    }

    public String getLocaleName() {
        return localeName;
    }

    public String getPassword() {
        return password;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public void setLocaleName(String localeName) {
        this.localeName = localeName;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

