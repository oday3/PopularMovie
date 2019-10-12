package com.am.popularmoviesstageone.network;


import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.am.popularmoviesstageone.util.CONST.BASE_IMDB_URL;

public class APIClient {

    private static Retrofit retrofit = null;

    public static Retrofit getClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder().baseUrl(BASE_IMDB_URL).addConverterFactory(GsonConverterFactory.create()).build();
        }
        return retrofit;
    }
    public ApiRequests getApiService() {
        return APIClient.getClient().create(ApiRequests.class);
    }
}
