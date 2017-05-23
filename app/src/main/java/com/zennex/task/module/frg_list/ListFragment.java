package com.zennex.task.module.frg_list;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import com.zennex.task.R;
import com.zennex.task.common.interfaces.OnBackPressedListener;
import com.zennex.task.common.interfaces.OnItemClickListener;
import com.zennex.task.common.interfaces.OnItemLongClickListener;
import com.zennex.task.common.mvp.BaseFragment;
import com.zennex.task.model.ListItem;
import com.zennex.task.module.frg_list_dialog.ListDialog;
import com.zennex.task.module.frg_list_dialog.ListDialogPresenter;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ListFragment extends BaseFragment implements ListContract.View, OnBackPressedListener {

    public static final String TAG = "tag_list_frg";

    @BindView(R.id.frg_list_rv_item) RecyclerView rvList;
    private List<ListItem> list;
    private ListAdapter adapter = null;

    @Inject ListPresenter presenter;
    @Inject ListDialogPresenter dialog;
    private ListRouter router;
    private ListContract.View viewListContract;

    // region - Lifecycle -

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        router = (ListRouter) activity;
        getFragmentComponent().inject(this);
        presenter.attachView(this);
        viewListContract = (ListContract.View) this;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View root;
        root = inflater.inflate(R.layout.frg_list, container, false);

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

    @OnClick(R.id.frg_list_btn_add)
    public void onClick(View view) {
        router.navigateToListEditScreen();
    }

    private OnItemLongClickListener itemLongClickListener = new OnItemLongClickListener() {
        @Override
        public void onLongClick(View view, int position) {
            ListDialog.newInstance(activity, dialog, viewListContract, list.get(position), position);
        }
    };

    private OnItemClickListener itemClickListener = new OnItemClickListener() {
        @Override
        public void onClick(View view, int position) {
            ListItem item = list.get(position);
            router.navigateToListEditScreen(item);
        }
    };

    private CompoundButton.OnCheckedChangeListener itemClickListenerCheckBoxItem = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            int position = (int) buttonView.getTag();
            ListItem item = list.get(position);
            presenter.updateItem(item, position);
        }
    };

    // endregion


    // region - Contract -

    @Override
    public void showList(List<ListItem> list) {

        this.list = list;
        adapter = new ListAdapter(activity, list, itemClickListener,
                itemLongClickListener, itemClickListenerCheckBoxItem);

        rvList.setLayoutManager(new LinearLayoutManager(activity));
        rvList.setAdapter(adapter);
    }

    @Override
    public void refreshChangedItem(int position) {
        adapter.notifyItemChanged(position);
    }

    @Override
    public void refreshRemovedItem(int position) {
        this.list.remove(position);
        adapter.notifyItemRemoved(position);
        //adapter.notifyDataSetChanged();
    }

    @Override
    public void showMessage(String message) {
        showToastShort(message);
    }

    // endregion


    // region - Methods -

    private void initScreen() {
        setTextTitleToolbar(getString(R.string.title_list));
    }

    // endregion
}
