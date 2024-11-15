package com.unab_library.modules.users;

import java.util.List;

public record UserStudentSaveDTO(
    /**
     * User's person.
     */
    Person person,
    /**
     * User's career.
     */
    String career,
    /**
     * Student's current career.
     */
    String currentCareer
) {}
