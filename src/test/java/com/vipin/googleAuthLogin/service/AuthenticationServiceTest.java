package com.vipin.googleAuthLogin.service;

import com.vipin.googleAuthLogin.dto.UserLoginDto;
import com.vipin.googleAuthLogin.dto.UserRegistrationDto;
import com.vipin.googleAuthLogin.model.UserData;
import com.vipin.googleAuthLogin.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.awt.image.BufferedImage;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AuthenticationServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private GoogleAuthService googleAuthService;

    @InjectMocks
    private AuthenticationService authenticationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void signup_validInput_savesUser() {
        UserRegistrationDto input = new UserRegistrationDto("test@example.com", "Test User", "testuser", "password");
        when(passwordEncoder.encode(input.getPassword())).thenReturn("encodedPassword");
        when(googleAuthService.generateKey()).thenReturn("mfaSecret");

        authenticationService.signup(input);

        verify(userRepository, times(1)).save(any(UserData.class));
    }

    @Test
    void signup_exceptionThrown_throwsRuntimeException() {
        UserRegistrationDto input = new UserRegistrationDto("test@example.com", "Test User", "testuser", "password");
        when(userRepository.save(any(UserData.class))).thenThrow(new RuntimeException("Database error"));

        assertThrows(RuntimeException.class, () -> authenticationService.signup(input));
    }

    @Test
    void authenticate_validInput_returnsUser() {
        UserLoginDto input = new UserLoginDto("test@example.com", "password");
        UserData user = new UserData();
        when(userRepository.findByEmail(input.getEmail())).thenReturn(Optional.of(user));

        UserData result = authenticationService.authenticate(input);

        assertEquals(user, result);
    }

    @Test
    void authenticate_invalidCredentials_throwsRuntimeException() {
        UserLoginDto input = new UserLoginDto("test@example.com", "wrongpassword");
        doThrow(new RuntimeException("Invalid credentials")).when(authenticationManager).authenticate(any());

        assertThrows(RuntimeException.class, () -> authenticationService.authenticate(input));
    }

    @Test
    void generateTotpQR_validEmailId_returnsQRImage() {
        String emailId = "test@example.com";
        UserData user = new UserData();
        user.setMfaSecret("mfaSecret");
        BufferedImage qrImage = new BufferedImage(100, 100, BufferedImage.TYPE_INT_RGB);
        when(userRepository.findByEmail(emailId)).thenReturn(Optional.of(user));
        when(googleAuthService.generateQRImage(user.getMfaSecret(), emailId)).thenReturn(qrImage);

        BufferedImage result = authenticationService.generateTotpQR(emailId);

        assertEquals(qrImage, result);
    }

    @Test
    void generateTotpQR_userNotFound_throwsNoSuchElementException() {
        String emailId = "test@example.com";
        when(userRepository.findByEmail(emailId)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> authenticationService.generateTotpQR(emailId));
    }

    @Test
    void verifyTotp_validCode_returnsTrue() {
        String emailId = "test@example.com";
        int code = 123456;
        UserData user = new UserData();
        user.setMfaSecret("mfaSecret");
        when(userRepository.findByEmail(emailId)).thenReturn(Optional.of(user));
        when(googleAuthService.isValid(user.getMfaSecret(), code)).thenReturn(true);

        boolean result = authenticationService.verifyTotp(emailId, code);

        assertTrue(result);
    }

    @Test
    void verifyTotp_invalidCode_returnsFalse() {
        String emailId = "test@example.com";
        int code = 123456;
        UserData user = new UserData();
        user.setMfaSecret("mfaSecret");
        when(userRepository.findByEmail(emailId)).thenReturn(Optional.of(user));
        when(googleAuthService.isValid(user.getMfaSecret(), code)).thenReturn(false);

        boolean result = authenticationService.verifyTotp(emailId, code);

        assertFalse(result);
    }

    @Test
    void verifyTotp_userNotFound_throwsNoSuchElementException() {
        String emailId = "test@example.com";
        int code = 123456;
        when(userRepository.findByEmail(emailId)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> authenticationService.verifyTotp(emailId, code));
    }
}