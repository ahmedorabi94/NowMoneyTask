package com.example.nowmoneytask.repository;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import com.example.nowmoneytask.api.ApiInterface;
import com.example.nowmoneytask.api.ApiResponse;
import com.example.nowmoneytask.api.Resource;
import com.example.nowmoneytask.repository.model.LoginResponse;
import com.example.nowmoneytask.repository.model.UserBody;
import com.example.nowmoneytask.util.InstantAppExecutors;
import com.example.nowmoneytask.util.TestUtil;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.Objects;

import static com.example.nowmoneytask.util.ApiUtil.successCall;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(JUnit4.class)
public class LoginRepositoryTest {


    private ApiInterface mApiInterface;
    private LoginRepository repository;


    @Rule
    public InstantTaskExecutorRule instantExecutorRule = new InstantTaskExecutorRule();

    @Before
    public void setup() {
        mApiInterface = mock(ApiInterface.class);
        repository = new LoginRepository(mApiInterface, new InstantAppExecutors());
    }


    @Test
    public void testLogin() {

        UserBody userBody = new UserBody("ahmed", "AhmeD123");


        LoginResponse loginResponse = TestUtil.createLoginResponse("123456");
        LiveData<ApiResponse<LoginResponse>> call = successCall(loginResponse);

        when(mApiInterface.login(userBody)).thenReturn(call);

        LiveData<Resource<LoginResponse>> data = repository.login(userBody);

        Observer<Resource<LoginResponse>> observer = mock(Observer.class);
        data.observeForever(observer);

        verify(mApiInterface).login(userBody);


        LoginResponse response = Objects.requireNonNull(data.getValue()).data;

        assert response != null;
        assertThat(response.getToken(), is("123456"));


    }

}