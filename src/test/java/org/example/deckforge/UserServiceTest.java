package org.example.deckforge;

import org.example.deckforge.Application.UserService;
import org.example.deckforge.Domain.Enums.Role;
import org.example.deckforge.Domain.User;
import org.example.deckforge.Infrastructure.JdbcUserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private JdbcUserRepository userRepository;



    @InjectMocks
    private UserService userService;

    private User user;

    @BeforeEach
    void setup() {

        user = new User();

        user.setUsername("testuser");
        user.setEmail("test@test.com");
        user.setPasswordHash("password123");
        user.setRole(Role.USER);
    }


    @Test
    void shouldCreateNewUser(){

        userService.createUser(user);

        // Assert

        verify(userValidator, times(1))
                .validateUser(user);

        verify(userRepository, times(1))
                .save(user);

        assertEquals(
                "hashed_password123",
                user.getPasswordHash());

    }


}
