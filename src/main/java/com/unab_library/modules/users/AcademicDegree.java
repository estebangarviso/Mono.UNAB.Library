package com.unab_library.modules.users;

import java.util.logging.Logger;

public class AcademicDegree {
    private static final Logger LOGGER = Logger.getLogger(AcademicDegree.class.getName());
    private String name;
    private AcademicDegreeCategory category;

    public AcademicDegree(String name, AcademicDegreeCategory category) {
        this.name = name;
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public AcademicDegreeCategory getCategory() {
        return category;
    }

    @Override
    public String toString() {
        return String.format("AcademicDegree[" +
            "name='%s', " +
            "category='%s'" +
            "]",
            getName(), getCategory().getName());
    }
}
