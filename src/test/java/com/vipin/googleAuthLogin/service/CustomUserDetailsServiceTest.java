package com.vipin.googleAuthLogin.service;

import com.vipin.googleAuthLogin.model.UserData;
import com.vipin.googleAuthLogin.repository.UserRepository;
import com.vipin.googleAuthLogin.service.implementation.CustomUserDetailsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CustomUserDetailsServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private CustomUserDetailsService customUserDetailsService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void loadUserByUsername_existingUsername_returnsUserDetails() {
        String username = "testuser";
        UserData user = new UserData();
        user.setUserName(username);
        user.setPassword("password");
        when(userRepository.findByUserName(username)).thenReturn(Optional.of(user));

        UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);

        assertEquals(username, userDetails.getUsername());
        assertEquals("password", userDetails.getPassword());
    }

    @Test
    void loadUserByUsername_nonExistingUsername_throwsUsernameNotFoundException() {
        String username = "nonexistentuser";
        when(userRepository.findByUserName(username)).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class, () -> customUserDetailsService.loadUserByUsername(username));
    }
}