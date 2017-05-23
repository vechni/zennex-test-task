package com.zennex.task.module.act_navigation;

import com.zennex.task.common.mvp.MvpBaseView;

public interface NavigationContract {

    public interface View extends MvpBaseView {

    }

    public interface Presenter {

        void setSelectedLanguage();
    }
}