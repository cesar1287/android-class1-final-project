package com.github.cesar1287.class1dhfinalproject.features.home.repository

import com.github.cesar1287.class1dhfinalproject.api.ApiService
import com.github.cesar1287.class1dhfinalproject.base.BaseRepository
import com.github.cesar1287.class1dhfinalproject.utils.ResponseApi

class HomeRepository : BaseRepository() {

    suspend fun getNowPlayingMovies(): ResponseApi {
        return safeApiCall {
            ApiService.tmdbApi.getNowPlayingMovies()
        }
    }

    suspend fun getPopularMovies(): ResponseApi {
        return safeApiCall {
            ApiService.tmdbApi.getPopularMovies()
        }
    }
}