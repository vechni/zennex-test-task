package com.zennex.task.di.component;

import android.content.Context;

import com.zennex.task.data.DataLayer;
import com.zennex.task.di.module.ModuleApplication;
import com.zennex.task.di.module.data.ModulePreferences;
import com.zennex.task.di.module.data.ModuleRepository;
import com.zennex.task.di.module.data.ModuleRest;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {ModuleApplication.class, ModulePreferences.class, ModuleRest.class, ModuleRepository.class})
public interface ComponentApplication {

    Context context();

    DataLayer dataLayer();
}

