package com.example.nowmoneytask.util.validation.ValidationRules;

import android.content.Context;
import android.text.TextUtils;



public class Required implements IValidationRule {
    private final String msg;
    private boolean mBooleanValue;
    private String value;
    private int length;
    private int lastIndex;


    public Required(String value, String msg, int len, int lastIndex) {
        this.value = value;
        this.msg = msg;
        this.length = len;
        this.lastIndex = lastIndex;
    }

    @Override
    public String validate(Context context) {

       // Timber.e("value : %s", value);



        if (lastIndex == 0) {
            return value != null && !TextUtils.isEmpty(value) && value.trim().length() >= length ? null : msg;
        } else {
            return value != null && !TextUtils.isEmpty(value) && value.trim().length() >= length && value.trim().length() <= lastIndex ? null : msg;
        }


        //   return value != null && !TextUtils.isEmpty(value) && value.trim().length() >= length ? null : msg;
    }


}
