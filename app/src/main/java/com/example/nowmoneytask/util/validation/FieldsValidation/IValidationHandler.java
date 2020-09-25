package com.example.nowmoneytask.util.validation.FieldsValidation;



import com.example.nowmoneytask.util.validation.error.ValidationError;

import java.util.List;



public interface IValidationHandler {


    void onValidationSuccess();
    void onValidationFailed(List<ValidationError> validationErrors);
}
