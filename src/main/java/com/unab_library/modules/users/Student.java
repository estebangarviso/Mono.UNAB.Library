package com.unab_library.modules.users;

import java.util.logging.Logger;
import com.unab_library.common.exception.general.BadRequestException;
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
        if (currentCareer == null) {
            throw BadRequestException.currentCareerIsRequired();
        }
        this.currentCareer = currentCareer;
    }

    @Override
    public void showData() {
        super.showData();
        String currentCareerString = String.format("Current career: %s", currentCareer);
        LOGGER.info(currentCareerString);
    }

    // Builder
    public static class StudentBuilder {
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
            if (person.getIdentityDocument() == null) {
                throw BadRequestException.identityDocumentIsRequired();
            }
            if (person.getGender() == null) {
                throw BadRequestException.genderIsRequired();
            }
            if (person.getFullName() == null) {
                throw BadRequestException.fullNameIsRequired();
            }
            
            return new Student(person.getIdentityDocument(), person.getGender(), person.getFullName(), currentCareer);
        }
    }
}
