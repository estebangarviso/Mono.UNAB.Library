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

    public void showData() {
        String nameString = String.format("Name: %s", name);
        LOGGER.info(nameString);
        LOGGER.info("Category: " + category.getName());
    }
}
