package com.zennex.task.module.frg_main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.zennex.task.R;
import com.zennex.task.common.interfaces.OnBackPressedListener;
import com.zennex.task.common.mvp.BaseFragment;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainFragment extends BaseFragment implements MainContract.View, OnBackPressedListener {

    public static final String TAG = "tag_main_frg";

    @Inject MainPresenter presenter;
    private MainRouter router;

    // region - Lifecycle -

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        router = (MainRouter) activity;
        getFragmentComponent().inject(this);
        presenter.attachView(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View root;

        setHasOptionsMenu(true);

        root = inflater.inflate(R.layout.frg_main, container, false);

        ButterKnife.bind(this, root);

        initScreen();

        return root;
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        MenuItem item_menu = menu.findItem(R.id.menu_settings);
        item_menu.setVisible(true);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        presenter.detachView();
    }

    // endregion


    // region - Event handlers -

    @OnClick(R.id.frg_main_btn_map)
    public void clickBtnMap(View view) {
        router.navigateToMapScreen();
    }

    @OnClick(R.id.frg_main_btn_parsing)
    public void clickBtnParsing(View view) {
        router.navigateToParsingScreen();
    }

    @OnClick(R.id.frg_main_btn_scaling)
    public void clickBtnScaling(View view) {
        router.navigateToScalingMainScreen();
    }

    @OnClick(R.id.frg_main_btn_list)
    public void clickBtnlist(View view) {
        router.navigateToListScreen();
    }

    @Override
    public void onBackPressed() {
        router.finish();
    }

    // endregion


    // region - Contract -

    // endregion


    // region - Methods -

    private void initScreen() {
        setTextTitleToolbar(getString(R.string.title_main));
    }

    // endregion
}

