package com.zennex.task.model;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;

import java.util.List;

public class Parsing {

    @SerializedName("total") private int total;
    @SerializedName("last") private String last;
    @SerializedName("quotes") private List<ParsingDetail> quotes;

    public Parsing(JsonObject jItem) {
        this.total = jItem.get("total").getAsInt();
        this.last = jItem.get("last").getAsString();
        this.quotes = new Gson().fromJson(jItem.get("quotes").getAsJsonArray(), new TypeToken<List<ParsingDetail>>() {
        }.getType());
    }

    // region - get/set -

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public String getLast() {
        return last;
    }

    public void setLast(String last) {
        this.last = last;
    }

    public List<ParsingDetail> getQuotes() {
        return quotes;
    }

    public void setQuotes(List<ParsingDetail> quotes) {
        this.quotes = quotes;
    }

    // endregion
}
