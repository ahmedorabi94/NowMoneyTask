package com.example.nowmoneytask.util.validation.ValidationViews;


import android.widget.EditText;

import com.example.nowmoneytask.util.validation.error.IVewError;


public class ViewFieldEditText implements IVewError {


    private EditText editText;

    public ViewFieldEditText(EditText editText) {
        this.editText = editText;
    }

    @Override
    public void setError(String message) {
        editText.setError(message);
    }
}
