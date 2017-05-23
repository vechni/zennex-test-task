package com.zennex.task.module.frg_main;

import android.content.Context;

import com.zennex.task.common.mvp.BasePresenter;
import com.zennex.task.data.DataLayer;

import javax.inject.Inject;

public class MainPresenter extends BasePresenter<MainContract.View> implements MainContract.Presenter {

    public static final String TAG = "tag_main_pres";

    private MainContract.View view;
    @Inject DataLayer dataLayer;
    @Inject Context context;

    @Inject
    public MainPresenter() {

    }

    // region - Lifecycle -

    @Override
    public void attachView(MainContract.View view) {
        super.attachView(view);
        this.view = view;
        getPresenterComponent().inject(this);
    }

    // endregion
}