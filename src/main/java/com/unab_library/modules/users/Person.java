package com.unab_library.modules.users;

import com.unab_library.common.enums.GenderEnum;
import java.util.logging.Logger;

class Person {
    private static final Logger LOGGER = Logger.getLogger(Person.class.getName());
    private String identityDocument;
    private GenderEnum gender;
    private String fullName;
    
    Person(String identityDocument, GenderEnum gender, String fullName) {
        this.identityDocument = identityDocument;
        this.gender = gender;
        this.fullName = fullName;
    }
    
    void showData() {
        String identityDocumentString = String.format("Identity document: %s", identityDocument);
        String fullNameString = String.format("Full name: %s", fullName);
        LOGGER.info(identityDocumentString);
        LOGGER.info("Gender: " + gender.getName());
        LOGGER.info(fullNameString);
    }
}