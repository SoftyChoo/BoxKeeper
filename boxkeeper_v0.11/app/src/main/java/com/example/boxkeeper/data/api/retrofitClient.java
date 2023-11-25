package com.example.boxkeeper.data.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class retrofitClient {
    private static final String BASE_URL = "https://info.sweettracker.co.kr/";

    private static Retrofit retrofit;

    public static SweetTrackerService getService() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        return retrofit.create(SweetTrackerService.class);
    }
}