package com.github.cesar1287.class1dhfinalproject.api

import com.github.cesar1287.class1dhfinalproject.model.NowPlaying
import com.github.cesar1287.class1dhfinalproject.model.Popular
import retrofit2.Response
import retrofit2.http.GET

interface TMDBApi {

    @GET("movie/now_playing?api_key=3fdab48e2bddf5d597050debe64abb1c")
    suspend fun getNowPlayingMovies(): Response<NowPlaying>

    @GET("movie/popular?api_key=3fdab48e2bddf5d597050debe64abb1c")
    suspend fun getPopularMovies(): Response<Popular>
}