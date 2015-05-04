package com.example.home.hackathon.api;

import java.util.List;

import retrofit.Callback;
import retrofit.http.GET;

public interface ServerApi {
    @GET("/api/item/?format=json")
    void listRepos(Callback<List<Clothing>> cb);
}