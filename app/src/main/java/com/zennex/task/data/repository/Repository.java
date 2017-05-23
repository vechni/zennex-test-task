package com.zennex.task.data.repository;

import java.util.List;

import io.reactivex.Observable;
import io.realm.RealmObject;

public interface Repository<T extends RealmObject> {

    Observable<Integer> add(T item);

    Observable<Integer> add(List<T> items);

    Observable<Integer> update(T item);

    Observable<Integer> update(List<T> items);

    Observable<Integer> remove(T item);

    Observable<Integer> remove(List<T> items);

    Observable<Integer> removeAll();

    Observable<List<T>> fetchAll();
}
