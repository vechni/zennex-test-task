package com.zennex.task.data;

import com.zennex.task.data.preference.Preferences;
import com.zennex.task.data.repository.RepListItem;
import com.zennex.task.data.rest.RestClient;
import com.zennex.task.model.ListItem;

import javax.inject.Inject;

public class DataLayer {

    @Inject public RestClient restApi;
    @Inject public Preferences.RxPref pref;
    @Inject public Preferences.ImpPref prefImp;
    @Inject public RepListItem<ListItem> repListItem;

    @Inject
    public DataLayer() {

    }
}
