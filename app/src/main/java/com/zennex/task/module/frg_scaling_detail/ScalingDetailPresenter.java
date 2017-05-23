package com.zennex.task.module.frg_scaling_detail;

import android.content.Context;

import com.zennex.task.common.mvp.BasePresenter;
import com.zennex.task.data.DataLayer;

import javax.inject.Inject;

public class ScalingDetailPresenter extends BasePresenter<ScalingDetailContract.View> implements ScalingDetailContract.Presenter {

    public static final String TAG = "tag_scaling_detail_pres";

    private ScalingDetailContract.View view;
    @Inject DataLayer dataLayer;
    @Inject Context context;

    @Inject
    public ScalingDetailPresenter() {

    }

    // region - Lifecycle -

    @Override
    public void attachView(ScalingDetailContract.View view) {
        super.attachView(view);
        this.view = view;
        getPresenterComponent().inject(this);
    }

    // endregion


    // region - Contract -

    @Override
    public void onClickBtnZoomIn() {
        view.increaseZoom();
    }

    @Override
    public void onClickBtnZoomOut() {
        view.reduceZoom();
    }

    // endregion


    // region - Methods -

    // endregion
}