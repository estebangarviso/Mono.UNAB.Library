package com.unab_library.modules.users;

import com.google.gson.reflect.TypeToken;
import com.unab_library.core.AbstractRepository;
import com.unab_library.core.AbstractRepository.Result;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicReference;


public class UserRepository extends AbstractRepository<User> {
    // private constructor to prevent instantiation
    private UserRepository() {
        super("users.json", new TypeToken<ArrayList<User>>(){});
    }

    private static final AtomicReference<UserRepository> INSTANCE = new AtomicReference<>();

    public static UserRepository getInstance() {
        if (INSTANCE.get() == null) {
            INSTANCE.set(new UserRepository());
        }
        return INSTANCE.get();
    }

    public Result<User> getByIdentityDocument(String identityDocument) {
        return getByProperty(User::getIdentityDocument, identityDocument);
    }

    public boolean existsByIdentityDocument(String identityDocument) {
        for (User user : this.getAll()) {
            if (user.getIdentityDocument().equals(identityDocument)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Saves a student.
     * 
     * @param userStudentSaveDTO the student to save
     */
    public Result<User> save(UserStudentSaveDTO userStudentSaveDTO) {
        User newUser = User.builder(Role.STUDENT, userStudentSaveDTO.person())
            .setCareer(userStudentSaveDTO.career())
            .setCurrentCareer(userStudentSaveDTO.currentCareer())
            .build();

        return this.save(newUser);
    }

    /**
     * Saves a teacher.
     * 
     * @param userTeacherSaveDTO the teacher to save
     */
    public Result<User> save(UserTeacherSaveDTO userTeacherSaveDTO) {
        User newUser = User.builder(Role.TEACHER, userTeacherSaveDTO.person())
            .setCareer(userTeacherSaveDTO.career())
            .setProfession(userTeacherSaveDTO.profession())
            .setAcademicDegrees(userTeacherSaveDTO.academicDegrees())
            .build();

        return this.save(newUser);
    }
}
