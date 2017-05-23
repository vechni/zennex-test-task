package com.zennex.task.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.UUID;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class ListItem extends RealmObject implements Parcelable {

    @PrimaryKey private String id = null;
    private String name;
    private boolean state = false;

    public ListItem() {

    }

    public ListItem(String id, boolean state, String name) {
        this.id = id;
        this.name = name;
        this.state = state;
    }

    // region - get/set -

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // endregion


    // region - Parcelable -

    protected ListItem(Parcel in) {
        id = in.readString();
        name = in.readString();
        state = in.readByte() != 0;
    }

    public static final Creator<ListItem> CREATOR = new Creator<ListItem>() {
        @Override
        public ListItem createFromParcel(Parcel in) {
            return new ListItem(in);
        }

        @Override
        public ListItem[] newArray(int size) {
            return new ListItem[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
        dest.writeByte((byte) (state ? 1 : 0));
    }

    // endregion

    // region - Methods public -

    public void validationId() {
        if (id == null) {
            id = UUID.randomUUID().toString();
        }
    }

    // endregion
}
