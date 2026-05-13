package org.example.deckforge.Application;

import org.example.deckforge.Application.Validation.Validation;
import org.example.deckforge.Application.Validation.ValidationException;
import org.example.deckforge.Domain.Repository.IUserRepository;
import org.example.deckforge.Domain.User;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;

@Service
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

    public void updateUser(User user, Boolean updPass) {
        validation.validateUser(user);
        if (updPass){
            hashPassword(user);
        }
        uRepo.updateUser(user.getId(), user);
    }

    public void deleteUser(int id) {
        validation.validateInt(id);
        uRepo.deleteUser(id);
    }

    private void hashPassword(User user) throws ValidationException{
        String hashed = BCrypt.hashpw(user.getPasswordHash(), BCrypt.gensalt());
        user.setPasswordHash(hashed);
    }


    public User login(String username, String password) {
    }
}
