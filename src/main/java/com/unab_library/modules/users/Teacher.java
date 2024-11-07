package com.unab_library.modules.users;

import java.util.ArrayList;
import com.unab_library.common.enums.GenderEnum;
import java.util.logging.Logger;

public class Teacher extends Person {
    private static final Logger LOGGER = Logger.getLogger(Teacher.class.getName());
    private String profession;
    private ArrayList<AcademicDegree> academicDegrees;

    public Teacher(String identityDocument, GenderEnum gender, String fullName, String profession) {
        super(identityDocument, gender, fullName);
        this.profession = profession;
        this.academicDegrees = new ArrayList<>();
    }

    public void addAcademicDegree(AcademicDegree academicDegree) {
        this.academicDegrees.add(academicDegree);
    }

    @Override
    public void showData() {
        super.showData();
        String professionString = String.format("Profession: %s", profession);
        LOGGER.info(professionString);
        LOGGER.info("Academic degrees:");
        for (AcademicDegree academicDegree : academicDegrees) {
            academicDegree.showData();
        }
    }
}
