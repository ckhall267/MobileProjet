package com.example.eco_track.api;

import com.example.eco_track.model.Product;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiService {
    @GET("products/{code}")
    Call<Product> getProductByCode(@Path("code") String code);
}
