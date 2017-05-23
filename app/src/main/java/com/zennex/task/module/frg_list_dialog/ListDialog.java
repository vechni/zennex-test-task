package com.zennex.task.module.frg_list_dialog;

import android.app.Activity;
import android.graphics.Point;
import android.support.v7.widget.Toolbar;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.zennex.task.R;
import com.zennex.task.model.ListItem;
import com.zennex.task.module.frg_list.ListContract;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class ListDialog implements ListDialogContract.View {

    public static final String TAG = "tag_list_dialog_window";

    private PopupWindow window;
    private ListItem item;
    private int position;

    private Activity activity;
    private ListDialogRouter router;
    private ListContract.View view;
    private ListDialogPresenter presenter;

    // region - Lifecycle -

    public static ListDialog newInstance(Activity activity, ListDialogPresenter listDialogPresenter,
                                         ListContract.View view, ListItem item, int position) {
        return new ListDialog(activity, listDialogPresenter, view, item, position);
    }

    public ListDialog(Activity activity, ListDialogPresenter presenter, ListContract.View view, ListItem item, int position) {

        this.view = view;
        this.presenter = presenter;
        this.presenter.attachView(this);

        this.activity = activity;
        this.router = (ListDialogRouter) activity;

        this.item = item;
        this.position = position;

        onCreateView();
    }

    public void onCreateView() {
        LayoutInflater layoutInflater = (LayoutInflater) activity.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        View popupView = layoutInflater.inflate(R.layout.dialog_list, null);
        window = new PopupWindow(popupView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        ButterKnife.bind(this, popupView);

        Toolbar toolbar = (Toolbar) activity.findViewById(R.id.toolbar);
        Display display = activity.getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int height = size.y;
        window.showAsDropDown(toolbar, 0, height / 5);
        window.setTouchable(true);
        window.setFocusable(true);
        window.update();

        window.setOnDismissListener(() -> presenter.detachView());
    }

    // endregion


    // region - Event handlers -

    @OnClick(R.id.dialog_list_btn_edit)
    public void clickBtnEdit(View view) {
        closeDialog();
        router.navigateToListEditScreen(item);
    }

    @OnClick(R.id.dialog_list_btn_delete)
    public void clickBtnDelete(View view) {
        presenter.removeItem(item, position);
    }

    // endregion


    // region - Contract -

    @Override
    public void removeItemFromList(int position) {
        closeDialog();
        view.refreshRemovedItem(position);
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(activity, message, Toast.LENGTH_LONG).show();
    }

    // endregion

    // region - Methods -

    private void closeDialog() {
        window.dismiss();
    }

    // endregion
}
