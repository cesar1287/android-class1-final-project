package com.github.cesar1287.class1dhfinalproject.api

import com.github.cesar1287.class1dhfinalproject.model.NowPlaying
import retrofit2.Response
import retrofit2.http.GET

interface TMDBApi {

    @GET("movie/now_playing")
    suspend fun getNowPlayingMovies(): Response<NowPlaying>
}