package com.example.nowmoneytask.repository;


import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.example.nowmoneytask.api.ApiInterface;
import com.example.nowmoneytask.api.ApiResponse;
import com.example.nowmoneytask.api.Resource;
import com.example.nowmoneytask.repository.db.TaskDao;
import com.example.nowmoneytask.repository.db.User;
import com.example.nowmoneytask.repository.model.StatusResponse;
import com.example.nowmoneytask.util.InstantAppExecutors;
import com.example.nowmoneytask.util.TestUtil;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.ArgumentCaptor;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static com.example.nowmoneytask.util.ApiUtil.successCall;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(JUnit4.class)
public class ReceiverRepositoryTest {

    private ApiInterface mApiInterface;
    private TaskDao mTaskDao;
    private ReceiverRepository repository;


    @Rule
    public InstantTaskExecutorRule instantExecutorRule = new InstantTaskExecutorRule();

    @Before
    public void setup() {
        mApiInterface = mock(ApiInterface.class);
        mTaskDao = mock(TaskDao.class);
        repository = new ReceiverRepository(mApiInterface, new InstantAppExecutors(), mTaskDao);
    }


    @Test
    public void loadAllUsers() {
        repository.getAllReceiversTest(true, "12345");
        verify(mTaskDao).getAllUsers();
    }


    @Test
    public void getFromApi() {

        MutableLiveData<List<User>> dbData = new MutableLiveData<>();
        when(mTaskDao.getAllUsers()).thenReturn(dbData);

        LiveData<Resource<List<User>>> data = repository.getAllReceiversTest(true, "123");

        verify(mTaskDao).getAllUsers();

        verify(mApiInterface, never()).getAllReceivers("123");


        List<User> users = TestUtil.createUsers(10, "ahmed", "1234", "cairo");

        LiveData<ApiResponse<List<User>>> call = successCall(users);

        when(mApiInterface.getAllReceivers("123"))
                .thenReturn(call);

        Observer observer = mock(Observer.class);
        data.observeForever(observer);


        verify(observer).onChanged(Resource.loading(null));

        MutableLiveData<List<User>> updatedDbData = new MutableLiveData<>();
        when(mTaskDao.getAllUsers()).thenReturn(updatedDbData);
        dbData.setValue(Collections.emptyList());

        verify(mApiInterface).getAllReceivers("123");
        ArgumentCaptor<List<User>> inserted = ArgumentCaptor.forClass((Class) List.class);
        verify(mTaskDao).insertAll(inserted.capture());

        assertThat(inserted.getValue().size(), is(10));
        User first = inserted.getValue().get(0);
        assertThat(first.getName(), is("ahmed 0"));
        assertThat(first.getAddress(), is("cairo 0"));

        updatedDbData.setValue(users);
        verify(observer).onChanged(Resource.success(users));


    }


    @Test
    public void testAddReceiver() {

        // parameters
        User userBody = TestUtil.createUserBody("ahmed", "888", "Egypt");
        String token = "123";


        // output
        StatusResponse statusResponse = TestUtil.createStatusResponse("Ok");
        LiveData<ApiResponse<StatusResponse>> call = successCall(statusResponse);


        when(mApiInterface.addReceiver(token, userBody)).thenReturn(call);


        LiveData<Resource<StatusResponse>> data = repository.addReceiver(userBody, token);


        Observer<Resource<StatusResponse>> observer = mock(Observer.class);
        data.observeForever(observer);


        verify(mApiInterface).addReceiver(token, userBody);


        verify(mApiInterface, times(1)).addReceiver(token, userBody);


        StatusResponse response = Objects.requireNonNull(data.getValue()).data;

        assert response != null;
        assertThat(response.getStatus(), is("Ok"));


    }

    @Test
    public void testDeleteApi() {

        String id = "7890";
        String token = "123";

        // output
        StatusResponse statusResponse = TestUtil.createStatusResponse("Ok");
        LiveData<ApiResponse<StatusResponse>> call = successCall(statusResponse);

        when(mApiInterface.deleteReceiver(token, id)).thenReturn(call);


        LiveData<Resource<StatusResponse>> data = repository.deleteReceiver(id, token);

        Observer<Resource<StatusResponse>> observer = mock(Observer.class);
        data.observeForever(observer);


        verify(mApiInterface).deleteReceiver(token, "7890");


        StatusResponse response = Objects.requireNonNull(data.getValue()).data;

        assertThat(response.getStatus(), is("Ok"));


    }


}
