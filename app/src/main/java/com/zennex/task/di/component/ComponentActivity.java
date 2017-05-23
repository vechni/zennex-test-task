package com.zennex.task.di.component;

import android.app.Activity;

import com.zennex.task.di.module.ModuleActivity;
import com.zennex.task.di.scope.ScopeActivity;
import com.zennex.task.module.act_navigation.NavigationActivity;
import com.zennex.task.module.act_splash.SplashActivity;

import javax.inject.Named;

import dagger.Component;

@ScopeActivity
@Component(dependencies = ComponentApplication.class, modules = ModuleActivity.class)
public interface ComponentActivity {

    @Named("Activity")
    Activity activity();

    void inject(NavigationActivity activity);

    void inject(SplashActivity activity);
}
