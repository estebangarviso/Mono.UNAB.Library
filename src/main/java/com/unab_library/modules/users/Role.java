package com.unab_library.modules.users;

public enum Role {
    STUDENT("Student"), TEACHER("Teacher");
    private String name;

    Role(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
