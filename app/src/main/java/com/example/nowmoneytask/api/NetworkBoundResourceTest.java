package com.example.nowmoneytask.api;

import androidx.annotation.MainThread;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;

public abstract class NetworkBoundResourceTest<ResultType, RequestType> {

    private final MediatorLiveData<Resource<ResultType>> result = new MediatorLiveData<>();


    @MainThread
    public NetworkBoundResourceTest() {

        fetchFromNetwork();

    }


    private void fetchFromNetwork() {

        setValue(Resource.loading(null));

        final LiveData<ApiResponse<RequestType>> apiResponse = createCall();


        result.addSource(apiResponse, response -> {

            if (response.isSuccessful()){
                setValue(Resource.success(((ApiResponse<ResultType>)response).body));

            }else {
                setValue(Resource.error(response.errorMessage,null));
            }


        });


    }


    @MainThread
    private void setValue(Resource<ResultType> newValue) {
        if (result.getValue() != newValue) {
            result.setValue(newValue);
        }
    }


    // Called to create the API call.
    protected abstract LiveData<ApiResponse<RequestType>> createCall();


    // Called when the fetch fails. The child class may want to reset components
    // like rate limiter.
    @MainThread
    protected void onFetchFailed() {

    }


    // Returns a LiveData object that represents the resource that's implemented
    // in the base class.
    public final LiveData<Resource<ResultType>> getAsLiveData() {
        return this.result;
    }

}