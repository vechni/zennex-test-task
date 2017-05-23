package com.zennex.task.data.rest;

import com.zennex.task.model.Parsing;

import io.reactivex.Observable;

public interface RestClient {

    Observable<Boolean> isNetworkConnectionAsync();

    boolean isNetworkConnection();

    Observable<Parsing> requestParsing(String time);
}
