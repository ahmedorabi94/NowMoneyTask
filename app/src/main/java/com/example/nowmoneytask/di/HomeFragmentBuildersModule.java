package com.example.nowmoneytask.di;

import com.example.nowmoneytask.ui.AddDialogFragment;
import com.example.nowmoneytask.ui.ReceiversFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class HomeFragmentBuildersModule {

    @ContributesAndroidInjector
    abstract ReceiversFragment contributeReceiversFragment();

    @ContributesAndroidInjector
    abstract AddDialogFragment contributeAddDialogFragment();
}
