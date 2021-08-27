package com.github.cesar1287.class1dhfinalproject.features.moviedetail.repository

import com.github.cesar1287.class1dhfinalproject.api.ApiService
import com.github.cesar1287.class1dhfinalproject.base.BaseRepository
import com.github.cesar1287.class1dhfinalproject.utils.ResponseApi

class MovieDetailRepository: BaseRepository() {

    suspend fun getMovieById(movieId: Int): ResponseApi {
        return safeApiCall {
            ApiService.tmdbApi.getMovieById(movieId)
        }
    }
}