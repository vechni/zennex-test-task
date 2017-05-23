package com.zennex.task.module.frg_scaling_detail;

import com.zennex.task.common.mvp.MvpBasePresenter;
import com.zennex.task.common.mvp.MvpBaseView;

public interface ScalingDetailContract {

    public interface View extends MvpBaseView {

        void reduceZoom();

        void increaseZoom();
    }


    public interface Presenter extends MvpBasePresenter<View> {

        void onClickBtnZoomIn();

        void onClickBtnZoomOut();
    }
}
