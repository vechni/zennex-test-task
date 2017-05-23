package com.zennex.task.module.frg_list;

import com.zennex.task.common.mvp.MvpBaseView;
import com.zennex.task.model.ListItem;

import java.util.List;

public interface ListContract {

    public interface View extends MvpBaseView {

        void showList(List<ListItem> list);

        void refreshChangedItem(int position);

        void refreshRemovedItem(int position);

        void showMessage(String message);
    }

    public interface Presenter {

        void loadData();

        void updateItem(ListItem item, int position);
    }
}
