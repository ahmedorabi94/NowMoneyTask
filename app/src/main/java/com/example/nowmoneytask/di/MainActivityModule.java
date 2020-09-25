package com.example.nowmoneytask.di;


import com.example.nowmoneytask.ui.HomeActivity;
import com.example.nowmoneytask.ui.MainActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class MainActivityModule {


    @ContributesAndroidInjector(modules = FragmentBuildersModule.class)
    abstract MainActivity contributeMainActivity();


    @ContributesAndroidInjector(modules = HomeFragmentBuildersModule.class)
    abstract HomeActivity contributeHomeActivity();

}
