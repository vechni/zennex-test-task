package com.zennex.task.di.component;

import android.app.Activity;
import android.support.v4.app.Fragment;

import com.zennex.task.di.module.ModuleFragment;
import com.zennex.task.di.scope.ScopeFragment;
import com.zennex.task.module.frg_list.ListFragment;
import com.zennex.task.module.frg_list_edit.ListEditFragment;
import com.zennex.task.module.frg_main.MainFragment;
import com.zennex.task.module.frg_map.MapFragment;
import com.zennex.task.module.frg_parsing.ParsingFragment;
import com.zennex.task.module.frg_scaling.ScalingMainFragment;
import com.zennex.task.module.frg_scaling_detail.ScalingDetailFragment;
import com.zennex.task.module.frg_settings.SettingsFragment;

import javax.inject.Named;

import dagger.Component;

@ScopeFragment
@Component(dependencies = ComponentApplication.class, modules = ModuleFragment.class)
public interface ComponentFragment {

    @Named("FrgActivity")
    Activity activity();

    Fragment fragment();

    void inject(ParsingFragment fragment);

    void inject(SettingsFragment fragment);

    void inject(MainFragment fragment);

    void inject(ListFragment fragment);

    void inject(ListEditFragment fragment);

    void inject(ScalingMainFragment fragment);

    void inject(ScalingDetailFragment fragment);

    void inject(MapFragment fragment);
}
