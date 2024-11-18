package com.vipin.googleAuthLogin.service;

import com.vipin.googleAuthLogin.service.implementation.GoogleAuthService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import java.awt.image.BufferedImage;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GoogleAuthServiceTest {

    @InjectMocks
    private GoogleAuthService googleAuthService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void generateQRImage_validText_returnsBufferedImage() {
        String qrCodeText = "testQRCodeText";
        BufferedImage result = GoogleAuthService.generateQRImage(qrCodeText);
        assertNotNull(result);
    }

    @Test
    void generateQRImage_invalidText_throwsRuntimeException() {
        String qrCodeText = "";
        assertThrows(RuntimeException.class, () -> GoogleAuthService.generateQRImage(qrCodeText));
    }

    @Test
    void generateKey_returnsNonEmptyString() {
        String key = googleAuthService.generateKey();
        assertNotNull(key);
        assertFalse(key.isEmpty());
    }

    @Test
    void isValid_validCode_returnsTrue() {
        String secret = "testSecret";
        int code = 123456;
        GoogleAuthService spyService = spy(googleAuthService);
        doReturn(true).when(spyService).isValid(secret, code);
        assertTrue(spyService.isValid(secret, code));
    }

    @Test
    void isValid_invalidCode_returnsFalse() {
        String secret = "testSecret";
        int code = 123456;
        GoogleAuthService spyService = spy(googleAuthService);
        doReturn(false).when(spyService).isValid(secret, code);
        assertFalse(spyService.isValid(secret, code));
    }

    @Test
    void generateQRImage_validSecretAndEmail_returnsBufferedImage() {
        String secret = "testSecret";
        String emailId = "test@example.com";
        BufferedImage result = googleAuthService.generateQRImage(secret, emailId);
        assertNotNull(result);
    }
}