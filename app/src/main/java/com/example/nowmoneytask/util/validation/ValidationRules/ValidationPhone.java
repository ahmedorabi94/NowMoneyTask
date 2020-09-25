package com.example.nowmoneytask.util.validation.ValidationRules;

import android.content.Context;

import com.example.nowmoneytask.R;
import com.example.nowmoneytask.util.validation.FieldsValidation.IFieldValidation;
import com.example.nowmoneytask.util.validation.FieldsValidation.ValidationField;
import com.example.nowmoneytask.util.validation.error.IVewError;


public class ValidationPhone  implements IFieldValidation {


    private final ValidationField validationField;

    public ValidationPhone(Context context , IVewError view, String value , String msg , int length , int lastLength) {

        IValidationRule[] iValidationRules =   new IValidationRule[]{
                new Required(value, msg , length , lastLength),
                new Mobile(value,context.getString(R.string.appbar_scrolling_view_behavior))
        };

        validationField =  new ValidationField(view , iValidationRules);
    }

    @Override
    public ValidationField getValidationField() {
        return validationField;
    }
}
