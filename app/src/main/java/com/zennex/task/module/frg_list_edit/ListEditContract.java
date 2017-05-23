package com.zennex.task.module.frg_list_edit;

import com.zennex.task.common.mvp.MvpBaseView;
import com.zennex.task.model.ListItem;

public interface ListEditContract {

    public interface View extends MvpBaseView {

        void goBackScreen();

        void showMessage(String message);
    }

    public interface Presenter {

        void saveChangedItem(ListItem item);
    }
}
