package com.zennex.task.di.module.data;

import android.content.Context;

import com.zennex.task.data.preference.Preferences;
import com.zennex.task.data.preference.PreferencesImpl;
import com.zennex.task.data.preference.PreferencesRxImpl;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class ModulePreferences {

    @Provides
    @Singleton
    Preferences.ImpPref providesPreferencesImpl(Context context) {
        return new PreferencesImpl(context);
    }

    @Provides
    @Singleton
    Preferences.RxPref PreferencesRxImpl(Context context) {
        return new PreferencesRxImpl(context);
    }
}
