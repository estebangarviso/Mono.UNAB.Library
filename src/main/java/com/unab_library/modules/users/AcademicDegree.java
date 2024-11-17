package com.unab_library.modules.users;
public class AcademicDegree {
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
