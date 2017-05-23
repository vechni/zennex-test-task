package com.zennex.task;

import android.app.Application;

import com.zennex.task.di.component.ComponentApplication;
import com.zennex.task.di.component.DaggerComponentApplication;
import com.zennex.task.di.module.ModuleApplication;
import com.zennex.task.di.module.data.ModulePreferences;
import com.zennex.task.di.module.data.ModuleRepository;
import com.zennex.task.di.module.data.ModuleRest;

public class TestApplication extends Application {

    private static ComponentApplication component;

    @Override
    public void onCreate() {
        super.onCreate();

        component = DaggerComponentApplication
                .builder()
                .moduleApplication(new ModuleApplication(this))
                .modulePreferences(new ModulePreferences())
                .moduleRest(new ModuleRest())
                .moduleRepository(new ModuleRepository(this))
                .build();
    }

    public static ComponentApplication getComponentApplication() {
        return component;
    }
}
