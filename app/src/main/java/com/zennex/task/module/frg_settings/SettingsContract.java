package com.zennex.task.module.frg_settings;

import com.zennex.task.common.interfaces.Language;
import com.zennex.task.common.mvp.MvpBasePresenter;
import com.zennex.task.common.mvp.MvpBaseView;

public interface SettingsContract {

    public interface View extends MvpBaseView {

        void showSetupSettings(@Language int code);

        void refreshLanguageInApp();
    }


    public interface Presenter extends MvpBasePresenter<View> {

        void saveSettings(@Language int code);

        void initSettings();
    }
}
