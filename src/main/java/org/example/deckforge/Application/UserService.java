package org.example.deckforge.Application;

import org.example.deckforge.Application.Validation.Validation;
import org.example.deckforge.Domain.Repository.IUserRepository;
import org.example.deckforge.Domain.User;

public class UserService {
    private IUserRepository uRepo;
    private Validation validation;

    public UserService(IUserRepository uRepo, Validation validation) {
        this.uRepo = uRepo;
        this.validation = validation;
    }

    public void createUser(User user) {
        validation.validateUser(user);
        uRepo.createUser(user);
    }

    public void readUser(User user) {
        validation.validateUser(user);
        uRepo.readUser(user);
    }

    public void updateUser(int id, User user) {
        validation.validateUser(user);
        uRepo.updateUser(id. user);
    }

    public void deleteUser(int id) {
        validation.validateUser(id);
        uRepo.deleteUser(id);
    }
}
