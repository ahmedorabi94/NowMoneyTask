package com.example.nowmoneytask.di;


import android.app.Application;

import androidx.room.Room;

import com.example.nowmoneytask.api.ApiInterface;
import com.example.nowmoneytask.repository.db.TaskDao;
import com.example.nowmoneytask.repository.db.TaskDb;
import com.example.nowmoneytask.util.LiveDataCallAdapterFactory;

import java.io.File;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module(includes = ViewModelModule.class)
public class TaskModule {


    @Singleton
    @Provides
    OkHttpClient provideHttpClient(Application application) {

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        File httpCacheDirectory = new File(application.getCacheDir(), "http-cache");
        int cacheSize = 10 * 1024 * 1024; // 10 MiB
        Cache cache = new Cache(httpCacheDirectory, cacheSize);

        return new OkHttpClient.Builder()
                .addInterceptor(logging)
             //   .cache(cache)
                .build();


    }


    @Singleton
    @Provides
    ApiInterface provideRetrofit(OkHttpClient client) {
        return new Retrofit.Builder()
                .baseUrl(ApiInterface.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(new LiveDataCallAdapterFactory())
                .client(client)
                .build()
                .create(ApiInterface.class);

    }



    @Singleton
    @Provides
    TaskDb provideDb(Application application) {
        return Room.databaseBuilder(application, TaskDb.class, "users.db")
                .fallbackToDestructiveMigration()
                .build();
    }


    @Singleton
    @Provides
    TaskDao provideMovieDao(TaskDb taskDb) {
        return taskDb.taskDao();
    }


}
