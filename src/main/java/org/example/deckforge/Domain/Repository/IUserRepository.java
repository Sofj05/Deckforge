package org.example.deckforge.Domain.Repository;

import org.example.deckforge.Domain.User;

import java.util.List;

public interface IUserRepository {

    void createUser(User user);
    User readUser(User user);
    void updateUser(int id, User user);
    void deleteUser(int id);
    User getUserByUsername(String username);
    User getUserById(int id);
}
