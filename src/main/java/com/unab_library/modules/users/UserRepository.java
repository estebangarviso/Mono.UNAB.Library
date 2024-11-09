package com.unab_library.modules.users;

import java.util.concurrent.atomic.AtomicReference;

import com.unab_library.core.AbstractRepository;

public class UserRepository extends AbstractRepository<User> {
    // private constructor to prevent instantiation
    private UserRepository() {}

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
}
