package com.zennex.task.data.repository;

import io.reactivex.Observable;
import io.realm.RealmObject;

public interface RepListItem<T extends RealmObject> extends Repository<T> {

    Observable<T> fetchById(String id);
}
