package org.example.deckforge.Application;

import org.example.deckforge.Application.Validation.Validation;
import org.example.deckforge.Application.Validation.ValidationException;
import org.example.deckforge.Domain.Enums.Role;
import org.example.deckforge.Domain.Repository.IUserRepository;
import org.example.deckforge.Domain.User;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private IUserRepository uRepo;
    private Validation validation;

    @Autowired
    public UserService(IUserRepository uRepo, Validation validation) {
        this.uRepo = uRepo;
        this.validation = validation;
    }

    public void createUser(User user) {
        validation.validateUser(user);

        user.setUsername(user.getUsername().trim());
        user.setEmail(user.getEmail().trim());
        user.setPassword(user.getPassword().trim());
        if (user.getRole() == null) {
            user.setRole(Role.USER);
        }
        hashPassword(user);
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
        String hashed = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());
        user.setPasswordHash(hashed);
    }


    public User login(String username, String password) {
        username = username.trim();
        password = password.trim();

        User user = uRepo.getUserByUsername(username);
        validation.validateLogin(user, password);
        return user;
    }

    public User getUserById(int id) {
        User user = new User();

        return user;
    }

}
