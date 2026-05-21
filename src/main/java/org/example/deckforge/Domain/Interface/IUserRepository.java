package org.example.deckforge.Domain.Interface;

import org.example.deckforge.Domain.User;

public interface IUserRepository {

    void createUser(User user);
    User readUser(User user);
    void updateUser(int id, User user);
    void deleteUser(int id);
    User getUserByUsername(String username);
    User getUserByEmail(String email);
    User getUserById(int id);
}
