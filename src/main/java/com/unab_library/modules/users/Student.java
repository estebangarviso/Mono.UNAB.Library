package com.unab_library.modules.users;

import java.util.logging.Logger;

import com.unab_library.common.enums.Gender;

public class Student extends Person {
    private static final Logger LOGGER = Logger.getLogger(Student.class.getName());
    private String currentCareer;

    public Student(String identityDocument, Gender gender, String fullName, String currentCareer) {
        super(identityDocument, gender, fullName);
        this.currentCareer = currentCareer;
    }

    public String getCurrentCareer() {
        return currentCareer;
    }

    public void setCurrentCareer(String currentCareer) {
        this.currentCareer = currentCareer;
    }

    @Override
    public void showData() {
        super.showData();
        String currentCareerString = String.format("Current career: %s", currentCareer);
        LOGGER.info(currentCareerString);
    }
}
