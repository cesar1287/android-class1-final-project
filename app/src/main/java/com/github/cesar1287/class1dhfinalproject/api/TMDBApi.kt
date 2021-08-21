package com.github.cesar1287.class1dhfinalproject.api

import com.github.cesar1287.class1dhfinalproject.model.Movie
import com.github.cesar1287.class1dhfinalproject.model.NowPlaying
import com.github.cesar1287.class1dhfinalproject.model.Popular
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface TMDBApi {

    @GET("movie/now_playing")
    suspend fun getNowPlayingMovies(): Response<NowPlaying>

    @GET("movie/popular")
    suspend fun getPopularMovies(): Response<Popular>

    @GET("movie/{movie_id}")
    suspend fun getMovieById(
        @Path("movie_id") movieId: Int
    ): Response<Movie>

    @POST("movie/save")
    suspend fun saveMovie(
        @Body movie: Movie
    ): Response<ResponseBody>
}