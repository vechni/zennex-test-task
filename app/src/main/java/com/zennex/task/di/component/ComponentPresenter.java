package com.zennex.task.di.component;

import com.zennex.task.di.scope.ScopePresenter;
import com.zennex.task.module.act_navigation.NavigationPresenter;
import com.zennex.task.module.act_splash.SplashPresenter;
import com.zennex.task.module.frg_list.ListPresenter;
import com.zennex.task.module.frg_list_dialog.ListDialogPresenter;
import com.zennex.task.module.frg_list_edit.ListEditPresenter;
import com.zennex.task.module.frg_main.MainPresenter;
import com.zennex.task.module.frg_map.MapPresenter;
import com.zennex.task.module.frg_parsing.ParsingPresenter;
import com.zennex.task.module.frg_scaling.ScalingMainPresenter;
import com.zennex.task.module.frg_scaling_detail.ScalingDetailPresenter;
import com.zennex.task.module.frg_settings.SettingsPresenter;

import dagger.Component;

@ScopePresenter
@Component(dependencies = ComponentApplication.class)
public interface ComponentPresenter {

    void inject(SettingsPresenter presenter);

    void inject(ScalingDetailPresenter presenter);

    void inject(ScalingMainPresenter presenter);

    void inject(ParsingPresenter presenter);

    void inject(MapPresenter presenter);

    void inject(MainPresenter presenter);

    void inject(SplashPresenter presenter);

    void inject(NavigationPresenter presenter);

    void inject(ListPresenter presenter);

    void inject(ListDialogPresenter presenter);

    void inject(ListEditPresenter presenter);
}
