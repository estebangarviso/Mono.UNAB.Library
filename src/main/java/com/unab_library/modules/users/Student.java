package com.unab_library.modules.users;

import java.util.logging.Logger;

import com.unab_library.common.enums.GenderEnum;

public class Student extends Person {
    private static final Logger LOGGER = Logger.getLogger(Student.class.getName());
    private String currentCareer;

    public Student(String identityDocument, GenderEnum gender, String fullName, String currentCareer) {
        super(identityDocument, gender, fullName);
        this.currentCareer = currentCareer;
    }

    @Override
    public void showData() {
        super.showData();
        String currentCareerString = String.format("Current career: %s", currentCareer);
        LOGGER.info(currentCareerString);
    }
}
