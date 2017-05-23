package com.zennex.task.module.frg_list_edit;

import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.zennex.task.R;
import com.zennex.task.common.interfaces.OnBackPressedListener;
import com.zennex.task.common.mvp.BaseFragment;
import com.zennex.task.common.utils.BundleUtils;
import com.zennex.task.model.ListItem;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ListEditFragment extends BaseFragment implements ListEditContract.View, OnBackPressedListener {

    public static final String TAG = "tag_list_edit_frg";

    @Inject ListEditPresenter presenter;
    private ListEditRouter router;

    @BindView(R.id.frg_list_et_hint) TextView txtHelp;
    @BindView(R.id.frg_list_et_name) EditText etName;

    private ListItem item = null;
    private boolean isAdd;
    private String oldNameItem = null;

    public static ListEditFragment newInstance(ListItem item, boolean isAdd) {
        ListEditFragment frg = new ListEditFragment();

        Bundle bundle = new Bundle();
        bundle.putParcelable(BundleUtils.LIST_ITEM_KEY, item);
        bundle.putBoolean(BundleUtils.IS_ADD_FLAG_KEY, isAdd);
        frg.setArguments(bundle);

        return frg;
    }

    // region - Lifecycle -

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getFragmentComponent().inject(this);
        presenter.attachView(this);
        router = (ListEditRouter) activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View root;
        root = inflater.inflate(R.layout.frg_list_edit, container, false);

        ButterKnife.bind(this, root);

        Bundle bundle = getArguments();
        if (bundle != null) {
            item = bundle.getParcelable(BundleUtils.LIST_ITEM_KEY);
            isAdd = bundle.getBoolean(BundleUtils.IS_ADD_FLAG_KEY);
        }

        initScreen();

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        presenter.detachView();
    }

    // endregion


    // region - Event handlers -

    @OnClick(R.id.frg_list_btn_cancel)
    public void clickBtnCancel(View view) {
        router.navigateToListScreen();
    }

    @OnClick(R.id.frg_list_btn_commit)
    public void clickBtnCommit(View view) {
        saveItem();
    }

    @Override
    public void onBackPressed() {
        boolean isEmptyName = etName.getText().toString().isEmpty();
        boolean isNotChangedName = etName.getText().toString().equals(oldNameItem);

        if (isAdd && isEmptyName) {
            showWarningDialog();
        } else if (!isAdd && isNotChangedName) {
            showWarningDialog();
        } else {
            router.navigateToListScreen();
        }
    }

    // endregion

    // region - Contract -

    @Override
    public void showMessage(String message) {
        showToastShort(message);
    }

    @Override
    public void goBackScreen() {
        router.navigateToListScreen();
    }

    // endregion

    // region - Methods -

    private void initScreen() {
        if (isAdd) {
            setHintAdd();
        } else {
            setHintEdit();
            oldNameItem = item.getName();
        }
    }

    private void showWarningDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle(getString(R.string.txt_warning));
        builder.setMessage(getString(R.string.warning_message));
        builder.setCancelable(true);
        builder.setPositiveButton(getString(R.string.txt_yes), (dialog, which) -> saveItem());
        builder.setNegativeButton(getString(R.string.txt_no), (dialog, which) -> router.navigateToListScreen());

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void saveItem() {
        if (item == null) item = new ListItem();

        String name = etName.getText().toString();
        item.setName(name);
        presenter.saveChangedItem(item);
    }

    private void setHintAdd() {
        txtHelp.setText(getString(R.string.text_add));
        etName.setText(getString(R.string.empty_string));
        etName.setHint(getString(R.string.text_hint_name));
        setTextTitleToolbar(getString(R.string.title_list_add));
    }

    private void setHintEdit() {
        txtHelp.setText(getString(R.string.text_edit));
        etName.setText(item.getName());
        setTextTitleToolbar(getString(R.string.title_list_edit));
    }

    // endregion
}