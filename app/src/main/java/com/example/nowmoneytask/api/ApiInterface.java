package com.example.nowmoneytask.api;

import androidx.lifecycle.LiveData;

import com.example.nowmoneytask.repository.db.User;
import com.example.nowmoneytask.repository.model.LoginResponse;
import com.example.nowmoneytask.repository.model.StatusResponse;
import com.example.nowmoneytask.repository.model.UserBody;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiInterface {

    String BASE_URL = "https://nowmoney-test.herokuapp.com/";


    @POST("api/v1/users/login")
    LiveData<ApiResponse<LoginResponse>> login(@Body UserBody userBody);


    @GET("api/v1/receivers")
    LiveData<ApiResponse<List<User>>> getAllReceivers(@Header("auth") String auth);


    @POST("api/v1/receivers/add")
    LiveData<ApiResponse<StatusResponse>> addReceiver(@Header("auth") String auth , @Body User user);

    @POST("api/v1/receivers/{id}/delete")
    LiveData<ApiResponse<StatusResponse>> deleteReceiver(@Header("auth") String auth, @Path("id") String id);


}
