package com.unab_library.modules.users;

import java.util.ArrayList;
import java.util.List;

import com.unab_library.common.enums.Gender;
import java.util.logging.Logger;

public class Teacher extends Person {
    private static final Logger LOGGER = Logger.getLogger(Teacher.class.getName());
    private String profession;
    private ArrayList<AcademicDegree> academicDegrees;

    // For master or doctorate degree
    public Teacher(String identityDocument, Gender gender, String fullName, String profession, AcademicDegree academicDegree) {
        super(identityDocument, gender, fullName);
        this.profession = profession;
        this.academicDegrees = new ArrayList<>();
        this.academicDegrees.add(academicDegree);
    }
    // For master and doctorate degree
    public Teacher(String identityDocument, Gender gender, String fullName, String profession, List<AcademicDegree> academicDegrees) {
        super(identityDocument, gender, fullName);
        this.profession = profession;
        this.academicDegrees = new ArrayList<>(academicDegrees);
    }

    public void addAcademicDegree(AcademicDegree academicDegree) {
        this.academicDegrees.add(academicDegree);
    }

    public void addAcademicDegrees(List<AcademicDegree> academicDegrees) {
        this.academicDegrees.addAll(academicDegrees);
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
