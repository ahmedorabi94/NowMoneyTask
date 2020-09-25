package com.example.nowmoneytask.viewmodel;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.Observer;

import com.example.nowmoneytask.repository.ReceiverRepository;
import com.example.nowmoneytask.repository.db.User;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.ArgumentCaptor;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SuppressWarnings("unchecked")
@RunWith(JUnit4.class)
public class ReceiversViewModelTest {

    @Rule
    public InstantTaskExecutorRule instantExecutorRule = new InstantTaskExecutorRule();

    private ReceiversViewModel viewModel;
    private ReceiverRepository repository;

    @Before
    public void setup() {
        repository = mock(ReceiverRepository.class);
        viewModel = new ReceiversViewModel(repository);
    }


    @Test
    public void testNull() {
        assertThat(viewModel.getAddResponseLiveData(), notNullValue());
        assertThat(viewModel.getDeleteResponseLiveData(), notNullValue());
        assertThat(viewModel.getAllReceivers(), notNullValue());
        verify(repository, never()).deleteReceiver(anyString(), anyString());
    }

    @Test
    public void testDelete() {

        ArgumentCaptor<String> first = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> second = ArgumentCaptor.forClass(String.class);


        viewModel.setId("1234");
        viewModel.getDeleteResponseLiveData().observeForever(mock(Observer.class));

        verify(repository, times(1)).deleteReceiver(first.capture(),
                second.capture());

        assertThat(first.getValue(), is("1234"));


    }

    @Test
    public void testAdd() {

        ArgumentCaptor<User> first = ArgumentCaptor.forClass(User.class);
        ArgumentCaptor<String> second = ArgumentCaptor.forClass(String.class);


        viewModel.setUser(new User("Ahmed", "123456", "Cairo"));
        viewModel.getAddResponseLiveData().observeForever(mock(Observer.class));

        verify(repository, times(1)).addReceiver(first.capture(),
                second.capture());

        assertThat(first.getValue().getAddress(), is("Cairo"));


    }

    @Test
    public void testAllReceivers() {

        ArgumentCaptor<Boolean> first = ArgumentCaptor.forClass(Boolean.class);
        ArgumentCaptor<String> second = ArgumentCaptor.forClass(String.class);


        viewModel.setForceUpdate(true);


        viewModel.getAllReceivers().observeForever(mock(Observer.class));

        verify(repository, times(1)).getAllReceiversTest(first.capture(),
                second.capture());

        assertThat(first.getValue(), is(true));


    }


}