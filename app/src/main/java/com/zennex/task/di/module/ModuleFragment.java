package com.zennex.task.di.module;

import android.app.Activity;
import android.support.v4.app.Fragment;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

@Module
public class ModuleFragment {

    private final Fragment fragment;

    public ModuleFragment(Fragment fragment) {
        this.fragment = fragment;
    }

    @Provides
    @Named("FrgActivity")
    Activity providesActivity() {
        return fragment.getActivity();
    }

    @Provides
    Fragment providesFragment() {
        return fragment;
    }
}
