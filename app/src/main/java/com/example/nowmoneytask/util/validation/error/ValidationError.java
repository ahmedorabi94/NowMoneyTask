package com.example.nowmoneytask.util.validation.error;


public class ValidationError {

    String message;
    IVewError vewError;

    public ValidationError(String message, IVewError vewError) {
        this.message = message;
        this.vewError = vewError;
    }

    public void displayError() {

        vewError.setError(message);
    }
}