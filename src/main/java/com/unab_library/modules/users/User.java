package com.unab_library.modules.users;

import com.unab_library.common.exception.general.BadRequestException;
import com.unab_library.core.AbstractRepository.Result;
import com.unab_library.modules.book_management.book_loans.BookLoan;
import com.unab_library.modules.book_management.book_loans.BookLoanRepository;
import java.util.List;


public class User implements UserInterface {
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
        Student student = Student.builder(person)
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
        Teacher teacher = Teacher.builder()
        .setPerson(person)
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
        Teacher teacher = Teacher.builder()
        .setPerson(person)
        .setProfession(profession)
        .setAcademicDegrees(academicDegrees)
        .build();
        this.person = teacher;
    }

    public String getIdentityDocument() {
        return person.getIdentityDocument();
    }

    @Override
    public boolean hasActiveLoan() {
        return idLoan != null;
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
        return bookLoanRepository.getById(idLoan).getValue();
    }

    public void setIdLoan(String idLoan) {
        this.idLoan = idLoan;
    }

    public void releaseLoan() {
        userRepository.updateLoan(this, null);
    }

    public void attachLoan(String idLoan) {
        userRepository.updateLoan(this, idLoan);
    }

    @Override
    public String toString() {
        return String.format("User[" +
            "role='%s', " +
            "career='%s', " +
            "person='%s', " +
            "loan=%s" +
            "]", getRole().getName(), getCareer(), getPerson().toString(), hasActiveLoan() ? getLoan().toString() : "null");
    }

    public static Result<User> createStudent(UserStudentSaveDTO userStudentSaveDTO) {
        return userRepository.save(userStudentSaveDTO);
    }

    public static Result<User> createTeacher(UserTeacherSaveDTO userTeacherSaveDTO) {
        return userRepository.save(userTeacherSaveDTO);
    }

    public static UserBuilder builder(Role role, Person person) {
        return new UserBuilder(role, person);
    }

    private static final UserRepository userRepository = UserRepository.getInstance();
    private static final BookLoanRepository bookLoanRepository = BookLoanRepository.getInstance();
    private Role role;
    private String career;
    private Person person;
    private String idLoan;

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

        public UserBuilder setCurrentCareer(String currentCareer) {
            this.currentCareer = currentCareer;
            return this;
        }

        public UserBuilder setProfession(String profession) {
            this.profession = profession;
            return this;
        }

        public UserBuilder setAcademicDegrees(List<AcademicDegree> academicDegrees) {
            this.academicDegrees = academicDegrees;
            return this;
        }

        public User build() {
            if (role == null) {
                throw BadRequestException.invalidUserData("Role is required");
            }
            if (person == null || person.getIdentityDocument() == null) {
                throw BadRequestException.invalidUserData("Identity document is required");
            }
            if (person.getGender() == null) {
                throw BadRequestException.invalidUserData("Gender is required");
            }
            if (person.getFullName() == null) {
                throw BadRequestException.invalidUserData("Full name is required");
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
