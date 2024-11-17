package com.vipin.googleAuthLogin.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.vipin.googleAuthLogin.util.annotation.FieldValidation;
import com.vipin.googleAuthLogin.util.annotation.PasswordValidation;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRegistrationDto {

    @Email
    @FieldValidation(group = "full_name", message = "Email already exists")
    @NotBlank(message = "Email is required")
    @JsonProperty(value = "email_id", index = 1, required = true)
    private String email;

    @NotBlank(message = "Password is required")
    @PasswordValidation()
    @JsonProperty(value = "password", index = 2, required = true)
    private String password;

    @NotBlank(message = "Full name is required")
    @JsonProperty(value = "full_name", index = 3, required = true)
    private String fullName;

    @NotBlank(message = "User name is required")
    @FieldValidation(group = "user_name", message = "Username already exists")
    @JsonProperty(value = "user_name", index = 4, required = true)
    private String userName;
}
