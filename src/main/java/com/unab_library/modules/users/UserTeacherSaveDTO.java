package com.unab_library.modules.users;

import java.util.List;

public record UserTeacherSaveDTO(
    /**
     * User's person.
     */
    Person person,
    /**
     * User's career.
     */
    String career,
    /**
     * Teacher's profession.
     */
    String profession,
    /**
     * Teacher's academic degrees.
     */
    List<AcademicDegree> academicDegrees
) {}
