package com.zennex.task.module.frg_settings;

import android.content.Context;

import com.zennex.task.common.interfaces.Language;
import com.zennex.task.common.mvp.BasePresenter;
import com.zennex.task.data.DataLayer;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class SettingsPresenter extends BasePresenter<SettingsContract.View> implements SettingsContract.Presenter {

    public static final String TAG = "tag_settings_pres";

    private SettingsContract.View view;
    @Inject DataLayer dataLayer;
    @Inject Context context;

    @Inject
    public SettingsPresenter() {

    }

    // region - Lifecycle -

    @Override
    public void attachView(SettingsContract.View view) {
        super.attachView(view);
        this.view = view;
        getPresenterComponent().inject(this);
    }

    @Override
    public void onStart() {
        super.onStart();
        initSettings();
    }

    // endregion


    // region - Contract -

    @Override
    public void saveSettings(@Language int code) {
        unsubscribeOnDestroy(saveLanguage(code));
    }

    @Override
    public void initSettings() {
        unsubscribeOnDestroy(getLanguage());
    }

    // endregion


    // region - Methods -

    private Disposable saveLanguage(@Language int code) {
        return dataLayer.pref.saveLanguage(code)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(result -> {
                    view.refreshLanguageInApp();
                }, error -> {
                });
    }

    private Disposable getLanguage() {
        return dataLayer.pref.getLanguage()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(result -> {
                    view.showSetupSettings(result);
                }, error -> {
                });
    }

    // endregion
}