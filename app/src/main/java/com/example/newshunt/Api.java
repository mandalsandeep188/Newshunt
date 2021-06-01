package com.example.newshunt;

import com.google.gson.JsonObject;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface Api {
    String BASE_URL ="https://newsapi.org/v2/";

    @GET("top-headlines?country=in&apiKey=d23fe9e55fce497c94b663b0b23b510b")
    Call<JsonObject> getHome();

    @GET("top-headlines")
    Call<JsonObject> getCategory(@Query("country") String country,
                                 @Query("category") String category,
                                 @Query("apiKey") String apiKey);
}
