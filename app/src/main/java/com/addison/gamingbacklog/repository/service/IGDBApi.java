package com.addison.gamingbacklog.repository.service;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface IGDBApi {
    @POST("games")
    Call<List<Game>> getGames(@Body String query);
}
