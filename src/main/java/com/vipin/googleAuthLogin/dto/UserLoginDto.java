package com.vipin.googleAuthLogin.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.vipin.googleAuthLogin.util.annotation.PasswordValidation;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserLoginDto {

    @Email
    @NotBlank(message = "Email is required")
    @JsonProperty(value = "email_id", index = 1, required = true)
    private String email;

    @PasswordValidation()
    @NotBlank(message = "Password is required")
    @JsonProperty(value = "password", index = 2, required = true)
    private String password;

}
