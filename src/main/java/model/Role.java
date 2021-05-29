package model;

public enum Role {
    ADMIN(), CLIENT();

    public static Role getRole(Person person) {
        return Role.values()[person.getRoleId()];
    }
}
