package com.zennex.task.di.module.data;

import android.content.Context;

import com.zennex.task.common.utils.DatabaseUtils;
import com.zennex.task.data.repository.RepListItem;
import com.zennex.task.data.repository.realm.RepListItemRealm;
import com.zennex.task.model.ListItem;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.rx.RealmObservableFactory;

@Module
public class ModuleRepository {

    public ModuleRepository(Context context) {
        Realm.init(context);

        RealmConfiguration realmConfig = new RealmConfiguration.Builder()
                .name(DatabaseUtils.DATABASE_NAME)
                .schemaVersion(DatabaseUtils.DATABASE_VERSION)
                .deleteRealmIfMigrationNeeded()
                .rxFactory(new RealmObservableFactory())
                .build();

        Realm.setDefaultConfiguration(realmConfig);
    }

    @Provides
    @Singleton
    RepListItem<ListItem> providesRepUser() {
        return new RepListItemRealm();
    }
}
