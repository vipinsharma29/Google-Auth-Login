package com.vipin.googleAuthLogin.controller;

import com.vipin.googleAuthLogin.dto.*;
import com.vipin.googleAuthLogin.model.UserData;
import com.vipin.googleAuthLogin.service.AuthenticationService;
import com.vipin.googleAuthLogin.service.JwtService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("/signup")
    public ResponseEntity<?> register(@RequestBody @Valid UserRegistrationDto registerUserDto) {
        authenticationService.signup(registerUserDto);
        return ResponseEntity.created(null).build();
    }

    @GetMapping("/generate-qr")
    public void generateQR(@Valid @NotBlank @Email @RequestParam String emailId, HttpServletResponse response) {
        BufferedImage qrImage = authenticationService.generateTotpQR(emailId);
        if (qrImage != null) {
            try {
                response.setContentType(MediaType.IMAGE_PNG_VALUE);
                OutputStream outputStream = response.getOutputStream();
                ImageIO.write(qrImage, "png", outputStream);
                outputStream.flush();
                outputStream.close();
            } catch (IOException e) {
                throw new RuntimeException("Failed to generate QR code");
            }
        }
        response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> authenticate(@RequestBody UserLoginDto loginUserDto) {
        UserData authenticatedUser = authenticationService.authenticate(loginUserDto);
        return ResponseEntity.ok(new LoginResponse(jwtService.generateToken(authenticatedUser)));
    }

    @PostMapping("/verifyTotp")
    public ResponseEntity<?> verifyTwoFactor(@RequestBody User2fDto verifyTotpRequest) {
        MfaVerificationResponse mfaVerificationResponse = MfaVerificationResponse.builder()
                .username(verifyTotpRequest.getEmailId())
                .tokenValid(Boolean.FALSE)
                .message("Invalid OTP provided.")
                .build();

        if (authenticationService.verifyTotp(verifyTotpRequest.getEmailId(), verifyTotpRequest.getValidationOtp())) {
            mfaVerificationResponse = MfaVerificationResponse.builder()
                    .username(verifyTotpRequest.getEmailId())
                    .tokenValid(Boolean.TRUE)
                    .authValid(Boolean.TRUE)
                    .jwt(verifyTotpRequest.getToken())
                    .message("Provided OTP is valid.")
                    .build();
        }

        return ResponseEntity.ok(mfaVerificationResponse);
    }
}