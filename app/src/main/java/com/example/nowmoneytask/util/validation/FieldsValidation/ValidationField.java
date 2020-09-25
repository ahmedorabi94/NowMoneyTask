package com.example.nowmoneytask.util.validation.FieldsValidation;


import com.example.nowmoneytask.util.validation.ValidationRules.IValidationRule;
import com.example.nowmoneytask.util.validation.error.IVewError;

public class ValidationField {


    public IVewError view;
    public IValidationRule[] validationRules;

    public ValidationField(IVewError view, IValidationRule[] validationRules) {

        this.view = view;
        this.validationRules = validationRules;
    }


}
