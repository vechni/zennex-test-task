package com.zennex.task.module.frg_parsing;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zennex.task.R;
import com.zennex.task.common.interfaces.OnBackPressedListener;
import com.zennex.task.common.mvp.BaseFragment;
import com.zennex.task.model.ParsingDetail;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import jp.wasabeef.recyclerview.adapters.AlphaInAnimationAdapter;
import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter;

public class ParsingFragment extends BaseFragment implements ParsingContract.View, OnBackPressedListener {

    public static final String TAG = "tag_parsing_frg";

    @BindView(R.id.frg_parsing_rv) RecyclerView rvParsing;
    @BindView(R.id.frg_parsing_swipe_container) SwipeRefreshLayout containerRecycler;

    @Inject ParsingPresenter presenter;
    private ParsingRouter router;

    // region - Lifecycle -

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        router = (ParsingRouter) activity;
        getFragmentComponent().inject(this);
        presenter.attachView(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View root;
        root = inflater.inflate(R.layout.frg_parsing, container, false);

        ButterKnife.bind(this, root);

        initScreen();

        return root;
    }

    @Override
    public void onStart() {
        super.onStart();
        presenter.onStart();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        presenter.detachView();
    }

    // endregion


    // region - Event handlers -

    @Override
    public void onBackPressed() {
        router.navigateToMainScreen();
    }

    // endregion


    // region - Contract -

    @Override
    public void showListParsing(List<ParsingDetail> list) {
        ParsingAdapter parsingAdapter = new ParsingAdapter(activity, list);
        AlphaInAnimationAdapter animationAdapter = new AlphaInAnimationAdapter(parsingAdapter);
        ScaleInAnimationAdapter adapter = new ScaleInAnimationAdapter(animationAdapter);

        rvParsing.setLayoutManager(new LinearLayoutManager(activity));
        rvParsing.setAdapter(adapter);
    }

    @Override
    public void startAnimationRefreshData() { containerRecycler.setRefreshing(true); }

    @Override
    public void finishAnimationRefreshData() {
        containerRecycler.setRefreshing(false);
    }

    @Override
    public void startWaitDialog() {
        String message = activity.getString(R.string.message_loading);
        openWaitDialog(message, null);
    }

    @Override
    public void finishWaitDialog() {
        closeWaitDialog();
    }

    @Override
    public void showMessage(String message) {
        showToastShort(message);
    }

    // endregion


    // region - Methods -

    private void initScreen() {
        setTextTitleToolbar(getString(R.string.title_parsing));

        containerRecycler.setOnRefreshListener(() -> presenter.refreshData());
        containerRecycler.setColorSchemeResources(
                R.color.refresh_progress_1,
                R.color.refresh_progress_2,
                R.color.refresh_progress_3,
                R.color.refresh_progress_4);
    }

    // endregion
}

