package com.vipin.googleAuthLogin.repository;

import com.vipin.googleAuthLogin.model.UserData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserRepositoryTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserRepositoryTest userRepositoryTest;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findByEmail_existingEmail_returnsUserData() {
        String email = "test@example.com";
        UserData user = new UserData();
        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));

        Optional<UserData> result = userRepository.findByEmail(email);

        assertTrue(result.isPresent());
        assertEquals(user, result.get());
    }

    @Test
    void findByEmail_nonExistingEmail_returnsEmpty() {
        String email = "nonexistent@example.com";
        when(userRepository.findByEmail(email)).thenReturn(Optional.empty());

        Optional<UserData> result = userRepository.findByEmail(email);

        assertFalse(result.isPresent());
    }

    @Test
    void findByUserName_existingUserName_returnsUserData() {
        String userName = "testuser";
        UserData user = new UserData();
        when(userRepository.findByUserName(userName)).thenReturn(Optional.of(user));

        Optional<UserData> result = userRepository.findByUserName(userName);

        assertTrue(result.isPresent());
        assertEquals(user, result.get());
    }

    @Test
    void findByUserName_nonExistingUserName_returnsEmpty() {
        String userName = "nonexistentuser";
        when(userRepository.findByUserName(userName)).thenReturn(Optional.empty());

        Optional<UserData> result = userRepository.findByUserName(userName);

        assertFalse(result.isPresent());
    }
}