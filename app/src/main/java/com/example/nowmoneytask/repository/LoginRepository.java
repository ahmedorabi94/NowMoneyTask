package com.example.nowmoneytask.repository;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;

import com.example.nowmoneytask.AppExecutors;
import com.example.nowmoneytask.api.ApiInterface;
import com.example.nowmoneytask.api.ApiResponse;
import com.example.nowmoneytask.api.NetworkBoundResource;
import com.example.nowmoneytask.api.NetworkBoundResourceTest;
import com.example.nowmoneytask.api.Resource;
import com.example.nowmoneytask.repository.model.LoginResponse;
import com.example.nowmoneytask.repository.model.UserBody;

import javax.inject.Inject;
import javax.inject.Singleton;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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

     //   Log.e("Repppppp","Call Login");



//        return new NetworkBoundResource<LoginResponse, LoginResponse>(appExecutors) {
//
//            @Override
//            protected LiveData<ApiResponse<LoginResponse>> createCall() {
//                return mApiInterface.login(userBody);
//            }
//
//            @Override
//            protected void saveCallResult(@NonNull LoginResponse item) {
//
//            }
//
//            @Override
//            protected boolean shouldFetch(@Nullable LoginResponse data) {
//                return true;
//            }
//
//            @NonNull
//            @Override
//            protected LiveData<LoginResponse> loadFromDb() {
//                return null;
//            }
//        }.getAsLiveData();




        return new NetworkBoundResourceTest<LoginResponse,LoginResponse>(){

            @Override
            protected LiveData<ApiResponse<LoginResponse>> createCall() {
                return mApiInterface.login(userBody);
            }
        }.getAsLiveData();

//        mApiInterface.loginTest(userBody).enqueue(new Callback<LoginResponse>() {
//            @Override
//            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
//
//              //  Log.e("Repppppp",response.body().getToken());
//
//            }
//
//            @Override
//            public void onFailure(Call<LoginResponse> call, Throwable t) {
//                Log.e("Repppppp",t.getMessage());
//
//            }
//        });

    }




}
