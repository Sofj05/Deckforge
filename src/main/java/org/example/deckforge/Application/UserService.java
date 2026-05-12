package org.example.deckforge.Application;

import org.example.deckforge.Application.Validation.Validation;
import org.example.deckforge.Domain.Repository.IUserRepository;

public class UserService {
    private IUserRepository uRepo;
    private Validation validation;

    public UserService(IUserRepository uRepo, Validation validation) {
        this.uRepo = uRepo;
        this.validation = validation;
    }

    public void createUser() {}

    public void readUser() {}

    public void updateUser() {}

    public void deleteUser() {}
}
