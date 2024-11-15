package com.unab_library.modules.users;

public enum AcademicDegreeCategory {
    MASTER("Master"),
    DOCTORATE("Doctorate");
    private String name;

    AcademicDegreeCategory(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
