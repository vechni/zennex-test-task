package com.zennex.task.module.frg_list_dialog;

import com.zennex.task.model.ListItem;

public interface ListDialogContract {

    public interface View {

        void removeItemFromList(int position);

        void showMessage(String message);
    }

    public interface Presenter {

        void removeItem(ListItem item, int position);
    }
}
