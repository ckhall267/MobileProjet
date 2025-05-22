package com.example.eco_track.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static final String BASE_URL = "http://10.0.2.2:8080/"; // URL pour l'émulateur Android
    private static final String OPEN_FOOD_FACTS_URL = "https://world.openfoodfacts.org/";
    
    private static Retrofit retrofit = null;
    private static Retrofit openFoodFactsRetrofit = null;
    
    public static Retrofit getClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    public static OpenFoodFactsService getOpenFoodFactsService() {
        if (openFoodFactsRetrofit == null) {
            openFoodFactsRetrofit = new Retrofit.Builder()
                    .baseUrl(OPEN_FOOD_FACTS_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return openFoodFactsRetrofit.create(OpenFoodFactsService.class);
    }

    public static AuthApiService getAuthApiService() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit.create(AuthApiService.class);
    }
}
