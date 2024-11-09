package com.unab_library.modules.users;

import java.util.List;
import java.util.logging.Logger;
import com.unab_library.modules.loans.Loan;

public class User implements UserInterface {
    private static final Logger LOGGER = Logger.getLogger(User.class.getName());
    private Roles role;
    private String career;
    private Person person;
    private Loan loan;

    // Student
    public User(Person person, String career, String currentCareer) {
        this.role = Roles.STUDENT;
        this.career = career;
        this.person = person;
        Student student = (Student) person;
        student.setCurrentCareer(currentCareer);
    }

    // Teacher
    public User(Person person, String career, AcademicDegree academicDegree) {
        this.role = Roles.TEACHER;
        this.career = career;
        Teacher teacher = (Teacher) person;
        teacher.addAcademicDegree(academicDegree);
        this.person = teacher;
    }

    // Teacher
    public User(Person person, String career,  List<AcademicDegree> academicDegrees) {
        this.role = Roles.TEACHER;
        this.career = career;
        Teacher teacher = (Teacher) person;
        teacher.addAcademicDegrees(academicDegrees);
        this.person = teacher;
    }

    public String getIdentityDocument() {
        return person.getIdentityDocument();
    }

    public boolean hasActiveLoan() {
        return loan == null;
    }

    public Roles getRole() {
        return role;
    }

    public Person getPerson() {
        return person;
    }

    public String getCareer() {
        return career;
    }

    public Loan getLoan() {
        return loan;
    }

    public void setLoan(Loan loan) {
        this.loan = loan;
    }

    public void showData() {
        String roleString = String.format("Role: %s", role.getName());
        String careerString = String.format("Career: %s", career);
        LOGGER.info(roleString);
        LOGGER.info(careerString);
        person.showData();
    }
}
