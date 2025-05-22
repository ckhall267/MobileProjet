package com.example.eco_track.api;

import com.example.eco_track.model.OpenFoodFactsProduct;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface OpenFoodFactsService {
    @GET("api/v0/product/{barcode}.json")
    Call<OpenFoodFactsResponse> getProduct(@Path("barcode") String barcode);

    class OpenFoodFactsResponse {
        public OpenFoodFactsProduct product;
    }
} 