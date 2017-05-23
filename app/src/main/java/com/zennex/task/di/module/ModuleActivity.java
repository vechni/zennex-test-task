package com.zennex.task.di.module;

import android.app.Activity;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

@Module
public class ModuleActivity {

    private final Activity activity;

    public ModuleActivity(Activity activity) {
        this.activity = activity;
    }

    @Provides
    @Named("Activity")
    Activity providesActivity() {
        return activity;
    }
}
