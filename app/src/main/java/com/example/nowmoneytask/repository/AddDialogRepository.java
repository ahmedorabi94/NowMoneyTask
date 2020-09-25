package com.example.nowmoneytask.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.nowmoneytask.AppExecutors;
import com.example.nowmoneytask.api.ApiInterface;
import com.example.nowmoneytask.api.ApiResponse;
import com.example.nowmoneytask.api.NetworkBoundResourceTest;
import com.example.nowmoneytask.api.Resource;
import com.example.nowmoneytask.repository.db.TaskDao;
import com.example.nowmoneytask.repository.db.User;
import com.example.nowmoneytask.repository.model.StatusResponse;
import com.example.nowmoneytask.ui.LoginFragment;
import com.example.nowmoneytask.util.SessionManager;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class AddDialogRepository {

    private ApiInterface mApiInterface;
    private final AppExecutors appExecutors;
    private TaskDao mTaskDao;
    private Application application;

    @Inject
    AddDialogRepository(ApiInterface apiInterface, AppExecutors appExecutors, TaskDao taskDao, Application application) {
        this.mApiInterface = apiInterface;
        this.appExecutors = appExecutors;
        this.mTaskDao = taskDao;
        this.application = application;
    }


    public LiveData<Resource<StatusResponse>> addReceiver(User userBody) {

        SessionManager sessionManager = new SessionManager(application);

        String token = sessionManager.getUSerDetails().get(SessionManager.KEY_TOKEN);



        return new NetworkBoundResourceTest<StatusResponse, StatusResponse>() {

            @Override
            protected LiveData<ApiResponse<StatusResponse>> createCall() {
                return mApiInterface.addReceiver(token, userBody);
            }
        }.getAsLiveData();

    }

    public LiveData<Resource<StatusResponse>> deleteReceiver(String id) {

        SessionManager sessionManager = new SessionManager(application);

        String token = sessionManager.getUSerDetails().get(SessionManager.KEY_TOKEN);


        return new NetworkBoundResourceTest<StatusResponse, StatusResponse>() {

            @Override
            protected LiveData<ApiResponse<StatusResponse>> createCall() {
                return mApiInterface.deleteReceiver(token, id);
            }
        }.getAsLiveData();

    }


}
