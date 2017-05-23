package com.zennex.task.module.frg_map;

import android.content.Context;

import com.zennex.task.common.mvp.BasePresenter;
import com.zennex.task.data.DataLayer;

import javax.inject.Inject;

public class MapPresenter extends BasePresenter<MapContract.View> implements MapContract.Presenter {

    public static final String TAG = "tag_map_pres";

    private MapContract.View view;
    @Inject DataLayer dataLayer;
    @Inject Context context;

    // region - Lifecycle -

    @Inject
    public MapPresenter() {

    }

    @Override
    public void attachView(MapContract.View view) {
        super.attachView(view);
        this.view = view;
        getPresenterComponent().inject(this);
    }

    // endregion


    // region - Contract -

    @Override
    public void requestCoordinateLocation() {
        view.showCoordinateLocation();
    }

    // endregion


    // region - Methods -

    // endregion
}

