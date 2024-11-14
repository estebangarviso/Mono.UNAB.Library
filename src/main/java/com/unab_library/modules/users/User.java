package com.unab_library.modules.users;

import java.util.List;
import java.util.logging.Logger;

import com.unab_library.common.exception.general.BadRequestException;
import com.unab_library.modules.book_loans.BookLoan;

public class User implements UserInterface {
    private static final Logger LOGGER = Logger.getLogger(User.class.getName());
    private Role role;
    private String career;
    private Person person;
    private BookLoan loan;

    /**
     * Constructor for users
     * @param person
     * @param career
     * @param loan
     */
    private User(Person person, String career, BookLoan loan) {
        this.role = Role.STUDENT;
        this.career = career;
        this.person = person;
        this.loan = loan;
    }

    /**
     * Constructor for students
     * @param person
     * @param career
     * @param currentCareer
     */
    public User(Person person, String career, String currentCareer) {
        this.role = Role.STUDENT;
        this.career = career;
        this.person = person;
        Student student = Student.StudentBuilder(person)
        .setCurrentCareer(currentCareer)
        .build();
        this.person = student;
    }

    /**
     * Constructor for teachers with a single academic degree
     * @param person
     * @param career
     * @param profession
     * @param academicDegree
     */
    public User(Person person, String career, String profession, AcademicDegree academicDegree) {
        this.role = Role.TEACHER;
        this.career = career;
        Teacher teacher = Teacher.TeacherBuilder(person)
        .setProfession(profession)
        .setAcademicDegrees(List.of(academicDegree))
        .build();
        this.person = teacher;
    }

    /**
     * Constructor for teachers with multiple academic degrees
     * @param person
     * @param career
     * @param profession
     * @param academicDegrees
     */
    public User(Person person, String career, String profession, List<AcademicDegree> academicDegrees) {
        this.role = Role.TEACHER;
        this.career = career;
        Teacher teacher = Teacher.TeacherBuilder(person)
        .setProfession(profession)
        .setAcademicDegrees(academicDegrees)
        .build();
        this.person = teacher;
    }

    public String getIdentityDocument() {
        return person.getIdentityDocument();
    }

    public boolean hasActiveLoan() {
        return loan == null;
    }

    public Role getRole() {
        return role;
    }

    public Person getPerson() {
        return person;
    }

    public String getCareer() {
        return career;
    }

    public BookLoan getLoan() {
        return loan;
    }

    public void setLoan(BookLoan loan) {
        this.loan = loan;
    }

    public void showData() {
        String roleString = String.format("Role: %s", role.getName());
        String careerString = String.format("Career: %s", career);
        LOGGER.info(roleString);
        LOGGER.info(careerString);
        person.showData();
    }

    // Builder
    public static class UserBuilder {
        private Role role;
        private String career;
        private Person person;
        private String currentCareer;
        private String profession;
        private List<AcademicDegree> academicDegrees;

        public UserBuilder(Role role, Person person) {
            this.role = role;
            this.person = person;
        }

        public UserBuilder setCareer(String career) {
            this.career = career;
            return this;
        }

        public User build() {
            if (role == null) {
                throw BadRequestException.invalidUserData("Role is required");
            }
            if (person == null) {
                throw BadRequestException.invalidUserData("Person data is required");
            }
            if (career == null) {
                throw BadRequestException.invalidUserData("Career is required");
            }
            // Build user
            if (role == Role.STUDENT) {
                return new User(person, career, currentCareer);
            } else {
                return new User(person, career, profession, academicDegrees);
            }
        }
    }
}
