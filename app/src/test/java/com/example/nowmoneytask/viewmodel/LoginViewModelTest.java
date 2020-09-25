package com.example.nowmoneytask.viewmodel;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.example.nowmoneytask.api.Resource;
import com.example.nowmoneytask.repository.LoginRepository;
import com.example.nowmoneytask.repository.model.LoginResponse;
import com.example.nowmoneytask.repository.model.UserBody;
import com.example.nowmoneytask.util.TestUtil;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.ArgumentCaptor;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SuppressWarnings("unchecked")
@RunWith(JUnit4.class)
public class LoginViewModelTest {

    @Rule
    public InstantTaskExecutorRule instantExecutorRule = new InstantTaskExecutorRule();

    private LoginViewModel loginViewModel;
    private LoginRepository loginRepository;

    @Before
    public void setup() {
        loginRepository = mock(LoginRepository.class);
        loginViewModel = new LoginViewModel(loginRepository);
    }

    @Test
    public void testNull() {
        assertThat(loginViewModel.getLoginResponse(), notNullValue());
        verify(loginRepository, never()).login(any());
        loginViewModel.setUserBody(new UserBody("ahmed", "123"));
        verify(loginRepository, never()).login(any());
    }

    @Test
    public void loadLoginResponse() {
        ArgumentCaptor<UserBody> captor = ArgumentCaptor.forClass(UserBody.class);

        loginViewModel.getLoginResponse().observeForever(mock(Observer.class));

        loginViewModel.setUserBody(new UserBody("ahmed", "123"));
        verify(loginRepository).login(captor.capture());
        assertThat(captor.getValue().getUsername(), is("ahmed"));

        reset(loginRepository);

        loginViewModel.setUserBody(new UserBody("salem", "123777"));
        verify(loginRepository).login(captor.capture());
        assertThat(captor.getValue().getPassword(), is("123777"));
    }

    @Test
    public void sendResultToUI() {
        MutableLiveData<Resource<LoginResponse>> ahmed = new MutableLiveData<>();
        MutableLiveData<Resource<LoginResponse>> salem = new MutableLiveData<>();

        when(loginRepository.login(new UserBody("ahmed", "123"))).thenReturn(ahmed);
        when(loginRepository.login(new UserBody("salem", "123777"))).thenReturn(salem);
        Observer observer = mock(Observer.class);
        ahmed.observeForever(observer);
        salem.observeForever(observer);

        loginViewModel.getLoginResponse().observeForever(observer);
        loginViewModel.setUserBody((new UserBody("ahmed", "123")));

        verify(observer, never()).onChanged(any(Resource.class));


        LoginResponse loginResponse = TestUtil.createLoginResponse("ahmed");
        Resource<LoginResponse> ahmedResponse = Resource.success(loginResponse);

        ahmed.setValue(ahmedResponse);
        verify(observer).onChanged(ahmedResponse);


        reset(observer);

        LoginResponse salemRes = TestUtil.createLoginResponse("salem");
        Resource<LoginResponse> salemResponse = Resource.success(salemRes);
        salem.setValue(salemResponse);
        loginViewModel.setUserBody(new UserBody("salem", "123777"));
        verify(observer).onChanged(salemResponse);
    }


}