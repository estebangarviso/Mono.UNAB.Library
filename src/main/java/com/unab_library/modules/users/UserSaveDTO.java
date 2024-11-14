package com.unab_library.modules.users;

import java.util.List;

public record UserSaveDTO(
    /**
     * User's person.
     */
    Person person,
    /**
     * User's role.
     */
    Role role,
    /**
     * User's career.
     */
    String career,
    /**
     * Student's current career.
     */
    String currentCareer,
    /**
     * Teacher's profession.
     */
    String profession,
    /**
     * Teacher's academic degrees.
     */
    List<AcademicDegree> academicDegrees
) {}
