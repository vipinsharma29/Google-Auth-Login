package com.vipin.googleAuthLogin.util.validator;

import com.vipin.googleAuthLogin.repository.UserRepository;
import com.vipin.googleAuthLogin.util.annotation.FieldValidation;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

public class FieldValidator implements ConstraintValidator<FieldValidation, String> {

    private static final Map<String, Set<String>> groupValues = new HashMap<>();
    private String group;

    @Autowired
    private UserRepository userRepository;

    @Override
    public void initialize(FieldValidation constraintAnnotation) {
        this.group = constraintAnnotation.group();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.isEmpty()) {
            return true;
        }
        return switch (group) {
            case "full_name" -> validateEmail(value);
            case "user_name" -> validateUsername(value);
            default -> true;
        };
    }

    private boolean validateUsername(String value) {
        return userRepository.findByUserName(value).isEmpty();
    }

    private boolean validateEmail(String value) {
        return userRepository.findByEmail(value).isEmpty();
    }
}
