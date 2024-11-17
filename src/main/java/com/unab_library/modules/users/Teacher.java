package com.unab_library.modules.users;

import com.unab_library.common.exception.general.BadRequestException;
import java.util.ArrayList;
import java.util.List;


public class Teacher extends Person {
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
    public String toString() {
        return String.format("Teacher[" +
            "identityDocument='%s', " +
            "fullName='%s', " +
            "gender='%s', " +
            "profession='%s', " +
            "academicDegrees=%s" +
            "]",
            getIdentityDocument(), getFullName(), getGender().getName(), getProfession(), getAcademicDegrees().toString());
    }

    public static TeacherBuilder builder() {
        return new TeacherBuilder();
    }

    // Builder
    protected static class TeacherBuilder {
        private Person person;
        private String profession;
        private List<AcademicDegree> academicDegrees;

        public TeacherBuilder setPerson(Person person) {
            this.person = person;
            return this;
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
            if (profession == null) {
                throw BadRequestException.invalidUserData("Profession is required");
            }
            if (academicDegrees == null || academicDegrees.isEmpty()) {
                throw BadRequestException.invalidUserData("Academic degrees are required");
            }
            return new Teacher(person.getIdentityDocument(), person.getGender(), person.getFullName(), profession, academicDegrees);
        }
    }
}
