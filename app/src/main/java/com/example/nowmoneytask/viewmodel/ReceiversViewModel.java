package com.example.nowmoneytask.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.example.nowmoneytask.api.Resource;
import com.example.nowmoneytask.repository.ReceiverRepository;
import com.example.nowmoneytask.repository.db.User;
import com.example.nowmoneytask.repository.model.StatusResponse;
import com.example.nowmoneytask.util.AbsentLiveData;

import java.util.List;

import javax.inject.Inject;

public class ReceiversViewModel extends ViewModel {

    private LiveData<Resource<StatusResponse>> addResponseLiveData;
    private LiveData<Resource<StatusResponse>> deleteResponseLiveData;

    private final MutableLiveData<User> userbody = new MutableLiveData<>();
    private final MutableLiveData<String> id = new MutableLiveData<>();
    private final MutableLiveData<String> token = new MutableLiveData<>();

    private LiveData<Resource<List<User>>> allReceivers;
    private MutableLiveData<Boolean> _forceUpdate = new MutableLiveData<>(false);


    @Inject
    public ReceiversViewModel(ReceiverRepository repository) {

        allReceivers = Transformations.switchMap(_forceUpdate,input -> repository.getAllReceiversTest(input,token.getValue()));


        addResponseLiveData = Transformations.switchMap(userbody, input -> {
            if (input == null) {
                return AbsentLiveData.create();
            } else {
                return repository.addReceiver(input,token.getValue());
            }

        });


        deleteResponseLiveData = Transformations.switchMap(id, input -> {
            if (input == null) {
                return AbsentLiveData.create();
            } else {
                return repository.deleteReceiver(input,token.getValue());
            }

        });

    }

    public void setForceUpdate(Boolean forceUpdate) {
        _forceUpdate.setValue(forceUpdate);
    }

    public void setUser(User user) {
        userbody.setValue(user);
    }

    public void setId(String val) {
        id.setValue(val);
    }

    public void setToken(String val){
        this.token.setValue(val);
    }


    public LiveData<Resource<StatusResponse>> getAddResponseLiveData() {
        return addResponseLiveData;
    }

    public LiveData<Resource<StatusResponse>> getDeleteResponseLiveData() {
        return deleteResponseLiveData;
    }


    public LiveData<Resource<List<User>>> getAllReceivers() {
        return allReceivers;
    }
}
