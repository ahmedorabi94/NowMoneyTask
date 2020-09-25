package com.example.nowmoneytask.di;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;


import com.example.nowmoneytask.viewmodel.AddDialogViewModel;
import com.example.nowmoneytask.viewmodel.LoginViewModel;
import com.example.nowmoneytask.viewmodel.ReceiversViewModel;
import com.example.nowmoneytask.viewmodel.TaskViewModelFactory;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class ViewModelModule {


    @Binds
    @IntoMap
    @ViewModelKey(LoginViewModel.class)
    abstract ViewModel bindLoginViewModel(LoginViewModel loginViewModel);


    @Binds
    @IntoMap
    @ViewModelKey(ReceiversViewModel.class)
    abstract ViewModel bindReceiversViewModel(ReceiversViewModel receiversViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(AddDialogViewModel.class)
    abstract ViewModel bindAddDialogViewModel(AddDialogViewModel addDialogViewModel);


    @Binds
    abstract ViewModelProvider.Factory bindViewModelFactory(TaskViewModelFactory factory);


}
