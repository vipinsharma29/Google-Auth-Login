package com.vipin.googleAuthLogin.exception.validationException;

import lombok.Getter;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Getter
public class CustomValidationException extends RuntimeException implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private final List<String> errorMessages;

    public CustomValidationException(List<String> errorMessages) {
        super("Multiple validation errors occurred");
        this.errorMessages = errorMessages;
    }

    public CustomValidationException(String message, List<String> errorMessages) {
        super(message);
        this.errorMessages = errorMessages;
    }

    public CustomValidationException(String message, Throwable cause, List<String> errorMessages) {
        super(message, cause);
        this.errorMessages = errorMessages;
    }

    public CustomValidationException(Throwable cause, List<String> errorMessages) {
        super(cause);
        this.errorMessages = errorMessages;
    }

    public CustomValidationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, List<String> errorMessages) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.errorMessages = errorMessages;
    }
}
