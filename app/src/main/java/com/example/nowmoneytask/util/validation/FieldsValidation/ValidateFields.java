package com.example.nowmoneytask.util.validation.FieldsValidation;

import android.content.Context;

import java.util.List;


public class ValidateFields {


    private Validation validation = new Validation();

    public ValidateFields(IFieldValidation[] iFieldValidations) {

        for (IFieldValidation iFieldValidation : iFieldValidations ){
            validation.addValidationField(iFieldValidation.getValidationField());
        }

    }

    public ValidateFields(List<IFieldValidation> iFieldValidations) {

        for (IFieldValidation iFieldValidation : iFieldValidations ){
            validation.addValidationField(iFieldValidation.getValidationField());
        }

    }

    public void validate(Context context, IValidationHandler iValidationHandler) {
        validation.validate(context ,iValidationHandler);
    }
}
