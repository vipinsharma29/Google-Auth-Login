package com.vipin.googleAuthLogin.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class User2fDto {

    @NotBlank
    @Email
    @JsonProperty(value = "email_id", index = 1, required = true)
    private String emailId;

    @Min(6)
    @JsonProperty(value = "login_otp", index = 2, required = true)
    private int validationOtp;

    @JsonProperty(value = "token", index = 3, required = true)
    private String token;
}
