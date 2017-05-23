package com.zennex.task.data.rest;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.zennex.task.common.utils.NetworkUtils;
import com.zennex.task.model.Parsing;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import retrofit2.Response;

public class RestClientImpl implements RestClient {

    private RestApi restApi;
    private ConnectivityManager connMgr;

    public RestClientImpl(Context context, RestApi restApi) {
        this.restApi = restApi;
        connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
    }


    // region - Interface -

    @Override
    public Observable<Boolean> isNetworkConnectionAsync() {
        return Observable.just(isNetworkConnection());
    }

    @Override
    public boolean isNetworkConnection() {
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }

    @Override
    public Observable<Parsing> requestParsing(String time) {
        return restApi.requestParsing(time)
                .map(response -> {
                    checkResponse(response);
                    return response.body();
                })
                .timeout(NetworkUtils.WAIT_TIMEOUT, TimeUnit.SECONDS);
    }

    // endregion


    // region - Methods -

    private void checkResponse(Response response) throws RuntimeException {
        if (!response.isSuccessful()) {
            throw new RuntimeException(String.valueOf(response.code()));
        }
    }

    // endregion
}
