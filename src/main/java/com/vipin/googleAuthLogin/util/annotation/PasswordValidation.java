package com.vipin.googleAuthLogin.util.annotation;

import com.vipin.googleAuthLogin.util.validator.PasswordValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PasswordValidator.class)
public @interface PasswordValidation {

    String message() default "Password must contain at least 1 uppercase, 1 lowercase, 1 digit, 1 special character, and minimum 8 characters";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}