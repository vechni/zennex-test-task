package com.zennex.task.data.rest;

import com.zennex.task.common.utils.NetworkUtils;
import com.zennex.task.model.Parsing;

import io.reactivex.Observable;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RestApi {

    @GET(NetworkUtils.URL_METHOD_PARSING)
    Observable<Response<Parsing>> requestParsing(@Query("sort") String time);
}






