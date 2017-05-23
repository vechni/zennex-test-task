package com.zennex.task.di.module.data;

import android.content.Context;

import com.zennex.task.data.rest.RestApi;
import com.zennex.task.data.rest.RestClient;
import com.zennex.task.data.rest.RestClientImpl;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module(includes = ModuleRetrofit.class)
public class ModuleRest {

    @Provides
    @Singleton
    RestClient providesRestClient(Context context, RestApi restApi) {
        return new RestClientImpl(context, restApi);
    }
}
