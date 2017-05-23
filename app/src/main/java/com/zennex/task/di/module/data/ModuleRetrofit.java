package com.zennex.task.di.module.data;

import com.zennex.task.common.utils.NetworkUtils;
import com.zennex.task.data.rest.RestApi;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class ModuleRetrofit {

    private Retrofit retrofit;
    private OkHttpClient okHttpClient;
    private HttpLoggingInterceptor logging = null;

    public ModuleRetrofit() {
        addHttpLogging();
        initHttpClient();
        initRetrofit();
    }

    private void addHttpLogging() {
        logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
    }

    private void initHttpClient() {
        okHttpClient = new OkHttpClient().newBuilder()
                .addInterceptor(logging)
                .connectTimeout(NetworkUtils.CONNECT_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(NetworkUtils.READ_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(NetworkUtils.WRITE_TIMEOUT, TimeUnit.SECONDS)
                .build();
    }

    private void initRetrofit() {
        retrofit = new Retrofit.Builder()
                .baseUrl(NetworkUtils.URL_BACKEND)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okHttpClient)
                .build();
    }

    @Provides
    @Singleton
    RestApi providesRestApi() {
        return retrofit.create(RestApi.class);
    }
}