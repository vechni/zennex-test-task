package com.zennex.task.module.frg_scaling;

import android.content.Context;

import com.zennex.task.common.mvp.BasePresenter;
import com.zennex.task.data.DataLayer;

import javax.inject.Inject;

public class ScalingMainPresenter extends BasePresenter<ScalingMainContract.View> implements ScalingMainContract.Presenter {

    public static final String TAG = "tag_scaling_main_pres";

    private ScalingMainContract.View view;
    @Inject DataLayer dataLayer;
    @Inject Context context;

    @Inject
    public ScalingMainPresenter() {

    }

    // region - Lifecycle -

    @Override
    public void attachView(ScalingMainContract.View view) {
        super.attachView(view);
        this.view = view;
        getPresenterComponent().inject(this);
    }

    // endregion


    // region - Contract -

    @Override
    public void onClickPickImageGallery() {
        view.pickImageFromGallery();
    }

    @Override
    public void onClickPickImageCamera() {
        view.pickImageFromCamera();
    }

    // endregion


    // region - Methods -

    // endregion
}