package com.zennex.task.module.act_splash;

import android.content.Context;

import com.zennex.task.common.mvp.BasePresenter;
import com.zennex.task.data.DataLayer;

import javax.inject.Inject;

public class SplashPresenter extends BasePresenter<SplashContract.View> implements SplashContract.Presenter {

    public static final String TAG = "tag_splash_pres";

    private SplashContract.View view;
    @Inject DataLayer dataLayer;
    @Inject Context context;

    @Inject
    public SplashPresenter() {

    }

    // region - Lifecycle -

    @Override
    public void attachView(SplashContract.View view) {
        super.attachView(view);
        this.view = view;
        getPresenterComponent().inject(this);
    }

    @Override
    public void onStart() {
        super.onStart();
        view.navigateToMainScreen();
    }

    // endregion
}