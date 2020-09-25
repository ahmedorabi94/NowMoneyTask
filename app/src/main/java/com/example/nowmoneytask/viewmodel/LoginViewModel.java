package com.example.nowmoneytask.viewmodel;

import android.util.Log;

import androidx.annotation.VisibleForTesting;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.example.nowmoneytask.api.Resource;
import com.example.nowmoneytask.repository.LoginRepository;
import com.example.nowmoneytask.repository.model.LoginResponse;
import com.example.nowmoneytask.repository.model.UserBody;
import com.example.nowmoneytask.util.AbsentLiveData;

import javax.inject.Inject;

public class LoginViewModel extends ViewModel {

    @VisibleForTesting
    private LiveData<Resource<LoginResponse>> loginResponse;
    private final MutableLiveData<UserBody> userbody = new MutableLiveData<>();


    @Inject
    public LoginViewModel(LoginRepository repository) {

        loginResponse = Transformations.switchMap(userbody, input -> {
            if (input == null) {
                return AbsentLiveData.create();
            } else {
                return repository.login(input);
            }

        });

    }

    @VisibleForTesting
    public void setUserBody(UserBody body) {
//        Log.e("ViewModel","setUserBody");
        userbody.setValue(body);

    }

    @VisibleForTesting
    public LiveData<Resource<LoginResponse>> getLoginResponse() {
        return loginResponse;
    }
}
