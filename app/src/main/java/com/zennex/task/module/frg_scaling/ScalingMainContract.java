package com.zennex.task.module.frg_scaling;

import com.zennex.task.common.mvp.MvpBaseView;

public interface ScalingMainContract {

    public interface View extends MvpBaseView {

        void pickImageFromGallery();

        void pickImageFromCamera();
    }


    public interface Presenter {

        void onClickPickImageGallery();

        void onClickPickImageCamera();
    }
}