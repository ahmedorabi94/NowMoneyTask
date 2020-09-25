package com.example.nowmoneytask.util.validation.FieldsValidation;

import android.content.Context;
import android.util.Log;


import com.example.nowmoneytask.util.validation.ValidationRules.IValidationRule;
import com.example.nowmoneytask.util.validation.error.IVewError;
import com.example.nowmoneytask.util.validation.error.ValidationError;

import java.util.ArrayList;
import java.util.List;



public class Validation {


    private  final String TAG = "Validation";
    private List<ValidationField> validationFields = new ArrayList<>();

    private ArrayList<ValidationError> validationErrors;


    public Validation() {
    }

    public Validation(List<ValidationField> validationFields) {
        this.validationFields = validationFields;
    }



    public Validation addValidationField(IVewError vewError , IValidationRule[] rules){

        validationFields.add( new ValidationField( vewError , rules));

        return this;
    }


    public Validation addValidationField(ValidationField validationField){

        validationFields.add(validationField);

        return this;
    }

    public void validate(Context context , IValidationHandler iValidationHandler){

        int errorNo = 0;
        validationErrors = new ArrayList<>();

        for(int i = 0 ; i < validationFields.size() ; i++ ){

            ValidationField validationField =  validationFields.get(i);

            IValidationRule[] rules = validationField.validationRules;

            String error = "";
            for(int j = 0 ; j < rules.length ; j++){
                 String mess = rules[j].validate(context);
                Log.e(TAG , "error : " + mess);
                if(mess != null) {
                    errorNo++;
                    error = mess;
//                    error += mess + "\n";
                    break;
                }
            }

            if(!error.equals(""))
                validationErrors.add(new ValidationError(error , validationField.view));
            else
                validationErrors.add(new ValidationError(null , validationField.view));
        }


        if (errorNo > 0) {
            iValidationHandler.onValidationFailed(validationErrors);
        }
        else {
            resetErrors();
            iValidationHandler.onValidationSuccess();
        }

    }

    private void resetErrors() {

        for(int i = 0; i < validationErrors.size() ; i++){
            validationErrors.get(i).displayError();
        }
    }

}
