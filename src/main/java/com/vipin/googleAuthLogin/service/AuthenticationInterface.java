package com.vipin.googleAuthLogin.service;

import com.vipin.googleAuthLogin.dto.UserLoginDto;
import com.vipin.googleAuthLogin.dto.UserRegistrationDto;
import com.vipin.googleAuthLogin.model.UserData;

import java.awt.image.BufferedImage;

public interface AuthenticationInterface {

    void signup(UserRegistrationDto input);

    UserData authenticate(UserLoginDto input);

    BufferedImage generateTotpQR(String emailId);

    boolean verifyTotp(String emailId, int code);
}
