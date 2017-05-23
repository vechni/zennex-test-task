package com.zennex.task.common.utils;

public class NetworkUtils {

    // region - URL -

    public static final String URL_BACKEND = "http://quotes.zennex.ru/api/v3/";

    // endregion


    // region - Timeout -

    public static final int CONNECT_TIMEOUT = 10;
    public static final int WRITE_TIMEOUT = 10;
    public static final int READ_TIMEOUT = 10;
    public static final int WAIT_TIMEOUT = 10;

    public static final int CONNECT_TIMEOUT_LONG = 60;
    public static final int WRITE_TIMEOUT_LONG = 60;
    public static final int READ_TIMEOUT_LONG = 60;
    public static final int WAIT_TIMEOUT_LONG = 30;

    // endregion


    // region - Methods -

    public static final String URL_METHOD_PARSING = "bash/quotes";

    // endregion
}
