package com.unab_library.modules.users;

import com.unab_library.common.enums.Gender;
import com.unab_library.common.exception.general.BadRequestException;
import com.unab_library.common.libs.ValidateUtils;

public class Person {
    private String identityDocument;
    private Gender gender;
    private String fullName;
    
    public Person(String identityDocument, Gender gender, String fullName) {
        this.setIdentityDocument(identityDocument);
        this.setGender(gender);
        this.setFullName(fullName);
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getIdentityDocument() {
        return identityDocument;
    }

    public void setIdentityDocument(String identityDocument) {
        if(!ValidateUtils.isValidChileanIdentityDocument(identityDocument)) {
            throw BadRequestException.invalidIdentityDocument(identityDocument);
        }
        this.identityDocument = identityDocument;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }
    
    @Override
    public String toString() {
        return String.format("Person[" +
            "identityDocument='%s', " +
            "fullName='%s', " +
            "gender='%s'" +
            "]",
            getIdentityDocument(), getFullName(), getGender().getName());
    }
}