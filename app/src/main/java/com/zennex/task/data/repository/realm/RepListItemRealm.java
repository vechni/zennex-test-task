package com.zennex.task.data.repository.realm;

import com.zennex.task.common.utils.DatabaseUtils;
import com.zennex.task.data.repository.RepListItem;
import com.zennex.task.model.ListItem;

import java.util.Collections;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.exceptions.Exceptions;
import io.realm.Realm;
import io.realm.RealmResults;

public class RepListItemRealm implements RepListItem<ListItem> {

    // region - Methods add -

    @Override
    public Observable<Integer> add(ListItem item) {
        return add(Collections.singletonList(item));
    }

    @Override
    public Observable<Integer> add(List<ListItem> items) {
        return Observable.just(addFunc(items));
    }

    private Integer addFunc(List<ListItem> list) {
        Realm realm = Realm.getDefaultInstance();
        try {
            copyToRealmOrUpdate(realm, list);
        } catch (Exception e) {
            realm.cancelTransaction();
            Exception exception = new Exception(DatabaseUtils.ERROR_MESSAGE_ADD_DEFAULT, e);
            throw Exceptions.propagate(exception);
        } finally {
            realm.close();
        }
        return list.size();
    }

    // endregion


    // region - Methods update -

    @Override
    public Observable<Integer> update(ListItem item) {
        return update(Collections.singletonList(item));
    }

    @Override
    public Observable<Integer> update(List<ListItem> items) {
        return Observable.just(updateFunc(items));
    }

    private Integer updateFunc(List<ListItem> list) {
        Realm realm = Realm.getDefaultInstance();
        try {
            copyToRealmOrUpdate(realm, list);
        } catch (Exception e) {
            realm.cancelTransaction();
            Exception exception = new Exception(DatabaseUtils.ERROR_MESSAGE_UPDATE_DEFAULT, e);
            throw Exceptions.propagate(exception);
        } finally {
            realm.close();
        }
        return list.size();
    }

    // endregion


    // region - Methods remove -

    @Override
    public Observable<Integer> remove(ListItem item) {
        return remove(Collections.singletonList(item));
    }

    @Override
    public Observable<Integer> remove(List<ListItem> items) {
        return Observable.just(removeFunc(items));
    }

    private Integer removeFunc(List<ListItem> list) {
        Realm realm = Realm.getDefaultInstance();
        try {
            removeFromRealm(realm, list);
        } catch (Exception e) {
            realm.cancelTransaction();
            Exception exception = new Exception(DatabaseUtils.ERROR_MESSAGE_REMOVE_DEFAULT, e);
            throw Exceptions.propagate(exception);
        } finally {
            realm.close();
        }
        return list.size();
    }

    @Override
    public Observable<Integer> removeAll() {
        return Observable.just(removeAllFunc());
    }

    private Integer removeAllFunc() {
        Integer size = 0;
        Realm realm = Realm.getDefaultInstance();
        try {
            size = removeAllFromRealm(realm);
        } catch (Exception e) {
            realm.cancelTransaction();
            Exception exception = new Exception(DatabaseUtils.ERROR_MESSAGE_REMOVE_DEFAULT, e);
            throw Exceptions.propagate(exception);
        } finally {
            realm.close();
        }
        return size;
    }

    // endregion


    // region - Methods query -

    @Override
    public Observable<List<ListItem>> fetchAll() {
        return Observable.just(fetchAllFunc());
    }

    private List<ListItem> fetchAllFunc() {
        List<ListItem> result = null;

        Realm realm = Realm.getDefaultInstance();
        try {
            result = fetchAllFromRealm(realm);
        } catch (Exception e) {
            Exception exception = new Exception(DatabaseUtils.ERROR_MESSAGE_QUERY_DEFAULT, e);
            throw Exceptions.propagate(exception);
        } finally {
            realm.close();
        }
        return result;
    }

    @Override
    public Observable<ListItem> fetchById(String id) {
        return Observable.just(fetchByIdFunc(id));
    }

    private ListItem fetchByIdFunc(String id) {
        ListItem result = null;

        Realm realm = Realm.getDefaultInstance();
        try {
            result = fetchByIdFromRealm(realm, id);
        } catch (Exception e) {
            Exception exception = new Exception(DatabaseUtils.ERROR_MESSAGE_QUERY_DEFAULT, e);
            throw Exceptions.propagate(exception);
        } finally {
            realm.close();
        }
        return result;
    }

    // endregion


    // region - Methods -

    private void copyToRealmOrUpdate(Realm realm, List<ListItem> items) throws Exception {
        realm.beginTransaction();
        for (ListItem item : items) {
            item.validationId();
            realm.copyToRealmOrUpdate(item);
        }
        realm.commitTransaction();
    }

    private void removeFromRealm(Realm realm, List<ListItem> items) throws Exception {
        realm.beginTransaction();
        for (ListItem item : items) {
            ListItem result = realm.where(ListItem.class).equalTo(DatabaseUtils.TABLE_COLUMN_KEY, item.getId()).findFirst();
            result.deleteFromRealm();
        }
        realm.commitTransaction();
    }

    private Integer removeAllFromRealm(Realm realm) throws Exception {
        realm.beginTransaction();
        RealmResults results = realm.where(ListItem.class).findAll();
        results.deleteAllFromRealm();
        realm.commitTransaction();
        return results.size();
    }

    private List fetchAllFromRealm(Realm realm) throws Exception {
        RealmResults results = realm.where(ListItem.class).findAll();
        return realm.copyFromRealm(results);
    }

    private ListItem fetchByIdFromRealm(Realm realm, String id) throws Exception {
        ListItem copy = realm.where(ListItem.class).equalTo(DatabaseUtils.TABLE_COLUMN_KEY, id).findFirst();
        return realm.copyFromRealm(copy);
    }

    // endregion
}
