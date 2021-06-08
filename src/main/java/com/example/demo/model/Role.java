package com.example.demo.model;

public enum Role {
    ADMIN(), USER();

    public static Role getRole(Person person) {
        return Role.values()[person.getRoleId()];
    }
}
