package com.unab_library.modules.users;

import com.unab_library.common.enums.Gender;
import java.util.logging.Logger;

public class Person {
    private static final Logger LOGGER = Logger.getLogger(Person.class.getName());
    private String identityDocument;
    private Gender gender;
    private String fullName;
    
    public Person(String identityDocument, Gender gender, String fullName) {
        this.identityDocument = identityDocument;
        this.gender = gender;
        this.fullName = fullName;
    }

    public String getFullName() {
        return fullName;
    }

    public String getIdentityDocument() {
        return identityDocument;
    }
    
    public void showData() {
        String identityDocumentString = String.format("Identity document: %s", identityDocument);
        String fullNameString = String.format("Full name: %s", fullName);
        LOGGER.info(identityDocumentString);
        LOGGER.info("Gender: " + gender.getName());
        LOGGER.info(fullNameString);
    }
}