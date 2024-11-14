package com.unab_library.modules.users;

import java.util.ArrayList;
import java.util.List;

import com.unab_library.common.enums.Gender;
import com.unab_library.common.exception.general.BadRequestException;

import java.util.logging.Logger;

public class Teacher extends Person {
    private static final Logger LOGGER = Logger.getLogger(Teacher.class.getName());
    private String profession;
    private ArrayList<AcademicDegree> academicDegrees;

    // For master and doctorate degree (single academic degree, send a list with one element)
    private Teacher(String identityDocument, Gender gender, String fullName, String profession, List<AcademicDegree> academicDegrees) {
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

    public String getProfession() {
        return profession;
    }

    public List<AcademicDegree> getAcademicDegrees() {
        return academicDegrees;
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

    // Builder
    public static class TeacherBuilder {
        private Person person;
        private String profession;
        private List<AcademicDegree> academicDegrees;

        public TeacherBuilder(Person person) {
            this.person = person;
        }

        public TeacherBuilder setProfession(String profession) {
            this.profession = profession;
            return this;
        }

        public TeacherBuilder setAcademicDegrees(List<AcademicDegree> academicDegrees) {
            this.academicDegrees = academicDegrees;
            return this;
        }

        public Teacher build() {
            if (person == null) {
                throw BadRequestException.invalidTeacherData("Person data is required");
            }
            if (profession == null) {
                throw BadRequestException.invalidTeacherData("Profession is required");
            }
            if (academicDegrees == null) {
                throw BadRequestException.invalidTeacherData("At least one academic degree is required");
            }
            return new Teacher(person.getIdentityDocument(), person.getGender(), person.getFullName(), profession, academicDegrees);
        }
    }
}
