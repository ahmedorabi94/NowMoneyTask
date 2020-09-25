package com.example.nowmoneytask.repository;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;

import com.example.nowmoneytask.AppExecutors;
import com.example.nowmoneytask.api.ApiInterface;
import com.example.nowmoneytask.api.ApiResponse;
import com.example.nowmoneytask.api.NetworkBoundResource;
import com.example.nowmoneytask.api.NetworkBoundResourceTest;
import com.example.nowmoneytask.api.Resource;
import com.example.nowmoneytask.repository.db.TaskDao;
import com.example.nowmoneytask.repository.db.User;
import com.example.nowmoneytask.repository.model.StatusResponse;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class ReceiverRepository {


    private ApiInterface mApiInterface;
    private final AppExecutors appExecutors;
    private TaskDao mTaskDao;


    @Inject
    ReceiverRepository(ApiInterface apiInterface, AppExecutors appExecutors, TaskDao taskDao) {
        this.mApiInterface = apiInterface;
        this.appExecutors = appExecutors;
        this.mTaskDao = taskDao;
    }


    public LiveData<Resource<List<User>>> getAllReceiversTest(boolean update, String token) {


        return new NetworkBoundResource<List<User>, List<User>>(appExecutors) {

            @Override
            protected LiveData<ApiResponse<List<User>>> createCall() {
                return mApiInterface.getAllReceivers(token);
            }

            @Override
            protected void saveCallResult(@NonNull List<User> item) {

                mTaskDao.insertAll(item);

            }

            @Override
            protected boolean shouldFetch(@Nullable List<User> data) {
                if (update) {
                    appExecutors.diskIO().execute(() -> mTaskDao.deleteAllUsers());
                    return true;
                }

                return data == null || data.size() == 0;
            }

            @NonNull
            @Override
            protected LiveData<List<User>> loadFromDb() {
                return mTaskDao.getAllUsers();
            }
        }.getAsLiveData();


    }

    public LiveData<Resource<StatusResponse>> addReceiver(User userBody, String token) {


        return new NetworkBoundResourceTest<StatusResponse, StatusResponse>() {

            @Override
            protected LiveData<ApiResponse<StatusResponse>> createCall() {
                return mApiInterface.addReceiver(token, userBody);
            }
        }.getAsLiveData();

    }

    public LiveData<Resource<StatusResponse>> deleteReceiver(String id, String token) {


        return new NetworkBoundResourceTest<StatusResponse, StatusResponse>() {

            @Override
            protected LiveData<ApiResponse<StatusResponse>> createCall() {
                return mApiInterface.deleteReceiver(token, id);
            }
        }.getAsLiveData();

    }

}
