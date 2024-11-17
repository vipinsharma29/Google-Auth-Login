package com.vipin.googleAuthLogin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@SpringBootApplication
public class GoogleAuthLoginApplication {

    public static void main(String[] args) {
        SpringApplication.run(GoogleAuthLoginApplication.class, args);
    }

}
