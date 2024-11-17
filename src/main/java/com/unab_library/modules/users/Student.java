package com.unab_library.modules.users;

import com.unab_library.common.exception.general.BadRequestException;

public class Student extends Person {
    private String currentCareer;

    public Student(String identityDocument, Gender gender, String fullName, String currentCareer) {
        super(identityDocument, gender, fullName);
        this.currentCareer = currentCareer;
    }

    public String getCurrentCareer() {
        return currentCareer;
    }

    public void setCurrentCareer(String currentCareer) {
        if (currentCareer == null) {
            throw BadRequestException.currentCareerIsRequired();
        }
        this.currentCareer = currentCareer;
    }

    @Override
    public String toString() {
        return String.format("Student[identityDocument='%s', gender='%s', fullName='%s', currentCareer='%s']",
            getIdentityDocument(), getFullName(), getGender().getName(), getCurrentCareer());
    }

    public static StudentBuilder builder(Person person) {
        return new StudentBuilder(person);
    }

    // Builder
    protected static class StudentBuilder {
        private Person person;
        private String currentCareer;

        public StudentBuilder(Person person) {
            this.person = person;
        }

        public StudentBuilder setCurrentCareer(String currentCareer) {
            this.currentCareer = currentCareer;
            return this;
        }

        public Student build() {
            if (currentCareer == null) {
                throw BadRequestException.currentCareerIsRequired();
            }
            
            return new Student(person.getIdentityDocument(), person.getGender(), person.getFullName(), currentCareer);
        }
    }
}
