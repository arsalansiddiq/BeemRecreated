package com.example.arsalansiddiq.beem.utils;

/**
 * Created by arsalansiddiq on 1/16/18.
 * Attaching Base URL and creating Retrofit Request Interface
 */

public class ApiUtils {

    private ApiUtils() {
    }

    public static NetworkRequestInterfaces getConnection() {
        return RetrofitClient.getRetrofitClient(Constants.BASE_URL).create(NetworkRequestInterfaces.class);
    }

}
