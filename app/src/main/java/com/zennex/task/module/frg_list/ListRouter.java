package com.zennex.task.module.frg_list;

import com.zennex.task.model.ListItem;

public interface ListRouter {

    void navigateToMainScreen();

    void navigateToListEditScreen();

    void navigateToListEditScreen(ListItem item);
}
