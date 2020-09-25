package com.example.nowmoneytask.repository;

import androidx.lifecycle.LiveData;

import com.example.nowmoneytask.AppExecutors;
import com.example.nowmoneytask.api.ApiInterface;
import com.example.nowmoneytask.api.ApiResponse;
import com.example.nowmoneytask.api.NetworkBoundResourceTest;
import com.example.nowmoneytask.api.Resource;
import com.example.nowmoneytask.repository.model.LoginResponse;
import com.example.nowmoneytask.repository.model.UserBody;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class LoginRepository {


    private ApiInterface mApiInterface;
    private final AppExecutors appExecutors;


    @Inject
    LoginRepository(ApiInterface apiInterface, AppExecutors appExecutors) {
        this.mApiInterface = apiInterface;
        this.appExecutors = appExecutors;
    }


    public LiveData<Resource<LoginResponse>> login(UserBody userBody) {


        return new NetworkBoundResourceTest<LoginResponse, LoginResponse>() {

            @Override
            protected LiveData<ApiResponse<LoginResponse>> createCall() {
                return mApiInterface.login(userBody);
            }
        }.getAsLiveData();

    }


}
