package com.github.cesar1287.class1dhfinalproject.features.moviedetail.repository

import android.app.Application
import com.github.cesar1287.class1dhfinalproject.api.ApiService
import com.github.cesar1287.class1dhfinalproject.base.BaseRepository
import com.github.cesar1287.class1dhfinalproject.database.Class1Database
import com.github.cesar1287.class1dhfinalproject.model.Movie
import com.github.cesar1287.class1dhfinalproject.utils.ResponseApi

class MovieDetailRepository(
    private val application: Application
) : BaseRepository() {

    suspend fun getMovieById(movieId: Int): ResponseApi {
        return safeApiCall {
            ApiService.tmdbApi.getMovieById(movieId)
        }
    }

    suspend fun getMovieByIdFromDb(movieId: Int) =
        Class1Database.getDatabase(application)
            .movieDao().loadMovieById(movieId)
}