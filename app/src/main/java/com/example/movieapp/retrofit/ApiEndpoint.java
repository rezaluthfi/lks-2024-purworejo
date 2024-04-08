package com.example.movieapp.retrofit;

import com.example.movieapp.MainModel;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiEndpoint {
    @GET("data.php")
    Call<MainModel> getData();

}
