package com.example.nowmoneytask.di;


import android.app.Application;

import com.example.nowmoneytask.MyApp;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjectionModule;

@Singleton
@Component(modules = {TaskModule.class, AndroidInjectionModule.class, MainActivityModule.class})
public interface TaskComponent {


    void inject(MyApp myApp);

    @Component.Builder
    interface Builder {

        @BindsInstance
        Builder application(Application application);


        TaskComponent build();
    }

}
