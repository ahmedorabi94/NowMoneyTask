package com.example.nowmoneytask.viewmodel;

import android.view.View;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.example.nowmoneytask.api.Resource;
import com.example.nowmoneytask.repository.AddDialogRepository;
import com.example.nowmoneytask.repository.db.User;
import com.example.nowmoneytask.repository.model.StatusResponse;
import com.example.nowmoneytask.repository.model.UserBody;
import com.example.nowmoneytask.util.AbsentLiveData;

import javax.inject.Inject;

public class AddDialogViewModel extends ViewModel {

    private LiveData<Resource<StatusResponse>> addResponseLiveData;
    private LiveData<Resource<StatusResponse>> deleteResponseLiveData;

    private final MutableLiveData<User> userbody = new MutableLiveData<>();
    private final MutableLiveData<String> id = new MutableLiveData<>();



    @Inject
    public AddDialogViewModel(AddDialogRepository repository){

        addResponseLiveData = Transformations.switchMap(userbody, input -> {
            if (input == null) {
                return AbsentLiveData.create();
            } else {
                return repository.addReceiver(input);
            }

        });


        deleteResponseLiveData = Transformations.switchMap(id, input -> {
            if (input == null) {
                return AbsentLiveData.create();
            } else {
                return repository.deleteReceiver(input);
            }

        });

    }

    public void setUser(User user){
        userbody.setValue(user);
    }

    public void setId(String val){
        id.setValue(val);
    }


    public LiveData<Resource<StatusResponse>> getAddResponseLiveData() {
        return addResponseLiveData;
    }

    public LiveData<Resource<StatusResponse>> getDeleteResponseLiveData() {
        return deleteResponseLiveData;
    }
}
