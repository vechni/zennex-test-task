package com.zennex.task.model;

import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;

public class ParsingDetail {

    @SerializedName("id") private int id;
    @SerializedName("description") private String description;
    @SerializedName("time") private String time;
    @SerializedName("rating") private int rating;

    public ParsingDetail(JsonObject jItem) {
        this.id = jItem.get("id").getAsInt();
        this.description = jItem.get("description").getAsString();
        this.time = jItem.get("time").getAsString();
        this.rating = jItem.get("rating").getAsInt();
    }

    // region - get/set -

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    // endregion
}
