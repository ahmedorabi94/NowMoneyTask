package com.example.nowmoneytask.util.validation.ValidationViews;

import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;

import com.example.nowmoneytask.R;
import com.example.nowmoneytask.util.validation.error.IVewError;


public class VErrorFieldTV implements IVewError {


    private TextView textView;
    private IErrorMessageHandler messageHandler;

    public VErrorFieldTV(TextView textView) {
        this.textView = textView;
    }

    public VErrorFieldTV(TextView textView, IErrorMessageHandler messageHandler) {
        this.textView = textView;
        this.messageHandler = messageHandler;
    }

    @Override
    public void setError(String message) {


        textView.setText(message);

        if (message != null) {
            Toast.makeText(textView.getContext(), message, Toast.LENGTH_SHORT).show();

        }

        if (messageHandler != null)
            messageHandler.setErrorToModel(message);
        else {
            textView.setTextColor(ContextCompat.getColor(textView.getContext(), R.color.design_default_color_background));
            textView.setVisibility(message == null ? View.GONE : View.VISIBLE);
        }
    }


    public interface IErrorMessageHandler {

        void setErrorToModel(String message);
    }
}
