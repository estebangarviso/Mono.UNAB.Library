package com.unab_library.common.enums;

public enum Gender {
    MALE("M", "Masculino"),
    FEMALE("F", "Femenino");

    private String code;
    private String name;

    Gender(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }
}
