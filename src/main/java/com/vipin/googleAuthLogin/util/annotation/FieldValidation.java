package com.vipin.googleAuthLogin.util.annotation;

import com.vipin.googleAuthLogin.util.validator.FieldValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = FieldValidator.class)
public @interface FieldValidation {

    String message() default "Validation failed";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String group() default "";
}
