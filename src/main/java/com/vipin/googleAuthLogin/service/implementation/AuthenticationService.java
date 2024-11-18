package com.vipin.googleAuthLogin.service.implementation;

import com.vipin.googleAuthLogin.dto.UserLoginDto;
import com.vipin.googleAuthLogin.dto.UserRegistrationDto;
import com.vipin.googleAuthLogin.model.UserData;
import com.vipin.googleAuthLogin.repository.UserRepository;
import com.vipin.googleAuthLogin.service.AuthenticationInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.awt.image.BufferedImage;
import java.util.NoSuchElementException;

@Service
public class AuthenticationService implements AuthenticationInterface {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private GoogleAuthService googleAuthService;

    @Override
    public void signup(UserRegistrationDto input) {
        try {
            UserData user = new UserData();
            user.setEmail(input.getEmail());
            user.setFullName(input.getFullName());
            user.setUserName(input.getUserName());
            user.setPassword(passwordEncoder.encode(input.getPassword()));
            user.setMfaSecret(googleAuthService.generateKey());
            user.setMfaEnabled(Boolean.TRUE);
            userRepository.save(user);
        } catch (Exception e) {
            throw new RuntimeException("Failed to register user");
        }
    }

    @Override
    public UserData authenticate(UserLoginDto input) {
        try {
            UserData user = userRepository.findByEmail(input.getEmail())
                    .orElseThrow();
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            input.getEmail(),
                            input.getPassword()));
            return user;
        } catch (NoSuchElementException e) {
            throw new NoSuchElementException("User not found");
        } catch (Exception e) {
            throw new RuntimeException("Invalid credentials");
        }
    }

    @Override
    public BufferedImage generateTotpQR(String emailId) {
        try {
            UserData user = userRepository.findByEmail(emailId).orElseThrow();
            return googleAuthService.generateQRImage(user.getMfaSecret(), emailId);
        } catch (NoSuchElementException e) {
            throw new NoSuchElementException("User not found");
        } catch (Exception e) {
            throw new RuntimeException("Failed to generate QR code");
        }
    }

    @Override
    public boolean verifyTotp(String emailId, int code) {
        try {
            UserData user = userRepository.findByEmail(emailId).orElseThrow();
            return googleAuthService.isValid(user.getMfaSecret(), code);
        } catch (NoSuchElementException e) {
            throw new NoSuchElementException("User not found");
        } catch (Exception e) {
            throw new RuntimeException("Failed to verify TOTP");
        }
    }
}
