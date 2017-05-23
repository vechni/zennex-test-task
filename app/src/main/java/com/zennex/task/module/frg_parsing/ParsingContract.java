package com.zennex.task.module.frg_parsing;

import com.zennex.task.common.mvp.MvpBaseView;
import com.zennex.task.model.ParsingDetail;

import java.util.List;

public interface ParsingContract {

    public interface View extends MvpBaseView {

        void startAnimationRefreshData();

        void finishAnimationRefreshData();

        void showListParsing(List<ParsingDetail> list);

        void startWaitDialog();

        void finishWaitDialog();

        void showMessage(String message);
    }

    public interface Presenter {

        void refreshData();
    }
}
