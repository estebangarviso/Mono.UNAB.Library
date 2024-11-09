package com.unab_library.modules.users;

public enum Roles {
    STUDENT("Student"), TEACHER("Teacher");
    private String name;

    Roles(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
