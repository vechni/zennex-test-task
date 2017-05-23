package com.zennex.task.model;

public class DrawerItem {

    private int id;
    private String name;
    private int imgResID;

    public DrawerItem(int id, String name, int imgResID) {
        this.id = id;
        this.name = name;
        this.imgResID = imgResID;
    }

    // region - get/set -

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImgResID() {
        return imgResID;
    }

    public void setImgResID(int imgResID) {
        this.imgResID = imgResID;
    }

    // endregion
}
