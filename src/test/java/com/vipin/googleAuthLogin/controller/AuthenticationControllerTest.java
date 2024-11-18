package com.vipin.googleAuthLogin.controller;

import com.vipin.googleAuthLogin.service.implementation.AuthenticationService;
import com.vipin.googleAuthLogin.service.implementation.JwtService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AuthenticationControllerTest {

    @Mock
    private JwtService jwtService;

    @Mock
    private AuthenticationService authenticationService;

    @InjectMocks
    private AuthenticationController authenticationController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void generateQR_validEmailId_generatesQRImage() throws IOException {
        String emailId = "test@example.com";
        BufferedImage qrImage = new BufferedImage(100, 100, BufferedImage.TYPE_INT_RGB);
        when(authenticationService.generateTotpQR(emailId)).thenReturn(qrImage);

        MockHttpServletResponse response = new MockHttpServletResponse();
        authenticationController.generateQR(emailId, response);

        assertEquals(MediaType.IMAGE_PNG_VALUE, response.getContentType());
        assertEquals(200, response.getStatus());

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ImageIO.write(qrImage, "png", outputStream);
        assertArrayEquals(outputStream.toByteArray(), response.getContentAsByteArray());
    }

    @Test
    void generateQR_invalidEmailId_returnsInternalServerError() {
        String emailId = "invalid@example.com";
        when(authenticationService.generateTotpQR(emailId)).thenReturn(null);

        MockHttpServletResponse response = new MockHttpServletResponse();
        authenticationController.generateQR(emailId, response);

        assertEquals(500, response.getStatus());
    }

    @Test
    void generateQR_ioException_returnsInternalServerError() throws IOException {
        String emailId = "test@example.com";
        BufferedImage qrImage = new BufferedImage(100, 100, BufferedImage.TYPE_INT_RGB);
        when(authenticationService.generateTotpQR(emailId)).thenReturn(qrImage);

        MockHttpServletResponse response = spy(new MockHttpServletResponse());
        doThrow(new IOException("IO Exception")).when(response).getOutputStream();

        assertThrows(RuntimeException.class, () -> authenticationController.generateQR(emailId, response));
    }
}