package com.vipin.googleAuthLogin.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginResponse {

    @JsonProperty(value = "token", index = 1)
    private String token;
}