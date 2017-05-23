package com.zennex.task.module.frg_map;

import com.zennex.task.common.mvp.MvpBaseView;

public interface MapContract {

    public interface View extends MvpBaseView {

        void showCoordinateLocation();
    }

    public interface Presenter {

        void requestCoordinateLocation();
    }
}
